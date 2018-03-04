[TOC]

# String 类的常用方法

​	对于系统类的方法，一定要去查询文档，一些不常用的方法允许不知道，但是一定要会查。但是对于 String 类的一些方法由于使用的情况比较多，为了方便开发必须背住。

#一、字符与字符串

很多语言中都是利用了字符数组的概念来描述字符串的信息，这一点在 String 类的方法上也都有所体现。

| No.  |                   方法名称                   |  说明  | 描述                   |
| :--: | :--------------------------------------: | :--: | -------------------- |
|  1   |       public String(char[] value)        |  构造  | 将字符数组变为 String 类对象   |
|  2   | public String(char[] value, int offset, int count) |  构造  | 将部分字符数组变为 String 类对象 |
|  3   |      public char charAt(int index)       |  普通  | 返回指定索引对应的字符          |
|  4   |       public char[] toCharArray()        |  普通  | 将字符串变为字符数组           |

举例：返回字符串指定索引的字符

>```java
>	String str = "hello";
>	char s = str.charAt(0);
>	System.out.println(s);
>```

举例：将字符串以字符数组返回

>```java
>	String str = "Hello";
>	char data[] = str.toCharArray();
>	for(int i=0;i<data.length;i++){
>		System.out.print(data[i]+"、");  
>	}
>```

举例：将字符串转大写

>```java
>	public class StringDemo{
>	public static void main(String args[]){
>		String str = "hello";
>		char data[] = str.toCharArray();
>		for(int i=0;i<data.length;i++){
>			data[i] -= 32;
>		}
>		str = new String(data); // 将字符数组变为String
>		System.out.println(str);
>
>		String str1 = new String(data,1,3); // 将字符数组部分内容变为String
>		System.out.println(str1);
>	}
>}
>```

# 二、字节与字符串

​	字节使用 byte 描述，字节一般主要用于数据的传输或者进行编码转换。将字符串变为字节数组的操作，目的就是为了传输以及编码转换。

| No.  |                   方法名称                   |  说明  | 描述                   |
| :--: | :--------------------------------------: | :--: | -------------------- |
|  1   |       public String(byte[] bytes)        |  构造  | 将全部字节数组变为 String 类对象 |
|  2   | public String(byte[] bytes,int offset,int length) |  构造  | 将部分字节数组变为 String 类对象 |
|  3   |         public byte[] getBytes()         |  普通  | 将字符串变为字节数组           |
|  4   | public byte[] getBytes(String charsetName) throws UnsupportedEncodingException |  普通  | 进行编码转换               |

举例：观察字符串与字节数组的转换

>```java
>public class StringDemo{
>public static void main(String args[]){
>	String str = "helloworld";
>	byte data [] = str.getBytes(); // 将String对象转换为byte数组
>	for(int i=0; i<data.length; i++){
>		System.out.print(data[i]+"、");
>		data[i] -= 32;	// 小写字母转为大写字母
>	}
>	System.out.println();
>	System.out.println(new String(data));      // 将byte数组转换为String对象
>	System.out.println(new String(data,1,3));  // 将byte数组部分内容转换为String对象
>}
>}
>```
>

​	因为现在操作的是英文字母，所以感觉与字符类似。在以后的IO操作的时候会牵扯到这种字节数组的操作，在后续的开发中会逐步遇到乱码需要转码的问题。

# 三、字符串比较

如果要进行字符串内容相等的判断使用 equals()，但是在 String 类里面定义的字符串判断相等的不止这一个。

| No.  |                   方法名称                   |  说明  | 描述                                       |
| :--: | :--------------------------------------: | :--: | ---------------------------------------- |
|  1   | public boolean equals(String anotherString)) |  普通  | 进行相等判断，区分大小写                             |
|  2   | public boolean equalsIgnoreCase(String anotherString) |  普通  | 进行相等判断，不区分大小写                            |
|  3   | **public int compareTo(String anotherString)** |  普通  | **判断两个字符串的大小（按照字符编码比较），返回值有如下 3 种：（1） =0 表示要比较的两个字符串内容相等；（2）>0 表示大于的结果；（3）<0 表示小于的结果** |

