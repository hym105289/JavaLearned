[TOC]

1、如何计算某个代码的执行时间；

2、垃圾回收操作。

#一、获取当前的系统时间

​	在 System 类里定义有一个重要方法：取得当前的系统时间

```java
public static long currentTimeMillis()
```

范例：统计某个代码的执行时间：

```java
long start = System.currentTimeMillis();
long end = System.currentTimeMillis();
System.out.println("本次操作所花费的时间:" + (end - start));
```

​	如果要想统计出所花费的时间，就用 long 型数据直接进行数学计算后得来。

#二、gc() 方法

​	在 System 类里定义了 gc() 方法，这个方法间接调用了 Runtime 类中的 gc() 方法。

```java
public static void gc()
```

#三、finalize() 方法

​	对象产生一定会调用构造方法，可以进行一些处理操作，如果某一个对象如果要被回收了，那么都没一个收尾的工作。如果需要给对象一个收尾的机会，那么就可以覆写 Object 类里的 finalize() 方法。

```java
protected void finalize() throws Throwable
```

​	在对象回收时，即使抛出了任何的异常，也不会影响程序的正常执行。

​	构造方法是留给对象初始化时使用的，而 finalize() 方法是留给对象回收前使用的。

**面试题：请解释 final、finally、 finalize 的区别？**

- final：关键字，定义不能被继承的类、不能被覆写的方法和不能改变的成员变量（常量）；
- finally：关键字，异常的统一出口；
- finalize：方法，Object 类提供的方法，对象回收前的收尾方法，即使抛出了异常也不会影响程序的正常执行。

# 四、总结

1、System 类可以使用 currentTimeMillies() 方法取得当前的系统时间；

2、System 类中的gc() 方法就直接调用了 Rutime.getRuntime().gc() 方法；

3、上述面试题是关键。