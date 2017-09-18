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
                             QWidget)

from demo5.data_class import PropertyData
from demo5.file_opt import FileOpt


class WidgetProxyZero(QDialog):
    def __init__(self, parent=None, path=""):
        super(WidgetProxyZero, self).__init__(parent)
        if len(path.strip()) <= 0:
            path = os.path.join(os.getcwd(), 'data.cfg')
        self.filePath = path
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

        layout = QFormLayout()
        layout.addRow(self.btnStart, self.btnStop)
        layout.addRow(self.btnReload, self.btnRestart)
        layout.addRow(self.btnAdd, self.btnRemove)
        layout.addRow(self.btnEnable, self.btnDisable)
        self.topRightGroupBox.setLayout(layout)

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
        self.model.setHeaderData(2, Qt.Horizontal, 'Method')
        self.model.setHeaderData(3, Qt.Horizontal, 'Head')
        self.model.setHeaderData(4, Qt.Horizontal, 'Status')
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
        str_data= FileOpt.read_data_from_file()
        return str_data.split()

    def _reload_data_(self):
        self.tableData = []
        self.selectCodes = []

        # TODO 读取数据
        property_data = self._do_reload_data_()
        table_len = len(property_data)
        if table_len <= 0:
            return

        # 设置行数
        self.model.setRowCount(table_len)

        # 迭代数据
        for i, value in enumerate(property_data):
            # TODO 反序列化value
            # json.dumps(value,default=PropertyData.data2dict)
            data = json.loads(value, default=PropertyData.dict2data)

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
            self.model.setItem(i, 1, QStandardItem(data.path))
            self.model.setItem(i, 2, QStandardItem(json.dumps(data.method)))
            self.model.setItem(i, 3, QStandardItem(json.dumps(data.head)))
            self.model.setItem(i, 4, QStandardItem(str(data.status)))
            self.model.setItem(i, 5, QStandardItem(data.remark))

            # 事件相关逻辑
            tmp_check_box.toggled.connect(self._check_box_selected)

            self.tableData.append(tmp_check_box)

        # tmp_btn_disable = QPushButton()
        # tmp_btn_disable.setText("Disable")
        # tmp_btn_remove = QPushButton()
        # tmp_btn_remove.setText("Remove")
        # opt_widget = QWidget()
        # opt_layout = QHBoxLayout()
        # opt_layout.addWidget(tmp_btn_disable)
        # opt_layout.addWidget(tmp_btn_remove)
        # opt_layout.setContentsMargins(5, 5, 5, 5)
        # opt_widget.setLayout(opt_layout)
        # self.widget_data.setIndexWidget(self.model.index(0, 6), opt_widget)

        self.widget_data.setModel(self.model)

    # 数据被选中时的事件
    def _check_box_selected(self):
        if len(self.tableData) <= 0:
            return

        for x in self.tableData:
            if x.isChecked():
                self.selectCodes.append(x.objectName())

        if len(self.selectCodes) <= 0:
            return

        self.btnRemove.setDisabled(False)
        self.btnEnable.setDisabled(False)
        self.btnDisable.setDisabled(False)

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


if __name__ == '__main__':
    import sys

    app = QApplication(sys.argv)
    gallery = WidgetProxyZero()
    gallery.show()
    sys.exit(app.exec_())
