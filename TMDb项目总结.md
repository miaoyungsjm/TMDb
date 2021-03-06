## 项目总结
在此次项目开发，我学会了很多东西，如：Retrofit 网络请求、Room 数据库的使用和设计、Retrofit 和 Room  配合 RxJava 的初步使用，以及 JetPacket Mvvm 的实战。  

但是，在这次开发中最让我深刻体会到的，并不是上面这些知识，而是自身的不足：  
（1）项目延期  
这是个严重的问题，开发没有按计划的时间点完成。  
首先，这是对项目前期分析和自身能力评估的不足。然后，开发过程中发现确实赶不上计划了，延期后也没及时调整计划。最后，也没能及时清楚的上报进度。


（2）开发思路的错误  
这次开发完成后，我仔细反思，发现项目延期的最大原因是：开发思路的错误。  
在 UI 出来前，我提前拿到了 API 接口，虽然熟悉了每个接口的调用，并分析出每个界面需要用到哪些接口。  

但是，我并没有仔细分析 API 接口返回的数据，并对此进行实体类的抽离。  
而且，再加上自身对数据库设计的不熟悉。导致在写每个模块的时候，数据库和实体类在一直在变动，改来改去浪费了很多时间。这也是为什么我无法正确评估进度，因为数据库一直在变动，每个界面的开发我都依赖于接口的真实数据。

最后，我发现数据稳定后，界面开发会变得很快。正确的做法应该是抽离好实体类，确定数据库，再利用抽离好的实体类，填入假数据把 UI 层的界面和交互逻辑先写完，最后再对接真数据，提高开发进度和效率。  


（3）JetPacket Mvvm 架构的理解不足  
① 缺少了 Model 层的 Repository，把业务逻辑和数据请求策略都写去了 ViewModel。  
虽然已经尽力对各块功能进行划分整理，但效果还是不好，重复代码很多，ViewModel 承担职责过多，对后期的拓展和维护也非常不友好。  
在写的过程中，苦思冥想都想不出怎么解决，消耗了不少时间。在项目写完后，通过学习和比对各位大佬的优秀代码才慢慢明白过来。只能后期再自行抽时间多练习和优化重构。  

② 对 ViewModel 的理解和 DataBinging 的使用也存在问题。
因为我的请求逻辑写去各个界面的 ViewModel ，所以就一直在思考 ViewModel 被销毁网络请求的异步数据如何去废弃，没有考虑好公用 ViewModel。  
关于 adapter 的 DataBinging 也没有理解透彻，导致 adapter 的重复和不能公用。


（4）界面绘制不正确  
没理解好基本控件的一些属性，导致 xml 界面绘制的比较复杂，出现嵌套。  
比对后才发现原来还能这样用。

（5）kotlin 学习  
此次项目，用的是 java 写的，还没用上 kotlin。  
后期需要继续完成 kotlin 语法和特性学习，及时替换。  

（6）自定义控件的学习  