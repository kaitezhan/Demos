#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/15 13:39
# @Author  : boneix
import asyncio
import logging

from aiohttp import web

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


@asyncio.coroutine
def init(loop):
    app = web.Application(loop=loop)
    app.router.add_get('/', handle)
    app.router.add_route('*', '/hello/{name}', hello)
    srv = yield from loop.create_server(app.make_handler(), 'localhost', 8080)
    logging.info('server started at http://localhost:8080...')
    return srv


loop = asyncio.get_event_loop()
loop.run_until_complete(init(loop))
loop.run_forever()
