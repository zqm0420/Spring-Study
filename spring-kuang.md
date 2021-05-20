## spring配置

除了bean就是Import比较有用，导入多个资源文件，alias别名也有点用

## spring依赖注入方式

```xml
<bean id="address" class="com.zqm.pojo.Address">
    <property name="address" value="中山路"/>
</bean>

<bean id="stu" class="com.zqm.pojo.Student">
    <!--第一种，普通值注入，使用value属性-->
    <property name="name" value="张启明"/>
    <!--第二种，bean注入，使用ref属性-->
    <property name="address" ref="address"/>
    <!--数组注入-->
    <property name="books">
        <array>
            <value>红楼梦</value>
            <value>西游记</value>
            <value>三国演义</value>
            <value>水浒传</value>
        </array>
    </property>
    <!--List注入-->
    <property name="hobbys">
        <list>
            <value>打球</value>
            <value>打人</value>
        </list>
    </property>
    <!--Map注入-->
    <property name="cards">
        <map>
            <entry key="银行卡" value="2313122313231313"/>
            <entry key="身份证" value="362321199507280818"/>
        </map>
    </property>
    <!--Set注入-->
    <property name="games">
        <set>
            <value>DOTA</value>
            <value>王者荣耀</value>
        </set>
    </property>
    <!--null值注入-->
    <property name="wife">
        <null/>
    </property>
    <!--Properties注入-->
    <property name="info">
        <props>
            <prop key="driver">sfdskfjla</prop>
            <prop key="url">dsfksfs</prop>
            <prop key="username">root</prop>
            <prop key="password">123</prop>
        </props>
    </property>
</bean>
```



## scope 作用域

Spring 管理的 bean 是根据 scope 来生成的，表示 bean 的作用域，共4种，默认值是 singleton。

- singleton：单例，表示通过 IoC 容器获取的 bean 是唯一的。
- prototype：原型，表示通过 IoC 容器获取的 bean 是不同的。
- request：请求，表示在一次 HTTP 请求内有效。
- session：回话，表示在一个用户会话内有效。

request 和 session 只适用于 Web 项目，大多数情况下，使用单例和原型较多。

prototype 模式当业务代码获取 IoC 容器中的 bean 时，Spring 才去调用无参构造创建对应的 bean。

singleton 模式无论业务代码是否获取 IoC 容器中的 bean，Spring 在加载 spring.xml 时就会创建 bean。



## spring自动装配

- byName需要保证所有bean的id唯一，并且这个id需要和自动注入的属性的set方法的值相等
- byType需要保证所有class的id唯一，并且这个id需要和自动注入的属性的类型相等，id可以省略

### 使用注解进行自动装配

jdk1.5支持注解，spring2.5支持注解

使用注解须知：

- 导入约束：context约束

- 配置注解支持：\<context:annotation-config/>

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
          https://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/context
          https://www.springframework.org/schema/context/spring-context.xsd">
  
      <context:annotation-config/>
  
  </beans>
  ```

@Autowired

直接在属性上使用即可，也可以在set方法上使用！

可以省略掉set方法，前提是该属性在ioc中已注入

@Qualifier

如果有多个类型相同的属性存在于ioc容器中，@Autowired无法识别，可以使用该注解显式指定

## spring注解

spring4后使用注解必须导入aop包

使用注解需要指定要扫描的包，这个包下注解就会生效

```xml
<context:component-scan base-package="com.zqm"/>
```



- @Autowired：自动装配通过类型，名字。如果AutoWired不能唯一自动装配上属性，则需要通过@Qualifier(value="")
- @Component：组件，放在类上，说明这个类被Spring管理了，就是bean
- @Value：注入属性，相当于\<property name="" valued="">
- @repository， @Controller， @Service
- @Scope：作用域

小结：

xml与注解：

- xml更加万能，适用所有场合！维护简单方便
- 注解只能在自己的类使用，维护相对复杂

xml与注解最佳实践：

- xml用来管理bean
- 注解只负责完成属性的注入

## JavaConfig实现配置

使用纯Java代码配合注解来代替xml配置

**注意ApplicationContext的实例需要使用AnnotationCinfigApplicationContext类来new！**

```java
package com.zqm.config;

