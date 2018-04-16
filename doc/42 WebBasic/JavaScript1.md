[TOC]

# 一、外部对象概述

- 外部对象本质就是浏览器提供的 API；


- 外部对象包括：BOM 和 DOM，他们是包含关系。

![ BOM&DO](/Users/chenjinhua/ziliao/JavaLearned/doc/42%20WebBasic/8%20BOM&DOM.png)



# 二、Window 对象

window 表示浏览器窗口，

## 2.1 常用属性

​	window 对象常用的属性有5个：document、history、location、screen、navigator 等，每一个 属性都是一个对象，**每一个属性都有自己的 API（属性和方法）**。

## 2.2 常用方法

window 对象常用的方法有三类：

- alert()，confirm()；
- setInterval()，clearInterval()；
- setTimeout()，clearTimeout()。

### 2.2.1 案例1：弹窗提示是否要删除

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        function f1(){
            // 返回布尔值，确定是 true，取消是 false.
            var result = confirm("您确定要删除吗?");
            if (result){
                console.log("已删除");
            }else {
                console.log("未删除");
            }
        }
    </script>
</head>
<body>
    <input type="button" value="删除" onclick="f1();"/>
</body>
</html>
```

### 2.2.2 案例2：周期性定时器

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        <!-- 周期性定时器 -->
        function f1(){
            var n = 5;
            /*
             1. setInterval()方法是启动周期性定时器;
             2. 该方法需要传入2个参数,第一个参数是一个函数,定义要定时做的事情,第2个参数是时间间隔;
             3. 该定时器的作用是:每隔1000ms 调用1次函数;
             4. 启动定时器时会返回一个 id,此 id 是定时器的唯一标识,停止定时器时要使用;
             */
            var id = setInterval(function(){
                console.log(n--);
                if(n<0){
                    // 停止定时器
                    clearInterval(id);
                    console.log("DUANG!");
                }
            },1000);
            /*
            当前 f1() 方法相当于主线程, setInterval 方法相当于启动了支线程.二者并发执行不会互相等待.
            主线程在启动支线程后立即向下执行,支线程需要等待1s 后才会执行第1次.
             */
            console.log("BOOM!");
        }
    </script>
</head>
<body>
    <input type="button" value="计时器" onclick="f1();"/>
</body>
</html>
```

### 2.2.3 案例3：一次性定时器

隔一段时间执行1个函数，执行1次后自动停止，如广告。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        var id;
        function closeAds(){
            console.log("显示广告");
            /*
             1. 启动1次性定时器;
             2. 作用:推迟5000ms 后调用函数,调用结束后自动结束.
             */
            id = setTimeout(function(){
                console.log("自动关闭广告");
            },5000)
        }
        function quitCloseAds(){
            /*
             1. 在定时器没有自动停止前,可以调用此方法提前停止.
             */
            clearTimeout(id);
        }
    </script>
</head>
<body>
    <input type="button" value="广告" onclick="closeAds();"/>
    <input type="button" value="看广告" onclick="quitCloseAds();"/>
</body>
</html>
```

## 2.3 案例1：动态时钟

要求：

- 显示当前时间；
- 启动时钟；
- 停止时钟。



代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        var id;
        function start(){
            var clock = document.getElementById("clock");
            id = setInterval(function(){
                var date = new Date();
                // 转换为 本地格式.
                var now = date.toLocaleTimeString();
                // 将时间写入 p.
                clock.innerHTML = now;
            },1000);
        }
        function pause(){
            clearInterval(id);
        }
    </script>
    <style>
        p {
            border: 1px solid red;
            width: 200px;
            height: 50px;
            line-height: 50px;
            text-align: center;
        }
    </style>
</head>
<body>
    <p id="clock"></p>
    <input type="button" value="开始" onclick="start();"/>
    <input type="button" value="停止" onclick="pause();"/>
</body>
</html>
```

​	上面程序有 bug：点击开始，点击3次，再点击停止，发现定时器不会停止。

原因是：每次点击开始都会产生1个定时器，点击3次就有3个定时器，产生3个 id的值，上述变量 id 值为第3个定时器的，停止时也只停止了第3个定时器，前2个定时器仍存在，故有此bug。

