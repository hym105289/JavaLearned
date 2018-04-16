[TOC]



# 一、JavaScript使用

有3种方式

1、事件定义式：在事件定义时直接写 js；

2、嵌入式：使用<script>标签；

3、文件调用式：html 引用外部的 .js 文件。



补充：事件是用户的操作（动作），是 js 调用的时机，比如单击事件、双击事件等。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!-- 2 嵌入式: 在 script 标签内写 js. 该标签可以写在网页的任意位置(而 css 只能写在 head 里.)-->

    <script>
         // js注释同 java,//单行, /**/ 多行
         /*
         1 js 中的函数都是公有的;
         2 js 函数的返回值类型不需要声明.
          */
        function f2(){
            alert("早上好!")
        }
    </script>

    <script src="my.js"></script>
</head>
<body>
    <!--1 事件定义式:在事件定义时直接写 js-->
    <input type="button" value="按钮1" onclick="alert('陈金花')"/>
    <input type="button" value="按钮2" onclick="f2()"/>
    <input type="button" value="按钮3" onclick="f3()"/>

</body>
</html>
```



# 二、js 数据类型之基本类型和基本语法

## 2.1 变量

变量没有类型，用 var 关键字声明，没有声明的变量值为 undefined。

## 2.2 数据类型

变量没有类型，变量所引用的数据是有类型的。

![ JS 数据类](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/6 JS 数据类型.png)



## 2.3 数据类型的隐式转换

数字、布尔、字符串，不同类型数据在计算过程中会自动进行转换：

字符串+任何类型：转换为字符串类型；

数字+ 任何类型（除字符串）：转换为数字类型；

布尔值+布尔值：转换为数字类型。

```js
/* 隐形转换 */
console.log("静心!" + 3.14);
console.log(true + true);
console.log(4.32 + true);
```

## 2.4 数据类型转换函数

- toString
- parseInt
- parseFloat

以上后两种如果转换失败，则返回 NaN。

```js
/* 类型转换函数 */
console.log(1.1415.toString());
console.log(parseInt("2.81"));
console.log(parseFloat(3));
console.log(parseFloat("abc"));
```

- typeof
- isNaN：判断被检测表达式经过转换后是不是一个数，不是数字则为 true，是则为 false。这个用的少，不用记忆，工作时一般用正则表达式去检测。

```js
console.log(typeof(str));  // String
console.log(isNaN(56));    // false
console.log(isNaN("56"));  // false 字符串经过转换后是一个数，所以这里是 true.
console.log(isNaN("a56")); // true
console.log(isNaN(parseInt("")));// true. 因为 parseInt 转换失败返回NaN,所以为 true.
console.log(isNaN(""));    // false 这里认为空串是数字,与上面 parseInt 函数认为空串不是数字是矛盾的,先不管,遇到看看能否规避.
```

## 2.5 案例

1、判断用户输入的数是否为数值；

2、如果输入的数不能转换成数值，提示用户重新输入；

3、如果输入的是文本则计算该数值的平方。

### 2.5.1 实现思路

![ JS 案例](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/7 JS 案例1.png)

### 2.5.2 完整代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>   
	<script>
        function pf(){
            // 获取文本框
            var input = document.getElementById("num");
            // 获取文本框的内容
            var n = input.value;
            // 获取 span
            var span = document.getElementById("result");
            if(!isNaN(n)){
                var result = n * n;
                // 向 span 写内容
                span.innerHTML = result;
            }else {
                span.innerHTML = "输入的不是数字,请重新输入";
            }
        }
    </script>
    <style>
        input {
            width: 40px;
            border: 1px solid blue;
            font-weight: bold;
        }
    </style>
</head>

<body>
        <input type="text" id="num"/>
        <input type="button" value="平方" onclick="pf();"/>
        = <span id="result"></span>

</body>
```

## 2.6 调试技巧

1、看控制台报错信息

> 对于反复出现的错误要记住

2、打桩

>- 跟踪程序执行的过程；
>- 观察变量的值是否正确。

**3、排除法**

- 每次删除一部分代码，看程序是否报错；
- 建议采用二分法，每次删除一半代码；
- 此方法适合定位问题。

## 2.7 运算符

- 判断数值是否相同

==：等于

!=：不等于

- 判断：类型相同，数值相同

====：全等

！== 不全等

```js
var a = "1";
var b = 1;
var c = 1;
console.log(a == b);  // true  因为a 和 b 可以转换,所以是相等的
console.log(a === b); // false 类型不同,不是全等的
console.log(b === c); // true  类型和数值都相同,是全等的
```

## 2.8 条件表达式

js中的表达式可以是任意表达式，即可以返回任何类型值。

若条件表达式非布尔值，js 规定：**一切表示空的值，都是false。**

