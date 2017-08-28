#!/user/bin/env python3
# -*- coding: utf-8 -*-

from mimetypes import MimeTypes

import requests


def get_file():
    file_path = r'C:\Users\rzhang\Pictures\33.png'
    return open(file_path, 'rb')


def get_file_mime():
    mime = MimeTypes()
    file_path = r'C:\Users\rzhang\Pictures\33.png'
    print(mime.guess_type(file_path))


def get_result():
    url = 'http://localhost:8080/api/mule/v1/attachment/upload'
    headers = {
        'Current-User-Id': '83',
        'Access-Token': '5b1d73a240e27f6b16da735eb459297090f53b67'
    }
    files = {'file': ('33.png', get_file(), 'image/png')}
    param = {'meta': 'asdasdad'}
    hs = requests.post(url, headers=headers, data=param, files=files)
    if hs is None:
        raise SystemError('http request failure')
    if hs.status_code is 200:
        print(hs.json())
    else:
        print('http request failure')


get_result()
