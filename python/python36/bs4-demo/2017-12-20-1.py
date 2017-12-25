#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/20 10:28
# @Author  : boneix
from urllib.request import urlopen

from bs4 import BeautifulSoup

url = 'http://www.pythonscraping.com/pages/warandpeace.html'
html = urlopen(url)
bs_obj = BeautifulSoup(html)

name_list = bs_obj.findAll('span', {'class': 'green'})
for name in name_list:
    print(name.get_text())

