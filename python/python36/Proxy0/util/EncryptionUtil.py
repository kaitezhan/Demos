#!/user/bin/env python3
# -*- coding: utf-8 -*-
import base64
import hashlib


class EncryptParser(object):
    # md5 加密
    @staticmethod
    def md5_str(ori_str, mode='utf-8'):
        if type(ori_str) is not str:
            raise ValueError('only str can be handler')
        return hashlib.md5(ori_str.encode(mode)).hexdigest()

    # sha256 加密
    @staticmethod
    def sha256_str(ori_str, mode='utf-8'):
        if type(ori_str) is not str:
            raise ValueError('only str can be handler')
        return hashlib.sha256(ori_str.encode(mode)).hexdigest()

    # sha3_256 加密
    @staticmethod
    def sha3_256_str(ori_str, mode='utf-8'):
        if type(ori_str) is not str:
            raise ValueError('only str can be handler')
        return hashlib.sha3_256(ori_str.encode(mode)).hexdigest()

    # 因为base64编码后的字符除 了英文字母和数字外还有三个字符 + / =, 其中=只是为了补全编码后的字符数为4的整数，而+和/在一些情况下需要被替换的
    # base64加密
    @staticmethod
    def base64_encode(ori_str, mode='utf-8'):
        if type(ori_str) is not str:
            raise ValueError('only str can be handler')
        return base64.b64encode(ori_str.encode(mode)).decode(mode)

    # base64解密
    @staticmethod
    def base64_decode(target_str, mode='utf-8'):
        if type(target_str) is not str:
            raise ValueError('only str can be handler')
        return base64.b64decode(target_str.encode(mode)).decode(mode)

    # url base64加密
    @staticmethod
    def base64_url_encode(ori_url, mode='utf-8'):
        if type(ori_url) is not str:
            raise ValueError('only str can be handler')
        return base64.urlsafe_b64encode(ori_url.encode(mode)).decode(mode)

    # url base64解密
    @staticmethod
    def base64_url_decode(target_str, mode='utf-8'):
        if type(target_str) is not str:
            raise ValueError('only str can be handler')
        return base64.urlsafe_b64decode(target_str.encode(mode)).decode(mode)
