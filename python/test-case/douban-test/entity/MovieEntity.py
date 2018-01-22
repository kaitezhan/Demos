#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2018/1/19 11:22
# @Author  : boneix


# 电影信息
class MovieInfo(object):
    def __init__(self,
                 chinese_title: dict(type=str, desc='中文片名') = None,
                 english_title: dict(type=str, desc='英文片名') = None,
                 base_info: dict(type=MovieBaseInfo, desc='基础信息') = None,
                 score_info: dict(type=str, desc='评分信息') = None,
                 tags: dict(type=dict, desc='标签') = None):
        self.chinese_title = chinese_title
        self.english_title = english_title
        self.base_info = base_info
        self.score_info = score_info
        self.tags = tags


# 电影基础信息
class MovieBaseInfo(object):
    def __init__(self,
                 production_info: dict(type=MovieProductionInfo, desc='制片方信息') = None,
                 release_info: dict(type=MovieReleaseInfo, desc='发行方信息') = None):
        self.production_info = production_info
        self.release_info = release_info


# 电影制片方信息
class MovieProductionInfo(object):
    def __init__(self,
                 auteur: dict(type=list, desc='导演') = None,
                 screenwriter: dict(type=list, desc='编剧') = None,
                 starring: dict(type=list, desc='主演') = None,
                 production_country: dict(type=list, desc='制片国家/地区') = None,
                 language: dict(type=list, desc='语言') = None):
        self.auteur = auteur
        self.screenwriter = screenwriter
        self.starring = starring
        self.production_country = production_country
        self.language = language


# 电影发行方信息
class MovieReleaseInfo(object):
    def __init__(self, release_date: dict(type=int, desc='发行日期') = None,
                 release_country: dict(type=list, desc='发行国家') = None):
        self.release_date = release_date
        self.release_country = release_country


# 电影评分信息
class MovieScoreInfo(object):
    def __init__(self,
                 douban_score: dict(type=MovieDouBanScoreInfo, desc='豆瓣评分') = None,
                 imdb_score: dict(type=MovieIMDBScoreInfo, desc='IMDB评分') = None):
        self.douban_score = douban_score
        self.imdb_score = imdb_score


# 豆瓣电影评分信息
class MovieDouBanScoreInfo(object):
    def __init__(self,
                 score: dict(type=float, desc='评分') = None,
                 update_index: dict(type=str, desc='更新索引') = None,
                 update_time: dict(type=int, desc='更新时间') = None):
        self.score = score
        self.update_index = update_index
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
