#!/user/bin/env python3
# -*- coding: utf-8 -*-
# --------------  case  -----------------------
import hashlib
import json

from util import EncryptionUtil
from util import HttpUtil
from util.ValidationUtil import ValidationOperator


def order_callback():
    # ip = r'192.168.1.62:8280'
    # ip1 = 'localhost:8080'
    #  url = 'http://192.168.1.62:8080/muleApi/api/mule/v1/user/modify_personal_info'
    url = 'http://localhost:8080/api/mule/v1/member/order_callback'
    method = 'POST'
    post_data = {"amount": "0.01",  # 金额
                 "topuporderDealcode": "WDAEQCFONCORCOAD",  # 第三方订单号
                 "channel": "yilianh5",  # 第三方渠道
                 "dealcode": "150408407489400983",  # 交易订单号
                 "status": "success"}  # 交易状态
    post_data_str = json.dumps(post_data)
    sign_data = str('postData=' + post_data_str + "643138394F10DA5E9647709A3FA8DD7F")
    sign = hashlib.md5(sign_data.encode("utf8"))
    sign_data = str('postData=' + post_data_str + "&") + str('sign=' + sign.hexdigest() + "&")
    url = url + '?' + sign_data
    hc = HttpUtil.SimpleHttpClient(url, method)
    hs = hc.execute()
    if hs.reason is True and hs.status is 200:
        print(hs.data)
    else:
        print('http request failure, %s,%s' % (hs.status, hs.message))


def aaa():
    str_a = "asdj23751阿诗丹顿ioasfn1218751"
    str_b = EncryptionUtil.EncryptParser.base64_url_encode(str_a)
    print(type(str_b))
    str_c = EncryptionUtil.EncryptParser.base64_url_decode(str_b)
    print(str_a, str_b, str_c)


def bbb():
    print(ValidationOperator.email_chk('23363.com'))


bbb()

# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
# --------------  case  -----------------------
