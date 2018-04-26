import requests
import json
from bs4 import BeautifulSoup as soup
import os
import re 
import threading
from queue import Queue
import csv
import time


base_url = 'http://localhost:8080/rest/v1'



def geneHeader():
    user_agent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"
    header={"User-Agent":user_agent,"Content-Type":"application/json"}
    return header

def requestByGet(url,isJson=False,params=None):
    header=geneHeader()
    r=requests.get(url,headers=header,params=params)
    if(isJson):
        return r.json()
    else:
        return r.text

def requestByPost(url,isJson=False,data=None):
    header=geneHeader()
    r=requests.post(url,headers=header,data=data)
    if(isJson):
        return r.json()
    else:
        return r.text

username="admin"
password="zhqadmin"

result = requestByPost(base_url+"/auth/signin",isJson=True,data=json.dumps({"username":username,"password":password}))
print(result)
token = result['token']
print(token)
header = geneHeader()
header["Authorization"]="Bearer "+token

test="test"
result = requests.get(base_url+"/echo/test/"+test,headers=header).text
print(test==result)

print(requestByGet(base_url+"/auth/signout/"+username))

result = requests.get(base_url+"/echo/test/"+test,headers=header).text
print(result)











