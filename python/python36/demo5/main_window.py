#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/15 14:53
# @Author  : boneix
from PyQt5 import QtCore, QtWidgets, QtGui
from PyQt5.QtCore import QRegExp
from PyQt5.QtGui import QRegExpValidator


class ProxyZeroMainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(800, 600)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.gridLayoutWidget = QtWidgets.QWidget(self.centralwidget)
        self.gridLayoutWidget.setGeometry(QtCore.QRect(50, 50, 660, 440))
        self.gridLayoutWidget.setObjectName("gridLayoutWidget")
        self.layout_main = QtWidgets.QGridLayout(self.gridLayoutWidget)
        self.layout_main.setContentsMargins(0, 0, 0, 0)
        self.layout_main.setObjectName("layout_main")
        # port
        self._ui_port_()
        # host
        self._ui_host_()
        # data opt
        self._ui_data_opt_()
        # data show
        self._ui_data_show_()
        # server opt
        self._ui_server_opt_()
        # other ui
        self._ui_other_()
        MainWindow.setCentralWidget(self.centralwidget)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.label_port.setText(_translate("MainWindow", "Port:"))
        self.btn_reload.setText(_translate("MainWindow", "Reload"))
        self.btn_add.setText(_translate("MainWindow", "Add"))
        # self.btn_modify.setText(_translate("MainWindow", "Modify"))
        self.btn_remove.setText(_translate("MainWindow", "Remove"))
        self.btn_start.setText(_translate("MainWindow", "Start"))
        self.btn_stop.setText(_translate("MainWindow", "Stop"))
        self.btn_restart.setText(_translate("MainWindow", "Restart"))
        self.label_main.setText(_translate("MainWindow", "Ver.01"))
        self.label_host.setText(_translate("MainWindow", "Host:"))
        self.label_info.setText(_translate("MainWindow", "Config:"))
        self.edit_host.setText(_translate("MainWindow", "http://"))

    def _ui_port_(self):
        self.edit_port = QtWidgets.QLineEdit(self.gridLayoutWidget)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Fixed, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.edit_port.sizePolicy().hasHeightForWidth())
        self.edit_port.setSizePolicy(sizePolicy)
        self.edit_port.setCursorPosition(0)
        self.edit_port.setObjectName("edit_port")
        self.layout_main.addWidget(self.edit_port, 1, 6, 1, 1)
        # external
        # number from 1024 to 65535
        regx = QRegExp()
        regx.setPattern(
            "[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)")
        q_validate = QRegExpValidator()
        q_validate.setRegExp(regx)
        self.edit_port.setValidator(q_validate)

    def _ui_host_(self):
        self.edit_host = QtWidgets.QLineEdit(self.gridLayoutWidget)
        self.edit_host.setObjectName("edit_host")
        self.layout_main.addWidget(self.edit_host, 1, 3, 1, 2)
        # external
        # check url
        regx = QRegExp()
        regx.setPattern(
            "((http|https)://)(([a-zA-Z0-9\._-]+\.[a-zA-Z]{2,6})|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))")
        q_validate = QRegExpValidator()
        q_validate.setRegExp(regx)
        self.edit_host.setValidator(q_validate)

    def _ui_data_opt_(self):
        self.layout_data = QtWidgets.QHBoxLayout()
        self.layout_data.setObjectName("layout_data")
        self.btn_reload = QtWidgets.QPushButton(self.gridLayoutWidget)
        self.btn_reload.setObjectName("btn_reload")
        self.layout_data.addWidget(self.btn_reload)
        self.btn_add = QtWidgets.QPushButton(self.gridLayoutWidget)
        self.btn_add.setObjectName("btn_add")
        self.layout_data.addWidget(self.btn_add)
        # self.btn_modify = QtWidgets.QPushButton(self.gridLayoutWidget)
        # self.btn_modify.setObjectName("btn_modify")
        # self.layout_data.addWidget(self.btn_modify)
        self.btn_remove = QtWidgets.QPushButton(self.gridLayoutWidget)
        self.btn_remove.setObjectName("btn_remove")
        self.layout_data.addWidget(self.btn_remove)
        self.layout_main.addLayout(self.layout_data, 2, 4, 1, 3)

    def _ui_data_show_(self):
        self.widget_data = QtWidgets.QTableView(self.gridLayoutWidget)
        self.widget_data.setLayoutDirection(QtCore.Qt.LeftToRight)
        self.widget_data.setObjectName("widget_data")

        self.model = QtGui.QStandardItemModel(self.widget_data)

        # 设置表格属性：
        self.model.setRowCount(5)
        self.model.setColumnCount(6)

        # 设置表头
        self.model.setHeaderData(0, QtCore.Qt.Horizontal, '')
        self.model.setHeaderData(1, QtCore.Qt.Horizontal, 'Path')
        self.model.setHeaderData(2, QtCore.Qt.Horizontal, 'Method')
        self.model.setHeaderData(3, QtCore.Qt.Horizontal, 'Head')
        self.model.setHeaderData(4, QtCore.Qt.Horizontal, 'Status')
        self.model.setHeaderData(5, QtCore.Qt.Horizontal, 'Operate')
        self.widget_data.setModel(self.model)

        # 设置列宽
        self.widget_data.setColumnWidth(0, 50)
        self.widget_data.setColumnWidth(1, 100)
        self.widget_data.setColumnWidth(2, 100)
        self.widget_data.setColumnWidth(3, 100)
        self.widget_data.setColumnWidth(4, 100)
        self.widget_data.setColumnWidth(5, 150)
        self.widget_data.setModel(self.model)

        self.layout_main.addWidget(self.widget_data, 3, 1, 1, 6)
        # external
        # add load method to fill data
        self._reload_data_()

    def _reload_data_(self):
        tmp_btn = QtWidgets.QCheckBox()
        tmp_widget = QtWidgets.QWidget()
        tmp_layout = QtWidgets.QHBoxLayout()
        tmp_layout.addWidget(tmp_btn)
        tmp_layout.setAlignment(QtCore.Qt.AlignCenter)
        tmp_widget.setLayout(tmp_layout)
        self.widget_data.setIndexWidget(self.model.index(0, 0), tmp_widget)
        self.model.setItem(0, 1, QtGui.QStandardItem('11'))

        self.widget_data.setModel(self.model)

    def _ui_server_opt_(self):
        self.layout_run = QtWidgets.QHBoxLayout()
        self.layout_run.setObjectName("layout_run")
        self.btn_start = QtWidgets.QPushButton(self.gridLayoutWidget)
        self.btn_start.setObjectName("btn_start")
        self.layout_run.addWidget(self.btn_start)
        self.btn_stop = QtWidgets.QPushButton(self.gridLayoutWidget)
        self.btn_stop.setObjectName("btn_stop")
        self.layout_run.addWidget(self.btn_stop)
        self.btn_restart = QtWidgets.QPushButton(self.gridLayoutWidget)
        self.btn_restart.setObjectName("btn_restart")
        self.layout_run.addWidget(self.btn_restart)
        self.layout_main.addLayout(self.layout_run, 0, 4, 1, 3)

    def _ui_other_(self):
        # port label
        self.label_port = QtWidgets.QLabel(self.gridLayoutWidget)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Minimum, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.label_port.sizePolicy().hasHeightForWidth())
        self.label_port.setSizePolicy(sizePolicy)
        self.label_port.setAlignment(QtCore.Qt.AlignHCenter)
        self.label_port.setObjectName("label_port")
        self.layout_main.addWidget(self.label_port, 1, 5, 1, 1)
        # info line
        self.line_info = QtWidgets.QFrame(self.gridLayoutWidget)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.line_info.sizePolicy().hasHeightForWidth())
        self.line_info.setSizePolicy(sizePolicy)
        self.line_info.setFrameShape(QtWidgets.QFrame.HLine)
        self.line_info.setFrameShadow(QtWidgets.QFrame.Sunken)
        self.line_info.setObjectName("line_info")
        self.layout_main.addWidget(self.line_info, 2, 3, 1, 1)
        # main label
        self.label_main = QtWidgets.QLabel(self.gridLayoutWidget)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Preferred, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.label_main.sizePolicy().hasHeightForWidth())
        self.label_main.setSizePolicy(sizePolicy)
        self.label_main.setAlignment(QtCore.Qt.AlignLeading | QtCore.Qt.AlignLeft | QtCore.Qt.AlignVCenter)
        self.label_main.setObjectName("label_main")
        self.layout_main.addWidget(self.label_main, 0, 1, 1, 2)
        # host label
        self.label_host = QtWidgets.QLabel(self.gridLayoutWidget)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Preferred, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.label_host.sizePolicy().hasHeightForWidth())
        self.label_host.setSizePolicy(sizePolicy)
        self.label_host.setAlignment(QtCore.Qt.AlignLeading | QtCore.Qt.AlignLeft | QtCore.Qt.AlignVCenter)
        self.label_host.setObjectName("label_host")
        self.layout_main.addWidget(self.label_host, 1, 1, 1, 2)
        # info label
        self.label_info = QtWidgets.QLabel(self.gridLayoutWidget)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Preferred, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.label_info.sizePolicy().hasHeightForWidth())
        self.label_info.setSizePolicy(sizePolicy)
        self.label_info.setAlignment(QtCore.Qt.AlignLeading | QtCore.Qt.AlignLeft | QtCore.Qt.AlignVCenter)
        self.label_info.setObjectName("label_info")
        self.layout_main.addWidget(self.label_info, 2, 1, 1, 2)
        # tmp line
        self.line_tmp = QtWidgets.QFrame(self.gridLayoutWidget)
        self.line_tmp.setFrameShape(QtWidgets.QFrame.HLine)
        self.line_tmp.setFrameShadow(QtWidgets.QFrame.Sunken)
        self.line_tmp.setObjectName("line_tmp")
        self.layout_main.addWidget(self.line_tmp, 0, 3, 1, 1)
