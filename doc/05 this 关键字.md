[TOC]



_this 关键字

知识点：

>1、this 调用属性；
>
>2、this 调用方法；
>
>3、利用 this 表示当前对象。



this 是 java 里面比较复杂的一个关键，因为 this 有一个核心概念：当前对象，而这个当前对象就很难理解。

在 Java 里面，利用 this 关键字可以实现类属性的调用、类方法的调用、表示当前对象。

# 一、调用属性、调用方法、表示当前对象。

## 1.1 调用属性

**在以后的开发之中，只要是访问类中的属性签名必须加上 “ this ”。**

## 1.2 调用方法

### 1.2.1 调用普通方法

调用普通方法的时候是否在方法前加 this，并没有一个明确的要求，即使不加 this 也表示本类调用的方法，但为了代码的严谨性，一定要加上 this。

### 1.2.2 调用构造方法

表示多个构造方法之间要进行互相调用，而使用的形式 “ this(参数，参数)”。

**实例**：定义一个雇员类（编号、姓名、工资、部门），在这个类里提供 4 个构造方法：

（1）无参构造：编号为 0，姓名为无名氏，工资为 0.0，部门设置为“未定”；

（2）单餐构造（传递编号）：姓名为临时工，工资为800.0，部门为后勤；

（3）双参构造（传递编号、姓名）：工资为2000.0，部门为技术部；

（4）四参构造。

实现方式一：按照传统的风格实现

>```java
>class Emp{
>	private int empno;
>	private String empname;
>	private double salary;
>	private String dep;
>
>	public Emp(){
>		this.empno = 0;
>		this.empname = "无名氏";
>		this.salary = 0.0;
>		this.dep = "未定";
>	}
>	public Emp(int empno){
>		this.empno = empno;
>		this.empname = "临时工";
>		this.salary = 800.0;
>		this.dep = "后勤";
>	}
>	public Emp(int empno,String empname){
>		this.empno = empno;
>		this.empname = empname;
>		this.salary = 2000.0;
>		this.dep = "tech";
>	}
>	public Emp(int empno,String empname,double salary,String dep){
>		this.empno = empno;
>		this.empname = empname;
>		this.salary = salary;
>		this.dep = dep;
>	}
>	// 省略 getter、 setter 方法
>	public String getInfo(){
>		return "编号：" + this.empno + ", 姓名：" + this.empname + ", 工资："+ this.salary + ", 部门："+this.dep;
>	}
>}
>
>public class StringTest{
>	public static void main(String args[]){
>		Emp ea = new Emp();
>		Emp eb = new Emp(456);
>		Emp ec = new Emp(456,"King");
>		Emp ed = new Emp(456,"King",5000.0,"销售");
>
>		System.out.println(ea.getInfo());
>		System.out.println(eb.getInfo());
>		System.out.println(ec.getInfo());
>		System.out.println(ed.getInfo());
>	}
>}
>```

实现方式二：利用 this 简化构造方法

>```java
>class Emp{
>	private int empno;
>	private String empname;
>	private double salary;
>	private String dep;
>
>	public Emp(){
>		this(0,"无名氏",0.0,"未定");
>	}
>	public Emp(int empno){
>		this(empno,"临时工",800.0,"后勤");
>	}
>	public Emp(int empno,String empname){
>		this(empno,empname,2000.0,"tech");
>	}
>	public Emp(int empno,String empname,double salary,String dep){
>		this.empno = empno;
>		this.empname = empname;
>		this.salary = salary;
>		this.dep = dep;
>	}
>	// 省略 getter、 setter 方法
>	public String getInfo(){
>		return "编号：" + this.empno + ", 姓名：" + this.empname + ", 工资："+ this.salary + ", 部门："+this.dep;
>	}
>}
>
>public class StringTest{
>	public static void main(String args[]){
>
>		Emp ea = new Emp();
>		Emp eb = new Emp(456);
>		Emp ec = new Emp(456,"King");
>		Emp ed = new Emp(456,"King",5000.0,"销售");
>
>		System.out.println(ea.getInfo());
>		System.out.println(eb.getInfo());
>		System.out.println(ec.getInfo());
>		System.out.println(ed.getInfo());
>	}
>}
>```

此时的操作利用构造方法间的互相调用，解决了代码重复的问题。

### 1.2.3 表示当前对象

所谓的当前对象指的就是当前正在调用类中方法的对象。



# 二、总结

1、类中的属性调用要加上 this；

2、类中的构造方法间的互相调用，一定要保留出口；

3、this 表示当前对象，指的是当前正在调用类中方法的对象，this 不是固定的。