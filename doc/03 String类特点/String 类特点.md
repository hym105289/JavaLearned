[TOC]

#String 类特点

String 是一个字符串类型的类，使用双引号定义的内容都是字符串，但是 String 本身是一个类，使用上会有一些特殊，我们必须从类的角度与内存关系上来分析这个类的作用。

#一、 String类对象的两种实例化方式

##1.1 直接赋值

在之前使用过 String，最早使用的时候是直接采用了  String 变量 = “字符串”；语法形式定义的。这也称为 直接赋值。

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "Hello World!";
>		System.out.println(str);
>}
>```

以上就是 String 对象的直接赋值，代码并没有使用关键字 new 进行。在 String 类里面实际上也定义了构造方法。

##1.2 构造方法赋值

构造方法：public String(String str)，在构造方法里面依然要接收一个本类对象；

举例：利用构造方法实例化

> ```java
> public class StringDemo{
> 	public static void main(String args[]){
> 		String str = new String("Hello World");
> 		System.out.println(str);
> }
> ```

​	String 类有两种形式，主观上会认为第二种构造方法的形式更加适合于我们，因为只要是类就要用关键字 new 的做法似乎很符合道理的。

#二、字符串比较：== 和 equals()区别

##2.1 比较内存地址

通过内存关系比较，理解 == 和 equals() 的区别。

​	判断两个基本数据类型的数据是否相等，可以使用 “ == ” 来完成。但是需要知道的是在 String 上也可以使用“ == ” 比较，那比较的结果呢？

举例：

> ```java
> public class StringDemo{
> 	public static void main(String args[]){
> 		String strA = "Hello World";
> 		String strB = new String("Hello World");
> 		String strC = strB;
> 		System.out.println(strA == strB);  // false
> 		System.out.println(strA == strC);  // false
> 		System.out.println(strB == strC);  // true
> }
> ```

图1

从内存关系分析来看，== 比较的是内存地址的数值，并不是字符串包含的内容。所以 **== 属于数值比较，比较的是内存地址**。

## 2.2 比较字符串内容

而如果想比较字符串内容的话，可以使用 String 类里定义的方法：

- 比较内容（与原始定义有一些差别）：public boolean equals(String str);

  举例：实现内容比较

  > ```java
  > public class StringDemo{
  > 	public static void main(String args[]){
  > 		String strA = "Hello World";
  > 		String strB = new String("Hello World");
  > 		String strC = strB;
  >
  > 		System.out.println(strA.equals(strB));  // true
  > 		System.out.println(strA.equals(strC));	// true
  > 		System.out.println(strB.equals(strC));	// true
  > 	}
  > }
  > ```

此时实现了字符串内容的比较，在以后的开发之中，**只要是进行字符串的判断，千万不要使用 == 完成**。



**面试题**：请解释在字符串比较中， == 与 equals() 的区别。

- “ == ” 是 java 提供的关系运算符，主要的功能是进行数值相等判断，如果用在了 String 对象上则表示的是内存地址数值的比较。
- “ equals() ” 是由 String 类提供的一个方法，此方法专门负责进行字符串内容的比较


#三、字符串常量就是 String 的匿名对象

举例：观察字符串是匿名对象的验证

> ```java
> public class StringDemo{
> 	public static void main(String args[]){
> 		String str = "Hello";
> 		System.out.println("Hello".equals(str)); 
> }
> ```

​	**那么所谓的直接赋值相当于将一个匿名对象设置了一个名字。但是唯一的区别是： String 类的匿名对象是由系统自动生成的，不再由用户自己创建。**

**小小技巧：为了避免空指向异常，如果要判断输入的内容是否是某一字符串，请一定要将字符串写在调用方法前面。**

#四、两种实例化方式的区别（重点）

##4.1 分析直接赋值

**直接赋值就是将一个字符串的匿名对象设置了一个名字。**

> ```java
> String str = "Hello";
> ```

此时在内存之中会开辟一块堆内存，并且由一块栈内存指向此堆内存。但是使用直接赋值还需要多观察一下。

> ```java
> public class StringDemo{
> 	public static void main(String args[]){
> 		String strA = "Hello";
> 		String strB = "Hello";
> 		String strC = "Hello";
> 		System.out.println(strA == strB);  // true
> 		System.out.println(strA == strC);  // true
> 		System.out.println(strB == strC);  // true
> }
> ```

​	发现以上的最终结果是：**所有采用直接赋值的 String 类对象的内存地址完全相同，即 strA、strB、strC指向了同一块堆内存空间。**

**图2**

​	共享设计模式：在 JVM 底层实际上会存在一个对象池（不一定只保存 String 对象），当代码之中使用了直接赋值的方式定义了一个 String 类对象时，会将此字符串对象所使用的匿名对象入池保存，如果后续还有其它 String 类对象也采用直接赋值方式，那么将不会开辟新的堆内存空间，而是使用已有的对象引用的分配，继续使用。

## 4.2 采用构造方法实例化

构造方法如果要使用一定要用关键字 new ，一旦使用了关键字 new 就表示要开辟新的堆内存空间。

> ```java
> public static void main(String args[]){
> 	String strA = new String("Hello");
> 	String strB = new String("Hello");
> 	String strC = new String("Hello");
> 	System.out.println(strA == strB);  // false
> 	System.out.println(strA == strC);  // false
> 	System.out.println(strB == strC);  // false
> }
> ```

​	通过内存分析发现，如果使用的是构造方法方式进行 String 类对象实例化的时候，开辟了两块堆内存空间，并且其中有一块堆内存空间将成为垃圾空间。	

​	除了内存的浪费之外，如果使用了构造方法定义的 String 类对象，其内容不会保存到对象池之中，因为是使用了关键字 new 开辟了新内存。如果希望开辟的新内存数据也可以进行对象池的保存，那么可以采用 String 类定义的手工入池的方法： public String intern();

举例：手工入池

> ```java
> public static void main(String args[]){
> 	String strA = new String("Hello").intern();
> 	String strB = "Hello";
> 	System.out.println(strA == strB);  // true
> }
> ```



**面试题**：请解释 String 类对象实例化的两种方式的区别？

- 直接赋值，只会开辟一块堆内存空间，并且匿名对象会自动保存到对象池中，以供下次重复使用。
- 构造方法赋值，会开辟两块堆内存空间，其中有一块空间将成为垃圾，并且不会自动入池，可以使用intern()方法手工入池。

工作中，使用直接赋值方式。



# 五、字符串内容不可改变

字符串一旦定义则不可改变。观察一段代码：

> ```java
> public static void main(String args[]){
> 	String str = "Hello";
> 	str += " World";
> 	str += "!!!";
> 	System.out.println(str);  
> }
> ```

运行结果是： Hello World!!! 以上代码最终结果实际上 str 对象的内容被改变了，下面通过内存关系分析：

**图4**

![String4](/Users/chenjinhua/ziliao/Java 基础/javaLearning/03 String类特点/String4.png)

​	以上的操作发现，所谓的字符串的内容根本就没有改变（ Java 就定义好了 String 的内容不能够改变），而对于字符串对象内容的改变是利用了引用关系的变化而实现的，但是每一次的变化都会产生垃圾空间。所以 String 类的内容不要频繁的修改。



#六、总结String 类的特点

1. String 类对象的相等判断使用 equals() 方法完成，“==”实现的是地址数值比较；
2. String 类对象内容一旦声明则不可改变，String 类对象内容的改变是依靠引用关系的变更实现的；
3. String 类有两种实例化方式，使用直接赋值可以不产生垃圾空间，并且可以自动入池。不要使用构造方法完成。