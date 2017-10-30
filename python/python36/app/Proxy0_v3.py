#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/19 16:07
# @Author  : boneix
import json
import os

from PyQt5.QtWidgets import (QApplication, QDialog)

from util.DateUtil import DateParser
from util.EncryptionUtil import EncryptParser


class WidgetProxyZero(QDialog):
    def __init__(self, parent=None):
        super(WidgetProxyZero, self).__init__(parent)

        # 初始化系统参数
        self._param_init()

        # 创建样式操作控件
        self._create_style_change_layout()

        # 生成主体控件
        self._create_top_left_group_box()
        self._create_top_right_group_box()
        self._create_bottom_tab()
        self._create_progress_bar()

        # 布局主体
        self._layout_main_window()

        # 设置应用头部标题
        self.setWindowTitle("WidgetProxyZero Ver 0.0.1")

    def _param_init(self):
        self.filePath = os.path.join(os.getcwd(), 'data.cfg')
        self.propertyData = []
        self.addDataChildWindow = None


# 数据传输类
class PropertyData(object):
    def __init__(self, path, code=None, method=[], head={}, status=True, remark=""):
        self.path = path
        self.method = method
        self.head = head
        self.status = status
        self.remark = remark

        if code is None:
            code = self._create_code_()
        self.code = code

    def _create_code_(self):
        data = {'path': self.path, 'time': DateParser.parse_stamp(DateParser.now())}
        return EncryptParser.sha3_256_str(json.dumps(data))

    @staticmethod
    def data2dict(data):
        return {
            'code': data.code,
            'path': data.path,
            'method': data.method,
            'head': data.head,
            'status': data.status,
            'remark': data.remark
        }

    @staticmethod
    def dict2data(dict_str):
        return PropertyData(dict_str['code'], dict_str['path'], dict_str['method'], dict_str['head'],
                            dict_str['status'], dict_str['remark'])


# 数据操作抽象类
class DataOpt(object):
    def add_data(self, data):
    def add_data(self, path: str, method: list, head: dict = {}, remark: str = "")：
        pass

    def modify_data(self, code, method, head, remark):
        pass

    def delete_data(self, code):
        pass

    def enable_data(self, code):
        pass

    def disable_data(self, code):
        pass


# 数据操作类
class FileDataOpt(DataOpt):
    def __init__(self, path=""):
        if len(path.strip()) <= 0:
            path = os.path.join(os.getcwd(), 'data.cfg')
        self.filePath = path
        self.message = []
        self.data = None
        self.fileData = self._reload_data_()

    def _read_data_from_file(self):
        with open(self.filePath, 'rb') as f:
            return f.read()

    def _write_data_from_file(self, data):
        with open(self.filePath, 'wb') as f:
            return f.write(data)

    def _reload_data_(self):
        self.fileData = []
        str_data = self._read_data_from_file()
        if len(str_data) <= 0:
            return
        lit = json.loads(str_data)
        result = []
        for x in lit:
            data = PropertyData.dict2data(x)
            result.append(data)
        self.fileData = result
        return result

    def add_data(self, path: str, method: list, head: dict = {}, remark: str = ""):
        self._check_path(path)
        self._check_method(method)
        self._check_head(head)
        if len(self.message) > 0:
            return self.message
        self.fileData.append(self.data)
        self._rewrite_data_()

    def modify_data(self, code: str, method: list = [], head: dict = {}, remark: str = ""):
        if not type(code) == str:
            raise ValueError("Method modify_data() #code can't handler type %s" % type(code))
        if not type(method) == list:
            raise ValueError("Method modify_data() #method  can't handler type %s" % type(method))
        if not type(head) == dict:
            raise ValueError("Method modify_data() #head  can't handler type %s" % type(head))
        if not type(method) == list:
            raise ValueError("Method modify_data() #method  can't handler type %s" % type(method))

        pass

    def delete_data(self, codes: list):
        if not type(codes) == list:
            raise ValueError("Method delete_data() can't handler type %s" % type(codes))
        if len(self.fileData) <= 0 or len(codes) <= 0:
            return
        for x in self.fileData:
            for y in codes:
                if x.code == y:
                    self.fileData.remove(x)
        self._rewrite_data_()

    def enable_data(self, codes: list):
        if not type(codes) == list:
            raise ValueError("Method enable_data() can't handler type %s" % type(codes))
        if len(self.fileData) <= 0 or len(codes) <= 0:
            return
        for x in self.fileData:
            for y in codes:
                if x.code == y:
                    x.status = True
        self._rewrite_data_()

    def disable_data(self, codes: list):
        if not type(codes) == list:
            raise ValueError("Method disable_data() can't handler type %s" % type(codes))
        if len(self.fileData) <= 0 or len(codes) <= 0:
            return
        for x in self.fileData:
            for y in codes:
                if x.code == y:
                    x.status = False
        self._rewrite_data_()

    def _rewrite_data_(self):
        str_data = json.dumps(self.fileData, default=PropertyData.data2dict)
        self._write_data_from_file(bytes(str_data, encoding="utf8"))

    def _check_path(self, path: str):
        if len(path.strip()) <= 0:
            self.message.append("Path must not empty！")
            return False
        return self._check_conflict(path)

    def _check_conflict(self, path: str):
        if len(self.fileData) <= 0:
            return
        for x in self.fileData:
            if x.path == path:
                self.message.append("Path has existed！")
            return False
        self.data = PropertyData(path)
        return True

    def _check_method(self, method: list):
        if not (type(method) == list and len(method) > 0):
            self.message.append("Method hasn't selected！")
            return False
        self.data.method = method
        return True

    def _check_head(self, head: str):
        head = head.strip()
        if len(head) <= 2:
            self.message.append("Head must be json！")
            return False
        try:
            self.data.head = json.loads(head, encoding='utf-8')
            return True
        except ValueError:
            self.message.append("Head must be json！")
            return False


if __name__ == '__main__':
    import sys

    app = QApplication(sys.argv)
    gallery = WidgetProxyZero()
    gallery.show()
    sys.exit(app.exec_())
