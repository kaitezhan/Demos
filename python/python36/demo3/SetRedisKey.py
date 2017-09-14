import redis

from util.DateUtil import *


def get_timestamp(dateStr):
    # dateStr=time.strftime("%Y-%m-%d %X", time.localtime())
    # str to date
    # dateStr = "1988-05-08 10:11:22"
    date = time.strptime(dateStr, "%Y-%m-%d %H:%M:%S")
    return time.mktime(date)


def set_register_verify_code(mobile, code):
    r = redis.Redis(host='118.178.230.176', port=6379, db=30, password="dev@Mo9.com")
    code = {'validateCode': code, 'createTime': get_timestamp("2017-09-13 14:31:22")}
    r.set('sheep_validate_code_mobile_1.0_' + str(mobile), code)


set_register_verify_code(18066071135, 321123)
# dateStr = "1988-05-08 10:11:22"
# date = time.strptime(dateStr, "%Y-%m-%d %H:%M:%S")
# # print(type(time.localtime()))
# # print(type(date) is time.struct_time)
#
# # print(DateParser.format_date(DateParser.parse_date_time(dateStr)))
#
# # print(DateParser.parse_stamp(dateStr))
#
# # print(DateParser.format_date_time(datetime.datetime.now()))
# # print(DateParser.format_date_time(time.localtime()))
# dateStr2 = "1998-05-08"
# # print(DateOperator.days_range(DateParser.parse_date_time(dateStr), DateParser.parse_date(dateStr2)))
# days = 10
# days1 = -10
# print(type(days), type(days1))