import com.zqm.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@configuration代表这是一个配置类
public class MyConfig {

    //注册一个Bean，相当于xml中写的Bean标签
    //返回类型User相当于class属性
    //方法名user相当于id属性
    @Bean
    public User user(){
        return new User();
    }
}
```

## 代理模式

### 静态代理

角色分析：

- 抽象角色：一般使用接口或者抽象类解决
- 真实角色：被代理的角色（如房东）
- 代理角色：代理真实角色，代理后一般会做一些附属操作（如中介）
- 客户：访问代理对象的人！

代理模式的好处：

- 可以使真实角色的操作更加纯粹！不用去关注一些 公共的业务

- 公共业务交给代理角色！实现了业务的分工！

- 公共业务发生扩展的时候，方便集中管理！

缺点：

- 一个真实角色就会产生一个代理角色；代码量翻倍，开发效率会变低

### 动态代理

- 动态代理和静态代理角色一样

- 动态代理的代理类是动态生成的，不是我们直接写好的

- 动态代理分为两大类：基于接口的动态代理和基于类的动态代理

  - 基于接口---JDK动态代理
  - 基于类---cglib
  - java字节码实现---JAVAssist

  #### JDK动态代理：

  需要两个类：Proxy：代理；InvocationHandler：调用处理程序

  动态代理的好处：

  - 可以使真实角色的操作更加纯粹！不用去关注一些公共的业务
  - 公共业务交给代理角色！实现了业务的分工！
  - 公共业务发生扩展的时候，方便集中管理
  - 一个动态代理类代理的是一个接口，一般就是对应的一类业务
  - 一个动态代理类可以代理多个类，只要是实现了同一个接口即可

## spring-aop

导包：

```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
```

### 方法一：使用原生Spring API接口

编写java类**实现org.springframework.aop包下的几个接口**，实现方法，然后将这几个类再spring中注册，例如beforeLog和afterLog，然后在下面的代码中添加执行环绕增加

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册bean-->
    <bean id="beforeLog" class="com.zqm.log.BeforeLog"/>
    <bean id="afterLog" class="com.zqm.log.AfterLog"/>
    <bean id="userService" class="com.zqm.service.UserServiceImpl"/>

    <!--方法一：使用原生Spring API接口-->
    <!--配置aop：需要导入aop约束-->
    <aop:config>
        <!--切入点：expression：表达式，execution(类型修饰符 类名 方法名 参数)-->
        <aop:pointcut id="pointcut" expression="execution(* com.zqm.service.UserServiceImpl.*(..))"/>

        <!--执行环绕增加-->
        <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
</beans>
```

### 方法二：自定义类实现

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd

">
    <!--方法二：使用自定义类实现-->
    <bean id="diy" class="com.zqm.diy.DiyPointCut"/>

    <aop:config>
        <!--自定义切面，ref要引用的类-->

        <aop:aspect ref="diy">
            <!--切入点-->
            <aop:pointcut id="point" expression="execution(* com.zqm.service.UserServiceImpl.*(..))"/>

            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
</beans>
```

### 方法三：注解实现（使用最多）

```java
package com.zqm.diy;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect //标记这个类是个切面
public class AnnotationPointCut {
    @Before("execution(* com.zqm.service.UserServiceImpl.*(..))")
    public void before(){
        System.out.println("====方法执行前====");
    }

    @After("execution(* com.zqm.service.UserServiceImpl.*(..))")
    public void after(){
        System.out.println("====方法执行后====");
    }
}

```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd

">
    <!--方式三：使用注解实现aop-->
    <bean class="com.zqm.diy.AnnotationPointCut" id="annotationPointCut"/>
    <!--开启注解支持！-->
    <aop:aspectj-autoproxy/>
</beans>
```

