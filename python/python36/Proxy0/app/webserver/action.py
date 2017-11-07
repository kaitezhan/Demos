#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/24 19:07
# @Author  : Boneix
import asyncio
import json
import logging
import os

from PyQt5.QtCore import Qt
from PyQt5.QtGui import QStandardItem
from PyQt5.QtWidgets import QApplication, QCheckBox, QWidget, QHBoxLayout, QPushButton
from aiohttp import web

from app.webserver.data import PropertyData
from app.webserver.view import WebServerView, PathConfigOperateView

logging.basicConfig(level=logging.INFO)


class WebServerAction(WebServerView):
    def __init__(self, parent=None):
        super(WebServerAction, self).__init__(parent)

        path = os.path.join(os.getcwd(), 'data.cfg')
        self.filePath = path

        self._init_web_server()

    def _init_web_server(self):
        # 事件绑定
        # operate
        self.btnAdd.clicked.connect(self._open_add_view)
        self.btnRemove.clicked.connect(self._remove_data)
        self.btnEnable.clicked.connect(self._enable_data)
        self.btnDisable.clicked.connect(self._disable_data)

        # start server
        self.btnStart.clicked.connect(self._start_server)
        # stop server
        self.btnStop.clicked.connect(self._stop_server)

        # 数据展示
        self._reload_data()

    def _open_add_view(self):
        self.addView = PathConfigAddView(self)
        self.addView.show()

    def _reload_data(self) -> object:
        self.tableData = []
        self.propertyData = self._do_reload_data()
        table_len = len(self.propertyData)
        if table_len <= 0:
            return

        # 设置行数
        self.model.setRowCount(table_len)

        # 迭代数据
        for i, data in enumerate(self.propertyData):
            # 配置 第一列的checkbox
            tmp_check_box = QCheckBox()
            tmp_widget = QWidget()
            tmp_layout = QHBoxLayout()
            tmp_layout.addWidget(tmp_check_box)
            tmp_layout.setAlignment(Qt.AlignCenter)
            tmp_layout.setContentsMargins(1, 1, 1, 1)
            tmp_widget.setLayout(tmp_layout)
            tmp_check_box.setObjectName(data.code)

            self.widget_data.setIndexWidget(self.model.index(i, 0), tmp_widget)

            # 配置 其他数据
            tmp_item = QStandardItem(data.path)
            tmp_item.setEditable(False)
            self.model.setItem(i, 1, tmp_item)
            tmp_item = QStandardItem(str(data.status))
            tmp_item.setTextAlignment(Qt.AlignCenter)
            tmp_item.setEditable(False)
            self.model.setItem(i, 2, tmp_item)
            tmp_item = QStandardItem(json.dumps(data.method))
            tmp_item.setTextAlignment(Qt.AlignCenter)
            tmp_item.setEditable(False)
            self.model.setItem(i, 3, tmp_item)
            tmp_item = QStandardItem(json.dumps(data.head))
            tmp_item.setEditable(False)
            self.model.setItem(i, 4, tmp_item)
            tmp_item = QStandardItem(data.remark)
            tmp_item.setEditable(False)
            self.model.setItem(i, 5, tmp_item)

            # 配置最后一列的操作按钮
            tmp_btn = QPushButton("Modify")
            tmp_widget = QWidget()
            tmp_layout = QHBoxLayout()
            tmp_layout.addWidget(tmp_btn)
            tmp_layout.setAlignment(Qt.AlignCenter)
            tmp_layout.setContentsMargins(1, 1, 1, 1)
            tmp_widget.setLayout(tmp_layout)
            self.widget_data.setIndexWidget(self.model.index(i, 6), tmp_widget)
            tmp_btn.setObjectName(data.code)

            # 事件相关逻辑
            tmp_check_box.toggled.connect(self._check_box_selected)
            tmp_btn.clicked.connect(lambda state, x=data.code: self._modify_clicked(x))

            self.tableData.append(tmp_check_box)

        self.widget_data.setModel(self.model)

    # 数据被选中时的事件
    def _check_box_selected(self):
        if len(self.tableData) <= 0:
            return
        self.selectCodes = []
        for x in self.tableData:
            if x.isChecked():
                self.selectCodes.append(x.objectName())

        if len(self.selectCodes) <= 0:
            self._opt_btn_init(True)
            return

        self._opt_btn_init(False)

    def _modify_clicked(self, code):
        if len(self.propertyData) <= 0 or len(code) <= 0:
            return
        for x in self.propertyData:
            if x.code == code:
                # 展示修改窗体
                self._init_modify_view(x)

    def _init_modify_view(self, data):
        self.modifyView = PathConfigModifyView(self)
        self.modifyView.labCode.setText(data.code)
        self.modifyView.editPath.setText(data.path)
        self.modifyView.editHead.setText(str(data.head))
        self.modifyView.editRemark.setText(data.remark)
        if len(data.method) > 0:
            for x in data.method:
                if x.lower() == "get":
                    self.modifyView.ck_get.setChecked(True)
                if x.lower() == "post":
                    self.modifyView.ck_post.setChecked(True)
                if x.lower() == "put":
                    self.modifyView.ck_put.setChecked(True)
                if x.lower() == "delete":
                    self.modifyView.ck_delete.setChecked(True)

        self.modifyView.dataType.setCurrentText(data.dateType)
        self.modifyView._change_type(data.dateType)
        if data.dateType.lower() == "text":
            self.modifyView.editData.setPlainText(data.data)
        elif data.dateType.lower() == "path":
            self.modifyView.editFilePath.setText(data.file)

        self.modifyView.show()

    def _opt_btn_init(self, bool_value):
        self.btnRemove.setDisabled(bool_value)
        self.btnEnable.setDisabled(bool_value)
        self.btnDisable.setDisabled(bool_value)

    def _do_reload_data(self):
        result = []
        str_data = FileOpt.read_data_from_file(self.filePath)
        if len(str_data) <= 0:
            return result
        lit = json.loads(str_data)
        for x in lit:
            data = PropertyData.dict2data(x)
            result.append(data)
        return result

    def _remove_data(self):
        if len(self.propertyData) <= 0 or len(self.selectCodes) <= 0:
            return
        for x in self.propertyData:
            for y in self.selectCodes:
                if x.code == y:
                    self.propertyData.remove(x)
        self._rewrite_data()
        self._reload_data()
        self._opt_btn_init(True)

    def _enable_data(self):
        if len(self.propertyData) <= 0 or len(self.selectCodes) <= 0:
            return
        for x in self.propertyData:
            for y in self.selectCodes:
                if x.code == y:
                    x.status = True
        self._rewrite_data()
        self._reload_data()
        self._opt_btn_init(True)

    def _disable_data(self):
        if len(self.propertyData) <= 0 or len(self.selectCodes) <= 0:
            return
        for x in self.propertyData:
            for y in self.selectCodes:
                if x.code == y:
                    x.status = False
        self._rewrite_data()
        self._reload_data()
        self._opt_btn_init(True)

    def _rewrite_data(self):
        str_data = json.dumps(self.propertyData, default=PropertyData.data2dict)
        FileOpt.write_data_from_file(self.filePath, bytes(str_data, encoding="utf8"))

    def _start_server(self):
        self.propertyData = self._do_reload_data()
        if len(self.propertyData) <= 0:
            return
        self.loop = asyncio.get_event_loop()
        # threading.Thread(target=stop_loop).start()
        self.loop.run_until_complete(self.init(self.loop))
        try:
            self.loop.run_forever()
        except Exception as e:
            logging.exception(e)
        finally:
            self.loop.close()

    @asyncio.coroutine
    def init(self, loop):
        server_app = web.Application(loop=loop)
        for x in self.propertyData:
            if not x.status:
                continue
            else:
                if len(x.method) > 0:
                    for y in x.method:
                        if y.lower() == "get":
                            server_app.router.add_get(x.path, lambda request, data=x: self._web_response(request, data))
                        if y.lower() == "post":
                            server_app.router.add_post(x.path,
                                                       lambda request, data=x: self._web_response(request, data))
                        if y.lower() == "put":
                            server_app.router.add_put(x.path, lambda request, data=x: self._web_response(request, data))
                        if y.lower() == "delete":
                            server_app.router.add_delete(x.path,
                                                         lambda request, data=x: self._web_response(request, data))

        srv = yield from loop.create_server(server_app.make_handler(), 'localhost', 8080)
        logging.info('server started at http://localhost:8080...')
        return srv

    @staticmethod
    def _web_response(request, data):
        heads = data.head
        if len(heads) > 0:
            for (d, x) in heads.items():
                if d.lower() == "content_type":
                    content_type = x
        if data.dateType.lower() == "text":
            return web.Response(body=bytes(data.data, encoding="utf8"), content_type=content_type, headers=heads)
        elif data.dateType.lower() == "path":
            return web.FileResponse(data.file, headers=heads)

    def _stop_server(self):
        pass


