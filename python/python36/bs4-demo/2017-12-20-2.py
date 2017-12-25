#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/20 10:28
# @Author  : boneix
from urllib.request import urlopen

from bs4 import BeautifulSoup

url = 'http://www.pythonscraping.com/pages/page3.html'
html = urlopen(url)
bs_obj = BeautifulSoup(html)

# for child in bs_obj.find("table", {"id": "giftList"}).children:
#     print(child)

for sibling in bs_obj.find("table", {"id": "giftList"}).tr.next_siblings:
    print(sibling)
