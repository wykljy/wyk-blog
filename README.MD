# 前言 
 ## 着手这个项目的目的就是想通过自己平时的积累，在代码中去锻炼一下自己，发现自己的不足
 
 ## 问题记录
 * 静态文件路径默认存放在src.main.resources.static 目录,所以页面文件默认也是访问这个位置,
 为了shiro 对静态文件权限的管理，特意在static 目录下建立assert 目录，方便统一文件目录去放权限制
 * shiro 对应其权限perm 如user:list 如果结果集中有空，会影响判断
 
 ##关于本次项目的介绍(2020.02.01 17:39)
 * 后台框架主要基于 springboot+mybatis实现
 * 权限主要是通过shiro来实现 用户--角色--权限 三者的对应关系,包括登录身份验证
 以及页面对应菜单及按钮权限功能的判断
 * 数据库用的是mysql
 * 前端页面实现主要采用layui的框架实现
 * 登录验证码采用的是hutool 工具包提供的方法做实现
 * 系统目前主要实现了用户登录身份认证，用户列表的查询，修改，删除，添加等
 * 列表数据的获取加入了分页，也是采用了pagehelper的依赖实现
 * 其他功能有待完善
 
 ## redis 配置说明
 * spring.redis.database=0  Redis 数据库索引,默认为0
 * spring.redis.host=localhost Redis 服务器地址
 * spring.redis.port=6379  Redis 服务器连接端口
 * spring.redis.password=123456 Redis 服务器连接密码,默认为空
 * spring.redis.lettuce.pool.max-active=8 连接池最大连接数
 * spring.redis.lettuce.pool.max-wait=-1 连接池最大阻塞等待时间,-1表示没有
 * spring.redis.lettuce.pool.max-idle=8 连接池中的最大空闲连接
 * spring.redis.lettuce.pool.min-idle=0 连接池中最小空闲连接
 * spring.redis.timeout=10000 连接超时时间(毫秒)
 