#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/7 17:39
# @Author  : boneix
import json
import time

from tornado.ioloop import IOLoop
from tornado.web import RequestHandler, Application


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


class FileHandler(RequestHandler):
    def get(self):
        filename = 'data.cfg'
        # Content-Type这里我写的时候是固定的了，也可以根据实际情况传值进来
        self.set_header('Content-Type', 'application/octet-stream')
        self.set_header('Content-Disposition', 'attachment; filename=' + filename)
        # 读取的模式需要根据实际情况进行修改
        buf_size = 4096
        with open(filename, 'rb') as f:
            while True:
                data = f.read(buf_size)
                if not data:
                    break
                self.write(data)
        # 记得有finish哦
        self.finish()


def stop(loop):
    print("loop start finish,will sleep 10s")
    time.sleep(10)
    print("loop ready to stop")
    loop.add_callback(loop.stop)
    print("loop stop finish")


if __name__ == '__main__':
    app = Application([(r"/atr/([0-9]+)", DemoHandler), (r"/file", FileHandler), ])
    app.listen(8088)
    loop = IOLoop.instance()
    #  启动一个线程
    # t1 = threading.Thread(target=stop, args=(loop,))
    # t1.setDaemon(True)
    # t1.start()
    # # async_method(ioloop=loop, callback=loop.stop)
    print("loop init finish")
    loop.start()
