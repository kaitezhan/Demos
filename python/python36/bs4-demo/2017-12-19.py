#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/19 17:49
# @Author  : boneix
from urllib.error import HTTPError
from urllib.request import urlopen

from bs4 import BeautifulSoup


def get_title(url):
    try:
        html = urlopen(url)
    except HTTPError as e:
        print("url: %s HTTPError: %s" % (url, e))
        return None
    try:
        bs_obj = BeautifulSoup(html.read())
        title = bs_obj.body.h1
    except AttributeError as e:
        print("Tag was not found %s" % e)
        return None
    return title


title = get_title("http://www.pythonscraping.com/pages/page1.html")
if title is None:
    print("Title could not be found")
else:
    print(title)
