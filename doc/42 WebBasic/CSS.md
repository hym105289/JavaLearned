

[TOC]

CSS：层叠样式表（Cascading *S*tyle *S*heets)

# 一、如何使用

使用方式有3种：

### 1.1 内联样式

在 HTML 元素内部，如下：

```html
<h1 style="color:red;">英雄联盟</h1>
```

### 1.2 内部样式表

在 head 元素内添加 style 元素，在 style 元素内添加样式规则，如下：

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        p{
            color: green;
        }
    </style>
</head>
```

### 1.3 外部样式表

#### 1.3.1 创建外部样式文件

my.css 里内容为：

```css
/* 引用 CSS 文件到 html 文件 */
h2{
    color: blue;
}
```

#### 1.3.2 引用该样式文件

外部式css样式就是把css代码写一个单独的外部文件中，这个css样式文件以“`.css`”为扩展名，在<head>内（不是在<style>标签内）使用<link>标签将css样式文件链接到HTML文件内，如下面代码：

`<link href="my.css" rel="stylesheet" type="text/css" />`

注意：

1、css样式文件名称以有意义的英文字母命名，如 main.css。

2、**rel="stylesheet" type="text/css" 是固定写法不可修改**。

3、<link>标签位置一般写在<head>标签之内。

# 二、CSS 语法

## 2.1 CSS 语法规范

CSS ：由多个样式规则组成；每个样式规则由两部分构成：选择器 和 样式声明。

![SS ](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/CSS 1.png)

```css
h1 {color:red; font-size:14px;}
```

## 2.2 CSS 规则特性

- 继承性：父元素的CSS 的声明可以被子元素继承；
- 层叠性：同一个元素若存在不同的 CSS 规则，对于不冲突的声明可以叠加；
- ### 优先级：同一个元素若存在多个 CSS 规则，对于冲突的声明以优先级高（元素就近原则）的为准。

# 三、CSS 选择器

## 3.1 元素选择器

- 通过元素名来选择css 作用的目标，如 p{}。

## 3.2 类选择器

- 类选择器允许以一种独立于文档元素的方式来指定样式，**语法为：.className{color:red}**；
- 所有能够附带 class 属性的元素都可以使用此样式声明，将元素的 class 属性的值设置为样式类名。

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .jinhua{
            color: #00ffee;
        }
    </style>
</head>
<body>
    <h2 class="jinhua"><a name="g1">1、游戏背景</a></h2>
    <h3>战争学院</h3>
    <p>
        &nbsp;&nbsp;战争学院是英雄联盟裁决瓦洛兰政治纠纷之地。这里是绝对中立的领土，严禁任何纷争。
        违反者将面对学院的士兵和魔法。学院坐落于一座巨型水晶枢纽之上，由黑曜石、
        贵金属和魔法塑形而成。它位于莫格罗恩关隘的北方入口，刚好位于相互敌对的城邦德玛西亚和诺克萨斯之间。
    </p>
<h3 class="jinhua">德玛西亚</h3>
```

- 可以将类选择器和元素选择器结合使用，实现对某种元素中不同样式的细分控制，**语法为：元素选择器.className{声明}**。


## 3.3 id 选择器

id 选择器以一种独立于文档元素的方式来指定样式，它仅作用于 id 属性的值，**语法为：#id{}**。

## 3.4 选择器组

选择器声明以逗号隔开的选择器列表，将一些相同的规则作用于多个元素。

## 3.5 派生选择器

派生选择器用来选择子元素，分2种：

- 后代选择器：选择某元素的所有后代（子孙）元素，语法为：**选择器1空格选择器2{}**。
- 子元素选择器：选择某元素的所有子元素，语法为：**选择器1>选择器2{}**。

## 3.6 伪类选择器

伪类用于设置同一个元素在不同状态下的样式。

常用伪类：

- :link 向未被访问的超链接添加样式；
- :visited 向被访问的超链接添加样式；
- :active 向被激活的元素添加样式；
- :hover 当鼠标悬停至元素上方时，向该元素添加样式；
- :focus 当元素获得焦点时，向该元素添加样式。

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        a:link{
            color: red;
        }
        a:visited{
            color: green;
        }
        .btn:active{
            background-color: yellow;
        }
        img:hover{
            width: 100px;
            height: 100px;;
        }
        #hua:focus{
            background-color: red;
        }
    </style>
    <link href="my.css" rel="stylesheet" type="text/css" />
