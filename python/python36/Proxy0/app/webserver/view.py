#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/24 12:37
# @Author  : Boneix
import json
import os

from PyQt5.QtCore import QRegExp, Qt
from PyQt5.QtGui import QRegExpValidator, QStandardItemModel
from PyQt5.QtWidgets import QDialog, QGroupBox, QLineEdit, QFormLayout, QLabel, QPushButton, QGridLayout, QTableView, \
    QHBoxLayout, QApplication, QPlainTextEdit, QCheckBox, QTextEdit, QComboBox, QVBoxLayout, QFileDialog


class WebServerView(QDialog):
    def __init__(self, parent=None):
        super(WebServerView, self).__init__(parent)
        # 创建样式操作控件
        # self._create_style_change_layout()

        # 生成主体控件
        self._create_top_left_group_box()
        self._create_top_right_group_box()
        self._create_bottom_tab()
        self._create_bottom_log()
        # self._create_progress_bar()

        # 布局主体
        self._layout_main_window()

        # 设置应用头部标题
        self.setWindowTitle("Proxy0.WebServer")

    # 设置服务地址及端口号
    def _create_top_left_group_box(self):
        self.topLeftGroupBox = QGroupBox("Config")

        self.editHost = QLineEdit("http://localhost")
        # regex = QRegExp()
        # regex.setPattern(
        #     "((http|https)://)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))")
        # q_validate = QRegExpValidator()
        # q_validate.setRegExp(regex)
        # self.editHost.setValidator(q_validate)
        self.editHost.setDisabled(True)

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

    # 操作控件
    def _create_top_right_group_box(self):
        self.topRightGroupBox = QGroupBox("Operate")

        self.btnStart = QPushButton()
        self.btnStart.setText("Start")

        self.btnStop = QPushButton()
        self.btnStop.setText("Stop")
        self.btnStop.setDisabled(True)

        self.btnImport = QPushButton()
        self.btnImport.setText("Import")

        self.btnExport = QPushButton()
        self.btnExport.setText("Export")
        self.btnExport.setDisabled(True)

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

        layout = QGridLayout()
        layout.addWidget(self.btnStart, 0, 0)
        layout.addWidget(self.btnStop, 0, 1)
        layout.addWidget(self.btnImport, 1, 0)
        layout.addWidget(self.btnExport, 1, 1)
        layout.addWidget(self.btnAdd, 2, 0)
        layout.addWidget(self.btnRemove, 2, 1)
        layout.addWidget(self.btnEnable, 3, 0)
        layout.addWidget(self.btnDisable, 3, 1)
        self.topRightGroupBox.setLayout(layout)

    # 数据展示
    def _create_bottom_tab(self):
        self.bottomTabWidget = QGroupBox("Properties")
        self.widget_data = QTableView()
        self.widget_data.setLayoutDirection(Qt.LeftToRight)
        self.widget_data.setObjectName("widget_data")
        self.model = QStandardItemModel(self.widget_data)

        # 设置表格属性：
        self.model.setColumnCount(7)

        # 设置表头
        self.model.setHeaderData(0, Qt.Horizontal, '')
        self.model.setHeaderData(1, Qt.Horizontal, 'Path')
        self.model.setHeaderData(2, Qt.Horizontal, 'Status')
        self.model.setHeaderData(3, Qt.Horizontal, 'Method')
        self.model.setHeaderData(4, Qt.Horizontal, 'Head')
        self.model.setHeaderData(5, Qt.Horizontal, 'Remark')
        self.model.setHeaderData(6, Qt.Horizontal, 'Operate')
        self.widget_data.setModel(self.model)

        # 设置列宽
        self.widget_data.setColumnWidth(0, 50)

        table_box = QHBoxLayout()
        table_box.setContentsMargins(5, 5, 5, 5)
        table_box.addWidget(self.widget_data)
        self.bottomTabWidget.setLayout(table_box)

    # 底部日志
    def _create_bottom_log(self):
        self.bottomLogWidget = QGroupBox("Log")
        self.editLog = QPlainTextEdit()
        self.editLog.setReadOnly(True)
        log_layout = QHBoxLayout()
        log_layout.addWidget(self.editLog)
        self.bottomLogWidget.setLayout(log_layout)

    def _create_progress_bar(self):
        pass

    # 主体布局
    def _layout_main_window(self):
        main_layout = QGridLayout()
        # main_layout.addLayout(self.topLayout, 0, 0, 1, 2)
        main_layout.addWidget(self.topLeftGroupBox, 1, 0)
        main_layout.addWidget(self.topRightGroupBox, 1, 1)
        main_layout.addWidget(self.bottomTabWidget, 2, 0, 1, 2)
        main_layout.addWidget(self.bottomLogWidget, 3, 0, 1, 2)
        # main_layout.addWidget(self.progressBar, 3, 0, 1, 2)
        main_layout.setRowStretch(1, 1)
        main_layout.setRowStretch(2, 1)
        main_layout.setColumnStretch(0, 1)
        main_layout.setColumnStretch(1, 1)
        self.setLayout(main_layout)


