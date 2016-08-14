#!/bin/bash  

online() {
  nc -z 8.8.8.8 53 >/dev/null 2>&1; [ $? -eq 0 ]
}

request() {
  res=$(curl -sw "%{http_code}" $1)
  http_code="${res:${#res}-3}"

  if [[ "$http_code" == "200" ]]; then
    echo "${res:0:${#res}-3}"
    return 0
  else
    return 1
  fi
}

token="$1"

if [ -z $token ]; then
  echo "usage \"sh $0 <token>\""
  exit 1
fi

startDate="2015-01-01"
endDate="2017-01-01"

if online; then
  response=$(request "https://www.fio.cz/ib_api/rest/periods/${token}/${startDate}/${endDate}/transactions.json")
  if [ $? -eq 0 ]; then
    echo "$response"
    exit 0
  else
    echo "unable to retrieve data"
    exit 1
  fi
fi
