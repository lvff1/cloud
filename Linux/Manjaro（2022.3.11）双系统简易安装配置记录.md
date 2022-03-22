@[TOC](目录)

# 前言
本文装的是Gnome桌面环境，不过大家也别纠结究竟是装XFace，Kde 还是Gnome了，官网也说了，只不过是桌面环境而已

我的桌面环境详细版本是：GNOME（21.2.4）


# 步骤

## 卸载掉之前的Linux系统

* 参考资料：
https://www.bilibili.com/video/av209430195/

* **下载diskgenius**
https://www.diskgenius.cn/download.php

* **删除Linux分区**
![在这里插入图片描述](https://img-blog.csdnimg.cn/44528e8479064c668592b7b57b5ce441.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

	**删完了点击左上方的保存更改！**

* <font color=red>**找到并删除Linux引导分区！**</font>
不要将Win的引导分区误删了。
有两个疑似引导分区，我不确定哪个是Win的哪个是Linux的。通过仔细观察：
![在这里插入图片描述](https://img-blog.csdnimg.cn/00c0a9b57d5442a1ae9989eaad46dc30.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
所以我们删除另一个引导分区，肯定是Linux的了：
果不其然里面有Manjaro Linux的引导文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2d875bb0053b47a38f0eba9f2176017a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
如上图所示：彻底删除即可


* <font color=red>**修复Win引导分区！**</font>
	此时必须修复win的引导分区，否则你的win就再也进不去了。
	工具下载：http://www.xitongzhijia.net/soft/199328.html
	自行百度其用法，很简单。

	**第一步：删除引导**

	![在这里插入图片描述](https://img-blog.csdnimg.cn/52364cd1498e41f7b9336d1c6eb728cb.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
		**第二步：修复：**
		![在这里插入图片描述](https://img-blog.csdnimg.cn/01daa5645f7c4d9690905dc6430e0182.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
		完成。

## 双系统分区思路：
* 根据之前Linux的使用经验，就算你是16G运行内存，我在日常开发中仍然稍显不足，于是给8G的交换空间是有必要的。
* 经过半年的Linux系统日常使用，对于开发而言，60GB作为/home目录已经绰绰有余，而根分区60G左右也够了。因此：
	* / 目录：60GB
	* /home 目录：60GB
	* Swap：8GB

* **由于Linux系统可以轻易访问Win系统的文件，而Win系统比较自私，不能兼容Linux的硬盘。所以可以把大文件（项目资料，视频）等，放在Win的硬盘下，反正Linux也能访问的到。** <font color=red>**所以真的没必要给Linux太大的内存空间。**</font>



## 将u盘制作成启动盘，或将启动盘还原回普通u盘

* **启动盘还原成普通u盘：**
	https://jingyan.baidu.com/article/ce436649e30d5e7673afd383.html
	
	**其实就是依次执行下图命令：**
	![在这里插入图片描述](https://img-blog.csdnimg.cn/a83e4413b9394e808d78390102cee770.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
	执行完之后，打开磁盘管理工具，新建分区，（记得选择分区类型为NTFS）。如下图所示即成功：
	![在这里插入图片描述](https://img-blog.csdnimg.cn/8266343054cc4154baf217da672d24c6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)


* **u盘制作manjaro启动盘：**

	按照官方网站教程走，需要光盘镜像：
https://manjaro.org/downloads/official/gnome/


# GNOME软件推荐：
## GPaste
管理你的剪贴板，批量复制粘贴
https://zhuanlan.zhihu.com/p/51522499
## 轻量级图片编辑软件:
![在这里插入图片描述](https://img-blog.csdnimg.cn/73dab6004c1644609d19cd79cf9d1bb6.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
## 微信:
**这里的qq和微信都是用spark方法安装的，所以命令和deepin的有区别**

```yay -S com.qq.weixin.spark```

微信调节缩放方法：

```env WINEPREFIX="$HOME/.deepinwine/Spark-WeChat" deepin-wine5 winecfg```


## 向日葵
从应用市场下载安装第一个：
![在这里插入图片描述](https://img-blog.csdnimg.cn/19f67598ab88498d9549d7fe85021ccb.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
**在命令行界面执行命令（这一步必须，否则无法启动）：**
```sudo systemctl start runsunloginclient.service```

你也可以设置开机自启动上述服务，省的每次启动向日葵都得手敲：
 ```sudo systemctl enable runsunloginclient.service```

# 配置快捷键和别名
全局配置别名：

```vim ~/.bashrc```
如果你用了zsh，还需要修改zsh的配置文件。直接让zsh继承bashrc即可：
```vim ~/.zshrc```
然后让zsh继承~/.bashrc的配置文件(在zsh配置文件添加如下一行)：
```source ~/.bash_profile```

使得修改生效：
```source ~/.bashrc```
```source ~/.zshrc```


# Manjaro开启ssh端口，和公司服务器通过内网直接互联
见我发表的另一篇博客文章：[Linux(Manjaro)开启ssh端口，和公司服务器内网互联，scp文件互传](https://blog.csdn.net/weixin_44757863/article/details/120341670)

# 安装touchegg

touchegg这个软件呢，能让你linux的触摸板触控体验媲美MacOS，使用了此软件你就再也不想用鼠标了，强烈推荐。

https://github.com/JoseExposito/touchegg

文档里写的比较清楚，大致步骤是先安装，然后启动touchegg服务：```systemctl start touchegg.service ```

**然后下载touchegg的图形化可视界面：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/371d6303ccbe4dfd837b160274d6465e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
<font color="red">**最后千万别忘记了最重要的一点，关闭Wayland：**</font>

某些软件默认不支持Wayland，只支持X11；所以你要修改配置文件禁用wayland支持：

cd到 /etc/gdm3
(如果你没有这个目录，请cd到/etc/gdm目录)

然后执行vim custom.conf，找到 #WaylandEnable=false这一行，把这一行的#（注释）给去掉。然后wq保存，重启计算机。

系统将在下次启动使用x11，而不是wayland。此办法亲测可行，适用于2021.9 Gnome桌面。

最后重启电脑。



# 触摸板手势和快捷键
**如果认真设置了，你就再也不想用鼠标和其它外设了，而且再也回不去了（慎入触摸板的坑，笔者花小一千块钱买的鼠标就这么白买了）。体验一把只需提着电脑想走就走的旅行～**

**而且触摸板真的比鼠标方便太多了，通过按压滑动的排列组合，可以模拟出共18种手势动作（Mac可能更多），相当于你的鼠标设了18种鼠标宏，简直就是办公效率之神！！**


手势和按键分为两种情况，一种是manjaro自带手势和按键(我未做改动)，另一种是我自己添加的手势（使用上文提到的touchegg修改）和按键（系统设置就可以修改）
**手势：**

*  [系统自带] 三指上滑 多任务切换界面，类似下图：

 	![在这里插入图片描述](https://img-blog.csdnimg.cn/41a715704f5843c9b14a9365b68419ab.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
 	* [系统自带] 三指下滑：显示桌面
 	* [系统自带] 三指左右滑：左右切换桌面
 	* 四指上滑：快捷键 super+～(同一应用内多实例切换)
 	* 四指下滑：同桌面内切换应用（依赖于快捷键alt+空格）
 	* 四指左右滑：将当前运行的应用带到左/右桌面去
 	* 两指捏：快捷键ctrl+c
 	* 两指反过来捏：快捷键ctrl+v
 	* 三指捏：关闭窗口
 	 *  三指反过来捏：最大/最小化窗口
 	 * 四指捏：启动应用深度截图（一种截屏软件） （依赖于快捷键super+A）
 	 * [系统自带] 一指轻点触摸板：鼠标左
 	 * [系统自带] 二指轻点触摸板：鼠标右
 	 * [系统自带] 三指轻点触摸板：鼠标中



**快捷键：**

* [系统自带]super + 12345678 ： 运行屏幕最下面收藏栏里的应用，如下图所示：
	![在这里插入图片描述](https://img-blog.csdnimg.cn/e667566779cb4d348284368f6e7da15a.png)
* super + Q 运行默认浏览器
* super + W 唤起WeChat
* super + A  同四指捏：启动应用深度截图（一种截屏软件） 
* super + Q
* super + Z  同三指捏：关闭窗口
* super + X	同三指反过来捏，最大化最小化窗口
* super + V  启动应用：任务管理器
* alt + 空格  同桌面内切换应用
* [系统自带]super+～(同一应用内多实例切换)
* [系统自带]ctrl + alt + 左右箭头：同三指左右滑：左右切换桌面
* [系统自带]ctrl + shift + alt + 左右箭头：同四指左右滑：将当前运行的应用带到左/右桌面去


# 切换jdk
很多软件需要jdk11+ 甚至jdk16+ 才能运行。但是我本人开发用的环境是jdk8。所以需要频繁切换jdk。好在archLinux（Manjaro）中的Java版本切换非常简单：

```archlinux-java status```查看你安装了几个jdk版本

```sudo archlinux-java set java-16-openjdk```切换某个jdk版本

执行前后对比：
![在这里插入图片描述](https://img-blog.csdnimg.cn/93dbeff7904a4012ae3f1b2cdb666352.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)