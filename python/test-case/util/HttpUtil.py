#!/user/bin/env python3
# -*- coding: utf-8 -*-
import json

import requests
from requests.exceptions import RequestException


class RequestResult(object):
    def __init__(self, status=None, reason=True, message=None, data=None, response=None):
        self.status = status
        self.reason = reason
        self.message = message
        self.data = data
        self.response = response


class SimpleHttpClient(object):
    def __init__(self, url, method='GET', headers=None, data=None, timeout=None):
        self.url = url
        self.method = method
        if headers is None:
            headers = {}
        self.headers = headers
        if data is None:
            data = {}
        self.data = data
        # 设置默认超时时间为2s
        if timeout is None:
            timeout = 2
        self.timeout = timeout

    def execute(self):
        result = RequestResult()
        try:
            if 'get' == self.method.lower():
                hs = self._get_data_by_url()
            elif 'post' == self.method.lower():
                hs = self._post_data_by_url()
            else:
                raise ValueError('http method %s is not support. ' % (self.method.lower(),))
        except RequestException as e:
            result.reason = False
            result.message = e.__class__.__name__
            print('http request raise exception %s' % e.__class__.__name__)
            return result
        if hs is None:
            raise SystemError('http request failure')
        # 解析请求结果
        result.status = hs.status_code
        result.data = self._parse_response_data(hs)
        result.message = hs.status_code
        result.response = hs
        return result

    def _get_data_by_url(self):
        return requests.get(self.url, headers=self.headers, params=self.data, timeout=self.timeout)

    def _post_data_by_url(self):
        if self.data is not None:
            if type(self.data) is not str:
                if type(self.data) is dict:
                    self.data = json.dumps(self.data, ensure_ascii=False)
                else:
                    self.data = str(self.data)
            self.data = self.data.encode('utf-8')
        return requests.post(self.url, headers=self.headers, data=self.data, timeout=self.timeout)

    @staticmethod
    def _parse_response_data(hs):
        content_type = hs.headers.get('content-type')
        if content_type is not None:
            if 'application/json' in content_type:
                return hs.json()
            elif 'text/' in content_type:
                # 手动设置解码方式
                hs.encoding = hs.apparent_encoding
                return hs.text
            return hs.content
        return None
