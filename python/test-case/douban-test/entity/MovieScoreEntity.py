#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/1/19 17:57
# @Author  : boneix

# 豆瓣电影评分信息
class MovieDouBanScoreInfo(object):
    def __init__(self,
                 index: dict(type=str, desc='索引编号'),
                 comments_count: dict(type=int, desc='点评数') = 0,
                 score: dict(type=float, desc='评分') = None,
                 update_time: dict(type=int, desc='更新时间') = None):
        self.index = index
        self.comments_count = comments_count
        self.score = score
        self.update_time = update_time

    class MovieDouBanScoreInfo(object):
        def __init__(self,
                     index: dict(type=str, desc='索引编号'),
                     comments_count: dict(type=int, desc='点评数') = 0,
                     score: dict(type=float, desc='评分') = None,
                     update_time: dict(type=int, desc='更新时间') = None):
            self.index = index
            self.comments_count = comments_count
            self.score = score
            self.update_time = update_time


# IMDB电影评分信息
class MovieIMDBScoreInfo(object):
    def __init__(self,
                 score: dict(type=float, desc='评分') = None,
                 update_index: dict(type=str, desc='更新索引') = None,
                 update_time: dict(type=int, desc='更新时间') = None):
        self.score = score
        self.update_index = update_index
        self.update_time = update_time
