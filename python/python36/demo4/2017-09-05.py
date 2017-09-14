#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/5 16:06
# @Author  : boneix
import traceback
import unittest

from elasticsearch import Elasticsearch
from pymongo import MongoClient


class DBTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    def test_01(self):
        print('%s is testing... ' % self._testMethodName)

        with MongoClient('localhost', 27017) as conn:
            # 尝试连接python数据库，没有则自动创建
            db = conn.python
            # 尝试连接到test集合
            test_set = db.test
            # 从MongoDB中查询数据，由于在Elasticsearch使用自动生成_id，因此从MongoDB查询
            # 返回的结果中将_id去掉。
            result = [x for x in test_set.find({}, projection={'_id': False})]

        if result is None:
            print('database has not data... ')
            return

        es = Elasticsearch()

        index_mapping = {
            "mappings": {
                "user": {
                    "properties": {
                        "name": {
                            "type": "text"
                        },
                        "userId": {
                            "type": "integer"
                        }
                    }
                }
            }
        }
        if not es.indices.exists('demo_index'):
            es.indices.create('demo_index', body=index_mapping)
        for child in result:
            try:
                es.index('demo_index', 'user_info', child, refresh=True)
            except:
                traceback.print_exc()
        _query_all = {
            'query': {
                'match_all': {}
            }
        }
        _searched = es.search(index='demo_index', doc_type='user_info', body=_query_all)
        # print(_searched, flush=True)
        for hit in _searched['hits']['hits']:
            print(hit['_source'], flush=True)


if __name__ == '__main__':
    unittest.main()
