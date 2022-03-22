@[TOC](目录)
# 前言
使用pacman安装软件时，有时会报错：

<font color=red>'pacman'无法安装缺失的依赖关系</font>

或者报错：<font color=red>无法下载 xxxxxxxx  正在放弃...</font>

或者报错：<font color=red>来自...的签名是未知信任的...已损坏</font>

**这三种问题会在下文一一列举并解决**


# 一、无法安装的解决方案
以下解决方案可能任取其一就解决，也可能组合解决
## 1、切换成yay方式，或者yaourt方式安装
manjaro默认是使用pacman作为包管理器的，但是这个pacman的软件有时是不全的，需要使用yay或者yaourt方式安装。

如果你没有yay和yaourt，需要先安装这两个东西本身：
```bash
sudo pacman -S yay
sudo pacman -S yaourt
```

安装完毕后，可以使用命令：

```bash
yay -Syy 软件名
```
或者
```bash
yaourt -Syy 软件名
```
来安装你的软件

## 2、有可能你没有安装包压缩解压等工具：
在应用商店搜索安装：base-devel 、fakeroot 全套工具解决。



# 二、无法下载的解决方案

## 1、换源
更换成国内的镜像地址，会大大提升下载速度：

执行 ```sudo pacman-mirror -i -c China -m rank```命令

然后会弹出选择镜像的页面，随便选择一个速度较快的即可

选择完成后，执行命令```sudo vim /etc/pacman.conf```**（如果你没有vim，需要先安装vim），**
会让你编辑一个文件，在文件末尾加入：
```bash
[archlinuxcn]
SigLevel = Optional TrustAll
Server = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/$arch
```
**第二行，很多教程写的是**：```SigLevel = Optional TrustedOnly```， **但是我们要配置成TrustAll。详见本文下面的：三、签名未知信任问题解决** 

## 2、配置应用商店自带的添加/删除软件
打开自带的应用商店，选择首选项，并进行下图的勾选设置：
![在这里插入图片描述](https://img-blog.csdnimg.cn/63556c10d56446a4a04a304f9c9324bc.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)




## 3、大招！解决某构建文件无法下载问题

如果安装完成中出现这个错：
```
==> 错误： 无法下载 https://mirrors.tuna.tsinghua.edu.cn/deepin/pool/non-free/d/deepin.com.qq.im/deepin.com.wechat.im_8.9.19983deepin22_i386.deb
    正在放弃...
==> 错误：Makepkg 无法构建 deepin-wechat-im.
```
**思路：** 说明找不到下载链接对应的文件（可能是由于你网络问题，挂个梯子试试），我们可以尝试一下手工下载该文件，然后放到PKGBUILD文件所在目录，这样不就跳过上面的下载步骤，直接进入校验下载文件并安装的步骤。

注意：这样做的前提是，你手工下载的文件文件名要一致。
**解决步骤：**

* 先安装，等待下载出错后问你是否重新开始，此时不要选择y或者n，重新打开一个控制台。

* 执行命令：```sudo find / -name "yaourt-tmp-你的用户名"```找到编译目录（一般情况下执行上面的命令就可以找到）

* 然后使用某种方式下载好报错无法下载的那个文件，重命名成正确的文件名，把该文件移动到上一步所在的目录的PKGBUILD文件所在目录

* 切换回下载出错后问你是否重新开始的页面，选择y，重试安装。由于你已经把安装文件下载完了，就不会再下载了，所以上述错误也就不会出现了。

# 三、签名未知信任问题解决
![在这里插入图片描述](https://img-blog.csdnimg.cn/cafec26a88fc47f78272f2e82f24f307.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
# 四、某软件突然就打不开了

## 0.遇事不决先重启
```ps aux | grep 程序名```查看该软件对应的后台进程，然后```kill -9 进程号```杀死后台进程，然后重启该软件试一下，不行就reboot计算机。这是最简单粗暴的解决办法，也比较有效
## 1.某些软件打开前需要服务启动
这种情况需要执行下：```systemctl start xxx```

善用tab提示，有可能就把应用需要的服务给提示出来

以向日葵为例，它启动必须依赖于某服务

## 2.需要手动打开可执行文件
linux不像win有个exe文件可以执行，需要找到该文件安装路径，用命令行的形式启动该执行文件。

### 2.1 如果你是官方应用商店安装的
那太简单了，直接如下图所示就能找到可执行文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/beb4884d879345ceb09e06d02d03585b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)然后复制路径到命令行：

![在这里插入图片描述](https://img-blog.csdnimg.cn/fd100247012c4f618e77be3275d501ab.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
就这样直接就打开了。很方便

## 2.2 如果不是在应用商店安装的

这种就比较麻烦了，但还是有办法解决的。



下面以找到postman安装目录为例：

```sudo find / -name "*ostman*" -type f```

上面命令的意思是，查找 postman 的可执行文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/b2f73aea35bc4e149aa77b435f088dc1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
最终得到两个可执行文件：
![在这里插入图片描述](https://img-blog.csdnimg.cn/a2a83cbdceb5442087f40a67a67abe76.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)<font color="red">**注意！！这种方式按照名称匹配，所以关键字一定要找对！如果你找motionpro，那么你按照motionpro  Motionpro  ```*otionpro*```
都不能正确匹配。因为motionpro这个应用的可执行文件叫做 MotionPro ！所以刚才的模式匹配当然不生效。**</font>

把上图路径复制到剪贴板：
![在这里插入图片描述](https://img-blog.csdnimg.cn/f3f56b3d634e4d75ba7c05c1b7a4c794.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)可以看到成功启动了。最后加一个 & 符号是因为我想让它后台启动，而不是退出命令行postman就关掉了。

**如果还是不行，在命令前面加** ```nohup```

退一步讲就算这种方法启动不了，也可以看到日志和错误信息，按图索骥查找错误就可以啦。






# 举例：deepin-wine安装微信
## 输入命令：
```yaourt -Syy com.qq.weixin.spark```
如果安装的是qq，输入：
```yaourt -Syy com.qq.tim.spark```

## 字体过小怎么办：
执行如下图命令（命令中的/home/daji 是我本人用户的家目录，你要切成你自己用户的家目录）：
![在这里插入图片描述](https://img-blog.csdnimg.cn/de7db7a7e1b14d46a3d63005b931b71e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
然后重启微信，设置成功！

如果你想设置qq的分辨率，修改上图命令中的```Spark-WeChat```为 ```Spark-TIM```即可。注意这个TIM全部都是大写的

由此可见分辨率的修改和应用签名有关系。