## spring整合mybatis

### 导包

必选

- mybatis
- spring-webmvc
- spring-jdbc（spring操作数据库）
- spring-mybatis（NEW!）
- mysql-connector-java(数据库相关)
- aspectjweaver(aop织入)

可选

- junit
- lombok

### 入门

要和 Spring 一起使用 MyBatis，需要在 Spring 应用上下文中定义至少两样东西：一个 `SqlSessionFactory` 和至少一个数据映射器类（DataSource）。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--要和 Spring 一起使用 MyBatis，需要在 Spring 应用上下文中定义至少两样东西：
    一个 `SqlSessionFactory` 和至少一个数据映射器类（DataSource）。-->
    <bean class="org.springframework.jdbc.datasource.DriverManagerDataSource" id="dataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai"/>
        <property name="username" value="root"/>
        <property name="password" value="123"/>
    </bean>
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
</beans>
```

然后使用SqlSessionTemplate，可以帮助我们省去每次创建SqlSession的步骤，Spring自动创建SqlSession。

1. 这需要我们创建一个UserMapper接口的实现类，在其中创建私有变量SqlSessionTemplate和set方法

   ```java
   package com.zqm.dao;
   
   import com.zqm.pojo.User;
   import org.mybatis.spring.SqlSessionTemplate;
   
   import java.util.List;
   
   public class UserMapperImpl implements UserMapper{
       private SqlSessionTemplate sqlSession;
   
       public void setSqlSession(SqlSessionTemplate sqlSession) {
           this.sqlSession = sqlSession;
       }
   
       public List<User> selectUser() {
           return sqlSession.getMapper(UserMapper.class).selectUser();
       }
   }
   ```

2. 在spring配置文件中注册SqlSessionTemplate的bean

   ```xml
   <!--使用SqlSessionTemplate-->
   <bean class="org.mybatis.spring.SqlSessionTemplate" id="sqlSessionTemplate">
       <constructor-arg index="0" ref="sqlSessionFactory"/>
   </bean>
   ```

3. 然后注册实现类的bean。

   ```xml
   <bean class="com.zqm.dao.UserMapperImpl" id="userMapper">
       <property name="sqlSession" ref="sqlSessionTemplate"/>
   </bean>
   ```

这样在测试类中就可以直接获得UserMapper的对象来执行相应方法，执行数据库相应操作

## spring事务

**spring支持编程式事务管理和声明式事务管理两种方式。**

### 声明式事务

**声明式事务**是建立在AOP之上的。其本质是对方法前后进行拦截，然后在目标方法开始之前创建或者加入一个事务，在执行完目标方法之后根据执行情况提交或者回滚事务。声明式事务最大的优点就是不需要通过编程的方式管理事务，这样就不需要在业务逻辑代码中掺杂事务管理的代码，只需在配置文件中做相关的事务规则声明(或通过基于@Transactional注解的方式)，便可以将事务规则应用到业务逻辑中。

1. xml配置文件需要引入tx和aop命名空间声明

2. 配置声明式事务

   ```xml
   <!--配置声明式事务-->
   <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
       <property name="dataSource" ref="dataSource"/>
   </bean>
   ```

3. 结合AOP实现事务的织入

   ```xml
   <!--结合AOP实现事务的织入-->
   <!--配置事务通知-->
   <tx:advice id="txAdvice" transaction-manager="transactionManager">
       <!--需要给哪些方法配置事务-->
       <!--配置事务的传播特性：propagation-->
       <tx:attributes>
           <tx:method name="*" propagation="REQUIRED"/>
       </tx:attributes>
   </tx:advice>
   <!--配置事务切入-->
   <aop:config>
       <aop:pointcut id="txPointCut" expression="execution(* com.zqm.dao.*.*(..))"/>
       <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
   </aop:config>
   ```

   