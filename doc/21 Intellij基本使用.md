[TOC]
http://blog.csdn.net/tiantiandjava/article/details/42269173

#一、新建一个 Java 项目

1、 建立一个 Java 项目

New - Project - Java 

项目建立完成后会在项目的文件目录下生成两个目录：

- src：保存所有的 *.java 源文件；
- bin：保存所有生成的 *.class 文件。

2、新建一个类

选中 src 文件夹，右键 - new - java class。

#二、Intellij 功能

1、写完代码后，编译器会自动的进行编译，如果有错误，编译器会提示。

2、新建一个类后，构造方法、getter、setter方法

在类里，右键 - Generator -setter +  getter、constructor。

3、调整所选项 Intellij - Preference

以放大字体为例： Intellij - Preference — Editor - Color&Fonts ...

4、常见快捷键

- 进行代码的提示：option + enter；
- 删除当前行代码：command + x；
- 复制当前行代码：command + D；
- 当行注释：command + /；
- 覆写方法：command + o。

5、debug（代码的跟踪调试）模式

打个断点，然后debug模式运行：程序会执行到断点处，然后等待用户的操作指令，调试的方式有如下 3 种：

- 单步进入：进入到执行的方法之中观察方法的执行效果；
- 单步跳过：在当前代码的表面上执行；
- 单步返回：返回到方法调用处；
- 恢复执行：停止调试，而直接正常执行完毕。

在调试之中可以清楚的知道方法中所有变量的数值的变化情况。

# 三、Junit 的使用（重点）

junit 是一个测试工具。

范例：定义一个程序

