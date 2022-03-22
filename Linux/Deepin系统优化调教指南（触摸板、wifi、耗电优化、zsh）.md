# 前言
虽然Deepin系统号称开箱即用，不过还是有很大的提升空间的。

**本文调教的点在于：**
* 1、Deepin应用商店应用比较少，安装一系列包管理工具进行扩展。

* <font color=red>**2、触摸板支持类似Mac的多指触控（触控板自由）**</font>
* 3、终端支持zsh，优化bash使用体验

# wget工具
* **wget + 下载链接：**
例如： ```wget http://cmotionpro.2.2.1.tar.gz```
 会在**当前目录**从给定的链接下载文件

**执行.sh脚本的时候，如果不能执行，那么就切换sudo，或者 chmod 777 给予运行权限。**

 *  **wget -c 断点续传** 
 wget可以在下载大文件的时候断点续传。


# apt-get工具


# apt工具
* 安装git
	```sudo apt install git```

# add-apt-repository，PPA等工具
* **sudo: add-apt-repository：找不到命令解决办法：**
	执行下面的命令（会有一个命中的）
	sudo apt-get install python-software-properties
	
	sudo apt-get install software-properties-common
	
	sudo apt-get update
	
	sudo apt-get install software-properties-common python-software-properties
	

# 安装deb包的方法
```sudo apt install ./touchegg_*.deb ```

# 安装flatpak，安装后缀为flatpakref的软件包
**flatpak官网：**
https://flatpak.org/setup/

选择Deepin系统（Deepin系统已经出名到直接支持了），如果没有Deepin系统，选择Debian。

**deepin安装flatpac方式：**
https://flatpak.org/setup/Deepin

**按照官方网站指引安装完成后，你就可以安装后缀为flatpakref后缀的文件了。教程网址如下：**

https://www.louishe.com/2021/07/22/doc-10854.html

<font color=red>**你也可以用flatpak install命令，像apt-get那样安装软件。比如：</font>**```flatpak install flathub com.github.joseexposito.touche```

# 安装zsh和oh-my-zsh
* **安装zsh和主题**
https://www.cnblogs.com/nangec/p/12788868.html

* **安装zsh插件的方法见上篇，不过你必须先将注释打开才可以无脑复制上篇(路径不能动)：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/af46d77237c94043810ed220a9fc6464.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)


* 手动克隆并安装oh-my-zsh（如果你自动下载oh-my-zsh失败）
https://www.jianshu.com/p/9df4bc46429a

