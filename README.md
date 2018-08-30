### 访问方式
curl -X GET "zl202:9200/_say/helloWorld?message=what%20your%20name"
需要注意的是在进行参数传递时，如果指定的参数值有特殊字符，需要进行url转码，否则会报错