</head>
<body>
<p>
    <a href="http://www.baidu.com"> 百度一下 </a>
</p>
<p>
    <a href="http://www.tmooc.cn"> tmooc </a>
</p>
<p>
    <a href="http://www.google.cn"> google </a>
</p>
<p>
    <form>
        <input type="submit" value="提交" class="btn"/>
        <p>
            请输入:<input type="text" id="hua"/>
        </p>
    </form>
</p>
<p>
    <img src="../images/biaoge.PNG">
</p>
</body>
```

# 四、CSS 声明

## 4.1 border

- border属性：用来设置元素的边框；
- 四边设置 border:width值 style值 color 值；
- 样式单位： %（百分比）、px（像素）、em（1em 等于当前的字体尺寸，2em等于当前的字体尺寸的2倍，一般用于首行缩进）；


- overflow：当元素溢出元素框时如何处理 visible、hidden、scroll、auto。

```html
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        p{
            border: 1px solid red;
            /* 一般固定宽度高度时才会有溢出 */
            width: 200px;
            height: 50px;
            overflow: auto;
        }
    </style>
</head>
<body>
    <h1>苍老师</h1>
    <p>&nbsp;&nbsp;《英雄联盟》（简称lol）是由美国<u>Riot Games</u>开发，<b>腾讯</b>游戏运营的英雄对战网游。
        《英雄联盟》除了即时战略、团队作战外，还拥有特色的英雄、自动匹配的战网平台，包括天赋树、召唤师系统、符文等元素。&nbsp;&nbsp;《英雄联盟》（简称lol）是由美国<u>Riot Games</u>开发，<b>腾讯</b>游戏运营的英雄对战网游。
        《英雄联盟》除了即时战略、团队作战外，还拥有特色的英雄、自动匹配的战网平台，包括天赋树、召唤师系统、符文等元素。&nbsp;&nbsp;《英雄联盟》（简称lol）是由美国<u>Riot Games</u>开发，<b>腾讯</b>游戏运营的英雄对战网游。
        《英雄联盟》除了即时战略、团队作战外，还拥有特色的英雄、自动匹配的战网平台，包括天赋树、召唤师系统、符文等元素。学习 java EE.</p>
</body>
```

## 4.2 box



![SS 2 box 模](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/CSS 2 box 模型.png)

# 五、CSS 声明——管理员列表

## 5.1 分区box设置

![SS 3 管理员列表 ](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/CSS 3 管理员列表 1.png)

思路：

- 将网页分区

## 5.2 背景图片

### 5.2.1 背景普通设置

3个属性：

- background-image 设置背景图片

语法：background-image:url('../images/background.png')；

- background-repeat 设置图片的平铺效果

- background-position 设置图片在元素中的位置

- background-attachment背景图片滚动

  默认 scroll 滚动，可设置为 fixed 固定。

### 5.2.2 背景简化设置

语法： background：颜色 图片 平铺 位置；上述4个值可酌情省略，但至少要保留颜色或图片。

例子：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<style>
    body {
        background-image: url('../images/background.png');
        background-repeat: repeat-y;
        background-position: center;
        background-attachment: fixed;
    }
    div {
        width: 200px;
        height: 100px;
        border: 1px solid red;
        margin: 5px auto ;
    }
    .enemy {
        background: url('../images/airplane.png') no-repeat center;
    }
    .hero {
        background: url('../images/hero1.png') no-repeat center;
    }
</style>
<body>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="enemy"></div>
    <div class="hero"></div>
    <div class="hero"></div>
</body>
</html>
```

## 5.3 文本格式化

- 指定字体font-family
- 大小 font-size
- 加粗font-weight
- 颜色color
- 文本排列 text-align
- 文本修饰 text-decoration
- 行高 line-height
- 首行文本缩进 text-indent。

注意：水平居中 text-align;不支持设置文字垂直居中，可通过行高设置：当行高=元素高时，文字会在整个元素单元内垂直居中。



