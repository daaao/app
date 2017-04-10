#  J2EE快速开发框架

## 项目简介

- 使用Maven对项目进行模块化管理，提高项目的易开发性、扩展性。
- 实现了通用的系统管理模块功能，包含：用户、角色、权限、菜单、字典管理。
- 实现了基于AOP解耦的日志模块。
- 实现了通用的异常处理和响应模型、错误码标准规范。（非restful）
- 实现了基于JWT和Redis的Token认证。
- 实现了支持根据参数动态构建复杂SQL语句的构建器，基于freemarker。
- 颜值还可以的后台UI界面。

## 模块说明
- app-admin         后台管理界面模块
- app-api           开放给移动端或其他终端的接口模块
- app-auth          基于Redis的身份认证模块
- app-base          基础模块，包含底层DAO、Service等的封装
- app-file          简单的文件服务模块，使用FTP服务器上传和下载
- app-generator     代码生成器
- app-log           通用日志模块
- app-system        通用的系统管理模块
- app-utils         一些通用的工具类

## 技术选型
- 核心框架：Spring Framework 4.3.6
- 安全框架：Apache Shiro 1.3.2
- 持久层框架：Hibernate 5.2.6.Final
- 数据库连接池：Alibaba Druid 1.0.29
- Token生成和管理：JWT、Redis
- 日志管理：SLF4J、Log4j2
- 数据库：MySQL
- 后台前端框架：Jquery EasyUI 1.5.X 和 JQuery EasyUI 1.5.x of Insdep Theme 

## 使用说明

### 1. 启动说明

    * 项目依赖Redis服务，请先安装Redis客户端。
    
    * 项目有2个war包模块，请使用不同的端口运行启动。
        app-admin：是后台管理界面
        app-api：是api模块，实现了基于jwt和redis的token认证。一般应用于前后端分离的项目，如Android、IOS等客户终端调用的接口都来源于此模块，使用token进行身份认证。
        
    * 数据库：
        运行前请先创建数据库，数据库名：app-xxx（xxx表示不同环境）
        项目使用了Hibernate注解映射，会自动生成表结构
        项目启动后，导入SQL文件（app-admin模块sql文件夹下app-dev.sql）进行导入数据。
        
    * 环境配置/打包：
    	 app-admin 和 app-api 模块下都包含4套环境配置，不同的环境请自行修改里面的参数。
    	 说明：
    	 local - 本地环境
    	 dev - 开发环境
    	 test - 测试环境
    	 pro - 生成环境
    	 
    	 使用maven打包时，可以选择不同的环境配置文件
    	 
    
### 2. 基于Redis的身份认证模块使用说明
依赖app-auth模块，也可将该模块可以单独打成jar包再引用
配置具体参考app-api模块中，spring-mvc.xml的配置

### 3. 通用日志模块
依赖app-log模块，也可将该模块可以单独打成jar包再引用

在spring-context.xml中添加配置

    <!-- 激活自动代理功能 -->
    <aop:aspectj-autoproxy proxy-target-class="true" />

    <!-- 扫描切面包路径 -->
    <bean id="logAspect" class="io.zhijian.log.aop.LogAspect">
        <property name="logPoint">
            <bean class="io.zhijian.system.service.impl.SystemLogService" />
        </property>
    </bean>
    <aop:config>
        <aop:aspect ref="logAspect">
            <aop:pointcut id="logPointCut" expression="@annotation(io.zhijian.log.annotation.Log)" />
            <aop:around pointcut-ref="logPointCut" method="save" />
        </aop:aspect>
    </aop:config>

SystemLogService实现了LogPoint类中的save方法，在该方法中实现日志的存储

具体参考app-system模块中SystemLogService.java

### 4. 复杂SQL构建器使用
我为什么做了这个东西？
因为ORM框架使用了Hibernate框架，而Hibernate对于复杂的自定义的SQL的支持力度不够，或许是我不太会使用Hibernate。
在有些时候有些业务逻辑有些复杂的查询需要连接很多张表，比如统计，并且是要根据参数来动态构建的。这个时候并没有没有任何一个实体类可以与这个结果集进行映射和匹配。以往我们都是自己在dao的Java类中使用String进行动态拼接，这样我觉得写起来很恶心，而且可读性很差，所以就有了这个构建器。
很简单的东西，基于freemarker，这里我并没有考虑查询的性能。

示例：按模块来统计日志记录的数据量，若参数中有模块名则添加条件没有则统计所有
先在项目的sql文件夹下建一个select-test.sql文件，里面是查询语句
```
<#assign data = params?eval>

select module, count(*) as count
from sys_log

<#if data.module??>
 where module =:module
</#if>

group by module order by count
```
可以看到我用了freemarker的判断语法，以及assign 语法， <#assign data = params?eval> 中的params名称是固定的。

然后在dao里面添加方法
```
/**
     * 测试SQLBuilder 的使用
     * 原理：使用freemarker模板定义要执行的sql文件，支持动态参数逻辑判断，构建SQL语句
     * @param module
     * @return
     */
    @Override
    public List<TestCount> findTest(String module) {
        try {
            Map params = new HashMap<>();
            params.put("module", module);

            String json = JSON.toJSONString(params);

            String sql = SqlBuilder.buildSql(SqlConfig.getSqlPath("select-test"), json);

            NativeQuery query = getSession().createNativeQuery(sql);
            query.setResultTransformer(new BeanTransformerAdapter<>(TestCount.class));
            setParameters(query, params);

            List<TestCount> result = query.list();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }
```

BeanTransformerAdapter是对Hibernate返回的结果集的映射转换器。



## 主要功能/界面展示
 1. 登录/主界面
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203422_ea01c37b_24573.png "在这里输入图片标题")

![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203217_f903a471_24573.png "在这里输入图片标题")
 2. 用户管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203237_02adaefc_24573.png "在这里输入图片标题")
 3. 角色管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203251_fb0ac960_24573.png "在这里输入图片标题")
 4. 权限管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203306_7984a3f7_24573.png "在这里输入图片标题")
 5. 菜单管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203319_f4a01aeb_24573.png "在这里输入图片标题")
 6. 字典管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203332_689beffb_24573.png "在这里输入图片标题")
 7. 日志管理
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203347_cdaeb8a5_24573.png "在这里输入图片标题")
 8. Token生成
![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203625_17874873_24573.png "在这里输入图片标题")


## 感谢
@ScienJus
项目基于Redis的认证模块修改和集成了此项目
https://github.com/ScienJus/spring-authorization-manager

Jquery EasyUI Insdep主题
https://www.insdep.com

## 交流
### 点击加入QQ群[631511782](https://jq.qq.com/?_wv=1027&k=47ErLEy)

![输入图片说明](http://git.oschina.net/uploads/images/2017/0410/203156_d7a3b7fd_24573.png "在这里输入图片标题")
