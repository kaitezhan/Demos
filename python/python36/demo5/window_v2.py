#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/18 13:46
# @Author  : boneix

import json
import os

from PyQt5.QtCore import QTimer, QRegExp, Qt
from PyQt5.QtGui import QRegExpValidator, QStandardItemModel, QStandardItem
from PyQt5.QtWidgets import (QApplication, QCheckBox, QComboBox, QDialog, QGridLayout, QGroupBox, QHBoxLayout, QLabel,
                             QProgressBar, QPushButton, QStyleFactory, QFormLayout, QLineEdit, QTableView,
                             QWidget, QTextEdit)

from demo5.data_class import PropertyData
from demo5.file_opt import FileOpt


class WidgetProxyZero(QDialog):
    def __init__(self, parent=None, path=""):
        super(WidgetProxyZero, self).__init__(parent)
        if len(path.strip()) <= 0:
            path = os.path.join(os.getcwd(), 'data.cfg')
        self.filePath = path
        self.propertyData = []
        self.addDataChildWindow = None
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

    # 改变系统样式
    def _change_style(self, style_name):
        QApplication.setStyle(QStyleFactory.create(style_name))
        self._change_palette()

    # 应用系统样式
    def _change_palette(self):
        if self.useStylePaletteCheckBox.isChecked():
            QApplication.setPalette(QApplication.style().standardPalette())
        else:
            QApplication.setPalette(self.originalPalette)

    # 创建样式操作控件
    def _create_style_change_layout(self):
        self.originalPalette = QApplication.palette()

        style_combo_box = QComboBox()
        style_items = QStyleFactory.keys()
        style_items.reverse()
        style_combo_box.addItems(style_items)
        style_label = QLabel("&Style:")
        style_label.setBuddy(style_combo_box)
        self.useStylePaletteCheckBox = QCheckBox("&Use style's standard palette")
        # self.useStylePaletteCheckBox.setChecked(True)

        # 样式事件绑定
        style_combo_box.activated[str].connect(self._change_style)
        self.useStylePaletteCheckBox.toggled.connect(self._change_palette)

        # 头部
        self.topLayout = QHBoxLayout()
        self.topLayout.addWidget(style_label)
        self.topLayout.addWidget(style_combo_box)
        # - addStretch设置中间的空白
        self.topLayout.addStretch(1)
        self.topLayout.addWidget(self.useStylePaletteCheckBox)
        # 初始化样式
        self._change_style(style_items[0])

    # 创建左上侧配置信息控件
    def _create_top_left_group_box(self):
        self.topLeftGroupBox = QGroupBox("Config")

        self.editHost = QLineEdit("http://localhost")
        regex = QRegExp()
        regex.setPattern(
            "((http|https)://)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))")
        q_validate = QRegExpValidator()
        q_validate.setRegExp(regex)
        self.editHost.setValidator(q_validate)

        self.editPort = QLineEdit("8080")
        regex = QRegExp()
        regex.setPattern("([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])")
        q_validate = QRegExpValidator()
        q_validate.setRegExp(regex)
        self.editPort.setValidator(q_validate)

        layout = QFormLayout()
        layout.addRow(QLabel("Host："), self.editHost)
        layout.addRow(QLabel("Port："), self.editPort)

        self.topLeftGroupBox.setLayout(layout)

    # 创建右上侧操作按钮控件
    def _create_top_right_group_box(self):
        self.topRightGroupBox = QGroupBox("Operate")

        self.btnStart = QPushButton()
        self.btnStart.setText("Start")

        self.btnStop = QPushButton()
        self.btnStop.setText("Stop")
        self.btnStop.setDisabled(True)

        self.btnRestart = QPushButton()
        self.btnRestart.setText("Restart")
        self.btnRestart.setDisabled(True)

        self.btnReload = QPushButton()
        self.btnReload.setText("Reload")

        self.btnAdd = QPushButton()
        self.btnAdd.setText("Add")

        self.btnRemove = QPushButton()
        self.btnRemove.setText("Remove")
        self.btnRemove.setDisabled(True)

        self.btnEnable = QPushButton()
        self.btnEnable.setText("Enable")
        self.btnEnable.setDisabled(True)

        self.btnDisable = QPushButton()
        self.btnDisable.setText("Disable")
        self.btnDisable.setDisabled(True)

        # TODO 绑定事件
        self.btnReload.clicked.connect(self._reload_data_)
        self.btnAdd.clicked.connect(self._add_data)
        self.btnRemove.clicked.connect(self._remove_data)
        self.btnEnable.clicked.connect(self._enable_data)
        self.btnDisable.clicked.connect(self._disable_data)

        layout = QGridLayout()
        layout.addWidget(self.btnStart, 0, 0)
        layout.addWidget(self.btnStop, 0, 1)
        layout.addWidget(self.btnReload, 1, 0)
        layout.addWidget(self.btnRestart, 1, 1)
        layout.addWidget(self.btnAdd, 2, 0)
        layout.addWidget(self.btnRemove, 2, 1)
        layout.addWidget(self.btnEnable, 3, 0)
        layout.addWidget(self.btnDisable, 3, 1)
        self.topRightGroupBox.setLayout(layout)

    def _opt_btn_init(self, bool_value):
        self.btnRemove.setDisabled(bool_value)
        self.btnEnable.setDisabled(bool_value)
        self.btnDisable.setDisabled(bool_value)

    def _add_data(self):
        self.addDataChildWindow = AddDataChildWindow(self)
        self.addDataChildWindow.show()

    def _remove_data(self):
        if len(self.propertyData) <= 0 or len(self.selectCodes) <= 0:
            return
        for x in self.propertyData:
            for y in self.selectCodes:
                if x.code == y:
                    self.propertyData.remove(x)
        self._rewrite_data_()
        self._reload_data_()
        self._opt_btn_init(True)

    def _enable_data(self):
        if len(self.propertyData) <= 0 or len(self.selectCodes) <= 0:
            return
        for x in self.propertyData:
            for y in self.selectCodes:
                if x.code == y:
                    x.status = True
        self._rewrite_data_()
        self._reload_data_()
        self._opt_btn_init(True)

    def _disable_data(self):
        if len(self.propertyData) <= 0 or len(self.selectCodes) <= 0:
            return
        for x in self.propertyData:
            for y in self.selectCodes:
                if x.code == y:
                    x.status = False
        self._rewrite_data_()
        self._reload_data_()
        self._opt_btn_init(True)

    # 创建底部表单控件
    def _create_bottom_tab(self):
        self.bottomTabWidget = QGroupBox("Properties")
        self.widget_data = QTableView()
        self.widget_data.setLayoutDirection(Qt.LeftToRight)
        self.widget_data.setObjectName("widget_data")
        self.model = QStandardItemModel(self.widget_data)

        # 设置表格属性：
        self.model.setColumnCount(6)

        # 设置表头
        self.model.setHeaderData(0, Qt.Horizontal, '')
        self.model.setHeaderData(1, Qt.Horizontal, 'Path')
        self.model.setHeaderData(2, Qt.Horizontal, 'Status')
        self.model.setHeaderData(3, Qt.Horizontal, 'Method')
        self.model.setHeaderData(4, Qt.Horizontal, 'Head')
        self.model.setHeaderData(5, Qt.Horizontal, 'Remark')
        # self.model.setHeaderData(6, Qt.Horizontal, 'Operate')
        self.widget_data.setModel(self.model)

        # 设置列宽
        self.widget_data.setColumnWidth(0, 50)

        # 更新数据
        self._reload_data_()

        table_box = QHBoxLayout()
        table_box.setContentsMargins(5, 5, 5, 5)
        table_box.addWidget(self.widget_data)
        self.bottomTabWidget.setLayout(table_box)

    def _do_reload_data_(self):
        str_data = FileOpt.read_data_from_file(self.filePath)
        if len(str_data) <= 0:
            return
        lit = json.loads(str_data)
        result = []
        for x in lit:
            data = PropertyData.dict2data(x)
            result.append(data)
        tmp_result = []
        for x in result:
            if x.status:
                tmp_result.append(x)
        for x in result:
            if not x.status:
                tmp_result.append(x)
        return tmp_result

    def _rewrite_data_(self):
        str_data = json.dumps(self.propertyData, default=PropertyData.data2dict)
        FileOpt.write_data_from_file(self.filePath, bytes(str_data, encoding="utf8"))

    def _reload_data_(self):
        self.tableData = []
        self.propertyData = self._do_reload_data_()
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
            tmp_layout.setContentsMargins(5, 5, 5, 5)
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

            # 事件相关逻辑
            tmp_check_box.toggled.connect(self._check_box_selected)

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

    # 创建进度条
    def _create_progress_bar(self):
        self.progressBar = QProgressBar()
        self.progressBar.setRange(0, 10000)
        self.progressBar.setValue(0)
        timer = QTimer(self)
        timer.timeout.connect(self._advance_progress_bar)
        timer.start(1000)

    def _advance_progress_bar(self):
        cur_val = self.progressBar.value()
        max_val = self.progressBar.maximum()
        self.progressBar.setValue(cur_val + (max_val - cur_val) / 100)

    def _layout_main_window(self):
        main_layout = QGridLayout()
        main_layout.addLayout(self.topLayout, 0, 0, 1, 2)
        main_layout.addWidget(self.topLeftGroupBox, 1, 0)
        main_layout.addWidget(self.topRightGroupBox, 1, 1)
        main_layout.addWidget(self.bottomTabWidget, 2, 0, 1, 2)
        main_layout.addWidget(self.progressBar, 3, 0, 1, 2)
        main_layout.setRowStretch(1, 1)
        main_layout.setRowStretch(2, 1)
        main_layout.setColumnStretch(0, 1)
        main_layout.setColumnStretch(1, 1)
        self.setLayout(main_layout)


