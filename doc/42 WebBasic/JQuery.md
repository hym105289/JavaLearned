JQuery 是一个 JS 框架，它封装了 JS、CSS、DOM，提供了一致的简洁的 API，极大的简化了 JS 编程。

[TOC]

# 一、jQuery 使用

在页面上有一个“+”按钮，点击按钮后页面上段落字体被放大。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        function big(){
            // jQuery 默认字体 16px
            var size = $("p").css("font-size");
            size = size.replace("px","");
            $("p").css("font-size",++size+"px");
        }
    </script>
</head>
<body>
    <input type="button" value="+" onclick="big();"/>
    <p> jQuery 是一个轻量级的 JS 框架.</p>
    <p> 提供了一套 API.</p>
    <p> 极大的提高了编程效率.</p>
</body>
</html>
```

# 二、jQuery 对象

- jQuery 对象本质就是对 DOM 数组的封装；
- 同时 jQuery 对象还提供了很多操作 DOM 数组的 API；
- 只有 jQuery 对象才能调用 jQuery API。

![0 jQuery](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/10 jQuery .png)

## 2.1 jQuery 对象变成 DOM 对象

案例1：页面上有几个段落和一个按钮，每点击1次按钮，输出一个段落。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>

        function prt(){
            for (var i=0;i< $("p").length;i++){
                console.log($("p")[i].innerHTML);
            }
        }
    </script>
</head>
<body>
    <input type="button" value="打印" onclick="prt();"/>
    <p> jQuery 对象本质是对 DOM 数组的封装.</p>
    <p> jQuery 对象还提供了很多对 DOM 数组操作的 API.</p>
    <p> 只有 jQuery 对象才能调用 jQuery 的 API. </p>
</body>
</html>
```

## 2.2 DOM 对象变成jQuery 对象

案例2：页面上有几张图片和一个按钮，点击1次图片图片变大，再点击图片图片会变小。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        function chg(img){
            if ($(img).width() == 500){
                $(img).width(750).height(425);
            }else {
                $(img).width(500).height(333);
            }
        }
    </script>
</head>
<body>
<div>
    <img src="../../images/car1.jpg" onclick="chg(this);"/>
    <img src="../../images/car2.jpg" onclick="chg(this);"/>
    <img src="../../images/car3.jpg" onclick="chg(this);"/>
</div>
</body>
</html>
```

# 三、jQuery 选择器

## 3.1 基本选择器

![1 jQuery 基本选择](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/11 jQuery 基本选择器.png)

## 3.2 层次选择器

## 3.3 过滤选择器(*)

### 3.3.1 基本过滤（*）

![2 jQuery 过滤选择](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/12 jQuery 过滤选择器.png)



3.3.2 内容过滤

3.3.3 可见性过滤

3.3.4 属性过滤

3.3.5 状态过滤

3.4 表单选择器

![2.5 jQuery 表单选择](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/12.5 jQuery 表单选择器.png)



# 四、jQuery 操作 DOM（即API）

## 4.1 jQuery 读写节点

![3 jQuery 读写节](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/13 jQuery 读写节点.png)

1. 读写节点的内容

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        $(function(){
            // 1. 读写节点的内容
            console.log($("p:last").html("<p> 有参数就是<u>写</u></p>"));
        });
    </script>
</head>
<body>
    <p> jQuery 支持<b>读写</b>节点.</p>
    <p> jQuery 支持<b>增删</b>节点.</p>
    <p> jQuery 支持<b>查询</b>节点.</p>
</body>
</html>
```

2. 读写节点的值

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        $(function(){
            // 2. 读写节点的值
            console.log($(":button:first").val("重置"));
        });
    </script>
</head>
<body>
    <p>
        <input type="button" value="按钮1"/>
        <input type="button" value="按钮2"/>
        <input type="button" value="按钮3"/>
    </p>
</body>
</html>
```

3. 读写节点的属性

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        $(function(){
            // 2. 读写属性的值
            console.log($("img").attr("src"));
            $("img").attr("src","../../images/car2.jpg");
        });
    </script>
</head>
<body>
   <img src="../../images/car1.jpg"/>
</body>
</html>
```

## 4.2 jQuery 增删节点

### 4.2.1 增加节点

1、创建节点

$("节点内容");

2、插入 DOM 节点（将节点挂在 DOM 树上）

parent.append(obj) 作为最后一个子节点添加进来

parent.prepend(obj) 作为第一个子节点添加进来

brother.after(obj) 作为下一个兄弟节点添加进来

brother.before(obj) 作为上一个兄弟节点添加进来

### 4.2.2 删除节点

obj.remove() 删除节点（自己删除自己）

obj.remove(selector) 

