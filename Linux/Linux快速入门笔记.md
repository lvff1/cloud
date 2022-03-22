# 前言
笔者根据尚硅谷经典Linux教程(千万级学习人次，linux最新升级版) 学习并取其精华整理，以备日后忘掉。

<font color=red size=5px>**全套资料和源代码已上传至Gitee：[https://gitee.com/da-ji/full-stack-developer](https://gitee.com/da-ji/full-stack-developer)，大家需要可自取**</font>

本文档 **足以应付日常工作中80%的基础Linux的使用和速查**（善用ctrl + f 查找你忘记的命令），如果你是专业Linux运维请移步；

**下附学习中整理的两张高清思维导图，如有需要可自取。谢谢大家！**


## Linux基础笔记整理
![在这里插入图片描述](https://img-blog.csdnimg.cn/3d04e2bcefd742d78cef2d701b3ea1aa.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
## Shell基础笔记整理
![在这里插入图片描述](https://img-blog.csdnimg.cn/6f536fe2ec3a49369291d4881d6dafc3.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
@[TOC](目录)

# Linux篇
## 一、最常用的基本命令
* **ls 和 ll**

	ls 列出当前目录下文件，ll列出当前目录下文件更详细信息
	![在这里插入图片描述](https://img-blog.csdnimg.cn/d280e54b35d449eaa1ba0569e6a111f7.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
	从左往右分别是：【文件类型和权限】、【目录个数】、【用户和所属组】、【文件大小（文件夹是4096）】、【修改日期】、【文件名】
<br>
初学者只需要知道 ll 命令 能比 ls 命令获取更多信息即可。


* **cd /路径**

	切换目录。很好记，change directory 的缩写
	
* **mkdir 创建目录**
   -   ```mkdir -p```一次性创建多级目录
     -   例：```mkdir -p /daji/niubi```

     <br>
     

*  **touch #创建文件**
	* 例 : ```touch daji.java```


-   **cp 复制命令**
	- -r #递归（recursive）复制整个文件夹

    -   例：cp -r 要复制的目录(包含子目录)  要复制的目的地
	普通的cp命令，只能复制单个文件或一个空目录，加入参数-r则可以复制一个含有多个子目录的目录。

-   **mv 移动 重命名**

    
       - mv 要复制的目录(包含子目录)  要复制的目的地
       -   mv 原名 新名



-   **cat 查看文件命令**

	 -   cat -n ———  显示行号
![在这里插入图片描述](https://img-blog.csdnimg.cn/0cf3f8d05dfb4638a2064efc628e46a1.png)


	-   ```cat -n 文件名 \| more ```———  分页显示文件

 -   **\> 指令 和 >\> 指令**

        -   \> 指令（内容覆盖）

            -   例：```ls  > a.txt```
![在这里插入图片描述](https://img-blog.csdnimg.cn/548df7f4184a4f4d831bf28406297ad7.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


        -   \>\> 指令（内容追加）
    
            -   例：将一行文字追加到a.txt中```echo 一行文字 >> a.txt```
![在这里插入图片描述](https://img-blog.csdnimg.cn/41d75c573dbb4af9a69da39e843ea66b.png)


    -   **echo 指令**
    
        -   输出命令到控制台，常用于Shell脚本，类似于C语言的printf
    
    -   **head 指令**
    
        -   head -5 文件名 ———  输出文件头5行字
    
    -   **tail指令**
    
        -   tail -5 文件名  ———  输出文件尾5行字
    
        -   **tail -f 文件名  ——— 输出日志！极常用**
    
    -   **history 查看历史指令**
    
        -   例：history 5 查看最近5条指令


    -   **查看文件大小指令**
    		
        -   ```du -sh *```		列出当前文件以及文件夹的大小。
    	-   ```du -sh * | sort -sh```		列出当前文件以及文件夹的大小,并按降序排列
    	![在这里插入图片描述](https://img-blog.csdnimg.cn/eca41961beb74ef89d9e5f508f1cf2c0.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)





## 二、Vim编辑器

 **三种常用模式：正常模式，插入模式，命令行模式**

*   **1、正常模式，该模式下可进行快捷键操作:**



| 常用命令 |       解释        |        举例         |
| :------: | :---------------: | :-----------------: |
|    yy    |    拷贝当前行     | 5yy 拷贝光标以下5行 |
|    p     |       粘贴        |                     |
|    dd    |    删除当前行     | 5dd 删除光标以下5行 |
|    G     |     去最末行      |     5G 去第5行      |
|    gg    |     去最首行      |                     |
|    u     |   撤销（undo）    |                     |
| ctrl + r |  重做  （redo）   |                     |
| ctrl + b |  向后翻页(back)   |                     |
| ctrl + f | 向前翻页（front） |                     |

*  **2、插入模式**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/e9fe1c209a854e7295b053692d8534d9.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
 **一般就使用 i  就可以了！**

* **3、命令行模式**
   * **命令行模式常用操作：**
        * w 写入
       * q 退出
       * wq 保存退出
      * q! 强制退出（不保存）

	*  **命令行模式常用技巧**

    -   查找关键字

        -   /关键字 【回车】（按回车查找）
            -   输入n（next的简称），光标跳到下一个查找到的地方
            -   输入N 光标跳到上一个查找到的地方
       ![在这里插入图片描述](https://img-blog.csdnimg.cn/0a1e0ce33d01438f91e99470f8d3affd.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


    -   设置行号
        -   set nu 和 set nonu

## 三、用户管理
   -   **创建新用户**

        -   useradd 用户名

            -   useradd -d 目录 用户名 —— 在指定目录下创建新用户

            -   useradd -g 用户组 用户名 —— 创建新用户时指定组

        -   passwd 用户名 —— 给新用户创建密码

        -   创建的新用户目录默认位于 /home 目录下（可修改，使用-d命令），/home 目录下和用户同名的文件夹，就是新用户一登陆的默认位置
        - 图例：
![在这里插入图片描述](https://img-blog.csdnimg.cn/0ad9f9b3112b46579b0eb1428998c9a9.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


    -   **删除用户**
    
        -   userdel 用户名
       <br>
    
    -   **查询用户信息**
    
        -   id 用户名
![在这里插入图片描述](https://img-blog.csdnimg.cn/0ce93765638e4da5bca894dbbd006941.png)


    -   **切换用户**
    
        -   su - 用户名 —— 非常好记，su是switch user
    
            -   低级别用户切换到高级别用户需要输入密码，反之不需要
    
        -   whoami —— 查看我是哪个用户
    
    -   **用户组**
    
        -   新增组
    
            -   groupadd 组名
    
        -   删除组
    
            -   groupdel 组名
        -   修改用户所属组
            -   usermod -g 用户组 用户名
    
    -   **用户和组的相关配置文件**
    
        -   /etc/passwd   用户的配置文件，记录用户信息
    
        -   /etc/shadow 口令的配置文件
    
        -   /etc/group 组的配置文件，记录组的信息

## 四、运行级别

   -   **级别列表：**

        -   0：关机

        -   1：单用户

        -   2：多用户无网络

        -   **3：多用户有网络（大多数情况）**

        -   5：图形界面

        -   6：重启

        -   **级别的配置文件**

        -   /etc/inittab

        -   **切换到指定运行级别**

        -   init 级别   例：init 3

## 五、常用目录结构

   -   **/bin**  存放着最经常使用的命令（ls cat等都在这）（/usr/bin 、/usr/local/bin ）

        -   **/sbin**   s为superuser的意思存放着系统管理员使用的系统管理程序（/usr/sbin、 /usr/local/sbin）
        
        -   **/home** 存放普通用户的主目录一般该目录是以用户账号命名
        
        -   **/root** 超级管理员的主目录
        
        -   **/lib** 类似windows的dll文件动态链接库
        
        -   **/lost+found**   系统非法关机后存放的文件
        
        -   **/etc** 系统管理需要的配置文件和子目录

        -   **/etc/profile** (是一个文件，不是目录) 存放着配置文件
    <br>

        -   **/usr 应用程序安装目录，用户文件目录类似于win的program files目录安装的程序绝大多数都在这个目录下**

        -   **/usr/local** 额外软件安装目录（一般是通过编译源码方式安装的）

        -   **/boot**  启动Linux时的核心文件

        -   **/dev**  类似于win的设备管理器，将所有硬件用文件形式存储

        -   **/var**  **存放着不断扩充的东西，日志文件常常放在这里**

        -   **/opt**  用户级的程序目录 用于放置第三方大型软件或者游戏


## 六、常用搜索查找类指令

   -   **find指令：从指定路径向下递归遍历整个子目录，将满足条件的文件显示到终端**
   
        -   语法：find 搜索范围 选项

           -   **常用选项1： -name 文件名查找指定文件**

                -   例：```find  -name hello.txt```递归查找整个子目录的所有hello.txt

            	 -   模糊查询使用通配符 \*

                    -   例：```find  -name *.txt ```递归查找整个子目录的所有 .txt 文件
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/29b3a1559102414bb7d825eb2f73d817.png)


            -  **常用选项2**：-user 用户名按拥有者查找
    
                -   例 : find /opt -user root  查找/opt目录下 root用户拥有的文件
    
            -   **常用选项3**：-size 文件大小
            文件大小的M大写 、k 要小写； 
            加号（+）代表 大于 ；
            减号（-）代表 小于 ；
            什么都不写就是等于
    			- 例：find / -size +20M 查找根目录下所有大于20M的文件
![在这里插入图片描述](https://img-blog.csdnimg.cn/df951b19cd054c4fbeaf15f9ff277712.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)
            -  **常用选项4**：-type 按文件类型查找

                -   例 : find / -name 'nginx' -type f  （查找名为nginx的可执行文件）
![在这里插入图片描述](https://img-blog.csdnimg.cn/a55f0b6af78249ba82563ee3d5ca2ced.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_17,color_FFFFFF,t_70,g_se,x_16)


 -   **grep指令和管道符 \|**

        -   **grep指令过滤查找**

            -   grep 选项 查找内容 源文件

                -   选项 -n 显示匹配行号

                    -   例：```cat hello.txt | grep 大吉```
![在这里插入图片描述](https://img-blog.csdnimg.cn/95ddf76bf7e049bc994f9f43b2b62bb9.png)


                -   选项 -i 忽略字母大小写
    
        -   **管道符   将前一个命令的处理输出结果传递给后面的命令处理**

<font color=red>**grep妙用：在一堆文件中查找某个文件夹中的某个字符：** </font>

看下图的例子，我可以确定某一句话在这个文件夹下。但是这个文件夹里面有很多子文件和子文件夹，我想根据这句话找到该文件位置。

使用命令 ```grep -r  "要匹配的字符串 ./*```

参数-r 可以递归搜索子文件夹，./* 代表当前目录

如下图所示，我们找到了包含这句话的文件夹：


![在这里插入图片描述](https://img-blog.csdnimg.cn/ea730875df244c72b7a302f218479692.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)

**该方法非常之实用！**




## 七、压缩&解压缩

   -   **gzip/gunzip压缩和解压(只能压缩成.gz文件)**

        -   例：```gzip a.txt```

        -   例：```gunzip a.gz```
![在这里插入图片描述](https://img-blog.csdnimg.cn/2fbffc91ba104375a183907129e24c3d.png)


    -   **zip/unzip压缩和解压**
    
        -   zip 参数 定义压缩后的文件名 要压缩的文件
    
            -   zip 常用参数
    
                -   -r 递归压缩（**压缩目录**）例：```zip -r mypachage.zip /home/daji```
                ![在这里插入图片描述](https://img-blog.csdnimg.cn/8cdd56fbd5564c12ba924c9af4baf615.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


        -   unzip 要解压的文件名
    
            -   unzip 常用参数
    
                -   -d 目录 —— 指定解压后存放目录 例：```unzip -d /newdir daji.zip```
    
    -   **tar 指令（压缩一般就用-zcvf，解压一般用-zxvf）**
        - 既可以压缩又能解压 **-zcvf 为压缩 ； -zxvf为解压**
    
        -   压缩例子：tar -zcvf xxx.tar.gz a.txt
    
        -   解压例子(大写C为指定解压路径，如果不写默认解压当前路径)：```tar -zxvf daji.tar.gz -C newdir/```
        - 具体参数解释 **（其实一般情况下，压缩连用-zcvf，解压连用-zxvf）**：
-c: 建立压缩档案
-x：解压
-t：查看内容
-r：向压缩归档文件末尾追加文件
-u：更新原压缩包中的文件

## 八、组管理和权限管理
 -   **查看权限**

        -   输入ll后，会看见文件的权限：
           ![在这里插入图片描述](https://img-blog.csdnimg.cn/01bda9905ed444c3a6369d059fb54f32.png)

            -   第一个字母代表文件的类型：如果是一个普通文件为\"-\",如果是一个目录为\"d\",

            -   剩下9个，以3个为一组看：
            	- 第一组表示文件所有者拥有的权限
            	- 第二组表示该文件所在组的用户,拥有的权限
            	- 第三组表示其他组的用户所拥有的权限

            -   每一组都有一个rwx：**r可读，w可写，x可执行**（可打开目录）

            -   举例：drwxr-xr-x
                 		 - 1、该文件是一个目录（d）
                 		 - 2、文件所有者可读可写可打开
                 		 - 3、该文件所在组用户只可读可打开
                 		 - 4、其他组用户只可读可打开

            -   root用户创建的文件或者文件夹，其他用户很有可能不能写入（只读）！





    -   **修改文件或目录权限 指令： chmod**
    
        -   **方法1：**
        	**u 所有者权限，g 同组权限，o 其他组用户权限**
        	
            例1：```chmod u=rwx,g=rwx,o=rwx text.txt```
            意为给text.txt所有用户最高权限
            
            例2：```chmod u=rwx,g=r--,o=r-- text.txt```
            意为给text.txt 同组用户、其他用户只读权限，给所属用户最高权限
            
        -   **方法2（强烈推荐）：**			
    
    		**根据：r=4，w=2，x=1，它们相加可以对权限进行组合，得到不同的权限（可写可执行3；只读 4；可读写 6；最高权限7）**
    		
    		所以修改权限的命令可以简化成：
    		
    		实现上面例1的相同功能（给text.txt所有用户最高权限）：
    		```chmod 777```
    		实现上面例2的相同功能（同组用户、其他用户只读权限，给所属用户最高权限）：
    		```chmod 744```


​			

    -   **修改文件所有者和所有组指令：chown**
    
        -  命令： ```chown newowner:newgroup file```
        - newowner 所有者； newgroup 所有组
        - -R 递归修改所有子文件和子目录
    
        -   例：```chown daji:group1 -R daji```
        **递归修改daji目录的所有文件的 所有者和所有组为root和root组**
        ![在这里插入图片描述](https://img-blog.csdnimg.cn/5018170680ae47e6810386fb500f582a.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


## 九、任务调度（定时任务）

   -   **crond任务调度（命令是crontab）**

    简单的任务调度可以使用crontab加入任务，复杂的一般写Shell脚本
    
        -   **相关指令**
    
            -   crontab -e写任务调度文档  可配合shell脚本使用
    
                -   例1：任务调度文档内容为：
                 ```*/1 * * * * date >> /home/tom/mydate.txt```
                 意为每隔一分钟向mydate.txt 文件中追加当前系统时间
    
                -   例2（与shell脚本配合）: 任务调度文档内容为
                ```*/1 * * * * /home/myshell.sh```
                shell文件内容为：```date >> /home/tom/mydate.txt```
                执行效果和例1一样。只不过这里使用了shell脚本
    
            -   crontab -l列出当前的所有任务调度
    
            -   crontab -r终止任务调度
    
                -   会删掉你写的任务调度文档内容
    
            -   service crond restart重启任务调度
    
        -   **任务的占位符（重点）**
       		 - \* \* \* \* \*
       		 第一个\*  代表一小时当中的第几分钟（范围0-59）
       		 第二个\* 代表一天当中的第几小时（范围0-23）
       		 第三个\* 一个月当中的第几天（范围1-31）
       		 第四个\* 一年当中的第几月（范围1-12）
       		 第五个\* 一周当中的星期几（范围0-7 0和7都代表星期日）
    
            -   占位符特殊符号
            1、如果是\* 代表任何时间
            2、逗号（ , ）代表不连续的时间
            3、( - ) 代表连续的时间范围
            4、( \*/数字) 代表每隔多久执行一次
            -   **下面多举几个例子方便理解：**
                -   例1：0 8,12,16 \* \* \*
                代表每天8点0分，12点0分，16点0分都执行一次命令
    
                -   例2： 0 5 \* \* 1-6
                代表5点0分 每周一到周六执行命令
    
                -   例3： \*/10 \* \* \* \*
                代表每隔10分钟执行一次命令



## 十、进程管理

 -   **ps 命令查看进程**

       -   ps -aux

     
            -   例1：```ps -aux | grep jar```
            查看带有jar的全部进程
            ![在这里插入图片描述](https://img-blog.csdnimg.cn/5ae0b45545db4a4eb642c13fd417ec21.png)


            -   例2：```ps -ef | grep jar```
    
                -   比-aux多了个父进程，你能查看父进程（PPID）了。PPID在第3列。如果第3列没有，就是没有父进程
    
    -   **top指令**
    
        -   和ps类似，但是和ps最大的区别是动态监控进程的运行状况(类似于win的任务管理器)
        ![在这里插入图片描述](https://img-blog.csdnimg.cn/ea2035f4a1b242d4b06418e83e24f605.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


        -   参数
    
            -   -d 秒数 —— 指定top指令每隔几秒更新（一般用默认的就行，不用指定）
    
            -   -i —— 使top不监控闲置或僵死进程
    
            -   -p 进程id —— 仅仅监控某个进程的状态
    
            -   -u  专门监控某个用户
            例： -u root
    
        -   交互操作（进top相当于进任务管理器）（就是top运行时候可以与其交互）
    
            -   P —— 以CPU使用率排序（默认）
    
            -   M —— 内存使用率排序
    
            -   N —— 以PID排序
    
            -   q —— 退出top
    
            -   k —— 根据进程号杀死进程
    
    -   **kill 命令 杀死进程**
    
        -   kill 参数 进程号
    
            -   例：kill -9 14785
    
        -   killall 进程名称根据名称过滤，支持通配符
    
            -   killall vim杀死所有vim的进程
            - pkill -f "java*"   （批量杀死所有，根据进程名过滤，杀进程）


    -   **服务管理**
    
        -   每个服务都有一个端口，外部程序通过该端口和Linux服务产生联系：<br>
        举例：3306是mysql端口，sqlYog通过3306和mysql服务产生联系<br>
        举例2：22是sshd（远程连接）端口，xShell通过22和sshd服务产生联系
    
        -   **管理服务的命令：service 服务名 参数**
         注意：在centos7.0后，systemctl代替了service
    
            -   **参数：start、 stop、 restart、reload 、status就是顾名思义的英文直译**（ 开始，结束，重启，重新加载，查看状态）
    
            -   例1：```service firewalld status```查看防火墙服务状态
            ![在这里插入图片描述](https://img-blog.csdnimg.cn/1ece6610e67a4439bae4038b72e207d3.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


        -   **telnet指令  （win和linux都可以）**
    
            -   telnet 主机ip 端口
            例：```telnet 47.100.53.56 9090```
    
    -   **监控网络状态netstat、ping**
    
        -   **命令：netstat 选项**
    		选项如下：
            -   -an 按一定顺序排列输出
            --p 显示哪个进程在调用
            **基本上连用，比如：netstat -anp**
    
         	 -   例：```netstat -anp | grep 8080```
                **极为常用，查看你8080端口被谁占用了，这时候就能得到pid，然后根据pid杀死该进程**
    
                -   例：```netstat -anp | grep java```
                查看java进程占用的端口
    
        -  **命令： ping IP地址**
        测试网络通不通
    
            -   例：```ping www.baidu.com```

## 十一、RPM包管理、yum（安装软件常用）

   -   **RPM包管理**

        -   概念：类似于windows下的 .exe安装程序，是linux的 行业标准

        -   查询已安装的rpm列表：```rpm -qa```

            -   ```rpm -qa | grep xxxx```—— 过滤查询

        -   查询安装的rpm软件包的信息（可用来查看安装目录）

            -   ```rpm -qi```包名

        -   安装和卸载

            -   安装

                -   ```rpm -ivh RPM包名```（是个路径名，你先要把它拷贝到linux系统中来）

            -   卸载

                -   ```rpm -e RPM包名```

        -   **yum方式**

        -   概念：yum是一个Shell前端软件包管理器，它基于RPM包管理器，优点是能从指定的服务器自动下载和安装，并能 一次性安装所有依赖的软件包。现在yum更加方便

        -   查询公网上存在的yum包：```yum list | grep xxxx```
        ![在这里插入图片描述](https://img-blog.csdnimg.cn/63a5e89a97424c138a3271014045c7bf.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


        -   安装
    
            -   yum install xxxx

# Shell篇
## 一、快速入门

- 脚本需以 #!/bin/bash 开头

- 脚本需具有可执行权限（x权限），一般新建完shell脚本要使用chmod命令先赋权限

- 每行语句后无需分号

- 注释：

  单行注释是# ;

  多行注释是 
  :\<\<! 

 		 我是多行注释
  ! 

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/de356bcc94554a2b96f8d71528af098f.png)
## 二、Shell的变量

- **系统变量**

  -   \$HOME、\$PWD、\$SHELL、\$USER 等
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/bf2b399818c74a0e8eb26007ede6db51.png)


- **用户自定义变量**

  -   定义变量(变量一般为大写，等号两侧不能有空格)：变量=值

      -   例：A=100

      -   **注意，变量等号两侧不能有空格！！**

  -   引用变量（使用变量） \$符号

  -   撤销变量：unset 变量

      -   使用readonly声明静态变量，静态变量不能unset

  -   将命令的返回值赋给变量

      -   A=\`ls -a\` —— 运行反引号的命令，并将结果返回给变量A

      -   A=\$(ls -a) —— 等价于反引号
      ![在这里插入图片描述](https://img-blog.csdnimg.cn/d62380929b044d7b8ac97d8171fddcb5.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDc1Nzg2Mw==,size_16,color_FFFFFF,t_70)


  -   用户定义全局变量

      -   修改 /etc/profile 文件

- **位置参数变量**

  -   \$n (功能描述: n为数字, \$0代表命令本身，\$1-\$9 代表第一到第九个参数， 十以上的参数，十以上的参数需要用大括号包含，如\${10})

  -   \$\* (功能描述: 这个变量代表命令行中所有的参数，\$\*把所有的参数看成一个整体)

  -   \$@ (功能描述:这个变量也代表命令行中所有的参数，不过\$\@把每个参数区分对待)

  -   \$\# (功能描述:这个变量代表命令行中所有参数的个数)
  <br>例：
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/ee79c6d3c65c4338a5e856b0f1d0b84e.png)
  运行脚本：
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/c88281b5c79e4d02a7a0705c154fd1f9.png)



- **预定义变量（Shell设计者预先设计好的全局变量）**

  - \$\$ (功能描述:当前进程的进程号(PID) )

  - \$! (功能描述:后台运行的最后一一个进程的进程号(PID) )

    -   例：你在shell脚本中的倒数第二行运行了一个程序，然后倒数第一行输入命令echo \"\$!\" ，就会打印出你倒数第二行运行的那个程序的PID

  - \$? (功能描述:最后一次执行的命令的返回状态。如果这个变量的值为0,证明上一个命令正确执行;

    如果这个变量的值为非0 (具体是哪个数，由命令自己来决定)，则证明上一个命令执行不正确了。)

- **显示当前shell中所有变量：终端输入set**

## 三、Shell运算符

- 语法：\$\[\] 中括号中间为表达式

  -   例：\$\[(2+3)\*4\]  —— 输出结果为20

- 例：求出两个输入参数的和（需要位置参数变量知识）

  脚本内容（test.sh）:

  ```shell
  #!/bin/bash
  
  RESULT=$[$1+$2]
  
  echo "输入的两个参数的和为：$RESULT"
  ```

  执行脚本命令：```./test.sh 8 6```

  -   输出：```输入的两个参数的和为：14```
## 四、自定义函数

**语法：**

```shell
[ function ] funname[()]
{
	Action;
	[return int;]
}
```

**例：编写自定义函数，计算两数之和：**

```shell
function getSum(){
   SUM=$[$num1+$num2]
   echo "和是=$SUM"
}
num1=8
num2=6
getSum $num1 $num2   #调用函数的语法。$num1和$num2是两个形参
```
## 五、Shell常用系统函数

- **读取控制台 输入 read（类似C语言的scanf）**

  - read 选项 参数 变量

  - 选项:

    - -p:指定读取值时的提示符;

      -t:指定读取值时等待的时间(秒)，如果没有在指定的时间内输入，就不再等待了

      变量:指定读取值的变量名
  - 例1:读取控制台输入一个num值:
  ```read -p "请输入一个数num1=" NUM1```
```echo "你输入的值是num1= $NUM1"```<br>
  例2: 读取控制台输入一个num值，在10秒内输入 :
  ```read -t 10 -p "请输入一个数num2=" NUM2```
 ```echo "你输入的值是num2=$NUM2 "```


- **basename 基本语法**

  - 功能:返回完整路径最后/的部分，常用于获取文件名

  - basename \[pathname\] \[suffix\]

  - basename \[string\] \[suffix\]

    功能描述: basename命令 会删掉所有的前缀包括最后一个( \'/\' )字符，然后将字符串显示出来。

    选项:suffix为后缀，如果suffix被指定了，basename会将pathname或string中的suffix去掉。
## 六、判断、循环和流程控制

- **判断语句**

  - **基本语法（注意 条件判断式 前后要有空格）：**

    ```shell
    if [ 条件判断式 ]
      then
       程序
    elif [ 条件判断式 ]
      then
         程序
    else
         程序
    fi
    ```

    - 非空就返回true 。可使用\$?验证：（0为true，非0为false）

    - **常用判断条件**

      - 比较判断：

        **= 等号用于字符串比较**

        **-It小于 （less than）**

        **-le小于等于 (less equal)**

        **-eq等于 (equal)**

        **-gt大于 (great than)**

        **-ge大于等于 (great equal)**

        **-ne不等于 （not equal）**

        例：

        ```shell
        if [ $1 -gt $2 ]
         then 
           echo “$1大于$2”
        else
           echo "$1小于$2"
        fi
        ```

      - 按照文件权限进行判断：

        * -r有读的权限

        * -w有写的权限

        * -x有执行的权限

      - 按照文件类型进行判断

        - -f文件存在并且是一个常规的文件

        - -e文件存在

        - -d文件存在并是一个目录

        - 例（判断大吉.sh 是否存在）：

          ```shell
          if [ -e /root/大吉.sh ]
          then
             echo "存在"
          fi
          ```

          

- **循环语句**

  - **for循环 基本语法（for循环的条件比较可以用 < > = ）：**

    ```shel
    for(( 初始值；循环控制条件；变量变化 ))
    do
          程序
    done
    ```

    - 例1：

      ```shell
      for((i=0;i<3;i++))	# 比较可以使用大于号小于号
      do
             echo "the num is $i"
      
      done
      ```

      - 输出结果：

        the num is 0

        the num is 1

        the num is 2

    - 例2（累加1到100）：

      ```shell
      SUM=0
      for((i=1;i<=100;i++))
      do 
         SUM=$[$SUM+$i]
      done
      echo $SUM
      ```

      - 输出结果 5050

        为什么使用\$\[\] ?   因为算术运算的语法是 \$\[\]

  - **while循环 基本语法：（while 和 \[\] 之间必须有空格）**

    - 例（输出1到10的累加）：

      ```shell
      SUM=0
      i=0
      while [ $i -le 10 ]  #比较必须用 -le；-eq 等等
      do
         SUM=$[$SUM+$i]
         i=$[$i+1]
      done
      echo "sum=$SUM"
      ```

      



## 七、Shell综合案例：
**任务目标：**
* 1、每天凌晨2:10备份数据库dajiDB到/root/shell/backupDB 这个文件夹中
* 2、备份开始和备份结束能够给出相应的提示信息
* 3、备份后的文件要求以备份时间为文件名，并打包成.tar.gz的形式，比如:```2018-03-12_ 230201.tar.gz```
* 4、在备份的同时，检查是否有2天前备份的数据库文件，如果有就将其删除。

**Shell代码如下：**
```bash
#!/bin/bash

# 1、每天凌晨2:10备份数据库dajiDB到/root/shell/backupDB 这个文件夹中
# 2、备份开始和备份结束能够给出相应的提示信息
# 3、备份后的文件要求以备份时间为文件名，并打包成.tar.gz的形式，比如:2018-03-12_ 230201.tar.gz
# 4、在备份的同时，检查是否有2天前备份的数据库文件，如果有就将其删除。

# 将这个脚本放置在crond任务调度队列中，定时执行

BACKUP=/root/shell/backupDB  # 这是备份后，数据库备份存放的路径

DATETIME=$(date +%Y_%m_%d_%H%M%S)  # 当前的时间作为文件名（Y M D H M S） 对应需求3

echo "=============开始备份=================================="
echo "==========备份的路径是：$BACKUP/$DATETIME.tar.gz=============="

HOST=localhost #主机
DBUSER=root	#用户名
DBPWD=root	#密码
DATABASE=dajiDB	#备份数据库名

if [ ! -d "$BACKUP"  ]  	#如果目录不存在，就创建该目录。
then
	echo "$BACKUP目录不存在，现在创建该目录"
	mkdir -p "$BACKUP"
fi

# 执行mysql的备份数据库指令
mysqldump -u$DBUSER -p$DBPWD --host=$HOST $DATABASE | gzip > $BACKUP/$DATETIME.sql.gz #管道符，将备份结果打包到指定的.gz 文件
echo "已建立并压缩名为$BACKUP/$DATETIME.sql.gz的sql文件"

# 打包该备份文件到 .tar.gz 格式
cd $BACKUP
tar -zcvf $DATETIME.tar.gz $DATETIME.sql.gz 	# 将 .sql.gz 文件 改成 .tar.gz 格式
echo "已经将$DATETIME.sql.gz 打包成 $DATETIME.tar.gz 格式"
 
# 删除之前建立的临时 .sql.gz 文件
rm -rf $BACKUP/$DATETIME.sql.gz
echo "已删除名为$BACKUP/$DATETIME.sql.gz的sql文件"

# 删除2天前的备份文件
find $BACKUP -mtime +2 -name "*.tar.gz" -exec rm -rf {} \; 	# exec 后面的指令，是将查找出来的文件夹删除。这是固定写法
echo "============备份文件成功============="
```
**将这个Shell脚本放置在crond任务调度队列中，定时执行(每天凌晨2:10)**：
* 在终端执行命令：```crontab -e```启动任务调度
* 编写任务调度文件：
```bash
10 2 * * * /root/shell/shellIntegration.sh
```
**完成！**
# 总结
看到这里，应该就可以对Linux和Shell做到一个基本的认知了。但也只是达到“知其然”的程度，不过对于后端开发来讲，熟记上述命令就足够了。

Linux是一个实操性特别强的技术，想要熟练使用Linux，只能靠多练习和多总结。日后我会更新更多前端/后端/Linux相关技术，如果这篇文章帮到了你，点个关注叭~