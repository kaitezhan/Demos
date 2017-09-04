#!/user/bin/env python3
# -*- coding: utf-8 -*-
import datetime
import time

import dateutil.relativedelta


# 时间处理类
class DateParser(object):
    # 将时间类型转为2017-05-06 格式的字符串 time#struct_time || datetime#datetime
    @staticmethod
    def format_date(date):
        if date is None:
            raise ValueError('date can\'t be None')
        if type(date) is not time.struct_time and type(date) is not datetime.datetime:
            raise ValueError('format_date can\'t handle type %s  ' % type(date))
        if type(date) is datetime.datetime:
            date = date.timetuple()
        return time.strftime("%Y-%m-%d", date)

    # 将时间类型转为2017-05-06 10:12:03 格式的字符串 time#struct_time || datetime#datetime
    @staticmethod
    def format_date_time(date):
        if date is None:
            raise ValueError('date can\'t be None')
        if type(date) is not time.struct_time and type(date) is not datetime.datetime:
            raise ValueError('format_date_time can\'t handle type %s  ' % type(date))
        if type(date) is datetime.datetime:
            date = date.timetuple()
        return time.strftime("%Y-%m-%d %H:%M:%S", date)

    # 返回秒级的时间戳 str || time#struct_time || datetime#datetime
    @staticmethod
    def parse_stamp_second(date):
        if date is None:
            raise ValueError('date can\'t be None')
        if type(date) is str:
            return time.mktime(DateParser.parse_date_time(date).timetuple())
        if type(date) is time.struct_time:
            return time.mktime(date)
        if type(date) is datetime.datetime:
            return time.mktime(date.timetuple())
        raise ValueError('parse_stamp_second can\'t handle type  %s ' % type(date))

    # 返回豪秒级的时间戳
    @staticmethod
    def parse_stamp(date):
        return int(DateParser.parse_stamp_second(date) * 1000)

    # 字符串转时间对象  datetime#datetime 可通过 timetuple() 转化为 time#struct_time
    @staticmethod
    def parse_date(date):
        if date is None:
            raise ValueError('date can\'t be None')
        if type(date) is not str:
            raise ValueError('parseDate can\'t handle type  %s ,only str support ' % type(date))
        return datetime.datetime.strptime(date, '%Y-%m-%d')

    # 字符串转时间对象  datetime#datetime 可通过 timetuple() 转化为 time#struct_time
    @staticmethod
    def parse_date_time(date):
        if date is None:
            raise ValueError('date can\'t be None')
        if type(date) is not str:
            raise ValueError('parse_date_time can\'t handle type  %s ,only str support ' % type(date))
        return datetime.datetime.strptime(date, '%Y-%m-%d %H:%M:%S')

    # 获取当前时间
    @staticmethod
    def now():
        return datetime.datetime.now()

    # 获取当前时间
    @staticmethod
    def now_date():
        return DateParser.format_date(DateParser.now())

    # 获取当前时间
    @staticmethod
    def now_date_time():
        return DateParser.format_date_time(DateParser.now())


# 时间操作类
class DateOperator(object):
    # 获取两个日期间的天数 datetime#datetime
    @staticmethod
    def days_range(start_date, end_date):
        if type(start_date) is not datetime.datetime:
            raise ValueError('days_range can\'t handle type  %s ,only datetime.datetime support ' % type(start_date))
        if type(end_date) is not datetime.datetime:
            raise ValueError('days_range can\'t handle type  %s ,only datetime.datetime support ' % type(end_date))
        delta = start_date - end_date
        return abs(delta.days)

    # 获取两个日期间的秒数 datetime#datetime
    @staticmethod
    def seconds_range(start_date, end_date):
        if type(start_date) is not datetime.datetime:
            raise ValueError('days_range can\'t handle type  %s ,only datetime.datetime support ' % type(start_date))
        if type(end_date) is not datetime.datetime:
            raise ValueError('days_range can\'t handle type  %s ,only datetime.datetime support ' % type(end_date))
        delta = start_date - end_date
        return abs(delta.total_seconds())

    # 日期小时增减操作
    @staticmethod
    def add_hours(start_date, num):
        if type(start_date) is not datetime.datetime:
            raise ValueError('add_hours can\'t handle type  %s ,only datetime.datetime support ' % type(start_date))
        if type(num) is not int:
            raise ValueError('add_hours can\'t handle type  %s ,only int support ' % type(start_date))
        return start_date + datetime.timedelta(hours=num)  # 日期天数增减操作

    # 日期天数增减操作
    @staticmethod
    def add_days(start_date, num):
        if type(start_date) is not datetime.datetime:
            raise ValueError('add_days can\'t handle type  %s ,only datetime.datetime support ' % type(start_date))
        if type(num) is not int:
            raise ValueError('add_days can\'t handle type  %s ,only int support ' % type(start_date))
        return start_date + datetime.timedelta(days=num)

    # 日期月份增减操作
    @staticmethod
    def add_weeks(start_date, num):
        if type(start_date) is not datetime.datetime:
            raise ValueError('add_weeks can\'t handle type  %s ,only datetime.datetime support ' % type(start_date))
        if type(num) is not int:
            raise ValueError('add_weeks can\'t handle type  %s ,only int support ' % type(start_date))
        return start_date + datetime.timedelta(weeks=num)

    # 日期月份增减操作
    @staticmethod
    def add_months(start_date, num):
        if type(start_date) is not datetime.datetime:
            raise ValueError('add_months can\'t handle type  %s ,only datetime.datetime support ' % type(start_date))
        if type(num) is not int:
            raise ValueError('add_months can\'t handle type  %s ,only int support ' % type(start_date))
        return start_date + dateutil.relativedelta.relativedelta(months=num)