空值有：0，null，NaN，""，undefined。

# 三、js 数据类型之常用内置对象

1、什么是 js 对象？

- 对象就是 js 中的 API。

2、js 有哪些对象？

- js包含多种对象：内置对象、外部对象（window 和 dom 对象）、自定义对象。

3、如何使用对象？

- 对象包含属性和方法，访问方式：对象.属性，对象.方法名()。

## 3.1 内置对象

js 中常用的内置对象

- String、Number、Boolean、Array、Math、Date、RegExp、Function 对象。

## 3.2 API 手册

**http://www.w3school.com.cn/js/index.asp **

## 3.3 Number 对象

```html
<script>
    /*
    * 1. Number 对象
    * toFixed():四舍五入后转为字符串.
    */
    var value = 23.5678;
    console.log(value.toFixed(2));
    console.log(typeof(value.toFixed(2)));
</script>
```

## 3.4 Array 对象

### 3.4.1 如何创建数组

2种方式，如下：

```js
/*
 * 2. Array 对象
 * 2.1 如何创建数组
 * (1) 已知数据
 */
var a1 = ["张三",false,10];
console.log(a1[0]);

/* (2) 数组数据未知 */
var a2 = new Array();
a2.push("lisi");
a2.push(true);
a2.push(26);
console.log(a2[1]);
```

### 3.4.2 数组排序

```js
console.log("----数组反转----");
var arr = [6,2,8,14,12,9,5,1];
arr.reverse();
console.log(arr);

console.log("----数组排序----");
arr.sort();
console.log(arr);
```

​	以上排序结果是：[1, 12, 14, 2, 5, 6, 8, 9]。可以猜出 sort() 方法可以对数组内容排序，但是该方法默认按照字符串有小到大排序。

​	如果想按照其他逻辑（这里需要按照数值从小到大进行排序），该方法需要传入一个参数，该参数是一个函数，在该函数内写清楚你想比较的逻辑，sort 方法会调用底层方法去比较。

​	这里的数值比较大小的逻辑是：a-b，只有数字支持减法，js 会把其他基本数据类型隐式转换为数字进行比较。

```js
        console.log("----数组排序----");
        var arr = [6,2,8,14,12,9,5,1];
//        function compare(a,b){
//            return a-b;
//        }
//        arr.sort(compare);
        arr.sort(function(a,b){
            return a-b;
        });
        console.log(arr);
```

注意：上述 sort 方法里的参数是匿名函数，因为该方法只会用一次，所以这里用匿名方法。

## 3.5 Date 对象

### 3.5.1 如何创建日期

```js
console.log("----创建时间----");
var now = new Date();
console.log(now);
// 参数书写固定
var now2 = new Date("2017/05/01 14:00:52")
console.log(now2);
```

### 3.5.2 如何格式化日期对象

- 转为字符串

```js
var now = new Date();
console.log(now.toLocaleDateString());
console.log(now.toLocaleTimeString());
```

### 3.5.3 如何读写时间分量

- 读写时间分量：getFullYear()获取年份、getMonth() 获取月份、getDate() 获取日； 

```js
console.log("获取时间分量");
console.log(now);
var y = now.getFullYear();
var m = now.getMonth() + 1; // 月份默认从0开始,所以这里需要加1;
var d = now.getDate();
var today = y + "年" + m + "月" + d + "日";
console.log(today);
```

- 读写时间毫秒数

## 3.6 RegExp 对象

RegExp 对象表示正则表达式；

### 3.6.1 创建 RegExp 对象

var regExp = /pattern/flags;

其中 flags 标识有以下2种：

（1）g：设定当前匹配为全局模式；

（2）i：忽略匹配中的大小写检测。

### 3.6.2 RegExp对象常用方法

reg.test()：检测字符串中是否包含与正则相匹配的子串，返回 true 或 false。

```js
console.log("----RegExp 对象----");
var str = "you can you up, no can no bb";
// 创建正则对象
var reg =/no/;
// test()方法
var b = reg.test(str);
console.log(b);
// exec()方法
// 普通模式: 匹配第一个子串
console.log(reg.exec(str))
// 全局模式: 第 N 次调用返回和 reg 相匹配的第 N 个子串.
var reg =/no/g;
console.log(reg.exec(str));
console.log(reg.exec(str));
console.log(reg.exec(str));
console.log(reg.exec(str));
```

### 3.6.3 校验登录案例

