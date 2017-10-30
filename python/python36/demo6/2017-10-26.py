#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/26 14:31
# @Author  : boneix
# @Description :first pip3 install flask

from flask import Flask

app = Flask(__name__)


@app.route('/')
def hello_world():
    return "hello world!"


@app.route('/user/<username>')
def show_user_profile(username):
    return 'User %s' % username


@app.route('/post/<int:post_id>')
def show_post(post_id):
    return 'Post %s' % post_id


@app.route('/projects/')
def projects():
    return 'The project page'


@app.route('/about')
def about():
    return 'The about page'


if __name__ == '__main__':
    app.debug = True
    app.run()