重点：**退出** 按钮的设置！

1、退出是一个超链接；

2、标签a居右，如果是让文本居右则达不到目的，因为文本已经居a 元素的右边，应该是让 a 元素居右；在 lego 元素内设置 text-align：right！

3、标签a 距离右侧40px，不能使用 lego 的内边距，这样会使 lego 的总宽度变大，应该使用 a元素的外边距！

4、鼠标悬停时，文本加粗。

 顶部 lego 区 div 的css 样式为：

```css
.lego {
    width: 960px;
    height: 61px;
    text-align: right;
    line-height: 61px;
}
.lego>a {
    margin: auto 40px;
    color: #FFF;
    text-decoration: none;
}
.lego>a:hover {
    font-weight: bold;
}
```

底部版权区div 的 css 样式为：

```css
.copyright>p {
    text-align: center;
    height: 25px;
    line-height: 25px;
    color: #FFFFFF;
}
```

## 5.4 表格样式

表格可以使用 **box 模型：宽、高、边框、内边距、外边距**，以及文本格式化属性。

表格特有样式属性：border-collapse属性 是否合并相邻的边框， collapse 合并，seperate 不合并。

### 5.4.1 设置表格边框

```css
.data>table {
    border: 1px solid red;
}
```

以上只给 table 加一个外边框，如果表格每一列每一行要加边框，需要设置 td，如下：

```css
.data>table {
    border: 1px solid red;
}
.data td {
    border: 1px solid red;
}
```

设置边框合并:   边框合并必须写在 table 上。

```css
.data>table {
    border: 1px solid #cccccc;
    /* 边框合并必须写在 table 上 */
    border-collapse: collapse;
}
.data td {
    border: 1px solid #cccccc;
}
```

### 5.4.2 设置表格内容

```css
.data thead tr{
    height:40px;
    background-color: yellow;
    font-weight: bold;
}
.data tbody tr {
    height: 32px;
    background-color: #FFFFFF;
}

.data tbody tr:hover {
    background-color: #f7f9fd;
}
```

## 六、定位

## 6.1 什么是定位

就是排列元素的方式，通过定位可以将元素摆放到网页的任意位置。

## 6.2 有哪些定位

1、默认定位（也叫流定位）

2、特殊定位：浮动定位、相对定位、决定定位、固定定位。

### 6.2.1 浮动定位

- 作用：让块元素左右排列；
- 特点：浮动的目标会离队（脱离流）；
- 步骤： （1） 目标离队（2）弟弟前进（3）目标到达指定位置；
- 分类：左浮动、右浮动。

#### 6.2.2.1 右浮动

- 作用：可以让块按照倒序左右排列；

补充：

浏览器渲染网页的规则：

1、元素的高度会自适应，以内部流的高度为准；

2、浏览器认为文字很重要，若浮动元素遮挡了文字，浏览器会想办法让文字显示完整。

例子：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        #d0,p {
            border: 1px solid red;
            width: 400px;
        }
        #d1,#d2,#d3 {
            border: 1px;
            width: 100px;
            height: 100px;
            margin: 10px;
        }
        #d1 {
            background-color: red;
        }
        #d2 {
            background-color: green;
        }
        #d3 {
            background-color: blue;
        }

        /* 浮动 */
        #d1,#d2,#d3 {
            float: right;
        }
    </style>
</head>

<body>
    <div id="d0">
        <div id="d1"></div>
        <div id="d2"></div>
        <div id="d3"></div>
    </div>
    <p>浮动时观察我的位置</p>

