# 一、前言

先上博客链接：[www.zr7.top](http://www.zr7.top)

全文转自本人的CSDN文章，欢迎关注：[【做项目】基于SpringBoot从零开发的个人博客 —— 从技术选型到部署实战（附学习路线）](https://blog.csdn.net/weixin_44757863/article/details/111751333)

## 1.1 背景介绍
笔者是一名非科班出身，对编程非常感兴趣的一名在校学生。在今年11月初，我学完了SSM阶段。但是对知识的掌握仅仅停留在网络视频以及技术书籍上的了解，并没有任何实际的项目经验。所以就产生了做一个综合项目的想法。

## 1.2 寻找开源项目
在寻找这类综合项目时，我却迷茫了。首先是网上的开源项目眼花缭乱，不知道哪些项目适合我现在的阶段。
* **跟着普通大学计算机专业的实训走，做一个SSM架构的图书管理系统？**
* **跟着尚硅谷，黑马程序员这类培训机构走，做一个黑马旅游网？谷粒商城？**
* **去GitHub或是码云上看看，有没有优秀的开源项目可以借鉴？**

最终我通过各种渠道，找到了各种各样的优秀综合项目：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226160919629.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)通过对上面思维导图的梳理，我的思路逐渐清晰——那就是做一个个人博客项目。

原因如下几点：
* **网络上开源项目多，可供学习资源多，也有相关技术讨论圈子——技术成熟**
* <font color=red  >**可扩展性强，能够基于自己的技术栈不断往里面扩充**</font>
  * 事后看来，这一条的确是非常明智的决定。该博客的技术栈最终经历了SSM，SpringBoot，Redis，Shiro等等，而这些技术并不是我本来就会的，而是我通过边建博客边学习逐步掌握的，**掌握一个点，就向个人博客中想办法添加一个点。**
* **可实际部署到远程服务器中，不仅锻炼全栈开发能力，还能获得一定的服务器运维知识。**

这里我并不是说其他类型的综合项目不好，比如音乐播放系统，图书馆系统，网盘系统等等，它们也很优秀，但是最终将其发布到互联网，并连接到域名，由于种种原因，部署的实际意义并不是很大，**但是它们的业务逻辑和涉及的知识点也很全面，适合自己练手做。**

## 1.3 技术选型

前台显示借鉴：[燕十三的前端模板](https://gitee.com/yssgit/yan_shisan_blog_template)（已于Gitee上开源）

后台管理借鉴：[李仁密老师——SpringBoot开发一个小而美的个人博客](https://www.bilibili.com/video/BV1nE411r7TF)（已于GitHub上开源，并有相关视频教学）

虽然借鉴了他们的开源代码学习，但是在编写的实践过程中，后台和前台并不是互通的（李仁密和燕十三根本就不认识），导致他们之间的接口不互通，对此我只能自己设计后端部分和接口。

而且第一版博客我并没有照着开源项目的SpringBoot架构写，而是用SSM头铁做的（**就是为了感受一下XML配Bean，更深入的了解Spring）**，中间出现了很多版本兼容的问题。


# 二、正式开发
## 2.1 看懂项目并模仿
真正进入了开发阶段，第一步大家应该都知道，就是先从GitHub上Clone下来人家的项目，跑一跑，确定能跑通之后，再照猫画虎地模仿。

这一时期大概花费了我五天左右，才搞清楚了人家的项目究竟是怎么一回事，技术点究竟有哪些。毕竟大家都是这样过来的，欲增加自己的功能，必先知道人家究竟是怎么跑的。

想学会跑，必先学会走，必须懂得循序渐进的道理，才能走的更远。

## 2.2 正式动手敲之前，内化成自己的项目，对其设计并架构
前面我已经介绍过，我所借鉴的两个开源项目之间并不互通。根据MVC架构的原则([什么是MVC架构，我之前博客有写](https://blog.csdn.net/weixin_44757863/article/details/109086022))，所以要自己设计从View层到Controller层之间的桥梁，也就是俗称的接口，是一种对接前后端的规范。然而Model层如何与Controller层交互的方法以及业务层和持久层的对接，**开源项目中设计的极为巧妙，我也从中获取到了很多知识，并真切的感受到了Spring为我们带来的解耦。**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226165231523.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)

 后来添加新功能时，我也遵循着已经架构好的模式，自顶向下的进行开发。（此处有争议，为什么自顶向下，是因为我自己一人写了全栈，自顶向下我可以根据前端需要的数据，一步一步的添加方法，一直走到Dao层访问数据库。）具体实际开发是前后端分离的，可能我自己这种开发模式不是很科学，但是前后端都是我写的，规矩自然我来定。

下面放出几张我当时设计好的设计图：

后台管理功能：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226170413371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
前台展示功能：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226170716298.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
## 2.3 动手编写
到这一步就开始愉快的敲代码了，按照之前的设计，一个一个功能去实现。期间遇到了很多的问题，但是这一时期也是我进步最快最大的时期，我也在此期间总结出了很多技术博客。

下面将一一介绍编写博客过程中踩过的坑。

# 三、博客功能展示
前台的页面显示大家都能在我的网站中看到。下面就说一下大家看不到的地方以及后台管理的部分功能好了：
## 3.1 Redis的集成
个人博客项目的主营业务（对博客的CRUD操作），实现了从Redis中读，从MySQL中写，从而加快了响应速度。
## 3.2 留言的级联关系
将所有除了父评论(爹级)的所有评论(不管你是子，孙，曾孙，重孙)找出来，并且一视同仁，全部视作父评论的子评论。
        也就是说不管它们在数据库里面有多复杂的关系（子，孙，曾孙，重孙这种连续几级关系），一律一视同仁，看作子级。只要你上面有父级评论(数据库中parent_comment_id不为-1)，那么一视同仁。
        因为在前端页面中，只有两层关系：父级评论和子级评论

 **这点有点像链表，有数据域和指针域。并递归调用寻找子集评论的方法查询出所有子孙级评论。**

 ## 3.3 后台管理功能（已用Shiro进行了权限控制，只有管理员能进去）
 能对博客，分类等进行增删改查的基本操作和搜索。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226190514978.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)搜索结果：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226190621491.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
## 3.4 文件上传功能
后台可以上传博客首图到数据库中，为此我还总结出博客：

[SSM或SpringBoot上传图片到数据库](https://blog.csdn.net/weixin_44757863/article/details/110563601)

 （上传的按钮设计的有点丑，反正大家也进不去管理员页面嘿嘿）
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226190757491.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


# 四、问题的出现与解决

## 4.1 MyBatis的多对多处理
人家网上的开源项目数据库都是一对一的，我为了挑战自我（作死），硬给改成了多对多的。因此出现了非常多的问题，好在后来都一一解决了，并且大大提升了我的SQL能力。

在处理这一问题的时候，我也是相当于把整个MyBatis和SQL语句都复习了一遍，总结出了不少博客和不少文章，欢迎大家观看：

* [【数据库】快速判断一对多，多对多关系，并建立数据库实体之间的映射](https://blog.csdn.net/weixin_44757863/article/details/109614230)
* [在MySQL中建立多对多关系的映射（建立中间表）](https://blog.csdn.net/weixin_44757863/article/details/109621839)
* [在MySQL中添加外键的几种方式(一对多关系)](https://blog.csdn.net/weixin_44757863/article/details/109322657)
* [狂神说MyBatis学习笔记: MyBatis中一对多和多对一处理](https://blog.csdn.net/weixin_44757863/article/details/109788057)
* [简单讲解MyBatis中的resultMap，collection，association](https://blog.csdn.net/weixin_44757863/article/details/109789477)
* [【MyBatis-Debug】在SQL语句中为数据库字段取别名的重要性](https://blog.csdn.net/weixin_44757863/article/details/109828210)
* [【MyBatis】多对多条件下插入中间表（使用insert标签的属性）](https://blog.csdn.net/weixin_44757863/article/details/110010556)
* [SQL语句 ORDER BY 多条件排序优先级（嵌套if语句）](https://blog.csdn.net/weixin_44757863/article/details/110839219)


## 4.2 RESTful风格的再理解
最开始我学RESTful风格的时候，认为只是一种风格而已，没有必要遵守，但是后来的麻烦教我做人了。

之前因为自己开发经验不足，在Controller里面接口都是乱写，动不动就是@RequestMapping，直到集成swagger进行接口测试的时候，傻眼了。

先给大家看看正常的接口是啥样的：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226184507755.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)每个接口的GET \ POST分工明确，各自有不同的功能。

再给大家看看我因为乱用@RequestMapping带来的后果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226184723818.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)看似有这么多接口，但是只对应了一个方法。产生这问题的原因就是乱使用@RequestMapping

经此以后，终于理会了好好设计接口，使用RESTful风格的必要性

## 4.3 Shiro资源过滤
之前写某个功能模块的子功能模块的接口路径地址，一直瞎起名。直到有一天我集成了Shiro，要进行资源过滤时，傻眼了。

假如你要对一组资源进行权限过滤，最方便的方式是利用Shiro的通配符（/**），这样的话，假如你这一组资源路径是不遵循规范，随机命名的，不但不美观，而且过滤就会带来极大的不便。不能使用通配符，只能一个一个手动过滤。


# 五、博客主营业务完成后，陆续添加的其他功能
这里就是自由探索阶段了。网络上的学习视频只有单个技术，不会手把手教你如何将这门技术应用到你自己的项目中。不过有了之前的基础，倒是也不难。

先上图，我自己实现的附加功能（对号的是已实现的，未勾选的是还未实现，会随本人技术栈陆续更新）：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226175014716.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70) <font color=blue size=5>以下是我集成的各个功能，如无特殊说明，链接内资料都是我自己写的（打个小广告，涨涨访问量）

## 5.1 集成Shiro
[Shiro不同身份多个Realm的问题及反思](https://blog.csdn.net/weixin_44757863/article/details/110448527)
[SSM环境下整合Shiro，网页过滤不生效](https://blog.csdn.net/weixin_44757863/article/details/110246801)
## 5.2 集成Redis
[Redis远程连接Linux云服务器(Jedis方式或Springboot方式)](https://blog.csdn.net/weixin_44757863/article/details/111349928)
## 5.3 集成Swagger
狂神说Java的b站视频  [【狂神说Java】一小时掌握Swagger技术](https://www.bilibili.com/video/BV1Y441197Lw?from=search&seid=6361078090386095542)
## 5.4 集成PageHelper
[【MyBatis】PageHelper无法处理多对多查询分页问题](https://blog.csdn.net/weixin_44757863/article/details/109890732)

[【分页查询】在SSM环境中使用PageHelper](https://blog.csdn.net/weixin_44757863/article/details/109728886)

## 5.5 集成文件上传功能
[SSM或SpringBoot上传图片到数据库](https://blog.csdn.net/weixin_44757863/article/details/110563601)


后期这个系列会持续更新，欢迎大家关注我。


# 六、购买服务器和域名，部署到阿里云，让大家访问

## 6.1 前置知识：Linux相关

这个需要好好说道说道了。

首先，如果想走这一步，必须先有Linux的相关知识。网络上关于Linux的学习资源也很多，我也学习了总结了不少。

先给出一张自己的学习路线吧，画的有点乱，后期有时间会精修一下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226180705660.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
**还有我写的几篇关于Linux的排坑博客：**

[Linux如何联网，以及SecureCRT解决中文乱码问题.](https://blog.csdn.net/weixin_44757863/article/details/109126398)

[【Debug】本地JDK和阿里云服务器JDK不一致，jar包在Linux上跑不起来](https://blog.csdn.net/weixin_44757863/article/details/111084080)

[Linux直接通过端口号杀对应进程，lsof命令无效](https://blog.csdn.net/weixin_44757863/article/details/111084368)

[SpringBoot项目打成war包，部署到远端Linux服务器](https://blog.csdn.net/weixin_44757863/article/details/111658135)

在进入下一个阶段之前，建议先自己在本地的电脑上安装一个Linux的虚拟机（我使用的是VMware）：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201226181228303.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


在本地环境下敲熟了，再去购买云服务器部署。

## 6.2 购买服务器和域名，并完成部署
这里就是上一个阶段的实战和应用了，有了Linux的前置知识，并不难。

下面介绍我的学习路线：

* [【狂神说Java】Linux最通俗易懂的教程阿里云真实环境学习](https://www.bilibili.com/video/BV187411y7hF)
* [【狂神说Java】服务器购买及宝塔部署环境说明](https://www.bilibili.com/video/BV177411K7bH)
* [2019-JAVA黑马程序员开发工程师架构（idea版）57期全套视频-web版（二）](https://www.bilibili.com/video/BV1tJ411v7k1?p=417)（这视频只需要看P414以后的内容即可）

最后说一下关于域名备案

备案没有大家想象的那么复杂，照着阿里云官方的指引(腾讯云，华为云也一样)一步一步做就好。是阿里云先审核你，阿里云的审核过了才会提交到工信部审核。

阿里云的审核就一天，而且你有些地方填的不对，客服小姐姐会给你打电话纠正。工信部审核就比较慢了，官方给出的时间是18天，因各地区而异。

笔者所在的山东地区，仅用了9天就通过了！必须得夸一下大山东的办事效率！

域名备案完事之后，想要将域名连接到你的端口，只需要配置一下Nginx的域名映射即可：

[阿里云服务器安装Nginx以及配置Nginx域名映射](https://blog.csdn.net/CoderYin/article/details/88743390)
# 七、总结与反思
该网站从构思到实际落地，共用了一个多月的时间，期间肯定有很多不足之处。如果各位看官在我的网页浏览体验不佳，我先要说一声抱歉。该博客一直在更新，体验一定会越来越好的。

通过这次个人博客的开发，对自己的提升真的不是一点半点，强烈建议大家学完一个阶段，都要找一个项目来实际练手，这样既能加深印象，还能以练促学。

当然，对我影响最大的还是各位大佬们的技术输出，他们的开源项目，技术分享态度，正代表了互联网精神。在仰望他们之余，正视自己的不足，努力填补短板，更坚定了自己在编程之路上走下去的决心和信念。

最后，希望自己能一直保持对技术的初心。非科班出身的我，**正是因为热爱，才选择了编程**，虽然之前因为找不到方向，走了大量的弯路，但我还是幸运的。相信以后的路会越走越宽。

欢迎大家访问[我的博客](http://www.zr7.top)！