举例：

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String strA = "helloworld";
>		String strB = "HelloWorld";
>		System.out.println(strB.equals(strA));   // false
>		System.out.println(strB.equalsIgnoreCase(strA));   //true
>	}
>}
>```

举例：观察 compareTo() 方法

> ```java
> public class StringDemo{
> 	public static void main(String args[]){
> 		String strA = "helloworld";
> 		String strB = "HelloWorld";
> 		System.out.println(strA.compareTo(strB));   // 32
> 		if(strA.compareTo(strB) > 0){
> 			System.out.println("大于");
> 		}
> 	}
> }
> ```

**现在只有 String 类的对象才具有大小关系的判断。**



# 四、字符串查找

从一个完整的字符串之中要判断某一个子字符串是否存在

| No.  |                   方法名称                   |  说明  | 描述                                       |
| :--: | :--------------------------------------: | :--: | ---------------------------------------- |
|  1   |  **public boolean contains(String s)**   |  普通  | **判断指定的内容是否存在**                          |
|  2   |      public int indexOf(String str)      |  普通  | 由前向后查找指定字符串的位置，如果查找到了则返回第一个字母的位置索引；如果找不到则返回-1. |
|  3   | public int indexOf(String str, int fromIndex) |  普通  | 由指定位置从前向后查找指定字符串的位置，如果查找到了则返回第一个字母的位置索引；如果找不到则返回-1. |
|  4   |    public int lastIndexOf(String str)    |  普通  | 由后向前查找指定字符串的位置，如果查找到了则返回第一个字母的位置索引；如果找不到则返回-1. |
|  5   | public int lastIndexOf(String str, int fromIndex) |  普通  | 由指定位置从后向前查找指定字符串的位置，如果查找到了则返回第一个字母的位置索引；如果找不到则返回-1. |
|  6   | public boolean startsWith(String prefix) |  普通  | 判断是否以指定的字符串开头，如果是则返回true，否则返回false       |
|  7   | public boolean startsWith(String prefix, int toffset) |  普通  | 在指定位置以指定字符串开始                            |
|  8   |  public boolean endsWith(String suffix)  |  普通  | 以指定的字符串结尾                                |

举例：使用 indexOf() 等功能查找

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "helloworld";
>
>		// 返回满足条件单词的第一个字母的索引
>		System.out.println(str.indexOf("l"));     // 2
>		System.out.println(str.indexOf("l",5));   // 8
>		// System.out.println(str.indexOf("world",6));   // -1
>
>		System.out.println(str.lastIndexOf("l")); // 8
>	// 	System.out.println(str.lastIndexOf("orld",4)); // -1
>	// 	System.out.println(str.lastIndexOf("orld",7)); //6
>	}
>}
>```

​	上面的功能都只返回了位置。但是在一些程序之中需要查找有没有，最早的做法是判断查询结果是否是 -1 来实现的。

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "helloworld";
>		if(str.indexOf("world") != -1){
>			System.out.println("能查询到数据");   
>		}else{
>			System.out.println("不能查询到数据");   
>		}
>	}
>}
>```

但是从 JDK 1.5 及之后出现了 contains() 方法，可直接返回 boolean。

>```java
>public static void main(String args[]){
>	String str = "helloworld";
>	if(str.contains("world")){
>		System.out.println("能查询到数据");   
>	}else{
>		System.out.println("不能查询到数据");   
>	}
>}
>```

使用 contains() 更加的简单，并且在整个 java 里面，contains 已经成为了查找的代名词。

**举例：开头或结尾判断**

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "##@@helloworld**";
>		System.out.println(str.startsWith("##"));   
>		System.out.println(str.startsWith("@",2));   
>		System.out.println(str.endsWith("*")); 			  
>	}
>}
>```

这些开头和结尾的判断往往可以作为一些标记在程序之中出现。

# 五、字符串替换

指的使用一个新的字符串替换掉旧的字符串数据，支持的方法有如下几个：

| No.  |                   方法名称                   |  说明  | 描述             |
| :--: | :--------------------------------------: | :--: | -------------- |
|  1   | public String replaceAll(String regex,String replacement) |  普通  | 用新的内容替换掉全部旧的内容 |
|  2   | public String replaceFirst(String regex,String replacement) |  普通  | 替换首个满足条件的内容    |

举例：观察替换的结果

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "##@@helloworld**";
>		System.out.println(str.replace("l","U"));        // ##@@heUUoworUd**
>		System.out.println(str.replaceFirst("@","!"));   // ##!@helloworld**
>	}
>}
>```

参数 regex 是正则的意思，后续学习。

# 六、字符串截取

从一个完整的字符串之中，截取部分子字符串的数据，支持的方法如下：

| No.  |                   方法名称                   |  说明  | 描述          |
| :--: | :--------------------------------------: | :--: | ----------- |
|  1   | public String substring(int beginIndex)  |  普通  | 从指定索引截取到结尾  |
|  2   | public String substring(int beginIndex,int endIndex) |  普通  | 截取部分子字符串的数据 |

举例：

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "##@@helloworld**";
>		System.out.println(str.substring(4));        // helloworld**
>		System.out.println(str.substring(4,13));   // helloworld
>	}
>}
>```

不能使用负数作为截取的开始点。

# 七、字符串拆分

将一个完整的字符串，按照指定的内容拆分为字符串数组（对象数组，String 类对象），方法如下：

