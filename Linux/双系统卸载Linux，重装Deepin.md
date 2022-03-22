# 卸载掉之前的Linux系统

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


# 双系统分区思路：
* 根据之前Linux的使用经验，就算你是16G运行内存，我在日常开发中仍然稍显不足，于是给8G的交换空间是有必要的。
* 经过半年的Linux系统日常使用，对于开发而言，60GB作为/home目录已经绰绰有余，而根分区60G左右也够了。因此：
	* / 目录：60GB
	* /home 目录：60GB
	* Swap：8GB

* **由于Linux系统可以轻易访问Win系统的文件，而Win系统比较自私，不能兼容Linux的硬盘。所以可以把大文件（项目资料，视频）等，放在Win的硬盘下，反正Linux也能访问的到。** <font color=red>**所以真的没必要给Linux太大的内存空间。**</font>



# 将u盘制作成启动盘，或将启动盘还原回普通u盘

* **启动盘还原成普通u盘：**
	https://jingyan.baidu.com/article/ce436649e30d5e7673afd383.html
	
	**其实就是依次执行下图命令：**
	![在这里插入图片描述](https://img-blog.csdnimg.cn/a83e4413b9394e808d78390102cee770.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
	执行完之后，打开磁盘管理工具，新建分区，（记得选择分区类型为NTFS）。如下图所示即成功：
	![在这里插入图片描述](https://img-blog.csdnimg.cn/8266343054cc4154baf217da672d24c6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)


* **u盘制作deepin启动盘：**

	https://www.deepin.org/zh/
	
	按照官方网站教程走就行了。


# 安装Deepin

**假如你的电脑型号比较新（2021年或者是2022年买的电脑），安装Deepin的时候一定要选择5.15的内核（不要选5.10），这里手速一定要快，在高级安装选项（好象是，没来得及截图），里面可以选择5.15版本的内核。**

**否则你的硬件适配就有概率出问题。比如没有wifi，没有蓝牙等等**

剩下的所有步骤，官方网站写的都很详细。本文仅仅作为官方网站涉及不到的补充。毕竟谁都不希望自己系统安装后进不去。

后面会继续更新Deepin系统安装后的调教指南。Linux is free , if your time is free. 继续折腾吧。