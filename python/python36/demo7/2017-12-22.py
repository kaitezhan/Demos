#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/22 15:23
# @Author  : boneix
# from urllib.request import urlopen

# url = 'http://www.pythonscraping.com/pages/warandpeace/chapter1-ru.txt'
# textPage = urlopen(url)
# print(str(textPage.read(), 'utf-8'))
# from bs4 import BeautifulSoup
#
# html = urlopen("http://en.wikipedia.org/wiki/Python_(programming_language)")
# bsObj = BeautifulSoup(html, "html.parse")
# content = bsObj.find("div", {"id": "mw-content-text"}).get_text()
# content = bytes(content, "UTF-8")
# content = content.decode("UTF-8")
# print(str(content, 'utf-8'))
from urllib.request import urlopen

from bs4 import BeautifulSoup


def ngrams(input, n):
    input = input.split(' ')
    output = []
    for i in range(len(input) - n + 1):
        output.append(input[i:i + n])
    return output


html = urlopen("http://en.wikipedia.org/wiki/Python_(programming_language)")
bsObj = BeautifulSoup(html)
content = bsObj.find("div", {"id": "mw-content-text"}).get_text()
ngrams = ngrams(content, 2)
print(ngrams)
print("2-grams count is: " + str(len(ngrams)))
