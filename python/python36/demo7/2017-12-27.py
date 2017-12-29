#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/27 10:23
# @Author  : boneix
# ----------------------------------
# from nltk import FreqDist
# from nltk.book import text1
#
# fdist1 = FreqDist(text1)
# # fdist1.plot(50, cumulative=True)
# print(fdist1.hapaxes())
# ----------------------------------
# from nltk import FreqDist
# from nltk.book import text5
#
# fdist5 = FreqDist(text5)
# aa = sorted([w for w in set(text5) if len(w) > 7 and fdist5[w] > 7])
# print(aa)
# ----------------------------------

# books = nltk.corpus.gutenberg.fileids()
# print(books)
# ----------------------------------
# from nltk.corpus import gutenberg
#
# emma = gutenberg.words('austen-emma.txt')
# print(len(emma))
# ----------------------------------
# from nltk.corpus import webtext
#
# for fileid in webtext.fileids():
#     print('====================================')
#     print(fileid, webtext.raw(fileid)[:65])
# ----------------------------------
# from nltk.corpus import inaugural
#
# print(type(inaugural))
# print(inaugural.fileids())
# ----------------------------------
# import nltk
# from nltk.corpus import inaugural
#
# cfd = nltk.ConditionalFreqDist(
#     (target, fileid[:4]) for fileid in inaugural.fileids() for w in inaugural.words(fileid) for target in
#     ['america', 'citizen'] if w.lower().startswith(target))
# cfd.plot()
# ----------------------------------
# import nltk
# from nltk.corpus import brown
#
# cfd = nltk.ConditionalFreqDist(
#     (genre, word)
#     for genre in brown.categories()
#     for word in brown.words(categories=genre)
# )
# genres = ['news', 'religion', 'hobbies', 'science_fiction', 'romance', 'humor']
# modals = ['can', 'could', 'may', 'might', 'must', 'will']
# # cfd.tabulate(conditions=genres, samples=modals)
# cfd.plot(conditions=genres, samples=modals)
# ----------------------------------
from urllib.request import urlopen

import nltk
from bs4 import BeautifulSoup

url = "http://news.bbc.co.uk/2/hi/health/2284783.stm"
html = urlopen(url).read()
# print(html[:60])
raw = BeautifulSoup(html, 'html.parser').get_text()

tokens = nltk.word_tokenize(raw)
print(tokens)
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
# ----------------------------------