解决方法：保证在开始时只启动1次定时器，利用 id 来判断：如果 id为空则定时器未启动，若 id 非空则定时器已启动。如下：

```js
    <script>
        var id;
        function start(){
            var clock = document.getElementById("clock");
            // 如果 id非空则定时器已启动,不要重复启动了.
            if(id){
                return;
            }
            id = setInterval(function(){
                var date = new Date();
                // 转换为 本地格式.
                var now = date.toLocaleTimeString();
                // 将时间写入 p.
                clock.innerHTML = now;
            },1000);
        }
        function pause(){
            clearInterval(id);
        }
    </script>
```

但是上述代码仍有问题，点击停止后再点击开始，定时器没有生效。

原因是：停止定时器后，id 的值不为空，所以认为已经启动定时器了没有再启动定时器。

解决方法：停止定时器时，清空 id 值（id = null或者 undefined）。

完整代码为：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        var id;
        function start(){
            var clock = document.getElementById("clock");
            // 如果 id非空则定时器已启动,不要重复启动了.
            if(id){
                return;
            }
            id = setInterval(function(){
                var date = new Date();
                // 转换为 本地格式.
                var now = date.toLocaleTimeString();
                // 将时间写入 p.
                clock.innerHTML = now;
            },1000);
        }
        function pause(){
            clearInterval(id);
            // 清空定时器 id,保证下次能启动定时器.
            id = null;
        }
    </script>
    <style>
        p {
            border: 1px solid red;
            width: 200px;
            height: 50px;
            line-height: 50px;
            text-align: center;
        }
    </style>
</head>
<body>
    <p id="clock"></p>
    <input type="button" value="开始" onclick="start();"/>
    <input type="button" value="停止" onclick="pause();"/>
</body>
</html>
```

## 2.4 案例2：撤销发送邮件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        p {
            width: 300px;
            height: 50px;
            border: 1px solid red;
            text-align: center;
            line-height: 50px;
        }
    </style>
    <script>
        var id;
        function sendEmail(){
            if (id){
                return;
            }
            var p = document.getElementById("msg");
            p.innerHTML = "正在发送...";
            // 推迟3s 显示已发送.
            id = setTimeout(function(){
                p.innerHTML = "发送成功!";
            },3000);
        }
        function quit(){
            if (id){
                clearTimeout(id);
                // 不要将 p 提取为全局变量,尽量减少全局变量.
                var p = document.getElementById("msg");
                p.innerHTML = "取消发送";
                id = null;
            }
        }
    </script>
</head>

<body>
    <input type="button" value="发送邮件" onclick="sendEmail();"/>
    <input type="button" value="取消发送" onclick="quit();"/>
    <p id="msg"></p>
</body>
</html>
```

**注意：**

**1、取消定时器，只有定时器未结束时，取消才有意义，所以取消时要加判断；**

```js
        function quit(){
            if (id){
                clearTimeout(id);
                // 不要将 p 提取为全局变量,尽量减少全局变量.
                var p = document.getElementById("msg");
                p.innerHTML = "取消发送";
                id = null;
            }
        }
```

**2、 1次性定时器除了手动停止外，还会自动停止，所以自动停止时要清空 id；**

```js
function sendEmail(){
    ......
    id = setTimeout(function(){
        p.innerHTML = "发送成功!";
        id = null;
    },3000);
}
```

3、由于 sendEmail 和 quit 方法有需要用到变量 p，但不要将变量p 提取为全局变量，近来减少全局变量。

4、假设如果将变量p 提取为全局变量，不能按照如下方式写:

```html
   <script>
	    var p = document.getElementById("msg");
        function sendEmail(){
            ......
            p.innerHTML = "正在发送...";
      	    ......
        }
        function quit(){
            p.innerHTML = "取消发送";
            ......
        }
    </script>
```

因为 js 有2种方式:

1. 写函数,页面加载好后,用户操作时调用;
2. 直接写 js,页面加载前就执行 js. 此种方式 js 比网页加载更早。

将变量 p 写在函数外，这时 在页面加载前就执行 js，但是由于此时并没有页面，所以找不到 p，会报错。

# 三、location 对象

- 属性：href

location.href=“http://www.baidu.com”； 改变当前浏览的网址。

- 方法：reload()

重新加载当前网址，即刷新当前页面。

# 四、document 对象

