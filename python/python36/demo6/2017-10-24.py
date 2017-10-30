#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/10/24 11:26
# @Author  : boneix
# @Description :first "pip3 install tornado"
import json
import threading
import time

from tornado.ioloop import IOLoop
from tornado.web import RequestHandler, Application


class MainHandler(RequestHandler):
    def get(self):
        self.write("this is a main page")


class DemoHandler(RequestHandler):
    def get(self, story_id):
        self.write("this is a " + story_id)

    def post(self, a):
        res = json.loads(self.request.body)
        self.set_header("Content-Type", "text/plain")
        if res is not None and res["data"] is not None:
            self.write("this is a post page" + json.dumps(res["data"]))
        else:
            self.write("this is a post page")


def stop(loop):
    print("loop start finish,will sleep 10s")
    time.sleep(10)
    print("loop ready to stop")
    loop.add_callback(loop.stop)
    print("loop stop finish")


if __name__ == '__main__':
    app = Application([(r"/", MainHandler), (r"/atr/([0-9]+)", DemoHandler)])
    app.listen(8088)
    loop = IOLoop.instance()
    # 启动一个线程
    t1 = threading.Thread(target=stop, args=(loop,))
    t1.setDaemon(True)
    t1.start()
    # async_method(ioloop=loop, callback=loop.stop)

    print("loop init finish")
    loop.start()