class AddDataChildWindow(QDialog):
    def __init__(self, parent=None):
        super(AddDataChildWindow, self).__init__(parent)
        self.filePath = parent.filePath
        # 布局主体
        self._layout_main_window()

        # 设置应用头部标题
        self.setWindowTitle("WidgetProxyZero Add Properties")

    def _layout_main_window(self):
        main_layout = QFormLayout()
        lab_path = QLabel("Path:")
        lab_method = QLabel("Method:")
        lab_head = QLabel("Head:")
        lab_remark = QLabel("Remark:")
        self.editPath = QLineEdit()
        self.editMethod = QLineEdit()
        method_layout = QHBoxLayout()
        self.ck_all = QCheckBox("ALL")
        self.ck_get = QCheckBox("GET")
        self.ck_post = QCheckBox("POST")
        self.ck_put = QCheckBox("PUT")
        self.ck_delete = QCheckBox("DELETE")
        self.ck_all.toggled.connect(self._select_all)
        method_layout.addWidget(self.ck_all)
        method_layout.addWidget(self.ck_get)
        method_layout.addWidget(self.ck_post)
        method_layout.addWidget(self.ck_put)
        method_layout.addWidget(self.ck_delete)

        self.editHead = QTextEdit()
        self.editHead.setText("{}")
        self.editRemark = QTextEdit()
        main_layout.addRow(lab_path, self.editPath)
        main_layout.addRow(lab_method, method_layout)
        main_layout.addRow(lab_head, self.editHead)
        main_layout.addRow(lab_remark, self.editRemark)
        self.message = QLabel()
        self.message.setStyleSheet("color:red")
        main_layout.addRow(QLabel(), self.message)
        opt_layout = QHBoxLayout()
        btn_submit = QPushButton("Submit")
        btn_submit.clicked.connect(self._add_data)
        opt_layout.addWidget(btn_submit)
        main_layout.addRow(QLabel(), opt_layout)
        self.setLayout(main_layout)

    def _select_all(self):
        if self.ck_all.isChecked():
            self.ck_get.setChecked(True)
            self.ck_post.setChecked(True)
            self.ck_put.setChecked(True)
            self.ck_delete.setChecked(True)
            return
        self.ck_get.setChecked(False)
        self.ck_post.setChecked(False)
        self.ck_put.setChecked(False)
        self.ck_delete.setChecked(False)

    def _add_data(self):
        self.path = self.editPath.text().strip()
        if len(self.path) <= 0:
            self.message.setText("Path must not empty！")
            return
        if self._check_conflict():
            self.message.setText("Path has existed！")
            return
        if not self._check_method():
            self.message.setText("Method hasn't selected！")
            return
        if not self._check_head():
            self.message.setText("Head must be json！")
            return
        self._do_add_data()

    def _do_add_data(self):
        pd = PropertyData(None, self.path, self.method_list, self.head, False, self.editRemark.toPlainText())
        self.data.append(pd)
        str_data = json.dumps(self.data, default=PropertyData.data2dict)
        FileOpt.write_data_from_file(self.filePath, bytes(str_data, encoding="utf8"))
        self.close()
        self.reject()
        self.parent()._reload_data_()

    def _check_conflict(self):
        self.data = self._do_reload_data_()
        if len(self.data) <= 0:
            return
        path = self.editPath.text().strip()
        for x in self.data:
            if x.path == path:
                return True
        return False

    def _check_method(self):
        self.method_list = []
        if self.ck_get.isChecked():
            self.method_list.append(self.ck_get.text())
        if self.ck_post.isChecked():
            self.method_list.append(self.ck_post.text())
        if self.ck_put.isChecked():
            self.method_list.append(self.ck_put.text())
        if self.ck_delete.isChecked():
            self.method_list.append(self.ck_delete.text())
        return len(self.method_list) > 0

    def _check_head(self):
        self.head = {}
        head = self.editHead.toPlainText().strip()
        if len(head) <= 0:
            return False
        try:
            self.head = json.loads(head, encoding='utf-8')
        except ValueError:
            return False
        return True

    def _do_reload_data_(self):
        str_data = FileOpt.read_data_from_file(self.filePath)
        if len(str_data) <= 0:
            return
        lit = json.loads(str_data)
        result = []
        for x in lit:
            data = PropertyData.dict2data(x)
            result.append(data)
        return result


if __name__ == '__main__':
    import sys

    app = QApplication(sys.argv)
    gallery = WidgetProxyZero()
    gallery.show()
    sys.exit(app.exec_())
