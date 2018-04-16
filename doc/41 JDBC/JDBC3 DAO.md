![2 DA](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/12 DAO.png)



# 一、什么是 DAO

对 JDBC 封装的思想 —— DAO：

- DAO（Data Access Object）数据访问对象；
- 建立在数据库和业务层之间，封装所有对数据的访问；
- 目的：数据访问逻辑和业务逻辑分开。



# 二、什么是 JavaBean？

JavaBean 就是规范，满足规范的类就是 JavaBean，不满足则不是。

满足如下规范的类即是 javaBean：

- 必须有包；
- 必须有无参构造器；
- 必须实现序列化接口；
- 通常有 get/set 方法。

# 三、编写 entity

- 属性和数据库字段一致；
- 属性的类型使用包装类，因为数据库里字段存在 null，包装类有 null，基本类型没有 null。

![3 entit](/Users/chenjinhua/ziliao/JavaLearned/doc/41 JDBC/13 entity.png)



# 四、编写DAO

case 练习

注意两点

1、save 方法：

返回修改后的数据对象,注意对象是参数传进来的,只需要修改对象的 id （即主键）的值，再返回该对象即可。

2、entity里日期类型的包———— java.util.Date.