## 4.1 DOM 操作

- 查找节点
- 读取、修改节点信息
- 创建新节点
- 删除节点

### 4.1.1 读取、修改

#### 4.1.1.1 获取节点名称和类型

- .nodeName：节点名称
- .nodeType：节点类型

#### 4.1.1.2 读写元素节点的内容

- innerText：设置或获取位于对象起始和结束标签内的文本；
- innerHTML：设置或获取对象起始和结束标签内的 HTML。

以上区别就是（2）可以带标签，（1）只能是文本，不能带标签。

#### 4.1.1.3 读写节点的值

除了 label 之外的**表单控件**都有值。

- 读：input.value;
- 写：input.value ="";

**区分内容和值：**

- 内容：任何双标签中间的都是内容；
- 值：除了 label 之外的**表单控件**都有值。

#### 4.1.1.4 读写节点属性

- getAttribute() 根据属性名称获取属性的值；
- .属性名称，只有 style、className、id 属性可以用.调用进行读写。

**备注：style 是一个对象，对象里有很多属性，可以通过对象.属性对样式进行操作，如 a.style.color; a.style.display。**

#### 4.1.1.5 案例：图片轮播

1、实现图片轮播

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        div {
            width: 500px;
            height: 333px;
            border: 1px solid red;
        }
        .hidden {
            display: none;
        }
        .show {
            display: inline-block;
        }

    </style>
    <script>
        var index = 0;
        window.onload = function(){
            var imgs = document.getElementsByTagName("img");
            setInterval(function(){
                index ++;
                for(var i=0;i<imgs.length;i++){
                    imgs[i].className = "hidden";
                }
                imgs[index%3].className = "show";
            },2000);
        }
    </script>
</head>

<body>
   <div>
       <img src="../../images/car1.jpg"/>
       <img src="../../images/car2.jpg" class="hidden"/>
       <img src="../../images/car3.jpg" class="hidden"/>
   </div>

</body>
</html>
```

2、在上述功能之上实现鼠标悬停在图片上时图片停止轮播

- hover 是伪类选择器，是 CSS 中用于设置样式的；这里是 js 需要用事件：
- onmouseover 是鼠标悬停事件、onmouseout 是鼠标离开事件。

### 4.1.2 查询节点

查询节点的方式：

1、通过 id；

2、通过标签名称

- document.getElementsByTagName() 在整颗树上查询；
- **元素.getElementsByTagName() 只在元素范围内查询**。

3、通过层次（节点关系）

- parentNode查找单个父节点；
- childNodes 查找多个子节点（带文本）；
- **元素.getElementsByTagName()  查询某节点的孩子（不带文本）；**

注意：这个childNodes 会返回节点和其文本，一般用元素.getElementsByTagName() ，它只返回节点名称，不带文本。

- 获取节点的兄弟姐妹

节点.父亲.孩子们[i]。

4、通过 name 属性

根据标签的name 属性，一般适用于单选和多选。

### 4.1.3 增加节点

1、创建新节点：document.creatElement(elementName);

2、添加新节点：

- parentNode.appendChild(newNode) 新节点作为父节点的最后一个子节点添加；
- parentNode.insertBefore(newNode，refNode) 新节点位于参考节点之前。

### 4.1.4 删除节点

node.removeChild(childNode)删除某个子节点，childNode 是 node 的子节点。

### 4.1.5 案例1：联动菜单

创建省、市下拉选；改变省市时重置市。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        var cities;
        window.onload = function(){
            cities = [["扬州","南京","无锡"],["临沂","槐房"],["温州","杭州","湖州"]];
        };
        function chg(){
            //获取省份下标
            var provinceSelect = document.getElementById("provinceSelect");
            var p = provinceSelect.value;
            console.log(p);
            // 获取本省份的城市
            var pCities = cities[p];
            // 先删除后添加
            // 删除节点
            var citySelect = document.getElementById("citySelect");
            var cityOptions = citySelect.getElementsByTagName("option");

            // for 循环这里有坑,一定要从下往上删.若从上往下删,删除位置1,位置2的元素的位置会变成1,会出现错误
            for (var i=cityOptions.length-1; i>0;i--){
                citySelect.removeChild(cityOptions[i]);
            }
            // 增加节点
            if (pCities){
                for (var j=0;j<pCities.length;j++){
                    var cityOption = document.createElement("option");
                    cityOption.innerHTML = pCities[j];
                    citySelect.appendChild(cityOption);
                }
            }
        }
    </script>
</head>
<body>
省:
    <select id="provinceSelect" onchange="chg();">
        <option value="-1">请选择</option>
        <option value="0">江苏</option>
        <option value="1">山东</option>
        <option value="2">浙江</option>
    </select>
市:
    <select id="citySelect">
        <option>请选择</option>
    </select>
</body>
</html>	
```