class PathConfigAddView(PathConfigOperateView):
    def __init__(self, parent=None):
        super(PathConfigAddView, self).__init__(parent)
        path = os.path.join(os.getcwd(), 'data.cfg')
        self.filePath = path
        self._init_add_view()

    def _init_add_view(self):
        self.editHead.setText("{}")

    def _data_submit(self):
        check_flag = True
        error_messages = []
        if not self._check_path(True):
            error_messages.append("Path must not empty！")
            self.labPath.setStyleSheet("color:red")
            check_flag = False
        if not self._check_method():
            error_messages.append("Method hasn't selected！")
            self.labMethod.setStyleSheet("color:red")
            check_flag = False
        if not self._check_head():
            error_messages.append("Head must be json！")
            self.labHead.setStyleSheet("color:red")
            check_flag = False
        if not check_flag:
            self.message.setText(str(error_messages))
            return
        self._do_data_submit()

    def _do_data_submit(self):
        self.data = self._do_reload_data()
        pd = PropertyData(None, self.path, self.method_list, self.dataType.currentText(),
                          self.editData.toPlainText(), self.editFilePath.text(), self.head, False,
                          self.editRemark.toPlainText())
        if self.dataType.currentText().lower() == "text":
            pd.file = None
        else:
            pd.data = None
        self.data.append(pd)
        str_data = json.dumps(self.data, default=PropertyData.data2dict)
        FileOpt.write_data_from_file(self.filePath, bytes(str_data, encoding="utf8"))
        # 数据更新完成后先刷新数据后关闭当前窗体
        self.parent()._reload_data()
        self.close()

    def _check_path(self, is_repeat):
        path = self.editPath.text().strip()
        if len(path) <= 0:
            return False
        if not is_repeat and self._check_conflict(path):
            return False
        self.path = path
        return True

    def _check_conflict(self, path):
        data = self._do_reload_data()
        if len(data) <= 0:
            return
        for x in data:
            if x.path == path:
                return True
        return False

    def _do_reload_data(self):
        result = []
        str_data = FileOpt.read_data_from_file(self.filePath)
        if len(str_data) <= 0:
            return result
        lit = json.loads(str_data)
        for x in lit:
            data = PropertyData.dict2data(x)
            result.append(data)
        return result