obj.empty() 清空节点内容

## 4.3 jQuery 遍历节点

![4 jQuery 遍历节](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/14 jQuery 遍历节点.png)

## 4.4 jQuery 对象和 DOM 对象

1、通过 $ 获得的都是 jQuery 对象

```javascript
$("img") 、 $(img) 、 $("<li>广州</li>")
```

2、调用写方法返回的是 jQuery 对象

```js
obj.width(218);  obj.html("<p>段落</p>");
```

3、调用读方法

有时返回 jQuery 对象，有时返回 DOM 对象

（1）若方法返回元素则是 jQuery 对象

```js
obj.parent();   obj.next();
```

（2） 若方法返回的是文本，则是 DOM 对象

```js
obj.html();  obj.attr("src");
```

## 五、jQuery 样式操作

jQuery 专门对样式操作提供了支持：

![5 jQuery 样式操](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/15 jQuery 样式操作.png)

# 六、jQuery 事件处理

## 6.1 jQuery 和 js 相同和不同点

### 6.1.1 事件概述

（1）什么是事件 ：和 js 一样。

（2）事件的分类 ： 和 js 一样。

### 6.1.2 事件定义

（1）直接定义：和 js 一样

**（2）动态绑定**

- 页面加载： $(function(){})； 和 window.onload区别：后者只能写1次，若写了多次则后面的会覆盖前面的；而前者可以写多次，若写了多次它们的逻辑会叠加。
- $("").click(function(){})；

（3）取消事件： 和 js 一样。

### 6.1.3 事件对象

（1）什么是事件对象 ： 和 js 一样。

（2）如何获取事件对象

（2.1）直接定义事件 ：和 js 一样。

（2.2）动态绑定事件 ：和 js 一样，只是获得的 event 是被 jQuery 封装后的 event。

### 6.1.4 事件机制

（1）冒泡机制 ： 和 js 一样。

**（2）如何取消冒泡机制** : e.stopPropagation()；

（3）作用 ：和 js 一样。

**（4）如何获取事件源**： e.target。

## 6.2 jQuery 特有的事件：合成事件

（1）hover(mouseenter,mouseleave)：模拟鼠标悬停事件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .big {
            width: 750px;
            height: 500px;
        }
    </style>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        $(function(){
            // 给图片绑定悬停事件
            $("img").hover(
                    // 鼠标悬停事件
                    function(){
                        $(this).addClass("big");
                        // 鼠标离开事件
                    },function(){
                        $(this).removeClass("big");
                    });
        });
    </script>
</head>
<body>
   <img src="../../images/car1.jpg"/>
</body>
</html>
```

（2）toggle() 在多个响应事件中切换。

## 6.3 模拟操作

$obj.trigger(事件类型) 

案例：模拟广告自动关闭功能

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        div {
            border: 1px solid red;
            height: 500px;
        }
        input {
            float: right;
            margin: 10px;
        }
    </style>
    <script src="../js/jquery-3.3.1.js"></script>
    <script>
        $(function(){
            $(":button").click(function(){
                $("#gg").slideUp(600);
            });

            // 3s 之后
            setTimeout(function(){
                $(":button").trigger("click");
            },3000);

        });

    </script>
</head>
<body>
    <div id="gg">
        <input type="button" value="x"/>
    </div>
</body>
</html>
```

# 七、jQuery 动画

## 7.1 显示、隐藏的动画效果

show() / hide()

作用：通过同时改变元素的宽度和高度来实现元素的显示或隐藏；

用法：

（1）不传参数，元素会立刻显示或者隐藏；

（2）$obj.show(执行时间，回调函数);

​	执行时间：slow、normal、fast 或者毫秒数；

​	回调函数：动画执行完毕之后要执行的函数。

备注：为什么这里会有回调函数，而不是在 show() 执行之后处理？

原因：动画的实现原理，通过定时器不断的修改元素的样式，定时器和主线程是并发执行的。所以上面有参数2，匿名函数，在动画完成时由 jQuery 自动调用。

某件事完成时被自动调用的函数，称之为**回调函数**。

## 7.2 上下滑动式的动画实现

slideUp() / slideDown()

作用：通过改变元素的高度来实现元素的显示或隐藏；

用法同 show() / hide()。

## 7.3 淡入淡出式动画效果

fadeIn() / fadeOut()

作用：通过改变元素的不透明度opacity 来实现元素的显示或隐藏；

用法同 show() / hide()。

## 7.4 自定义动画效果

animate(偏移位置，执行时间，回调函数)

​	偏移位置：{} 描述动画执行之后元素的样式；

​	执行时间：毫秒数；

​	回调函数：动画结束后要执行的函数。