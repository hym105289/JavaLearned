[TOC]



#一、XML 用途

#二、XML 基本语法

###1、 XML 处理指令

```java
<?xml version="1.0" encoding="utf-8"?>
```

​	如上是 xml 处理指令，用来指挥解析引擎如何解析 xml 文档内容。

###2、标签和属性

```java
<?xml version="1.0" encoding="ISO-8859-1"?>

<taglib xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd"
        version="2.1">

    <tlib-version>1.0</tlib-version>
    <short-name>myshortname</short-name>
    <uri>http://mycompany.com</uri>

    <!-- Invoke 'Generate' action to add tags or functions -->

</taglib>
```

####2.1 标签

- 用一对“<>”和一个名字写的东西就叫做标签，如上面的 taglib 标签、tlib-version标签、short-name标签。
- 标签一定是成对出现，即要有开始标签和结束标签，如<taglib> </taglib>结束标签比开始标签多一个“/”。

####2.2 属性

- 标签里是可以添加属性的，而且属性只能添加在开始标签里，结束标签必须是干净的，如</taglib>。
- 如何添加属性：在开始标签的名字后面添加一个空格，写属性名和属性的值。规则是 属性名=属性值（一定要引号，单引号和双引号都可以）。

####2.3 其他内容

- 标签里面是可以包含其他内容的，其他内容只能是两种，要么是纯文本，要么是其他标签。如下所示

  ​	纯文本：

```java
<short-name>myshortname</short-name>
```

​		其他标签：

```java
<taglib xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
        version="2.1">
    <tlib-version>1.0</tlib-version>
    <short-name>myshortname</short-name>
    <uri>http://mycompany.com</uri>
    <!-- Invoke 'Generate' action to add tags or functions -->
</taglib>
```



###3、XML 大小写敏感

###4、XML 必须有根标签



![根标签4](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML/根标签4.png)

###5、实体引用

![实体引用5](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML/实体引用5.png)



###6、CDATA 段

​	如果要转义的内容很多，可以使用 CDATA，批量变成文本。XML 引擎在读到 CDATA 后，认为 CDATA 里的东西是纯文本。这样就避免了对特殊字符一个一个的转义。

![CDATA 段6](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML/CDATA 段6.png)

###7、注释

注释写法为

```xml
<!-- 这里是注释 -->
```

不区分单行还是多行，“<!—” 和 “—>”之间的内容都是注释。



# 三、Java 对 XML 的支持

##3.1 读取 XML 文件

###3.1.1  Java 里读取 XML 文件 

- JAVA 使用 SAXReader  读取 XML 文档；
- 使用 SAX 需要引入 dom4j jar 包，dom4j 是 java 的 XML API，用来读写 XML 文件。

### 3.1.2 引入dom4j jar 包？—— maven

1、新建 maven Project；

2、 利用 maven 引入 jar 包。

> 具体方法阅读：https://jingyan.baidu.com/article/5225f26b66fc3ce6fb090874.html

### 3.1.3 实战

**读取 emplist.xml 文件，将该 XML 文档中所有员工信息解析出来,并以若干 Emp 实例保存到一个 List 集合.**



![使用 DOM 解析 XML 流程 9](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/使用 DOM 解析 XML 流程 9.png)



Emp 类

```java
package JAVASE03.day02;

/**
 * Created by jinhua.chen on 2018/3/11.
 */
public class Emp {
    private int id;
    private String name;
    private int age;
    private String gender;
    private double sal;

    public Emp(){}

    public Emp(int id, String name, int age, String gender, double sal) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.sal = sal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        return 
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", sal=" + sal +
                '}';
    }
}

```

从 XML 文件读取 Emp 对象信息：

```java
package JAVASE03.day02;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jinhua.chen on 2018/3/11.
 */
public class ParseXMLDemo {
    public static void main(String[] args) {

        /*
        读取 emplist.xml 文件.
        将该 XML 文档中所有员工信息解析出来,并以若干 Emp 实例保存到一个 List 集合.
         */
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new FileInputStream("emplist.xml"));
            Element root = doc.getRootElement();
            System.out.println("根元素:" + root.getName());

            // 用于存放所有的员工信息.
            List<Emp> empList = new ArrayList<Emp>();

            // 获取所有的 emp 元素
            List<Element> list = root.elements();
            for (Element emp : list){
                int id = Integer.parseInt(emp.attribute("id").getValue());
                String name = emp.elementText("name");
                int age = Integer.parseInt(emp.elementText("age"));
                String gender = emp.elementText("gender");
                double sal = Double.parseDouble(emp.elementText("salary"));

                Emp empObject = new Emp(id,name,age,gender,sal);
                empList.add(empObject);
            }
            System.out.println("xml 文件解析完毕!");
            for (Emp e: empList){
                System.out.println(e);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

##3.2 写出到 XML 文件

![WriteXML 10](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/WriteXML 10.png)

将 Emp 对象写出到 myemp.xml 文件

```java
package JAVASE03.day02;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.EntityResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jinhua.chen on 2018/3/11.
 */
public class WriteXMLDemo {
    public static void main(String[] args) {
        List<Emp> empList = new ArrayList<Emp>();
        empList.add(new Emp(1,"张三",34,"男",3000.0));
        empList.add(new Emp(2,"李四",21,"女",4000.0));
        empList.add(new Emp(3,"王五",46,"女",6500.0));
        empList.add(new Emp(4,"赵六",28,"男",4400.0));

        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("list");

        for (Emp e: empList){
            // 设置 emp 标签
            Element empEle = root.addElement("emp");

            // 设置 name 标签
            Element nameEle = empEle.addElement("name");
            nameEle.addText(e.getName());

            empEle.addElement("age").addText(String.valueOf(e.getAge()));
            empEle.addElement("gender").addText(e.getGender());
            empEle.addElement("salary").addText(String.valueOf(e.getSal()));

            empEle.addAttribute("id",String.valueOf(e.getId()));
        }

        XMLWriter xmlWriter = null;

        try {
            FileOutputStream fos = new FileOutputStream("myemp.xml");
            xmlWriter = new XMLWriter(fos,OutputFormat.createPrettyPrint());
            xmlWriter.write(doc);
            System.out.println("写出完毕!");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                xmlWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

# 四、使用 XPath 解析 XML 文档

##4.1  XPath 基本语法

###4.1.1 路径表达式

![XPath 11](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/XPath 11.png)



![XPath 12](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/XPath 12.png)

![Xpath 13](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/Xpath 13.png)

### 4.1.2 谓语

![谓语14](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/谓语14.png)



![谓语 15](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/谓语 15.png)

![谓语 16](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/谓语 16.png)

###4.1.3 通配符

![通配符 17](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/通配符 17.png)

## 4.2 使用 Xpath 解析 XML 文档 

![XPath 18](/Users/chenjinhua/ziliao/JavaLearned/doc/35 XML文件/XPath 18.png)


参考文章：https://www.cnblogs.com/yangliguo/p/7398996.html

