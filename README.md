#使用说明
源代码使用Git版本管理工具，采用GitHub托管平台。
在操作系统上装好Git for Windows后，打开Git Bash切换到你的Eclipse工作目录，然后使用如下命令克隆本仓库文件。

	git clone git@github.com:kerneldeveloper/InternetOfVehicles.git

如果需要Git速成，可以打开下面的网址看一下廖学峰的教程，这是目前网络上最好的入门资料。
http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000/

*如果想要详细的系统的学习Git，目前最好的书籍是《Version Control with Git, 2nd Edition》，中文版名字叫《Git版本控制》（第二版，影印版）。图书馆有这本书。*

#分支与合并

##分支管理说明
Git依靠分支来管理开发进程，每个分支代表一个开发方向。这个仓库我会建立两个分支：master和develop。master分支是稳定分支，develop是开发中的分支，当develop分支内的代码迭代的比较稳定后才会并入master分支，所以日常开发都在develop分支上进行，master分支不要去做修改，这个分支由我来管理。

##冲突合并
协同开发难免产生代码冲突，比如初始情况下GitHub仓库里有原始代码，A克隆到本地进行开发，B也克隆到本地进行开发。然后A将修改完的本地代码推送到远程GitHub，这时B再推送就会失败，因为远程仓库的代码已经不是原来的样子，A的修改和B的修改产生了冲突，这时Git无法自己决定如何合并冲突，需要B自己来手动合并冲突，简单说来就是B先把GitHub远程仓库拉取下来（包含了A所做的修改），B将A的代码和自己的代码合并后再次推送就成功了。

详细教程参见：
http://blog.csdn.net/mad1989/article/details/16885569

**不要害怕冲突，Git诞生的目的就是为了解决协作开发所导致的冲突。**

##如何多人协作
首先在github上fork我的项目，你会得到一个同样的项目，不过这是你自己的而非我的，但代码是相同的。然后你将你参阅[这个文章](http://www.oschina.net/question/54100_167919?sort=time)将你的项目clone到你的eclipse工程目录里。然后就是做修改和提交了，这些提交都是在你自己的仓库里进行的，如果觉得修改的差不多了，可以在github给我的项目提交pull request，具体[教程见此](http://www.cnblogs.com/astwish/articles/3548844.html)。
我会在合适的时机将你们的代码并入我的仓库，然后你们依然参见上面的[教程](http://www.cnblogs.com/astwish/articles/3548844.html)，这个教程的最后有说明如何与我的仓库保持同步。

这样就可以多人合作了，每一次提交都会有commit记录，如果出现问题可以随时回退，用了git就不必担心代码被修改混乱了，也不必担心协作冲突。

#Tricks
每当做完大的修改或完成了一个功能就应该做一次提交（git commit），这样如果在编程中发现本次修改出现了重大问题，需要放弃现在的思路，可以随时进行版本回退。版本回退是Git最强大的功能。


###遇到其他问题可以随时问我。