class PathConfigOperateView(QDialog):
    def __init__(self, parent=None):
        super(PathConfigOperateView, self).__init__(parent)

        # 布局主体
        self._layout_main_window()

        self._init_action()

        # 设置应用头部标题
        self.setWindowTitle("Proxy0.WebServer#OperatePathConfig")

    def _layout_main_window(self):
        self.btnSubmit = QPushButton("Submit")
        self.editPath = QLineEdit()
        self.ck_all = QCheckBox("ALL")
        self.ck_get = QCheckBox("GET")
        self.ck_post = QCheckBox("POST")
        self.ck_put = QCheckBox("PUT")
        self.ck_delete = QCheckBox("DELETE")
        self.editHead = QTextEdit()
        self.editRemark = QTextEdit()
        self.message = QLabel()
        self.message.setStyleSheet("color:red")
        self.message.setWordWrap(True)
        self.dataType = QComboBox()
        self.dataType.addItem("Null")
        self.dataType.addItem("Text")
        self.dataType.addItem("Path")
        self.editData = QPlainTextEdit()
        self.editData.setHidden(True);
        self.btnAttach = QPushButton("...")
        self.btnAttach.setHidden(True)
        self.editFilePath = QLineEdit()
        self.editFilePath.setHidden(True)

        self.labPath = QLabel("Path:")
        self.labMethod = QLabel("Method:")
        self.labHead = QLabel("Head:")
        self.labData = QLabel("Data:")
        self.labRemark = QLabel("Remark:")

        # 隐藏的标记code
        self.labCode = QLabel()
        self.labCode.setHidden(True)

        # 提交layout
        opt_layout = QHBoxLayout()
        opt_layout.addWidget(self.btnSubmit)
        # 方法layout
        method_layout = QHBoxLayout()
        method_layout.addWidget(self.ck_all)
        method_layout.addWidget(self.ck_get)
        method_layout.addWidget(self.ck_post)
        method_layout.addWidget(self.ck_put)
        method_layout.addWidget(self.ck_delete)
        # 文件layout
        file_layout = QHBoxLayout()
        file_layout.addWidget(self.editFilePath)
        file_layout.addWidget(self.btnAttach)
        data_layout = QVBoxLayout()
        data_layout.addWidget(self.dataType)
        data_layout.addWidget(self.editData)
        data_layout.addLayout(file_layout)
        # 布局layout
        main_layout = QFormLayout()
        main_layout.addRow(self.labPath, self.editPath)
        main_layout.addRow(self.labMethod, method_layout)
        main_layout.addRow(self.labHead, self.editHead)
        main_layout.addRow(self.labData, data_layout)
        main_layout.addRow(self.labRemark, self.editRemark)
        main_layout.addRow(QLabel(), self.message)
        main_layout.addRow(self.labCode, opt_layout)

        self.setLayout(main_layout)

    def _init_action(self):
        self.ck_all.toggled.connect(self._select_all)

        self.dataType.activated[str].connect(self._change_type)
        self.btnAttach.clicked.connect(self._add_file)

        self.ck_get.toggled.connect(lambda: self._init_label(self.labMethod))
        self.ck_post.toggled.connect(lambda: self._init_label(self.labMethod))
        self.ck_put.toggled.connect(lambda: self._init_label(self.labMethod))
        self.ck_delete.toggled.connect(lambda: self._init_label(self.labMethod))
        self.editPath.textChanged.connect(lambda: self._init_label(self.labPath))
        self.editHead.textChanged.connect(lambda: self._init_label(self.labHead))
        self.btnSubmit.clicked.connect(self._data_submit)

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

    def _change_type(self, type_name):
        if type_name == "Path":
            self.editData.setHidden(True)
            self.btnAttach.setHidden(False)
            self.editFilePath.setHidden(False)
        elif type_name == "Text":
            self.editData.setHidden(False)
            self.btnAttach.setHidden(True)
            self.editFilePath.setHidden(True)
        else:
            self.editData.setHidden(True)
            self.btnAttach.setHidden(True)
            self.editFilePath.setHidden(True)

    def _add_file(self):
        file_name, file_type = QFileDialog.getOpenFileName(self, 'Open File', os.getenv('HOME'))
        self.editFilePath.setText(file_name)

    def _init_label(self, label):
        label.setStyleSheet("color:black")
        self.message.setText("")

    def _data_submit(self):
        pass

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


if __name__ == '__main__':
    import sys

    app = QApplication(sys.argv)
    # gallery = WebServerView()
    # gallery.show()
    child = PathConfigOperateView()
    child.show()
    sys.exit(app.exec_())
