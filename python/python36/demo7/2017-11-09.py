#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/9 15:52
# @Author  : boneix


class AbstractCaseClass(object):
    def case1(self):
        print("case1")
        self._get_head()

    def _get_head(self):
        pass


class ANDHeadClass(AbstractCaseClass):
    def _get_head(self):
        print("ANDHeadClass")


class IOSHeadClass(AbstractCaseClass):
    def _get_head(self):
        print("IOSHeadClass")


case = ANDHeadClass()
case.case1()
