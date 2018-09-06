#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/11/20 18:23
# @Author  : boneix

# unittest.skip(reason)	强制跳转。reason是跳转原因
# unittest.skipIf(condition, reason)	条件跳转，如果condition是True则跳转
# unittest.skipUnless(condition, reason)	除非condition为True，才进行调整
# unittest.expectedFailure()	标记该测试预期为失败 ，如果该测试方法运行失败，则该测试不算做失败
import unittest

from util.DateUtil import DateParser
from util.EncryptionUtil import EncryptParser
from util.HttpUtil import SimpleHttpClient


class HarbourFunction(object):
    def __init__(self, base_url, private_key, system_code):
        self.base_url = base_url
        self.private_key = private_key
        self.system_code = system_code

    # 创建账户
    def create_digital_account(self, coin_unit):
        params = {'coinUnit': coin_unit}
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sign(str(params), timestamp)
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': self.system_code,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/wallet/create_digital_account"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 提现
    def withdraw_coin(self, coin_unit, from_address, to_address, amount, business_no):
        params = {
            'coinUnit': coin_unit,
            'fromAddress': from_address,
            'toAddress': to_address,
            'amount': amount,
            'businessNo': business_no,
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sign(str(params), timestamp)
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': self.system_code,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/wallet/withdraw_coin"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 放款
    def withdraw_from_company(self, coin_unit, to_address, amount, business_no):
        params = {
            'coinUnit': coin_unit,
            'toAddress': to_address,
            'amount': amount,
            'businessNo': business_no,
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        sign = self.create_sign(str(params), timestamp)
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': self.system_code,
            'Timestamp': str(timestamp),
            'Sign': str(sign)
        }
        url = self.base_url + "/wallet/withdraw_from_company"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        print("===================请求签名：%s" % str(sign))
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 创建账户
    def create_system_code(self, user_id, gather_type, remark):
        params = {
            'userId': user_id,
            'gatherType': gather_type,
            'remark': remark,
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': self.system_code,
            'Timestamp': str(timestamp)
        }
        url = self.base_url + "/system/manage/create_system_code"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 触发定时器
    def manual_call_timer(self, user_id):
        params = {
            'userId': user_id
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': self.system_code,
            'Timestamp': str(timestamp)
        }
        url = self.base_url + "/system/compensate/manual_call_timer"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

            # 触发定时器

    def cancel_trade_record(self, user_id, system_code, business_no):
        params = {
            'userId': user_id,
            'systemCode': system_code,
            'businessNo': business_no
        }
        timestamp = DateParser.parse_stamp(DateParser.now())
        headers = {
            'Content-Type': 'application/json',
            'SystemCode': self.system_code,
            'Timestamp': str(timestamp)
        }
        url = self.base_url + "/system/compensate/cancel_trade_record"
        method = 'POST'
        print("===================请求地址：%s" % url)
        print("===================请求参数：%s" % params)
        hc = SimpleHttpClient(url, method, headers, params, 10)
        hs = hc.execute()
        if hs.reason is True and hs.status == 200:
            print("===================返回结果：%s" % hs.data)
            return hs.data
        else:
            print('===================http request failure, %s' % hs.message)

    # 生成签名
    def create_sign(self, content, timestamp):
        str_timestamp = str(timestamp)
        if timestamp % 2 == 0:
            mo9_timestamp = str_timestamp[:10]
        else:
            mo9_timestamp = str_timestamp[-10:]
        sign_content = str(content).replace('\'', '\"')
        str_sign = sign_content + str(mo9_timestamp) + str(self.private_key)
        return EncryptParser.md5_str(str_sign)


class DemoTest(unittest.TestCase):
    def setUp(self):
        print('============================= %s is ready to start...' % self._testMethodName)

    def tearDown(self):
        print('============================= %s had finished...' % self._testMethodName)

    @unittest.skip('has test')
    def test_create_system_code(self):

        # url = 'http://localhost:8082/bk/api/harbour/v1'
        # url = 'http://192.168.6.32:8082/api/harbour/v1'
        url = 'https://harbour.alpha.mo9.com/pearl-harbourApi/bk/api/harbour/v1'
        private_key = 'CC281beDAbD2DFF5'
        system_code = 'Libra-Credit-Collateral'
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.create_system_code(1, 0, "test")
        if data is None:
            print("===================创建systemCode返回为null")
            return
        if data['code'] is None:
            print("===================创建systemCode返回code为null")
            return
        if data['code'] != 0:
            print("===================创建systemCode失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================创建systemCode返回data为null")
            return
        print("===================创建systemCode返回 ，%s" % str(data['data']['entity']))

    @unittest.skip('has test')
    def test_create_digital_account(self):

        # url = 'http://localhost:8082/api/harbour/v1'
        url = 'http://192.168.6.59:8082/api/harbour/v1'
        # url = 'http://192.168.6.32:8082/api/harbour/v1'
        # url = 'https://harbour.alpha.mo9.com/pearl-harbourApi/api/harbour/v1'
        # private_key = 'CC281beDAbD2DFF5'
        # system_code = 'ioex'
        private_key = '2DA94Ab4284CeA27'
        system_code = 'owl_ltc'
        # system_code = 'owl_test'
        coin_unit = 'LTC'
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.create_digital_account(coin_unit)
        if data is None:
            print("===================创建账户返回为null")
            return
        if data['code'] is None:
            print("===================创建账户返回code为null")
            return
        if data['code'] != 0:
            print("===================创建账户失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================创建账户返回data为null")
            return
        if data['data']['entity'] is None:
            print("===================创建账户返回data.entity为null")
            return
        print("===================创建账户返回 ，%s" % str(data['data']['entity']))

    @unittest.skip('has test')
    def test_manual_call_timer(self):

        # url = 'http://localhost:8082/bk/api/harbour/v1'
        url = 'http://192.168.6.32:8082/bk/api/harbour/v1'
        private_key = '2DA94Ab4284CeA27'
        system_code = 'owl_test'
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.manual_call_timer(1)
        if data is None:
            print("===================提现定时器返回为null")
            return
        if data['code'] is None:
            print("===================提现定时器返回code为null")
            return
        if data['code'] != 0:
            print("===================提现定时器失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================提现定时器返回data为null")
            return
        print("===================提现定时器返回 ，%s" % str(data['data']['entity']))

    @unittest.skip('has test')
    def test_withdraw_eth(self):

        # url = 'http://192.168.6.32:8082/api/harbour/v1'
        # private_key = '2DA94Ab4284CeA27'
        # system_code = 'owl_test'
        url = 'http://localhost:8082/api/harbour/v1'
        private_key = 'CC281beDAbD2DFF5'
        system_code = 'ioex'
        coin_unit = 'ETH'
        from_address = '0xad001243dc70b6f3932e570b3b41960713c49f12'
        to_address = '0x22563bc3a16e9e9f552358287e0afed9450c5869'
        amount = 2
        business_no = DateParser.now_timestamp()
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.withdraw_coin(
            coin_unit,
            from_address,
            to_address,
            amount,
            business_no)
        if data is None:
            print("===================提现返回为null")
            return
        if data['code'] is None:
            print("===================提现返回code为null")
            return
        if data['code'] != 0:
            print("===================提现失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================提现返回data为null")
            return
        if data['data']['entity'] is None:
            print("===================提现返回data.entity为null")
            return
        print("===================提现返回 ，%s" % str(data['data']['entity']))

    @unittest.skip('has test')
    def test_withdraw_btc(self):

        # url = 'http://localhost:8082/api/harbour/v1'
        url = 'http://192.168.6.59:8082/api/harbour/v1'
        private_key = '2DA94Ab4284CeA27'
        system_code = 'owl_ltc'
        coin_unit = 'LTC'
        from_address = 'mpAc5defQSB8jY6o1cb7sivCxewiY2Uhvo'
        to_address = 'mr7DLvmiixx4V7N5zmDQYa7A4SrexyCdmF'
        amount = 0.023456
        business_no = DateParser.now_timestamp()
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.withdraw_coin(
            coin_unit,
            from_address,
            to_address,
            amount,
            business_no)
        if data is None:
            print("===================提现返回为null")
            return
        if data['code'] is None:
            print("===================提现返回code为null")
            return
        if data['code'] != 0:
            print("===================提现失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================提现返回data为null")
            return
        if data['data']['entity'] is None:
            print("===================提现返回data.entity为null")
            return
        print("===================提现返回 ，%s" % str(data['data']['entity']))

    @unittest.skip('has test')
    def test_withdraw_from_company_eth(self):

        url = 'http://localhost:8082/api/harbour/v1'
        private_key = 'CC281beDAbD2DFF5'
        system_code = 'ioex'
        coin_unit = 'ETH'
        to_address = '0x22563bc3a16e9e9f552358287e0afed9450c5869'
        amount = 0.01
        business_no = DateParser.now_timestamp()
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.withdraw_from_company(
            coin_unit,
            to_address,
            amount,
            business_no)
        if data is None:
            print("===================放款返回为null")
            return
        if data['code'] is None:
            print("===================放款返回code为null")
            return
        if data['code'] != 0:
            print("===================放款失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================放款返回data为null")
            return
        if data['data']['entity'] is None:
            print("===================放款返回data.entity为null")
            return
        print("===================放款返回 ，%s" % str(data['data']['entity']))

    @unittest.skip('has test')
    def test_withdraw_from_company_usdt(self):

        url = 'http://localhost:8082/api/harbour/v1'
        private_key = 'CC281beDAbD2DFF5'
        system_code = 'ioex'
        coin_unit = 'USDT'
        to_address = 'n2rbUPT7uva8oiYYMvuwrmc3WMytYaacGW'
        amount = 0.01
        business_no = DateParser.now_timestamp()
        self.harbour = HarbourFunction(url, private_key, system_code)
        # 创建账户
        data = self.harbour.withdraw_from_company(
            coin_unit,
            to_address,
            amount,
            business_no)
        if data is None:
            print("===================放款返回为null")
            return
        if data['code'] is None:
            print("===================放款返回code为null")
            return
        if data['code'] != 0:
            print("===================放款失败，%s" % str(data))
            return
        if data['data'] is None:
            print("===================放款返回data为null")
            return
        if data['data']['entity'] is None:
            print("===================放款返回data.entity为null")
            return
        print("===================放款返回 ，%s" % str(data['data']['entity']))

    def test_cancel_trade_record(self):

        # url = 'http://localhost:8082/bk/api/harbour/v1'
        url = 'https://harbour.alpha.mo9.com/pearl-harbourApi/bk/api/harbour/v1'
        private_key = '2DA94Ab4284CeA27'
        system_code = 'Libra-Credit-Cash'
        self.harbour = HarbourFunction(url, private_key, system_code)
        no_list = ['TRANSFER212644175554805760',
'TRANSFER213711898888110080',
'TRANSFER213729230968586240',
'WITHDRAW214084174343045120',
'TRANSFER214330040937086976',
'TRANSFER214330058326671360',
'TRANSFER214417309354164224',
'TRANSFER214418511269724160',
'TRANSFER214431346586550272',
'TRANSFER214431347266027520',
'TRANSFER214435875713449984',
'TRANSFER214436052272676864',
'TRANSFER214438264726093824',
'TRANSFER214438265346850816',
'TRANSFER214439692119048192',
'TRANSFER214439750382125056',
'TRANSFER214439787329748992',
'TRANSFER214439833374818304',
'TRANSFER214439911611170816',
'TRANSFER214439917281869824',
'TRANSFER214439969727447040',
'TRANSFER214440007014809600',
'TRANSFER214440020851818496',
'TRANSFER214440196752539648',
'TRANSFER214440436490567680',
'TRANSFER214441251125067776',
'TRANSFER214441460378894336',
'TRANSFER214442241815478272',
'TRANSFER214443926537371648',
'TRANSFER214444662398648320',
'TRANSFER214446975901237248',
'TRANSFER214449377102528512',
'TRANSFER214449387575705600',
'TRANSFER214449758519951360',
'TRANSFER214449759061016576',
'TRANSFER214449900685885440',
'TRANSFER214449901138870272',
'TRANSFER214450165665234944',
'TRANSFER214450166424403968',
'TRANSFER214450232857985024',
'TRANSFER214450233411633152',
'TRANSFER214450580259602432',
'TRANSFER214450729924952064',
'TRANSFER214451106472787968',
'TRANSFER214451266783281152',
'TRANSFER214451267412426752',
'TRANSFER214451913754673152',
'TRANSFER214452155078148096',
'TRANSFER214453297350705152',
'TRANSFER214456591326380032',
'TRANSFER214457249051967488',
'TRANSFER214457680473882624',
'TRANSFER214457740066553856',
'TRANSFER214457860925423616',
'TRANSFER214458310257016832',
'TRANSFER214459420917104640',
'TRANSFER214459683019161600',
'TRANSFER214460161798963200',
'TRANSFER214460481203601408',
'TRANSFER214477516520292352',
'WITHDRAW214496368293576704',
'TRANSFER214688419643654144',
'TRANSFER214690944081985536',
'TRANSFER214691143776993280',
'TRANSFER214692539666530304',
'TRANSFER214696853713715200',
'TRANSFER214701315853385728',
'TRANSFER214748036830593024',
'TRANSFER214779039796166656',
'TRANSFER214784593113382912',
'TRANSFER214787711184142336',
'TRANSFER214792752829825024',
'TRANSFER214798006786981888',
'TRANSFER214806270278893568',
'WITHDRAW214808707203072000',
'TRANSFER214834335558139904',
'TRANSFER214835080189706240',
'TRANSFER214835494318505984',
'TRANSFER214836627749470208',
'TRANSFER214837240214323200',
'TRANSFER214838578688032768',
'TRANSFER215114080778715136',
'TRANSFER215117026899787776',
'TRANSFER215119124752236544',
'WITHDRAW215151465960833024',
'TRANSFER215169233904992256',
'TRANSFER215172808550907904',
'TRANSFER216198190876065792',
'TRANSFER216207609567379456',
'TRANSFER216216156262891520',
'TRANSFER216253950989434880',
'TRANSFER216267115185831936',
'TRANSFER216272948372176896',
'TRANSFER216273087388188672',
'TRANSFER216497957837471744',
'TRANSFER216511412405534720',
'TRANSFER216511706946338816']
        for x in no_list:
            # 创建账户
            data = self.harbour.cancel_trade_record(1, system_code, x)
            if data is None:
                print("===================取消交易返回为null")
                continue
            if data['code'] is None:
                print("===================取消交易返回code为null")
                continue
            if data['code'] != 0:
                print("===================取消交易失败，%s" % str(data))
                continue
            print("===================取消交易返回 ，%s" % str(data))


if __name__ == '__main__':
    unittest.main()
