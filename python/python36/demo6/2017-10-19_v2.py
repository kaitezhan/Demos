#!/user/bin/env python3
# -*- coding: utf-8 -*-
# --------------  case  -----------------------

from util import HttpUtil


def statistics_search():
    # url = 'http://localhost:8080/bk/api/genie/v1/statistics/search'
    # url = 'https://bkcln.mo9.com/genieApi/bk/api/genie/v1/statistics/search'
    url = 'https://bkcln.mo9.com/genieApi/bk/api/genie/v1/statistics/search'
    method = 'GET'
    headers = {
        'Ticket': 'sss',
        'User-Id': "274"
    }
    hc = HttpUtil.SimpleHttpClient(url, method, headers)
    hs = hc.execute()
    if hs.reason is True and hs.status is 200:
        print(hs.data)
    else:
        print('http request failure, %s,%s' % (hs.status, hs.message))


statistics_search()

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