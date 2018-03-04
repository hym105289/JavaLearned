[TOC]

​	在每一个 JVM 进程里都会存在有一个 Runtime 类的对象，这个类的主要功能是取得一些与运行时有关的环境属性或者创建新的进程等操作。

​	在 Runtime 类定义的时候它的构造方法已经被私有化了，这就属于单例设计模式的应用，因为要保证在整个进程里只有唯一的一个 Runtime 类的对象。所以 在 Runtime 提供了一个方法可以取得 Runtime 实例化对象：

```java
public static Runtime getRuntime()
```

#一、Runtime 类常用方法

​	Runtime 类是直接与本地运行有关的所有相关属性的集合，所以在 Runtime 类里定义有如下的方法：

- 返回所有可用内存空间

```java
public long totalMemory()
```

- 返回最大可用空间

```java
public long maxMemory()
```

- 返回空余内存空间

```java
public long freeMemory()
```

​	如果一旦产生了过多的垃圾之后，那么就会改变可用的内存空间的大小。

- 在 Runtime 类里有一个方法，可以释放掉垃圾空间

```java
public void gc()
```

​	面试题：请解释什么叫GC？如何处理？

- GC（GarbageCollector）垃圾收集器，指的是释放无用的内存空间；
- GC会由系统不定期进行自动的回收，或者手工的调用 Runtime类中的gc()方法手工回收。

# 二、调用本机可执行程序

​	Runtime 类还有一个更加有意思的功能，就是它可以调用本机的可执行程序并且创建进程。

- 执行程序：

```java
public Process exec(String command) throws IOException
```

​	

# 三、总结

1、Runtime 类使用了单例设计模式，必须通过内部的 getRuntime() 方法才可以取得 Runtime 类的实例化对象；

2、Runtime 类提供了gc() 方法可以用于手工释放内存。