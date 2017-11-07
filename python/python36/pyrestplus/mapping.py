#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/1 15:50
# @Author  : boneix
import inspect
import logging
import re

import tornado.web

from pyrestplus import types
from pyrestplus.handler import RequestHandlerRegister, ResponseHandlerRegister

logging.basicConfig(level=logging.INFO)


class PyRestPlusException(Exception):
    def __init__(self, message):
        self.message = message

    def __str__(self):
        return repr(self.message)


def config(func, method, **kwparams):
    """ Decorator config function """
    path = None
    produces = None
    consumes = None
    method_types = None
    heads = None
    manual_response = None

    if len(kwparams):
        path = kwparams['_path']
        if '_produces' in kwparams:
            produces = kwparams['_produces']
        if '_consumes' in kwparams:
            consumes = kwparams['_consumes']
        if '_types' in kwparams:
            method_types = kwparams['_types']
        if '_heads' in kwparams:
            heads = kwparams['_heads']
        if '_manual_response' in kwparams:
            manual_response = kwparams['_manual_response']

    def operation(*args, **kwargs):
        return func(*args, **kwargs)

    operation.func_name = func.__name__
    operation._func_params = inspect.getfullargspec(func).args[1:]
    operation._types = method_types or [str] * len(operation._func_params)
    if len(operation._func_params) != len(operation._types):
        raise PyRestPlusException("len of %s _type is not match params's " % operation.func_name)

    operation._service_name = re.findall(r'(?<=/)\w+', path)
    operation._service_params = re.findall(r'(?<={)\w+', path)
    # regex route
    operation._query_params = re.findall(r"(?<=<)\w+", path)
    operation._method = method
    operation._produces = produces
    operation._consumes = consumes
    operation._path = path
    operation._heads = heads
    operation._manual_response = manual_response
    return operation


def get(*params, **kwparams):
    """ Decorator for config a python function like a Rest GET verb	"""

    def method(f):
        return config(f, 'GET', **kwparams)

    return method


def post(*params, **kwparams):
    """ Decorator for config a python function like a Rest POST verb """

    def method(f):
        return config(f, 'POST', **kwparams)

    return method


def put(*params, **kwparams):
    """ Decorator for config a python function like a Rest PUT verb	"""

    def method(f):
        return config(f, 'PUT', **kwparams)

    return method


def patch(*params, **kwparams):
    """ Decorator for config a python function like a Rest PATCH verb """

    def method(f):
        return config(f, 'PATCH', **kwparams)

    return method


def delete(*params, **kwparams):
    """ Decorator for config a python function like a Rest PUT verb	"""

    def method(f):
        return config(f, 'DELETE', **kwparams)

    return method


