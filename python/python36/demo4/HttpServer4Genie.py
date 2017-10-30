#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/15 13:39
# @Author  : boneix
import asyncio
import logging

from aiohttp import web
import json

logging.basicConfig(level=logging.INFO)


def index(request):
    return web.Response(body=b'<h1>Awesome</h1>', content_type='text/html')


def hello(request):
    text = '<h1>hello, %s!</h1>' % request.match_info['name']
    return web.Response(body=text.encode('utf-8'), content_type='text/html')


def handle(request):
    name = request.match_info.get('name', "Anonymous")
    text = "Hello, " + name
    return web.Response(text=text)


def lucky_draw(request):
    text = {
        "code": 11060008,
        "message": "今日已抽过奖"
    }
    return web.Response(body=json.dumps(text).encode('utf-8'), content_type='application/json')


@asyncio.coroutine
def init(loop):
    app = web.Application(loop=loop)
    # app.router.add_get('/', handle)
    # app.router.add_route('*', '/hello/{name}', hello)
    # http://localhost:8080/api/genie/v1/coupon/lucky_draw
    app.router.add_route('*', '/api/genie/v1/coupon/lucky_draw', lucky_draw)
    srv = yield from loop.create_server(app.make_handler(), 'localhost', 8080)
    logging.info('server started at http://localhost:8080...')
    return srv


loop = asyncio.get_event_loop()
loop.run_until_complete(init(loop))
loop.run_forever()