#### 3.6.3.1 分别对用户名和密码校验

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        // 检查账号格式
        function checkUserName(){
            // 获取账号输入框的内容
            var username = document.getElementById("username").value;
            var span = document.getElementById("checkUserNameResult");
//            var reg = /\w{6,15}/;  // 正则写成这样是不行的,如果用户输入 zhangsanzhangsanzhangsan 也是匹配的,因为 test 是检查包含正则的子串.所以开头要加^,结尾要加$.
            var reg = /^\w{6,15}$/;
            if(reg.test(username)){
                // 校验正确,span 字体变成绿色.
                span.className ="ok";
            }else {
                span.className ="error";
            }
        }
    </script>
    <style>
        .ok {
            color: green;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <form>
        <p>账号: <input id="username" type="text" onblur="checkUserName();"/> <span id="checkUserNameResult">6-15位字母、数字、下划线</span></p>
        <p>密码: <input type="password"/> <span>6-15位字母、数字、下划线</span></p>
        <p>账号: <input type="submit" value="登录"/> </p>
    </form>
</body>
</html>
```

#### 3.6.3.2 点击登录按钮时校验

- form 有 onsubmit 事件，触发该事件才能提交表单里的数据；
- 登录按钮作用：点击 submit 按钮就是触发 onsubmit 事件；
- 任何事件都可以取消，当事件返回 false 时就取消事件。

```html
<form action="http://www.baidu.com" onsubmit="return false">
```

#### 3.6.3.3 完整代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        // 检查账号密码格式
        function checkUserName(){
            // 获取账号输入框的内容
            var username = document.getElementById("username").value;
            var span = document.getElementById("checkUserNameResult");
//            var reg = /\w{6,15}/;  // 正则写成这样是不行的,如果用户输入 zhangsanzhangsanzhangsan 也是匹配的,因为 test 是检查包含正则的子串.所以开头要加^,结尾要加$.
            var reg = /^\w{6,15}$/;
            if(reg.test(username)){
                // 校验正确,span 字体变成绿色.
                span.className ="ok";
                return true;
            }else {
                span.className ="error";
                return false;
            }
        }
        function checkPwd(){
            // 校验密码
            var pwd = document.getElementById("pwd").value;
            var spanPwd = document.getElementById("checkPwdResult");
            var reg = /^\w{8,20}$/;
            if (reg.test(pwd)){
                spanPwd.className = "ok";
                return true;
            }else {
                spanPwd.className = "error";
                return false;
            }
        }
    </script>
    <style>
        .ok {
            color: green;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <form action="http://www.baidu.com" onsubmit="return checkUserName()&&checkPwd();">
        <p>账号: <input id="username" type="text" onblur="checkUserName();"/> <span id="checkUserNameResult">6-15位字母、数字、下划线</span></p>
        <p>密码: <input id="pwd" type="password" onblur="checkPwd();"/> <span id="checkPwdResult">8-20位字母、数字、下划线</span></p>
        <input type="submit" value="登录"/>
    </form>
</body>
</html>
```

#### 3.6.3.4 && 和 &

- &：只会返回1、0；
- &&：都是布尔类型则返回布尔值，若有一个非布尔值则返回非布尔类型。

```js
console.log("&&和&");
console.log(true&&false);  // false
console.log(true&&3);      // 3
console.log(true&false);  // 0
console.log(true&3);      // 1
```

- 所以为了避免上述概念混淆不清，可以**使用加和是否等于2.**

```js
    <form action="http://www.baidu.com" onsubmit="return checkUserName()+checkPwd()==2;">
```

且上面使用“+号”，还避免了短路与问题，当用户账号名称和密码都没有输入时，两处都变红（之前是只有账号变红）。



## 3.7 Function 对象

Js 中函数就是 Function 对象，函数名就是 Function 对象的引用。

### 3.7.1 语法

```js
function 函数名([参数]){
    函数体;
	return 返回值; 
}
```

### 3.7.2 函数的返回值

默认返回 undefined，可以使用 return 返回具体的值。

### 3.7.3 函数的参数

- 调用时只要函数名一样，无论传入多少个参数，调用的都是同一个函数；
- 所有的参数传给 arguments 数组对象；
- 没有接收到实参的参数值是 undefined 。

### 3.7.4 匿名函数

该函数只用1次，其他地方不会被复用，就用匿名函数。

### 3.7.5 全局函数

- 全局函数可用于所有的 js 对象；
- 常用的全局函数有：parseInt/parseFloat、isNaN、eval。

```js
//eval：把字符串当表达式执行
console.log("eval");
var str = "1+2";  
console.log(str);       // 1+2
console.log(eval(str)); // 3
```

### 3.7.5.1 简单计算器 - eval

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        function add(){
            // 获取文本框元素
            var input = document.getElementById("num");
            // 获取文本框内容
            var num = input.value;
            try{
                // 给文本框赋值
                input.value = eval(num);
            }catch(ex){}
                input.value = "Error";
        }
    </script>
</head>
<body>
    <input type="text" id="num"/>
    <input type="button" onclick="add();" value="="/>
</body>
</html>
```
