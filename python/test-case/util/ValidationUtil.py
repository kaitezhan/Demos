#!/user/bin/env python3
# -*- coding: utf-8 -*-
import re


class ValidationOperator(object):
    # 获取当前时间
    @staticmethod
    def email_chk(ori_str):
        if type(ori_str) is not str:
            raise ValueError('only str can be handler')
        mat = r'^[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+){0,4}@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+){0,4}$'
        return re.match(mat, ori_str) is not None
