这一个module讲了dobbo+zookeeper, 由于不常用,笔记也就没记, 不过代码写的很全

对应狂神说springboot的这一节:

![image-20201030202420350](C:\Users\WZR11\AppData\Roaming\Typora\typora-user-images\image-20201030202420350.png)

# 重要事说三遍,一定要看

# 重要事说三遍,一定要看

# 重要事说三遍,一定要看

**狂神的这一节, 注册非常慢,一直到24分01秒才成功注册.**

**其他时间一直在调试bug如何注册**

**我这边已经调试好了, 跟着下文的步骤走, 注册成功后, 直接跳到24分01秒开始听**

1. 启动 C:\codes\zookeeper\apache-zookeeper-3.6.2-bin\bin 下的 zkServer.cmd(开启zookeeper服务器端)

2. 管理员打开cmd, 然后进入目录 cd C:\codes\zookeeper 该目录存有dubbo的jar包

3. 进入目录之后,输入指令 java -jar dubbo-admin-0.0.1-SNAPSHOT.jar (意思就是打开dubbo的jar包, 该jar包存放在cd C:\codes\zookeeper里)

4. 运行idea:

   ![image-20201030212847547](C:\Users\WZR11\AppData\Roaming\Typora\typora-user-images\image-20201030212847547.png)

5.假如idea看到如下输出, 说明对了

![image-20201030212930464](C:\Users\WZR11\AppData\Roaming\Typora\typora-user-images\image-20201030212930464.png)

6.登陆网址 http://localhost:7001 用户名和密码都是root

7. 点服务

   ![image-20201030213026441](C:\Users\WZR11\AppData\Roaming\Typora\typora-user-images\image-20201030213026441.png)

看到这个lubenwei.service.TicketService 就对了!

![image-20201030213055222](C:\Users\WZR11\AppData\Roaming\Typora\typora-user-images\image-20201030213055222.png)

## 假如你做了这些步骤仍然不行, 就重新做 再试一遍, 流程有问题. 我2020/10/30就是照这个步骤做,成功了.