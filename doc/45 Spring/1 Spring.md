[TOC]

![ Spring 体系结](/Users/chenjinhua/ziliao/JavaLearned/doc/45 Spring/1 Spring 体系结构.png)

# 一、Spring 基本概念和体系概览

关于Spring框架各模块介绍如下：

（1）Spring核心模块

​	实现了IoC功能，将类和类之间的依赖从代码中脱离出来，用配置的方式进行依赖关系描述，由IoC容器负责依赖类之间的创建、拼接、管理、获取等工作。

　　BeanFactory接口时Spring框架的核心接口，实现了容器的许多核心功能。

　　Context模块构建于核心模块之上，扩展 了BeanFactory的功能，添加了il8n国际化、Bean生命周期控制、框架事件体系、资源加载透明化等多项功能。

　　表达式语言是统一表达式语言（unified EL）的一个拓展，用于查询和管理运行期的对象。

（2）AOP模块

　　在该模块中，Spring提供了满足AOP Alliance规范的实现，还整合了AspectJ这种AOP语言级框架。

（3）数据访问和集成模块

　　Spring在DAO的抽象层面，建立了一套面向DAO层统一的异常体系。

　　同时将各种访问数据的检查型异常转化为非检查型异常，为整合各种持久层框架提供基础。

　　Spring建立起了和数据形式以及访问技术无关的统一的DAO层，借助AOP技术，Spring提供了声明式事务的功能。

（4）Web及远程操作模块

　　该模块建立在Application Context模块智商，提供了Web应用的各种工具类。如：通过Listener或Servlet初始化Spring容器，将Spring容器注册到Web容器中。

　　其次该模块还提供了多项面向WEB的功能，如透明化文件上传、Velocity、FreeMarker、XSLT的支持。

（5）Web及远程访问框架

　　Spring自己提供了一个完整的类似于Strust的MVC框架，成为Spring MVC。

　　并针对每个功能模块，Spring框架都提供了独立的jar文件，这可以方便开发者有选择地使用Spring提供的功能。



## 二、什么是 Spring ？

​	**开源的，轻量级的应用开发框架。**

- **简化开发**

Sprint 对常用的 API 做了封装，这样可以大大简化这些 API 的使用；

- **解耦**

Spring 帮我们建立对象之间的依赖关系，这样对象之间的耦合度就大大降低，代码的维护性大大提高；

- **集成其他框架**

Spring 可以将其他框架集成进来，方便这些框架的使用。



# 三、Spring 容器

## 3.1 什么是 Spring 容器？

​	Spring 框架中的一个核心模块，用于管理对象。

## 3.2 启动 Spring 容器

步骤：

1、导入 spring-webmvc jar；

2、添加 spring 配置文件；

```xml 
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="first" class="first.FirstSpringDemo"></bean>

</beans>
```

3、启动容器

```java
public class FirstSpringDemo {

    public static void main(String[] args) {
        String config = "applicationContext.xml";
        // 启动 Spring 容器
        ApplicationContext ac = new ClassPathXmlApplicationContext(config);
        FirstSpringDemo first = ac.getBean("first",FirstSpringDemo.class);
        System.out.println(first);
    }
}
```

​	ApplicationContext 是接口，ClassPathXmlApplicationContext 是一个实现类，该类会依据类路径（即 resources 文件夹）去查找spring 配置文件，然后启动容器。



# 四、Spring 容器创建对象

## 4.1 利用无参构造器创建对象（*）

步骤：

**1、为类添加无参构造器；**

**2、在配置文件里，添加 bean元素（bean 就是容器所创建的对象**）；

```xml 
# id 是 bean 的名字，要求唯一；
# class 是完整的类名(包括包名)
<bean id="first" class="first.FirstSpringDemo"></bean>
```

**3、启动容器，调用容器的 getBean 方法。**

## 4.2 静态工厂方法创建对象（了解）

spring 配置文件：

```xml
<!--使用静态方法创建对象-->
<bean id="cal1" class="java.util.Calendar" factory-method="getInstance"></bean>
```

​	factory-method 指定一个静态方法，容器会调用该类的静态方法创建一个对象。

## 4.3 实例工厂方法创建对象

spring 配置文件：

```xml
<!--使用实例工厂方法创建对象-->
<bean id="cal1" class="java.util.Calendar" factory-method="getInstance"></bean>
<bean id="time1" factory-bean="cal1" factory-method="getTime"></bean>
```

​	factory-bean 指定一个 bean的 id，容器会调用该 bean 的实例方法来创建一个对象。



# 五、作用域、延迟加载

## 5.1 作用域

1、默认情况下，容器对于每个 bean 元素，只会创建一个对象；

2、如果将作用域设置为prototype，每调用一次getBean 方法就会创建一个新的对象。

```xml
<!--指定作用域 -->
<bean id="date" class="java.util.Date" scope="prototype"></bean>
```

​	scope 用来指定作用域，缺省值是 singleton：单例即只会创建一个对象；prototype：每调用一次getBean 方法就会创建一个新的对象。

## 5.2 延迟加载

1、默认情况下，容器启动之后，会将作用域为 singleton 的 bean 创建好；

2、延迟加载指：容器启动之后，不再创建作用域为 singleton 的 bean，直到调用了 getBean 方法才创建。

```xml
<bean id="date" class="java.util.Date" lazy-init="true"></bean>
```

​	lazy-init 值为 true，表示延迟加载。



# 六、生命周期

## 6.1 初始化方法

容器创建好 bean 之后，会立即调用初始化方法。

在类里定义初始化、销毁方法：

``` java
public class FirstSpringDemo {

    public FirstSpringDemo(){
        System.out.println("FirstSpringDemo 的构造方法");
    }
    public void init(){
        System.out.println("FirstSpringDemo 的初始化方法");
    }
    public void destory(){
        System.out.println("FirstSpringDemo 的销毁方法");
    }
}
```

在 spring 配置文件里设置：

```xml
<bean id="first" class="first.FirstSpringDemo" init-method="init" destroy-method="destory"></bean>
```

​	init-method 指定初始化方法名，destroy-method 指定销毁方法名，销毁方法只对作用域为 singleton 的 bean 有效。

测试方法：

```java
    @Test
    public void test(){
        String config = "applicationContext.xml";
        // ApplicationContext对象没有提供 close 方法,需要用其子接口 AbstractApplicationContext.
        AbstractApplicationContext ac = new ClassPathXmlApplicationContext(config);
        FirstSpringDemo first = ac.getBean("first",FirstSpringDemo.class);
        System.out.println("first 对象" + first);
        // 关闭容器
        ac.close();
    }
}
```