---
typora-root-url: ../41 JDBC/42 WebBasic
---

[TOC]

# 一、介绍

1、HTML：勾勒出网页的结构和内容；

2、CSS：美化网页

3、JavaScript（JS）：让网页呈现动态的数据和效果

4、jQuery：是一个框架，提高 JS 的开发效率



**HTML 部署在服务器，运行在浏览器上。**





# 二、XML 和 HTML 对比

1、XML

- 可扩展标记语言：标记、属性、标记之间的关系都可以扩展；
- 用来存储和传输数据。

2、HTML

- 超文本标记语言：标记、属性、标记之间的关系是固定的（w3c）；
- 某些版本的 HTML 严格遵守 XML 规范

**可以将 HTML 理解为标签固定的 XML。**



# 三、创建 web 项目

http://blog.csdn.net/determinateld/article/details/70198680

然后在 web 文件夹下新建 html 文件。



# 四、HTML 网页基本结构

HTML 注释的快捷键是 control + shift + /

```html
<!-- 声明网页的版本  --> <!-- HTML 注释的快捷键是 control + shift + /-->
<!doctype html>
<!-- 唯一的根元素 -->
<html>
<head>
    <!-- 只有 2 个子元素,用来对网页做出基本的声明-->
    
    <!-- 声明网页内容的编码-->
    <meta charset="utf-8">
    <!-- 声明网页的标题-->
    <title>Hello HTML</title>
</head>

<!-- 用来写网页的所有的内容-->
<body>
    HTML 基本
</body>
</html>
```
# 五、body 元素

## 5.1 文本元素

1、标题   

2、段落  

3、列表：有序列表、无序列表、列表嵌套

```html
<ol>
    <li>
        江苏
        <ul>
            <li>
                南京
            </li>
            <li>
                扬州
            </li>
            <li>
                昆山
            </li>
        </ul>
    </li>
    <li>
        浙江
        <ul>
            <li>
                杭州
            </li>
            <li>
                温州
            </li>
        </ul>
    </li>
</ol>
```

4、分区

块分区元素 <div></div>；

行内分区元素<span></span>：为行内部分元素设置文本样式。

5、实体引用

## 5.2 图像

单标签，图像标签<img>

属性，源属性 src，相对路径。

## 5.3 超链接

1、普通用法<a href="url" target="_blank">文本</a>

2、锚点定位：使用name 属性, href="#name"

3、特殊锚点：锚点到顶部，href="#"

## 5.4 表格

1、普通用法

2、跨行跨列

3、表格分组

例子

```html
<table border="1" cellspacing="0" width="30%">
    <thead>
        <tr style="font-weight: bold;">
            <td>编号</td>
            <td>名称</td>
            <td>金额</td>
        </tr>
    </thead>
    <tbody style="color: red;">
    <tr>
        <td>001</td>
        <td>鼠标</td>
        <td>50</td>
    </tr>
    <tr>
        <td>002</td>
        <td>键盘</td>
        <td>100</td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="2">合计</td>
        <td>150</td>
    </tr>
    </tfoot>
</table>
```

## 5.5 表单

### 5.5.1 什么是表单

**表单就是从浏览器向服务器传输数据的手段。**

### 5.5.2 表单元素

定义表单：使用成对的<form></form>标记，表示要将此元素中所涵盖的控件中的数据传输给服务器；

主要属性：

（1）action：表单要提交的 url；

（2）method：表单数据提交的方式；

（3）enctype：表单数据进行编码的方式。

### 5.5.3 表单控件

表单控件包括：

（1）input 元素：文本框、密码框、单选框、复选框、隐藏域、文件选择框、按钮；

（2）其他元素：标签、文本域、下拉选。

```html
 <!-- 1 表单 -->
<p>
    <!-- 2 表单元素-->
    <form>
        <!-- 3 input 标签-->
        <!-- 文本框 -->
        <p>
         账号:<input type="text"/>
        </p>
        <!-- 密码框-->
        <p>
         密码:<input type="password"/>
        </p>
        <!-- 单选框-->
        <p>
        性别:<input type="radio" name="sex"/> 男
            <input type="radio" name="sex"/> 女
        </p>
        <!--多选框-->
        <p>
        兴趣爱好:
            <input type="checkbox"/> 足球
            <input type="checkbox"/> 羽毛球
            <input type="checkbox"/> 篮球
        </p>
        <!-- 按钮 -->
        <p>
            <input type="submit" value="注册"/>
            <input type="reset" value="重置"/>
        </p>
        <!-- 文件-->
        <p>
            <input type="file" value="请上传头像"/>
        </p>

        <!-- 4 其他标签-->
            <!-- labor -->
        <p>
            <input type="checkbox" id="checkRead"/>
            <label for="checkRead">我已阅读上面内容</label>
        </p>
            <!-- 文本域-->
        <p>
            简介:<textarea cols="30" rows="8"></textarea>
        </p>
            <!-- 下拉选 -->
        <p>
            请选择城市:
            <select>
                <option>请选择</option>
                <option>上海</option>
                <option>北京</option>
                <option>南京</option>
                <option selected>扬州</option>
            </select>
        </p>
 </form>
</p>
```