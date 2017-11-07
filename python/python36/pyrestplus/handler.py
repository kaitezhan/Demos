#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/30 16:17
# @Author  : boneix
import json
import xml

from pyconvert.pyconv import convertXML2OBJ, convertJSON2OBJ, convert2JSON, convert2XML

from pyrestplus import media_types


class HandlerException(Exception):
    """ Class for Handler exceptions """

    def __init__(self, message):
        self.message = message

    def __str__(self):
        return repr(self.message)


class RequestHandlerSupport(object):
    def __init__(self):
        self.parsers = {}

    def parse(self, content_type, params_types, body):
        method = self.parsers.get(content_type)
        if handler is None or (not hasattr(method, '__call__')):
            raise HandlerException('Can\'t handler request content_type : %s' % content_type)
        return method(params_types, body)

    def register_handler_parser(self, content_type, parser):
        if self.parsers.get(content_type) is not None:
            raise HandlerException('Current handler request content_type  %s had register' % content_type)
        self.parsers[content_type] = parser


class RequestHandlerRegister(RequestHandlerSupport):
    def __init__(self):
        super().__init__()
        self.register_handler_parser(media_types.APPLICATION_XML, self.xml_parser)
        self.register_handler_parser(media_types.APPLICATION_JSON, self.json_parser)
        self.register_handler_parser(media_types.APPLICATION_JSON_UTF8, self.json_utf8_parser)

    @staticmethod
    def xml_parser(params_types, body):
        if params_types[0] in [str]:
            param_obj = xml.dom.minidom.parseString(body)
        else:
            param_obj = convertXML2OBJ(params_types[0],
                                       xml.dom.minidom.parseString(body).documentElement)
        return param_obj

    @staticmethod
    def json_parser(params_types, body):
        return convertJSON2OBJ(params_types[0], json.loads(body))

    @staticmethod
    def json_utf8_parser(params_types, body):
        return RequestHandlerRegister.json_utf8_parser(params_types, str(body, 'utf-8'))


class ResponseHandlerSupport(object):
    def __init__(self):
        self.parsers = {}

    def parse(self, content_type, response):
        method = self.parsers.get(content_type)
        if handler is None or (not hasattr(method, '__call__')):
            raise HandlerException('Can\'t handler response content_type : %s' % content_type)
        return method(content_type)(response)

    def register_handler_parser(self, content_type, parser):
        if self.parsers.get(content_type) is not None:
            raise HandlerException('Current handler response content_type  %s had register' % content_type)
        self.parsers[content_type] = parser


class ResponseHandlerRegister(ResponseHandlerSupport):
    def __init__(self):
        super().__init__()
        self.register_handler_parser(media_types.APPLICATION_XML, self.xml_parser)
        self.register_handler_parser(media_types.APPLICATION_JSON, self.json_parser)
        self.register_handler_parser(media_types.APPLICATION_JSON_UTF8, self.json_utf8_parser)

    @staticmethod
    def xml_parser(response):
        if hasattr(response, '__module__') and not isinstance(
                response, xml.dom.minidom.Document):
            return convert2XML(response)
        elif isinstance(response, xml.dom.minidom.Document):
            return response.toxml()
        else:
            raise HandlerException('Internal Server Error : response is not %s document' % media_types.APPLICATION_XML)

    @staticmethod
    def json_parser(response):
        if hasattr(response, '__module__'):
            return convert2JSON(response)
        elif isinstance(response, list):
            return json.dumps(response)
        elif isinstance(response, dict):
            return response
        else:
            raise HandlerException('Internal Server Error : response is not %s document' % media_types.APPLICATION_JSON)

    @staticmethod
    def json_utf8_parser(response):
        return ResponseHandlerRegister.json_parser(response)


if __name__ == '__main__':
    handler = RequestHandlerRegister()
    # handler.parse()
