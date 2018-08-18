# 自定义配置中心服务端程序


**说明**

1. 本项目旨在统一管理各个项目中常见的配置信息
2. 配合[客户端](https://github.com/lhyf/config-client)的使用,客户端可以自动获取配置中心的配置,
   并覆盖自己项目中部分配置(不是所有的配置都能使用配置中心来覆盖,如日志输出位置等等)


**基本概念**
1. app :项目,每一个项目即为一个app
2. env :环境,每个项目有多个环境,如开发环境,测试环境,生产环境;不同环境有不同的配置文件,如数据库连接信息等等
3. namespace :配置逻辑分组,类似于配置文件,mysql一个单独的配置文件,Redis一个单独的配置文件等等,这样不同的配置文件对应不同的namespace
4. item :具体的配置项 key-value 形式
5. pub_namespace :抽取公共的配置文件分组
6. pub_item :公共的配置文组下的具体的配置项


**使用**
1. 通过客户端获取: 获取配置将会从指定的appi下的指定环境下的,指定namespace下获取,若namespace没有配置,则直接获取指定appid下的,指定环境下的所有namespace下的配置
2. 通过 url 获取(主要针对于无法使用java客户端的项目,即可以通过请求连接获取配置项)
 ①. 获取某个namespace下的配置: http://ip:prot/api/config/appid/environment/namespace
 ②. 获取环境下所有namespace 的配置 http://ip:prot/api/config/appid/environment
返回格式为包含各个配置项形式的json串

**初始默认登录账号密码**
tom/123456