#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/25 13:55
# @Author  : boneix
# ----------------------------------
# import nltk
# word_str = 'Here is some not very interesting text'
# tokens = nltk.word_tokenize(word_str)
# text = nltk.Text(tokens)
# print(text)
# ----------------------------------
# import nltk
# from nltk.book import text6
#
# fdist = nltk.FreqDist(text6)
# aa = fdist.most_common(10)
# print(aa)
# print(fdist['Grail'])
# ----------------------------------
# from nltk import bigrams, FreqDist
# from nltk.book import text6
#
# bigrams = bigrams(text6)
# bigramsDist = FreqDist(bigrams)
# print(bigramsDist[("Sir", "Robin")])
# ----------------------------------
# from nltk import ngrams
# from nltk.book import text6
#
# fourgrams = ngrams(text6, 4)
# for fourgram in fourgrams:
#     if fourgram[0] == 'coconut':
#         print(fourgram)
# ----------------------------------
# from nltk import word_tokenize, pos_tag
#
# # text = word_tokenize(
# #     "Strange women lying in ponds distributing swords is no basis for a system of government.  Supreme executive power derives from a mandate from the masses, not from some farcical aquatic ceremony.")
#
# text = word_tokenize("The dust was thick so he had to dust")
# aa = pos_tag(text)
# print(aa)
# ----------------------------------
# from nltk import word_tokenize, sent_tokenize, pos_tag
#
# sentences = sent_tokenize(
#     "Google is one of the best companies in the world. I constantly google myself to see what I'm up to.")
# nouns = ['NN', 'NNS', 'NNP', 'NNPS']
#
# for sentence in sentences:
#     if "google" in sentence.lower():
#         taggedWords = pos_tag(word_tokenize(sentence))
#         for word in taggedWords:
#             if word[0].lower() == "google" and word[1] in nouns:
#                 print(sentence)
# ----------------------------------
# import requests
#
# params = {'firstname': 'Ryan', 'lastname': 'Mitchell'}
# r = requests.post("http://pythonscraping.com/files/processing.php", data=params)
# print(r.text)
# ----------------------------------
# import requests
#
# params = {'email_addr': 'ryan.e.mitchell@gmail.com'}
# r = requests.post("http://post.oreilly.com/client/o/oreilly/forms/quicksignup.cgi", data=params)
# print(r.text)
# ----------------------------------
# import requests
#
# params = {'username': 'Ryan', 'password': 'password'}
# r = requests.post("http://pythonscraping.com/pages/cookies/welcome.php", params)
# print("Cookie is set to:")
# print(r.cookies.get_dict())
# print("=======================")
# print("Going to profile page...")
# r = requests.get("http://pythonscraping.com/pages/cookies/profile.php", cookies=r.cookies)
# print(r.text)
# ----------------------------------
# import requests
#
# session = requests.Session()
# params = {'username': 'username', 'password': 'password'}
# s = session.post("http://pythonscraping.com/pages/cookies/welcome.php", params)
# print("Cookie is set to:")
# print(s.cookies.get_dict())
# print("=======================")
# print("Going to profile page...")
# s = session.get("http://pythonscraping.com/pages/cookies/profile.php")
# print(s.text)
# ----------------------------------
# import requests
# from requests.auth import HTTPBasicAuth
#
# auth = HTTPBasicAuth('ryan', 'password')
# r = requests.post(url='http://pythonscraping.com/pages/auth/login.php', auth=auth)
# print(r.text)
# ----------------------------------
# import time
#
# from selenium import webdriver
#
# path = 'D:/Program Files/phantomjs-2.1.1-windows/bin/phantomjs'
# driver = webdriver.PhantomJS(executable_path=path)
# driver.get("http://pythonscraping.com/pages/javascript/ajaxDemo.html")
# time.sleep(3)
# print(driver.find_element_by_id('content').text)
# driver.close()
# ----------------------------------
# from selenium import webdriver
# from selenium.webdriver.common.by import By
# from selenium.webdriver.support import expected_conditions as EC
# from selenium.webdriver.support.wait import WebDriverWait
#
# path = 'D:/Program Files/phantomjs-2.1.1-windows/bin/phantomjs'
# driver = webdriver.PhantomJS(executable_path=path)
# driver.get("http://pythonscraping.com/pages/javascript/ajaxDemo.html")
# try:
#     element = WebDriverWait(driver, 10).until(
#         EC.presence_of_element_located((By.ID, "loadedButton")))
# finally:
#     print(driver.find_element_by_id("content").text)
#     driver.close()
# ----------------------------------
import time

from selenium import webdriver
from selenium.common.exceptions import StaleElementReferenceException


def waitForLoad(driver):
    elem = driver.find_element_by_tag_name("html")
    count = 0
    while True:
        count += 1
        if count > 20:
            print("Timing out after 10 seconds and returning")
            return
        time.sleep(.5)
        try:
            elem == driver.find_element_by_tag_name("html")
        except StaleElementReferenceException:
            return


path = 'D:/Program Files/phantomjs-2.1.1-windows/bin/phantomjs'
driver = webdriver.PhantomJS(executable_path=path)
driver.get("http://pythonscraping.com/pages/javascript/redirectDemo1.html")
print(driver.page_source)
print("=======================")
waitForLoad(driver)
print(driver.page_source)
