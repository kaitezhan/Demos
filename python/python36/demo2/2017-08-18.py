#!/user/bin/env python3
# -*- coding: utf-8 -*-
#--------------  case  -----------------------
from requests.exceptions import RequestException
import requests,json
class RequestResult(object):
	def __init__(self,status=None,reason=True,message=None,data=None):
		self.status=status
		self.reason=reason
		self.message=message
		self.data=data

class SimpleHttpClient(object):
	def __init__(self,url,method,headers=None,data=None):
		self.url=url
		self.method=method
		if(None==headers):
			headers={}
		self.headers=headers
		if(None==data):
			data={}
		self.data=data

	def execute(self):
		result=RequestResult()
		hs=None
		try:
			if 'get'==self.method.lower():
				hs= self._get_data_by_url()
			elif 'post'==self.method.lower():
				hs= self._post_data_by_url()
			else:
				raise ValueError('http method %s is not support. ' % (self.method.lower(),))
		except RequestException as e:
			result.reason=False
			result.message=e.__class__.__name__
			print('http request raise exception %s' % e.__class__.__name__)
			return result
		if(None==hs):
			raise SystemError('http request failure')
		result.status=hs.status_code
		result.data=hs.json()
		result.message='success'
		return result

	def _get_data_by_url(self):
		return requests.get(self.url, headers=self.headers, params=self.data)

	def _post_data_by_url(self):
		if(None!=self.data):
			self.data=self.data.encode('utf-8')
		return requests.post(self.url, headers=self.headers, data=self.data)


def login_by_password(mobile,password):
	address=r'http://localhost:8080/api/mule/v1'
	url=address+r'/user/login'
	# data=r'{"mobile":"'+mobile+r'","password":"'+password+r'"}'
	data={"mobile":mobile,"password":password}
	print(json.dumps(data))
	headers={
		'Content-Type':'application/json'
		}
	method = 'POST'
	hc=SimpleHttpClient(url,method,headers,json.dumps(data))
	hs=hc.execute()
	if(hs.reason==True and hs.status==200):
		return hs.data['data']['entity']
	else:
		return None

def fill_user_info():
	mobile='18066077801'
	data=login_by_password(mobile,'xxsYYF820')
	method = 'POST'
	address=r'http://localhost:8080/api/mule/v1'
	url=address+r'/user/fill_user_information'
	# data=r'{"mobile":'+mobile+r',"sheepAccountId":'+str(data['sheepAccountId'])+r',"hasPassword":'+str(data['hasPassword'])+r'}'
	data={"mobile":mobile,"sheepAccountId":str(data['sheepAccountId']),"hasPassword":str(data['hasPassword'])}
	print(json.dumps(data))
	headers={'Content-Type':'application/json'}
	hc=SimpleHttpClient(url,method,headers,json.dumps(data))
	hs=hc.execute()
	if(hs.reason==True and hs.status==200):
		return hs.data
	else:
		return None
print(fill_user_info())
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
#--------------  case  -----------------------
