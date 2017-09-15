#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/14 13:44
# @Author  : boneix

import sys

from PyQt5.QtWidgets import QApplication, QMainWindow

from demo5 import main_window

if __name__ == '__main__':
    app = QApplication(sys.argv)
    MainWindow = QMainWindow()
    # ui = test.Ui_MainWindow()
    ui = main_window.ProxyZeroMainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