</body>
</html>
```

#### 6.2.2.2 左浮动

- 作用：可以让块按照正序左右排列；

### 6.2.2 如何消除浮动影响

clear：left/right。在哪个元素上加上 clear，就消除浮动对该元素的影响，则该元素就会出现在浮动元素的下方。

#### 6.2.2.1 若无需保留父元素边框，消除影响的方法

若无需保留父元素边框，消除影响方法为：

- 对父元素设置边框高为0，对p 元素设置 clear；

css 样式设置为：

```html
/* 消除浮动影响 */
p {
    clear: left;
}
#d0 {
    border: 0;
}
```

#### 6.2.2.2 若需保留父元素边框，消除影响的方法

若需要保留父元素边框，消除影响方法为：

- 在父元素内加一个空块（边框、内容均无）；
- 在空块上写 clear，此时空块会出现在浮动元素下方，由于空块还在流内从而拉伸了父元素的高度；
- 对 p 元素 clear。

html 新增 #d4

```html
<body>
    <div id="d0">
        <div id="d1"></div>
        <div id="d2"></div>
        <div id="d3"></div>
        <div id="d4"></div>
    </div>
    <p>浮动时观察我的位置</p>

</body>
```

css 样式设置为：

```html
 /*消除浮动影响 */
#d4,p {
    clear: left;
}
```

### 6.2.3 照片墙效果

注意，块区域不用 div，用列表 ul 或者 ol，性能更佳。

![SS 4 照片墙效](/Users/chenjinhua/ziliao/JavaLearned/doc/42 WebBasic/CSS 4 照片墙效果.png)

**1、思路：先由一张图片的宽度、计算出 li 的宽度、再计算出 ul 的宽度。**

**2、设置 CSS 样式：先从外向内考虑，先 body，然后 ul，li，img、p。分别从box 模型的宽、高、边框、内边距、外边距考虑。**

3、由于 li 都浮动定位了，所以 ul 里的流为空，所以 ul 只有1条红色的边框，该怎么办呢？

注意：ul 的边框我们只是用于调试，本不需要的，所以这里只需要将ul 的 border 设置注释掉或者 border 的宽度设置为0.



## 6.3 相对、绝对、固定定位区别和联系

#### 6.3.1 区别

- 参考的目标不同。

#### 6.3.2 相同点

- 设置偏移的方式相同：以目标的任意边为基准，向中心方向偏移则为正，反之为负。

## 6.4 相对定位

- 以自身为目标产生偏移，通常偏移量很小；

- 目标不离队（不脱离流）。

  **特点：偏移量小，不离队。**

- 语法 position:relative 偏移量。

例子：采用相对定位，实现抖动的效果。

```css
/* 采用相对定位，实现抖动的效果 */
li:hover {
    position: relative;
    right: -2px;
    top:-2px;
}
```

## 6.5 绝对定位

- 以带有 position 属性的父辈为目标产生偏移，偏移量较大；
- 若父辈都有 position属性，则以最近的父辈为目标；
- 若父辈都没有 position 属性，则以 body 为目标；
- 目标离队；
- 语法 position：absolute。

**你想以谁为目标，就在谁上面加 position 属性**。

例子：

```Css
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        div {
            width: 80px;
            height: 80px;
            border: 1px solid blue;
            margin: auto;
            /* 给此 div 设置定位,仅仅是为了以它为目标进行绝对定位, position 设为 relative 时,该元素是不离队的. */
            position: relative;
        }
        p {
            border: 1px solid red;
            position: absolute;
            bottom: 10px;
            color: #FFFFFF;
        }
    </style>
</head>
<body>
    <div>
        <img src="../images/airplane.png"/>
        <p>到此一游</p>
    </div>
</body>
```

## 6.6 固定定位

- 固定定位以浏览器窗口为目标产生的偏移；
- 目标离队；
- 语法 position:fixed；
- 使用场景：将元素挂在窗口上不动时用固定定位。

例子：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        div {
            width: 50px;
            text-align: center;
            background-color:#cccccc;
            /* 固定位置 */
            position: fixed;
            bottom: 30px;
            right: 20px;
        }

    </style>
</head>
<body>
    <h1>iRobot</h1>
    <p>这是机器中的苹果.</p>
    <p>它能自动扫地和拖地</p>
    <p>它能自动充电</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <p>...........</p>
    <div><a href="#">Top</a></div>
</body>
</html>
```

## 6.7 定位归纳

选择定位的建议：

- 固定定位：是否需要将元素挂在窗口上不动；
- 浮动定位：是否要将元素左右排列；
- 相对定位：是否偏移量很小，是否元素不释放位置；
- 绝对定位：超过15s没想出来基本就是绝对定位。