| No.  |                   方法名称                   |  说明  | 描述                                       |
| :--: | :--------------------------------------: | :--: | ---------------------------------------- |
|  1   |   public String[] split(String regex)    |  普通  | 按照指定的字符串进行全部拆分                           |
|  2   | public String[] split(String regex,int limit) |  普通  | 按照指定的字符串进行部分拆分，最后的数组长度由 limit 决定（如果能拆分的结果很多，数组长度才由 limit 决定），即前面拆后面不拆作为整体。 |

举例：全部拆分

>```java
>	String str = "hello world nihao ma";
>	String data [] = str.split(" ");   
>	for(int i=0; i<data.length; i++){
>		System.out.println(data[i]);
>	} 
>```

如果在拆分的时候只是写了一个空字符串（“” 不是null），表示按照每一个字符进行拆分。

举例：部分拆分

>```java
>	String str = "hello world nihao ma";	
>	String data[] = str.split(" ",2);   
>	for(int i=0; i<data1.length; i++){
>		System.out.println(data1[i]);
>	} 
>```

举例：实现IPv4地址拆分

> ```java
> String str = "192.68.15.238";	
> String data[] = str.split(".");   
> for(int i=0; i<data.length; i++){
> 	System.out.println(data[i]);
> } 
> ```

​	如果写成上面，你会发现无法拆分。如果是一些敏感字符（正则标记），严格来讲是拆分不了的。如果真的遇见拆分不了的就使用 " \ \（就是 \ ）“，进行转义后才可以拆分。

​	字符串的拆分是非常常见的，因为很多时候会传递一组数据到程序中。现在有如下的一个字符串：”张三：20|李四：21|王五：22“ （姓名：年龄|）。当接收到此数据时必须要拆分。

> ```java
> 	String str = "张三:20|李四:21|王五:22";	
> 	String data[] = str.split("\\|");   
> 	for(int i=0; i<data.length; i++){
> 		String temp []= data[i].split(":");
> 		System.out.println("姓名: " + temp[0]+", 年龄: " + temp[1]);
> 	} 
> ```

# 八、其他操作

以上给出的方法是可以归类的，但是 String 类里面有部分代码是没法归类的。

| No.  |               方法名称               |  说明  | 描述                           |
| :--: | :------------------------------: | :--: | ---------------------------- |
|  1   | public String concat(String str) |  普通  | 字符串的连接，与 ” + “ 类似            |
|  2   |   public String toLowerCase()    |  普通  | 转小写                          |
|  3   |   public String toUpperCase()    |  普通  | 转大写                          |
|  4   |       public String trim()       |  普通  | 去掉字符串中左右两边的空格                |
|  5   |       public int length()        |  普通  | 取得字符串的长度                     |
|  6   |      public String intern()      |  普通  | 对象入池                         |
|  7   |     public boolean isEmpty()     |  普通  | 判断是否是空字符串（不是 null，而是”“，长度为0） |

举例：转小写与大写

>```java
>	String strA = "hello!!!&&";	
>	System.out.println(strA.toUpperCase());
>```

所有的非字母数据不会进行任何的转换操作。

>```java
>	String str = "   hello world ";
>	System.out.println("【" + str + "】");
>	System.out.println("【" + str.trim() + "】");	
>	String str = "   hello world ";
>```

​	一般在用户进行输入的时候有可能会携带有无用的空格内容，那么接收到这些数据后就需要消除掉所有的无用空格。

举例：取得字符串的长度

> ```java
> String str = "   hello world ";
> System.out.println(str.length());
> ```

​	用途：由于用户输入的数据长度是有限制的，可以利用此方式判断。数组中也有一个 length 属性，但是调用的形式不同。

- 数组对象.length;
- String对象.length();

举例：判断是否是空字符串

>```java
>	String str = "   hello world ";
>	System.out.println(str.isEmpty());  // false
>	System.out.println("".isEmpty());   // true
>```

以后如果觉得 isEmpty() 不方便，可以使用 ” ”“.isEquals(str); “。

​	String 类提供了大量的支持的方法，但是却少了一个重要的方法 —— initcap() 功能：首字母大写其余字母小写，这样的功能只能自己实现。

举例：

>```java
>public class StringDemo{
>	public static void main(String args[]){
>		String str = "hEllo world";
>		System.out.println(initcap(str));	
>	}
>	public static String initcap(String str){
>		String strA = str.substring(0,1);
>		String strB = str.substring(1);
>		return strA.toUpperCase() + strB.toLowerCase();
>	}
>}
>```

虽然 Java 的类库里没有此功能，但是一些第三方的组件包会提供，例如：Apache。



#九、总结

所有的方法要求：记下方法名称、方法作用、参数的类型及个数、返回值类型。

必须要背下来，否则写代码的时候会遇到很多挫折。