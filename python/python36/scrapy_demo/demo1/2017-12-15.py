#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/12/15 14:14
# @Author  : boneix
import scrapy


class QuotesSpider(scrapy.Spider):
    name = "quotes"
    start_urls = ['https://blog.scrapinghub.com']

    def parse(self, response):
        for title in response.css('h2.entry-title'):

            yield {'title': title.css('a ::text').extract_first()}

        for next_page in response.css('div.prev-post > a'):
            yield response.follow(next_page, self.parse)