* 设置随机zsh主题：
	```vim ~/.zshrc```，然后设置成random

	![在这里插入图片描述](https://img-blog.csdnimg.cn/d4086b5475114445bb83210726563d53.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
##  **配置zsh别名(alias)**


全局配置别名：
	
```vim ~/.bashrc```
	如果你用了zsh，还需要修改zsh的配置文件。直接让zsh继承bashrc即可：
	```vim ~/.zshrc```
	然后让zsh继承~/.bashrc的配置文件(在zsh配置文件添加如下一行)：
	```source ~/.bash_profile```
	
使得修改生效：
	```source ~/.bashrc```
	```source ~/.zshrc```

**最后注销或者重启生效。**

* 别名备份：

```bash
alias yc="cd /home/daji/data/works/yonyou/codeSpaces/shandongPort && ll"
alias yp="cd /home/daji/data/works/yonyou/projectDocs/shandongPort_proj && ll"
alias opn="xdg-open"
alias opnn="xdg-open ./"
alias seealias="cat ~/.zshrc"
alias data="cd ~/data && ll"
alias temp="cd ~/tempFiles"
alias powersave="sudo tlp start && systemctl start tlp.service"
alias reform="cd ~/data/codes/reform725 && ll"
alias csdn="cd /home/daji/data/codes/csdn-edit && ll"
alias sdgxx="opn ~/data/works/yonyou/projectDocs/shandongPort_proj/山东港信息.xlsx"
alias idea2020="sh ~/data/developtools/ideaIU-2020.3.4/idea-IU-203.8084.24/bin/idea.sh"
alias english="vim /home/daji/data/codes/reform725/englishVocabulary.md"
alias restartwifi="systemctl restart wpa_supplicant.service"
alias restartblue="systemctl restart bluetooth.service"
```

# 安装touchegg


**touchegg这个软件呢，能让你linux的触摸板触控体验媲美MacOS，使用了此软件你就再也不想用鼠标了，强烈推荐。**

https://github.com/JoseExposito/touchegg

文档里写的比较清楚，大致步骤是先安装，**安装你要找到Ubuntu和Debian系：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/2c8d58382b404b2b90eb8625333b8c75.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

文档里让你下载deb包。这里我们选择amd64的deb包：

![在这里插入图片描述](https://img-blog.csdnimg.cn/09e6c77dacca49248c1c4e91c927d85a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
执行命令：```sudo apt install ./touchegg_2.0.13_amd64.deb```安装该deb包

然后启动touchegg服务：```systemctl start touchegg.service ```

**使用flatpak下载可视化界面：touche**

https://flathub.org/apps/details/com.github.joseexposito.touche

首先你得有一个flatpak才能使用flatpak的命令。如何安装flatpak，见上文。

<font color=blood>**然后拉到文章最底部，看到安装touche的命令和执行touche可执行程序的方法：**</font>

![在这里插入图片描述](https://img-blog.csdnimg.cn/f72ee5cb46e6435bbb84c20b7222be10.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

## Deepin执行touche可视化界面的方法
```flatpak run com.github.joseexposito.touche```


# xdotool 命令，是Linux上的按键精灵。
例：控制台执行

```xdotool mousemove 10 10 click 1```

手势大全：https://github.com/jordansissel/xdotool/blob/master/xdotool.pod

**根据上文总结：**
* 鼠标中键，双击等：
	>click [options] button
  >Send a click, that is, a mousedown followed by mouseup for the given button with a short delay between the two (currently 12ms).

	>Buttons generally map this way: Left mouse is 1, middle is 2, right is 3, wheel up is 4, wheel down is 5.
	
  所以中键是：```xdotool click 2```
* alt+tab：
	```xdotool key alt+Tab```

# xdotool 键盘映射关系

使用xev即可获取。linux直接输入命令 ```xev```

**然后随便按一个键盘，观察keyRelease event，就可以得到keycode**
![在这里插入图片描述](https://img-blog.csdnimg.cn/b29acbc0a0cd450bb154ec221562b623.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

**所以我们可以知道一些奇葩的映射关系，比如左箭头是113**

```xdotool key super+113```就是super+←的快捷键


# 借助touchegg和xdotool实现触摸板自由

deepin的触摸板支持天生和Arch系列不能比。

导致我装了touchegg只有捏合好用。

编辑系统自带的触摸板json文件， ```sudo vim /usr/share/dde-daemon/gesture.json```只能实现滑动姿势好用。

所以可以两者结合。

**xdotool ， 可以用来写连点脚本，和中键事件。见上文有写到。我们可以利用xdotool来为gesture.json配置更加复杂的手势模拟。**




* **首先上文的touchegg，设置了两指捏合和反捏合分别为关闭页面和最大最小化窗口。**
* 三指轻拍：鼠标中键 （没错，就连这个基础功能deepin都不支持）
* 三指上滑：多任务视图（键盘快捷键为super+s）
* 三指下滑：显示桌面（键盘快捷键为super+d）
* 三指左右滑：切换桌面工作区（键盘快捷键为super+左右箭头）
* 四指轻拍：同应用切换不同实例（alt+~）
* 四指左右滑：将当前运行的应用带到左/右桌面去（快捷键为shift+super+左右箭头）
* 四指上划：同应用切换不同实例（alt+~）
* 四指下划：alt+tab

**三指左右划和四指左右划在我这里的设置是相反的。。我也不知道我咋养成的习惯。**

## gesture.json 备份
```json
[
    {
        "Event": {
            "Name": "swipe",
            "Direction": "up",
            "Fingers": 3
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key super+w"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "down",
            "Fingers": 3
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key super+d"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "left",
            "Fingers": 3
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key super+114"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "right",
            "Fingers": 3
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key super+113"
        }
    },
    {
        "Event": {
            "Name": "tap",
            "Direction": "none",
            "Fingers": 3
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool click 2"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "up",
            "Fingers": 4
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key alt+49"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "down",
            "Fingers": 4
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key alt+Tab"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "right",
            "Fingers": 4
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key shift+super+114"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "left",
            "Fingers": 4
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key shift+super+113"
        }
    },
    {
        "Event": {
            "Name": "tap",
            "Direction": "none",
            "Fingers": 4
        },
        "Action": {
            "Type": "commandline",
            "Action": "xdotool key alt+49"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "up",
            "Fingers": 5
        },
        "Action": {
            "Type": "built-in",
            "Action": "Handle4Or5FingersSwipeUp"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "down",
            "Fingers": 5
        },
        "Action": {
            "Type": "built-in",
            "Action": "Handle4Or5FingersSwipeDown"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "right",
            "Fingers": 5
        },
        "Action": {
            "Type": "built-in",
            "Action": "ReverseSwitchWorkspace"
        }
    },
    {
        "Event": {
            "Name": "swipe",
            "Direction": "left",
            "Fingers": 5
        },
        "Action": {
            "Type": "built-in",
            "Action": "SwitchWorkspace"
        }
    },
    {
        "Event": {
            "Name": "tap",
            "Direction": "none",
            "Fingers": 5
        },
        "Action": {
            "Type": "commandline",
            "Action": "dbus-send --type=method_call --dest=com.deepin.dde.Launcher /com/deepin/dde/Launcher com.deepin.dde.Launcher.Toggle"
        }
    }
]
```

# 电源管理
Linux的耗电问题永远比不过win，我们只能通过局部的优化让其尽可能耗电更低。
## 安装tlp
```sudo apt install tlp tlp-rdw```

启动：```sudo tlp start```
```systemctl start tlp.service```


查看tlp服务状态：```systemctl status tlp.service```

tlp还可以进行更详细的配置，这里可以自行百度。我这里只是用了默认的配置。

最后添加一行alias：```alias powersave="sudo tlp start && systemctl start tlp.service"```，每当断开电源的时候输入powersave就可以省电了。

## 实测续航
经过了上述的设置，我的Yoga14s（AMD锐龙），在轻度办公条件下（浏览器+markdown写文章+微信聊天），能够坚挺5.5h

Windows同样的办公条件下，能够坚挺6H。Linux能做到这样，已经是非常大的进步了。

如果是代码开发的话，估计续航会缩短很多。


# Deepin窗口上方标题栏太宽解决方案：

GitHub的issue里面有人给出了解决方案，非常有效：

https://github.com/linuxdeepin/developer-center/issues/1210

![在这里插入图片描述](https://img-blog.csdnimg.cn/8a8c9c24daaa4021bd1a92f3e620ea27.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

我个人的配置是：

```
[Active]
height=0

[Inactive]
height=0
```

重启计算机生效。


# wifi管理
笔者的deepin的wifi非常之难用, 建议通过一款命令行软件来使用：

**其实就是nmcli。如果没有，请使用	```sudo apt-get install nmcli```来安装。**

**详细配置介绍：**
https://www.cnblogs.com/mind-water/p/12079647.html?ivk_sa=1024320u

**如何开热点：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/1dfd3274b802412f878fa32b8f67b3f5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
**常用命令（不生效请使用sudo）：**

* 扫描wifi：
	nmcli dev wifi 

* 列出你历史连接过的已激活wifi：
	nmcli connection show --active
	去掉 --active，则显示未激活的

* 你只要连接过一次，你的wifi配置文件目录（含wifi密码）：
	/etc/NetworkManager/system-connections

* 开关wifi：
	nmcli r wifi on/off
* 连接某wifi：
	```sudo nmcli dev wifi connect "realme GT Neo" password "qwertuui"```