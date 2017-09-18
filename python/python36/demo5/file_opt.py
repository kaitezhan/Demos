#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/18 17:54
# @Author  : boneix


class FileOpt(object):
    @staticmethod
    def read_data_from_file(path):
        with open(path, 'rb') as f:
            return f.read()

    @staticmethod
    def write_data_from_file(path, data):
        with open(path, 'wb') as f:
            return f.write(data)
