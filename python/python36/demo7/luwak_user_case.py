#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/21 16:36
# @Author  : boneix
import unittest

from demo7.luwak_auth_case import LuwakFuction
from util.DateUtil import DateParser
from util.EncryptionUtil import EncryptParser
from util.HttpUtil import SimpleHttpClient


class LuwakUserCase(object):
    def __init__(self, base_url, mobile, private_key, access_token, heads={}):
        self.mobile = mobile
        self.base_url = base_url
        self.private_key = private_key
        self.access_token = access_token
        self.heads = heads

    def logout(self):
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign("", timestamp, self.access_token)
        self.heads['Timestamp'] = str(timestamp)
        self.heads['Sign'] = str(sign)
        url = self.base_url + "/user/logout"
        method = 'POST'
        hc = SimpleHttpClient(url, method, self.heads, None, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    def modify_password(self):
        params = {'password': '333', 'newPassword': '1234567'}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(params, timestamp, self.access_token)
        self.heads['Timestamp'] = str(timestamp)
        self.heads['Sign'] = str(sign)
        url = self.base_url + "/user/modify_password"
        method = 'POST'
        hc = SimpleHttpClient(url, method, self.heads, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    def modify_info(self):
        # params = {
        #     'baseInfo': {
        #         'address': 'aaaaaaaaaa',
        #         'mobile': '008615601596366',
        #         'regionInfo': {
        #             'cityId': 3,
        #             'regionId': 4,
        #             'streetId': 5
        #         }
        #     },
        #     'certifyInfo': {
        #         'realName': 'bbb',
        #         'idCard': '62'+self.mobile,
        #         'accountFrontImg': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon',
        #         'accountBackImg': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon',
        #         'accountOcr': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon'
        #     },
        #     'jobInfo': {
        #         'companyName': 'cccc',
        #         'industry': 2,
        #         'department': 2,
        #         'position': 3,
        #         'address': 'dddd',
        #         'companyTel': '161654156714',
        #         'employeeCardUrl': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon',
        #         'regionInfo': {
        #             'cityId': 3,
        #             'regionId': 4,
        #             'streetId': 5
        #         }
        #     },
        #     'contactInfo': {
        #         'societyContactInfo': [
        #             {
        #                 'contactType': 1,
        #                 'name': 'eeee',
        #                 'mobile': '008716015915844'
        #             },
        #             {
        #                 'contactType': 1,
        #                 'name': 'fffff',
        #                 'mobile': '008716999915844'
        #             }
        #         ]
        #     }
        # }
        params = {
            'certifyInfo': {
                'realName': 'bbb',
                'idCard': '6292666516958332',
                'accountFrontImg': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon',
                'accountBackImg': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon',
                'accountOcr': 'https://secure.gravatar.com/avatar/601dc2b900c17ca9c9633ebd845eb008?s=32&d=identicon'
            },
            'contactInfo': {
                'societyContactInfo': [
                    {
                        'contactType': 1,
                        'name': 'eeee',
                        'mobile': '008716015915844'
                    },
                    {
                        'contactType': 1,
                        'name': 'fffff',
                        'mobile': '008716999915844'
                    }
                ]
            }
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(params, timestamp, self.access_token)
        self.heads['Timestamp'] = str(timestamp)
        self.heads['Sign'] = str(sign)
        self.heads['Content-Type'] = 'application/json'
        url = self.base_url + "/user/modify_info"
        method = 'POST'
        print(params)
        hc = SimpleHttpClient(url, method, self.heads, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    def modify_bank_card_info(self):
        params = {
            'cardName': '629sadd516',
            'bankName': '6292666516',
            'bankAccount': "123123123"
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign(params, timestamp, self.access_token)
        self.heads['Timestamp'] = str(timestamp)
        self.heads['Sign'] = str(sign)
        self.heads['Content-Type'] = 'application/json'
        url = self.base_url + "/user/modify_bank_card_info"
        method = 'POST'
        print(params)
        hc = SimpleHttpClient(url, method, self.heads, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    def submit_account_info(self):
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sso_sign("", timestamp, self.access_token)
        self.heads['Timestamp'] = str(timestamp)
        self.heads['Sign'] = str(sign)
        self.heads['Content-Type'] = 'application/json'
        url = self.base_url + "/user/submit_account_info"
        method = 'POST'
        hc = SimpleHttpClient(url, method, self.heads, None, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print(hs.data)
        else:
            print('http request failure, %s' % hs.message)

    # 生成签名
    def create_sso_sign(self, content, timestamp, access_token):
        str_timestamp = str(timestamp)
        if timestamp % 2 == 0:
            mo9_timestamp = str_timestamp[:10]
        else:
            mo9_timestamp = str_timestamp[-10:]
        sign_content = str(content).replace('\'', '\"')
        # print(sign_content, mo9_timestamp, self.private_key, access_token)
        str_sign = sign_content + str(mo9_timestamp) + str(self.private_key)
        if access_token is not None:
            str_sign += str(access_token)
        # print(str_sign)
        return EncryptParser.md5_str(str_sign)


class DemoTest(unittest.TestCase):
    def init(self):
        base_url = 'http://localhost:8082/api/luwak/v1'
        # base_url = 'http://47.96.174.169:9093/api/luwak/v1/'
        # base_url = 'https://idnclone.mo9.com/luwakApi/api/luwak/v1'
        mobile = "006213308627904"
        private_key = "DBD8429F5BEF1C0B50AA7E2CB1EFE65A"

        lf = LuwakFuction(base_url, mobile, private_key)
        data = lf.login_by_password()
        if data is None:
            raise Exception("登录失败")
        access_token = str(data['data']['entity']['accessToken'])
        headers = {
            'Client-Id': '302',
            'Client-Version': '0',
            'Language': 'in',
            'Country': 'ID',
            'Device-Id': 'chiputaobutuputaopi',
            'Access-Token': access_token,
            'Account-Code': str(data['data']['entity']['accountCode'])
        }
        self.lu = LuwakUserCase(base_url, mobile, private_key, access_token, headers)

    @unittest.skip('has test')
    def test_logout(self):
        self.init()
        print('已完成登录')
        self.lu.logout()

    @unittest.skip('has test')
    def test_modify_password(self):
        self.init()
        print('已完成登录')
        self.lu.modify_password()

    def test_modify_info(self):
        self.init()
        print('已完成登录')
        self.lu.modify_info()

    def test_modify_bank_card_info(self):
        self.init()
        print('已完成登录')
        self.lu.modify_bank_card_info()

    @unittest.skip('has test')
    def test_submit_account_info(self):
        self.init()
        print('已完成登录')
        self.lu.submit_account_info()


if __name__ == '__main__':
    unittest.main()
