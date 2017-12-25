#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/15 15:01
# @Author  : boneix

import scrapy


class QuotesSpider(scrapy.Spider):
    name = "quotes"

    def start_requests(self):
        urls = [
            # 'http://quotes.toscrape.com/page/1/',
            # 'http://quotes.toscrape.com/page/2/',
            # 'https://www.toutiao.com/'
            # 'https://book.douban.com/'
            'http://pythonscraping.com/pages/page1.html'
        ]
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        # page = response.url.split("/")[-2]
        filename = 'pythonscraping-1.html'
        with open(filename, 'wb') as f:
            f.write(response.body)
        self.log('Save file %s' % filename)
