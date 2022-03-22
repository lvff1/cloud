# 问题描述
如题，笔者也被这一系列问题困扰很长时间，最终偶然在外网上看到了解决方案：

https://community.teamviewer.com/English/discussion/1103/linux-disable-wayland-support

![在这里插入图片描述](https://img-blog.csdnimg.cn/6278cfff11f44c73babd44b6fbdaa769.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
这篇文章大意是说：某些软件默认不支持Wayland，只支持X11；所以你要修改配置文件禁用wayland支持：

cd到 ```/etc/gdm3```
(如果你没有这个目录，请cd到```/etc/gdm```目录)

然后执行```vim custom.conf```，找到 ```#WaylandEnable=false```这一行，把这一行的#（注释）给去掉。然后wq保存，重启计算机。

系统将在下次启动使用x11，而不是wayland。此办法亲测可行，适用于2021.9 Gnome桌面。

重启电脑，发现标题所说黑屏问题全部解决！！！