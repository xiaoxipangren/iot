#!/usr/bin/env bash
host=http://localhost:8080
path=/rest/v1/auth
type=Content-Type:application/json


url=${host}${path}

result=`curl -X POST -i -H ${type} -d '{"username":"admin","password":"zhqadmin"}' ${url}/signin`

echo ${result} | grep -Po '"token":".*?"'





