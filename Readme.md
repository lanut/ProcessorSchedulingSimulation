# 处理机调度的模拟实现

> 操作系统课程设计（大三上学期）
> 
> 实现处理机调度模拟程序，能够根据不同的算法进行调度，包括先来先服务、短作业优先、最短剩余时间优先、时间片轮转、基于静态优先级的调度，基于高响应比优先的动态优先级调度，多级反馈队列调度等处理机调度算法，能够模拟进程调度情况，并根据周转时间、带权周转时间，平均周转时间和平均带权周转时间等对不同的调度算法进行分析评价。

> [!IMPORTANT]
> 使用Java EE 8 构建，在使用Tomcat时，要使用8.5以上，10.0以下版本。


## 项目系统功能

![思维导图-项目系统.svg](Readme%2F%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE-%E9%A1%B9%E7%9B%AE%E7%B3%BB%E7%BB%9F.svg)

### 已经实现的功能：

可以添加指定时长和优先级的程序
可以添加程序到指定时间到达的队列
设定是否为时间片轮转和选择调度算法
调试模式下的手动设置多级序列

### 预留接口为实现的功能：
决定是否为抢占式和非抢占式（现在使用的是抢占式）

### 需要重构项目才实现的功能：

完整的多级时间片轮转（因为我写死一个运行队列和一个预备队列）

<hr>

## 项目类结构

![思维导图-进程结构.svg](Readme%2F%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE-%E8%BF%9B%E7%A8%8B%E7%BB%93%E6%9E%84.svg)

程序：具有**程序名**，**运行时间**，**优先级**等重要属性，**id标识符**为可选属性（程序名哈希值加创建时间组合）

进程：包涵**执行程序**，**到达时间**的创建属性，在运行过程中有**运行状态**，**完成时间**

记录：记录运行状态，包涵<u>**运行的进程**</u>，**开始时间**，**结束时间**，**进程是否完成**（<u>运行的进程</u>不推荐使用，因为当一个模拟系统结束后会删除进程导致无法定位信息）

进程总结：记录运行完成后的总结，当一个进程完成后将记录**进程ID**，**进程名**，**到达时间**，**开始时间**，**结束时间**，**周转时间**，**带权周转时间**。



## 运行系统

![思维导图-运行系统.svg](Readme%2F%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE-%E8%BF%90%E8%A1%8C%E7%B3%BB%E7%BB%9F.svg)

每一个运行系统都是一个单独的实现，可以实现一个进程队列的模拟

核心函数是“运行进程”函数

给一个进程指定的运行时间，如果**运行时间**用完，但是**进程未结束**则返回0；否则返回剩下的时间。并通过返回的值来修改系统的当前时间。

是否时间片轮转的基础选择后运行一轮Running队列、运行所有进程都是调用上方函数实现的。

如果是强占式的，则为运行完1秒后执行调度，如果是非抢占式，则为运行完进程后调度（非时间片轮转）

## 调度系统

对指定的进程队列进行调度

![思维导图-调度系统.svg](Readme%2F%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE-%E8%B0%83%E5%BA%A6%E7%B3%BB%E7%BB%9F.svg)

