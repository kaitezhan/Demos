#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/20 10:28
# @Author  : boneix
import re
from urllib.request import urlopen

from bs4 import BeautifulSoup

url = 'http://en.wikipedia.org/wiki/Kevin_Bacon'
html = urlopen(url)
bs_obj = BeautifulSoup(html, "html.parser")

for link in bs_obj.find('div', {'id': 'bodyContent'}).findAll('a', href=re.compile('^(/wiki/)((?!:).)*$')):
    if 'href' in link.attrs:
        print(link.attrs['href'])