​	上述方法把 option 看出 select 的子节点，也可以把 option 看成 select 的内容，所以以上删除旧城市的代码有简化方法：

```js
citySelect.innerHTML = "<option>请选择</option>";
```

# 五、自定义对象

## 5.1 直接量（JSON）

{"name":"zhangsan","age":18}，其中 {} 代表一个对象，内含键值对。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
        function f1(){
            var stu = {"name":"jinhua","age":18,"work":function(){
                alert("静心!!");
            }};
            alert(stu.name);
            alert(stu.age);
            stu.work();
        }
    </script>
</head>
<body>
    <input type="button" value="按钮1" onclick="f1();"/>
</body>
</html>
```

## 5.2 构造器

首字母大写的函数就是构造器。

### 5.2.1 内置构造器

特殊构造器：Date，Array，String，RegExp；

通用构造器：Object。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
       function f2(){
           var tch = new Object();
           tch.name = "lisi";
           tch.age = 25;
           tch.teach = function(){
               alert("静心教学");
           }
           alert(tch.name);
           alert(tch.age);
           tch.teach();
       }
    </script>
</head>
<body>
    <input type="button" value="按钮2" onclick="f2();"/>
</body>
</html>
```

### 5.2.2 自定义构造器

创建一个首字母大写的函数；明确的声明参数；将参数记录到此对象上。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script>
       function Coder(name,age,work){
           this.name = name;
           this.age = age;
           // this 指代当前对象;
           // this.job 给当前对象加 job 属性;
           // =work,将参数 work 赋值给 job 属性.
           this.job = work;
       }
        function f2(){
            var coder = new Coder("wangwu",20,function(){
                alert("静心编码");
            });
            alert(coder.name);
            alert(coder.age);
            coder.job();
        }
    </script>
</head>
<body>
    <input type="button" value="按钮3" onclick="f2();"/>
</body>
</html>
```

## 5.3 小结

无论用哪种方式创建出来的对象都一样，都是 Object；

若对象需要复用，使用第3种。

# 六、事件

## 6.1 事件概述

1、什么是事件？

就是用户的操作，也是 js 调用的时机。

2、事件的分类

鼠标事件；

键盘事件；

状态事件：条件成立时自动触发。

## 6.2 事件定义

1、直接定义事件：在元素属性里定义事件；

2、动态绑定事件：在 js 里获取节点，通过修改属性的方式追加事件属性；

## 6.3 事件对象（*）

1、什么是事件对象？

​	在事件触发的那一刻，浏览器会创建一个事件对象 event，用来封装事件相关的信息，包括鼠标的坐标，键盘的按键等。

2、如果获取事件对象？

- 直接定义事件：在函数调用时传入参数 event，在函数的声明上加参数接收 event。
- 动态绑定事件：在函数调用时浏览器会自动传入 event，在函数的声明上加参数接收 event。


3、如何取消事件？

方法里return false；

## 6.4 事件机制（*）

1、什么是冒泡机制？

事件是由内向外传播的。

2、如何取消冒泡？

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        div {
            width: 218px;
            height: 218px;
            border: 1px solid red;
            padding: 50px;
        }
        p {
            width: 100px;
            height: 100px;
            border: 1px solid red;
            padding: 50px;
            text-align: center;
        }
    </style>
    <script>
        function f1(e){
            alert("button");
            if (e.stopPropagation()){
                e.stopPropagation();
            }else {
                e.cancelBubble = true;
            }
        }
    </script>
</head>
<body>
    <div onclick="alert('div')">
        <p onclick="alert('p');">
            <input type="button" value="按钮" onclick="f1(event);"/>
        </p>
    </div>
</body>
</html>
```

3、冒泡机制的作用