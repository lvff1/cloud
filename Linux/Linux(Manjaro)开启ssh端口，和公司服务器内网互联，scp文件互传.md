@[TOC](目录)


# 一、官网开放防火墙某端口的方法：
维基百科链接：https://wiki.manjaro.org/index.php/Firewalls
![在这里插入图片描述](https://img-blog.csdnimg.cn/559f62c3f42c48de84df53ca3ff1a1d1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
官网说了一大堆，看不懂没关系，最终官网给出了图形化界面：GUFW

**在应用商店下载安装GUFW**
![在这里插入图片描述](https://img-blog.csdnimg.cn/53d08870c23244bb8875c432b157b9f7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)



最终进行如下配置：
![在这里插入图片描述](https://img-blog.csdnimg.cn/94dfd0873c2e4c8181923bb596b1cb15.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/427ecbdb656749128578763f613cfc59.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)和上两个图配置的一样，然后重启防火墙即可

# 二、修改配置文件开端口
* 保证你安装了openssh，如果你没安装，百度搜索你自己的系统安装方法并安一个：
![在这里插入图片描述](https://img-blog.csdnimg.cn/fb0c544a277644f4bf5e79c95572d333.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
* 编辑openssh配置文件：```vim /etc/ssh/sshd_config```
共需要改动下2图几处：
![在这里插入图片描述](https://img-blog.csdnimg.cn/c405f1549cb84ece81a7b708fdcaac3f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/c8375ef991604faaa0a67e538b5b1bb7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)




# 三、Arch系统启动某服务，查看服务状态
确保你安装了某服务，下面以sshd服务为例：
>sshd             -- SSH服务端程序
>区别于ssh，ssh是SSH协议的客户端程序，用来登入远程系统或远程执行命令


**为什么要在这里查看sshd的状态？因为你要作为sshd的服务端，别人使用ssh登陆你的电脑，所以你要开启sshd这个服务端！**

```systemctl status sshd	```	查看服务状态
**如果服务状态如下图所示，说明启动成功：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/69e6020d2e094963bf4da9161090b124.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)


```systemctl enable sshd.service```开机启动
```systemctl start sshd.service```立即启动
```systemctl restart sshd.service```立即重启

# 四、查看22端口是否被监听：
执行命令：netstat -nltp|grep 22
![在这里插入图片描述](https://img-blog.csdnimg.cn/73dd55180d3c47c5839debad3edbc7f7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
# 五、与公司服务器在内网建立链接
<font color="red">这一步必须保证你的服务器和公司的服务器在同一个子网！！</font>

**1、首先查看你本地的ip地址:**

![在这里插入图片描述](https://img-blog.csdnimg.cn/43a6ad937ade4df09a05a4263320fde2.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)有个常识，你的ip如果是在公司内网链接，那么一定是一串私有地址：
![在这里插入图片描述](https://img-blog.csdnimg.cn/39b15a2d002949bc97357f68929350d0.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
>A类私有地址
>在A类地址中，10.0.0.0到10.255.255.255是私有地址（所谓的私有地址就是在互联网上不使用，而被用在局域网络中的地址）。<br>
>B类私有地址
>在B类地址中，172.16.0.0到172.31.255.255是私有地址。<br>
>C类私有地址
>在C类地址中，192.168.0.0到192.168.255.255是私有地址。

所以你的ip地址肯定在上面的地址区间之内，就比如说我图中的地址，是10.206.9.147，落在A类私有地址区间内，那么肯定是一个局域网地址。






**2、然后登录远程服务器，ping你自己的ip，telnet你的ip + 22端口，如果都没问题就如下图所示：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/ba4c4f6e08a641ad82cbf6a7deeef8ac.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
**3、如果上述操作都没问题，那你远程服务器就可以直接通过ssh控制你本机，进行scp文件互传等操作了**

举例1：公司服务器连接你本地的linux：
![在这里插入图片描述](https://img-blog.csdnimg.cn/12e9e120cafa462caf3b6b0f0cce521b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)举例2：公司服务器向本地直接传送文件：

**使用下图的命令：**
```scp ucf-enginepro.log  daji@10.206.9.147:~/tempfiles```
翻译一下：scp 远程文件名 本地用户名@本地ip:本地你希望存放的路径
![在这里插入图片描述](https://img-blog.csdnimg.cn/6636beda6c39403e9056e826a80c31e3.png)

# 总结
1、不管是linux还是win，基于scp的文件互传必须依赖于ssh；
2、**开启ssh，需要本地安装有ssh服务，而且开了22端口（这个开端口的概念，一个是已监听，另一个是本地防火墙打开了该端口）**；
3、**想要进行ssh连接，两者必须位于同一个网段下，要么是公网，要么是同一子网！**