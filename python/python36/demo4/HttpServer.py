#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/15 13:39
# @Author  : boneix
import asyncio
import logging
import time

from aiohttp import web

logging.basicConfig(level=logging.INFO)


def index(request):
    return web.Response(body=b'<h1>Awesome</h1>', content_type='text/html')


def hello(request, code):
    print(code)
    text = '<h1>hello, %s!</h1>' % request.match_info['name']
    return web.Response(body=text.encode('utf-8'), content_type='text/html')


def handle(request, code):
    print(code)
    name = request.match_info.get('name', "Anonymous")
    text = "Hello, " + name
    return web.Response(text=text)


def stop(request, code):
    print(code)
    return web.Response(body=b'<h1>code</h1>', content_type='text/html')


@asyncio.coroutine
def init(loop):
    app = web.Application(loop=loop)
    app.router.add_get('/', handle)
    app.router.add_route('*', '/hello/{name}', lambda request, x="123": hello(request, x))
    # app.router.add_route('*', '/hello/{name}', hello)
    # app.router.add_route('GET,POST', '/hello/{name}', hello)

    app.router.add_get('/stop', lambda request, x="123": stop(request, x))
    srv = yield from loop.create_server(app.make_handler(), 'localhost', 8080)
    logging.info('server started at http://localhost:8080...')
    return srv


def stop_loop():
    time.sleep(5)
    logging.info('server has wait 5,server will stop.')
    loop.call_soon_threadsafe(loop.stop)


loop = asyncio.get_event_loop()
# threading.Thread(target=stop_loop).start()
loop.run_until_complete(init(loop))
try:
    loop.run_forever()
finally:
    loop.close()
