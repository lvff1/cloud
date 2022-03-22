@[TOC](目录)
# 需求
对 ```{"code":200,"message":"操作成功","data": somedatas```
这一行进行过滤，要求只留下前面的状态码200

# 解决方案

 **为了考虑到区分不出哪些是正则表达式，哪些是过滤条件，先过滤一部分**：

测试：```echo '{"code":200,"message":"操作成功","data":' | sed 's/^.*code//g' | sed 's/message.*$//g'```
![在这里插入图片描述](https://img-blog.csdnimg.cn/15d1e7d7fae34a0ab1b1675ce0fad1ff.png)执行上述语句，过滤结果是：```":200,"```

 **然后再执行只留下200的过滤**

很简单，只需要这么写表达式就行了

```echo '{"code":200,"message":"操作成功","data":' | sed 's/^.*code"://g' | sed 's/,"message.*$//g'```

![在这里插入图片描述](https://img-blog.csdnimg.cn/9d211b1024d545b98afcffede7363d9d.png)

# 总结
* 前缀过滤条件：```sed 's/^.*过滤关键字"://g' ```
除了 过滤关键字 这五个大字，其余都是正则表达式

* 后缀过滤条件：```sed 's/,"过滤关键字.*$//g'```
除了 过滤关键字 这五个大字，其余都是正则表达式
* 要连续使用两个 | 来连接，这样才能实现前后都过滤

# 补充1 ：sed向行首行尾添加字符
在每行的头添加字符，比如“字符串”，命令如下：

```sed 's/^/字符串&/g'```

在每行的行尾添加字符，比如“字符串”，命令如下：

```sed 's/$/&字符串/g' ```

接上面去掉前后缀的例子： 既去掉前缀，又去掉后缀，又向前缀后缀追加指定字符串：

```echo '{"code":200,"message":"操作成功","data":' | sed 's/^.*code"://g' | sed 's/,"message.*$//g' | sed 's/^/字符串&/g' | sed 's/$/&字符串/g'```

![在这里插入图片描述](https://img-blog.csdnimg.cn/04c78a7aa8cc48379ad9c3d00be28fec.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)
# 补充2 ：vim对文本排序,对文本过滤
排序：
```:%!sort -n```
过滤：
[vim匹配特定的行并删除它](https://blog.csdn.net/joeblackzqq/article/details/7399019)

[vim 删除每行前/后n个字符](https://blog.csdn.net/u010555688/article/details/60136349)
vim保留每行前4个字符：```:% s/\v^(.{4})(.*)$/\1/g```




# 参考资料
菜鸟教程 —— linux sed 命令：https://www.runoob.com/linux/linux-comm-sed.html

![在这里插入图片描述](https://img-blog.csdnimg.cn/8965158438f841f28788e96976e9467e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAQOWkp-WQiQ==,size_20,color_FFFFFF,t_70,g_se,x_16)