class PathConfigModifyView(PathConfigOperateView):
    def __init__(self, parent=None):
        super(PathConfigModifyView, self).__init__(parent)
        path = os.path.join(os.getcwd(), 'data.cfg')
        self.filePath = path
        self._init_modify_view()

    def _init_modify_view(self):
        pass

    def _data_submit(self):
        check_flag = True
        error_messages = []
        if not self._check_path():
            error_messages.append("Path must not empty or has exist！")
            self.labPath.setStyleSheet("color:red")
            check_flag = False
        if not self._check_method():
            error_messages.append("Method hasn't selected！")
            self.labMethod.setStyleSheet("color:red")
            check_flag = False
        if not self._check_head():
            error_messages.append("Head must be json！")
            self.labHead.setStyleSheet("color:red")
            check_flag = False
        if not check_flag:
            self.message.setText(str(error_messages))
            return
        self._do_data_submit()

    def _do_data_submit(self):
        self.data = self._do_reload_data()
        code = self.labCode.text().strip()
        pd = PropertyData(code, self.path, self.method_list, self.dataType.currentText(),
                          self.editData.toPlainText(), self.editFilePath.text(), self.head, False,
                          self.editRemark.toPlainText())
        if self.dataType.currentText().lower() == "text":
            pd.file = None
        else:
            pd.data = None

        for i, x in enumerate(self.data):
            if x.code == code:
                self.data[i] = pd
        str_data = json.dumps(self.data, default=PropertyData.data2dict)
        FileOpt.write_data_from_file(self.filePath, bytes(str_data, encoding="utf8"))
        # 数据更新完成后先刷新数据后关闭当前窗体
        self.parent()._reload_data()
        self.close()

    def _check_path(self):
        path = self.editPath.text().strip()
        code = self.labCode.text().strip()
        if len(path) <= 0:
            return False
        if len(code) <= 0:
            return False
        data = self._do_reload_data()
        if len(data) <= 0:
            return False
        for x in data:
            if x.path == path and x.code != code:
                return False
        self.path = path
        return True

    def _do_reload_data(self):
        result = []
        str_data = FileOpt.read_data_from_file(self.filePath)
        if len(str_data) <= 0:
            return result
        lit = json.loads(str_data)
        for x in lit:
            data = PropertyData.dict2data(x)
            result.append(data)
        return result


class FileOpt(object):
    @staticmethod
    def read_data_from_file(path):
        with open(path, 'rb') as f:
            return f.read()

    @staticmethod
    def write_data_from_file(path, data):
        with open(path, 'wb') as f:
            return f.write(data)


if __name__ == '__main__':
    import sys

    app = QApplication(sys.argv)
    web_server = WebServerAction()
    web_server.show()
    sys.exit(app.exec_())
