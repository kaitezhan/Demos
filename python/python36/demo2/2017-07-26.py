#!/user/bin/env python3
# -*- coding: utf-8 -*-
#--------------  case  -----------------------
# import socket
# s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
# s.connect(('www.sina.com.cn',80))
# s.send(b'GET / HTTP/1.1\r\nHost: www.sina.com.cn\r\nConnection: close\r\n\r\n')
# buffer=[]
# while True:
# 	d=s.recv(1024)
# 	if d:
# 		buffer.append(d)
# 	else:
# 		break
# data=b''.join(buffer)
# s.close()
# header,html=data.split(b'\r\n\r\n',1)
# print(header.decode('utf-8'))
#--------------  case  -----------------------
# import time,threading
# import socket

# def tcplink(sock,addr):
# 	print('Accept new connection from %s:%s...' % addr)
# 	sock.send(b'Welcome!')
# 	while True:
# 		data=sock.recv(1024)
# 		time.sleep(1)
# 		if not data or data.decode('utf-8')==exit:
# 			break
# 		sock.send(('Hello, %s!' % data.decode('utf-8')).encode('utf-8'))
# 	sock.close()
# 	print('Connection from %s:%s closed.' % addr)

# s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
# s.bind(('127.0.0.1',9999))
# s.listen(5)
# print('Waiting for connection...')
# while  True:
# 	sock,addr=s.accept()
# 	t=threading.Thread(target=tcplink,args=(sock,addr))
# 	t.start()


#--------------  case  -----------------------
# import socket
# s=socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
# s.bind(('127.0.0.1',9999))
# print('Bind Udp on 9999...')
# while True:
# 	data,addr=s.recvfrom(1024)
# 	print('Received from %s:%s ' % (data,addr))
# 	s.sendto(b'hello,%s!'% data,addr)
#--------------  case  -----------------------
# from email.mime.text import MIMEText
# msg=MIMEText('Hello,send by python...','plain','utf-8')
# from_add='notice@mo9.com'
# password='8973Ukcnm'
# to_addr='rzhang@mo9.com'
# smtp_server='smtp.exmail.qq.com'
# smtp_port=465
# import smtplib
# server=smtplib.SMTP(smtp_server,smtp_port)
# server.set_debuglevel(1)
# server.login(from_add,password)
# server.sendmail(from_add,[to_addr],msg.as_string())
# server.quit()
#--------------  case  -----------------------
# import mysql.connector
# conn=mysql.connector.connect(user='root',password='bone@1992',database='test')
# cursor=conn.cursor()
# # cursor.execute('create table user (id varchar(20) primary key, name varchar(20))')
# cursor.execute('insert into user (id, name) values (%s, %s)', ['2', 'Brusoh'])
# print(cursor.rowcount)
# conn.commit()
# cursor.close()
# cursor=conn.cursor()
# cursor.execute('select * from user ')
# values=cursor.fetchall()
# print(values)
# cursor.close()
# conn.close()
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
