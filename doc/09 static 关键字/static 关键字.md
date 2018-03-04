static 关键字

1、定义属性、定义方法

2、static 操作的基本形式以及应用

# 一、 static 定义属性

1、static 定义的属性和非 static 定义的属性有一个最大的区别？

​	所有的非 static 属性必须产生实例化对象之后才可以访问，static 定义的属性不受此限制。也就是说，在没有实例化对象产生的情况下，依然可以使用 static 属性。

static 定义属性的一个特征：虽然定义在类结构里面，但是并不受对象的控制，是独立于类存在的。



# 二、static 定义方法

static 定义方法的时候也可以在没有实例化对象产生的时候利用类名称直接调用。

1、static 方法和非 static 方法区别？

此时类中的方法变成了两种：static 方法和非 static 方法，方法间的访问也受到了限制。

- static 方法不能直接访问非 static 属性和 非 static 方法，只能调用 static 属性和方法；

- 非 static 方法 可以直接访问 static 属性和方法，不受任何的限制。

  ​

分析：为什么会存在以上的限制？

- 所有非static 定义的结构，必须在类已经明确产生了实例化对象之后才会分配堆内存空间，才可以使用。
- 所有 static 定义的结构，不受实例化对象的控制，即可以在没有实例化对象的时候访问。



2、什么时候使用 static 方法？

​	类里面没有任何的属性存在，只有方法，建议将所有的方法定义为 static 方法，这样就不用在每次调用的时候再产生实例化对象了。



# 三、主方法

观察主方法的每一个构成：

- public：主方法是程序的开始，所以这个方法对任何的操作都一定是可见的，既然可见就必须使用public。
- static：不需要产生实例化对象，由类名称调用。编译运行 java 程序时我们是在命令行里执行: java 类名称。
- void：主方法是一切执行的开始点，既然是所有的开头，那么就不能回头（return），执行完毕为止。
- main：是一个系统规定好的方法名称，不能修改。
- String args[]：指的是程序运行时传递的参数，例子如下。

示例代码：

> ```java
> public class Test{
> 	public static void main(String args[]){
> 		for(int x=0; x< args.length; x++){
> 			System.out.println(args[x]);
> 		}
> 	}
> }
> ```

所有输入的参数必须使用空格分隔，例如： java Test adc tef sdf sdg。

# 四、static 的实际应用

之前的结论：

- 不管有多少个对象，都使用同一个 static 属性；
- 使用 static 方法可以避免掉实例化对象调用方法的限制。

功能一：实现实例化类对象个数的统计

希望每当实例化一个对象的时候，输出”这是产生的第x个实例化对象“

> ```java
> class Book{
> 	private static int num = 0;
> 	public Book(){
> 		num ++ ;
> 		System.out.println("这是产生的第" + num + "个实例化对象。");
> 	}
> }
> public class TestBook{
> 	public static void main(String [] args){
> 		new Book();new Book();new Book();new Book();new Book();
> 	}
> }
> ```

功能二：实现属性的自动设置

例如现在某一个类有一个无参构成方法、一个有参构造方法，有参构造方法主要目的是传递一个 title 属性，希望调用的不管是有参还是无参构造，都可以为 title 设置内容。 

> ```java
> class Book{
> 	private String title;
> 	private  int num = 0;
> 	public Book(){
> 		this.title = "Native-" + (num++);
> 	}
> 	public Book(String title){
> 		this.title = title;
> 	}
> 	public String getTitle(){
> 		return this.title;
> 	}
> }
> public class TestBook{
> 	public static void main(String [] args){
> 		System.out.println(new Book("Java").getTitle());
> 		System.out.println(new Book().getTitle());
> 		System.out.println(new Book().getTitle());
> 		System.out.println(new Book().getTitle());
> 	}
> }
> ```

此时就算是没有设置 title 属性，该内容也不会为 null。



# 五、总结

1、开发中首选的属性一定不是 static 属性，首选的方法一定不是 static 方法。

2、static 修饰的属性和方法可以在没有实例化对象的时候直接有类名称进行调用。

3、static 属性保存在全局数据区

内存区一共有四个：栈内存、堆内存、全局数据区、全局代码区