class RestHandler(tornado.web.RequestHandler):
    def get(self):
        """ Executes get method """
        self._exe('GET')

    def post(self):
        """ Executes post method """
        self._exe('POST')

    def put(self):
        """ Executes put method """
        self._exe('PUT')

    def patch(self):
        """ Executes patch method """
        self._exe('PATCH')

    def delete(self):
        """ Executes put method """
        self._exe('DELETE')

    def _exe(self, method):
        """ Executes the python function for the Rest Service """
        request_path = self.request.path
        request_service_params = list(filter(lambda x: x != '', request_path.split('/')))
        content_type = None
        if 'Content-Type' in self.request.headers.keys():
            content_type = self.request.headers['Content-Type']
        service_router = self._match_service_router(self.request, method)
        if service_router is None:
            raise tornado.web.HTTPError(404, 'The service has not found')
        try:
            service_name = getattr(service_router, '_service_name')
            service_params = getattr(service_router, '_service_params')
            # If the _types is not specified, assumes str types for the params
            params_types = getattr(service_router, "_types") or [str] * len(service_params)
            params_types = params_types + [str] * (len(service_params) - len(params_types))
            produces = getattr(service_router, '_produces')
            consumes = getattr(service_router, '_consumes')
            query_params = getattr(service_router, '_query_params')
            manual_response = getattr(service_router, '_manual_response')
            services_from_request = list(filter(lambda x: x in request_service_params, service_name))

            url_path_params=self._find_params_value_of_url(service_name,request_path)
            arguments_params= self._find_params_value_of_arguments(service_router)

            params_values = self._find_params_value_of_url(service_name,
                                                           request_path) + self._find_params_value_of_arguments(
                service_router)
            p_values = self._convert_params_values(params_values, params_types)

            request_handler = RequestHandlerRegister()
            response_handler = ResponseHandlerRegister()

            if consumes is None and produces is None:
                consumes = content_type
                produces = content_type
            if consumes is not None:
                p_values.append(request_handler.parse(consumes, params_types, self.request.body))
            response = service_router(*p_values)

            if response is None:
                return

            if produces:
                self.set_header('Content-Type', produces)

            if manual_response:
                return

            self.write(response_handler.parse(produces, response))
            self.finish()
        except Exception as detail:
            logging.exception('Internal Server Error : %s' % detail)

    def _match_service_router(self, request, method):
        functions = list(filter(lambda op: hasattr(getattr(self, op), '_service_name') is True and inspect.ismethod(
            getattr(self, op)) is True and getattr(getattr(self, op), '_method') == method, dir(self)))
        if len(functions) == 0:
            # if http request inter this func,url almost had existed.
            raise tornado.web.HTTPError(405, 'The service not have %s verb' % method)

        request_service_params = list(filter(lambda x: x != '', request.path.split('/')))

        final_service_router = None

        for operation in list(map(lambda op: getattr(self, op), functions)):
            service_name = getattr(operation, "_service_name")
            service_params = getattr(operation, "_service_params")
            services_from_request = list(filter(lambda x: x in request_service_params, service_name))

            if service_name == services_from_request and len(service_params) + len(service_name) == len(
                    request_service_params):
                final_service_router = operation
                break
        return final_service_router

    @staticmethod
    def _find_params_value_of_url(services, url):
        """ Find the values of path params """
        values_of_query = list()
        url_split = url.split('/')
        values = [item for item in url_split if item not in services and item != '']
        for v in values:
            if v is not None:
                values_of_query.append(v)
        return values_of_query

    def _find_params_value_of_arguments(self, operation):
        values = []
        if len(self.request.arguments) > 0:
            a = operation._service_params
            b = operation._func_params
            params = [item for item in b if item not in a]
            for p in params:
                if p in self.request.arguments.keys():
                    v = self.request.arguments[p]
                    values.append(v[0])
                else:
                    values.append(None)
        elif len(self.request.arguments) == 0 and len(operation._query_params) > 0:
            values = [None] * (len(operation._func_params) - len(operation._service_params))
        return values

    @staticmethod
    def _convert_params_values(values_list, params_types):
        """ Converts the values to the specifics types """
        values = list()
        i = 0
        for v in values_list:
            if v is not None:
                values.append(types.convert(v, params_types[i]))
            else:
                values.append(v)
            i += 1
        return values

    @classmethod
    def get_services(cls):
        """ Generates the resources (uri) to deploy the Rest Services """
        services = []
        for f in dir(cls):
            o = getattr(cls, f)
            if callable(o) and hasattr(o, '_service_name'):
                services.append(getattr(o, '_service_name'))
        return services

    @classmethod
    def get_paths(cls):
        """ Generates the resources from path (uri) to deploy the Rest Services """
        paths = []
        for f in dir(cls):
            o = getattr(cls, f)
            if callable(o) and hasattr(o, '_path'):
                paths.append(getattr(o, '_path'))
        return paths

    @classmethod
    def get_handlers(cls):
        """ Gets a list with (path, handler) """
        svs = []
        paths = cls.get_paths()
        for p in paths:
            s = re.sub(r'(?<={)\w+}', '.*', p).replace('{', '')
            o = re.sub(r'(?<=<)\w+', '', s).replace('<', '').replace('>', '').replace('&', '').replace('?', '')
            svs.append((o, cls))

        return svs


class RestService(tornado.web.Application):
    """ Class to create Rest services in tornado web server """
    resource = None

    def __init__(self, rest_handlers, resource=None, handlers=None, default_host='', transforms=None, **settings):
        rest_services = []
        self.resource = resource
        for r in rest_handlers:
            svs = self._generate_rest_services(r)
            rest_services += svs
        if handlers is not None:
            rest_services += handlers
        tornado.web.Application.__init__(self, rest_services, default_host, transforms, **settings)

    def _generate_rest_services(self, rest):
        svs = []
        paths = rest.get_paths()
        for p in paths:
            s = re.sub(r'(?<={)\w+}', '.*', p).replace('{', '')
            o = re.sub(r'(?<=<)\w+', '', s).replace('<', '').replace('>', '').replace('&', '').replace('?', '')
            logging.info('Scanning api path : %s' % o)
            svs.append((o, rest, self.resource))

        return svs
