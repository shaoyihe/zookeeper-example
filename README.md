# [zookeeper-example](http://shaoyihe.github.io/posts/2016/05/08/zookeeper-example.html)

必备条件：

* 安装[Jdk8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* 安装[maven](http://maven.apache.org/)
* 安装[node](https://nodejs.org/)
* 安装[zookeeper](http://zookeeper.apache.org/releases.html)

我用的Mac，上面软件都是用[brew](http://brew.sh/)自动安装的。windows环境需要单独下载，linux查看对应的包管理工具。测试步骤如下：

* 启动zookeeper(zoo.cfg文件路径不同环境可能可能不同)：

  ```
  ○ → zkServer start  /usr/local/etc/zookeeper/zoo.cfg
  ZooKeeper JMX enabled by default
  Using config: /usr/local/etc/zookeeper/zoo.cfg
  Starting zookeeper ... STARTED
  ```

  ​


* 下载样例代码[zookeeper example](https://github.com/shaoyihe/zookeeper-example)

* 命令行窗口打开样例目录，然后启动服务提供方（可以打开多个窗口，模拟多个提供方）：

  ```
  ± |master U:2 ?:2 ✗| → cd zookeeper-server

   2016-05-08 16:03:13 ☆  heshaoyideMacBook-Pro in ~/IdeaProjects/zookeeper-server/zookeeper-server
  ± |master U:2 ?:2 ✗| → mvn clean package && java -jar target/zookeeper-server-1.0-SNAPSHOT.jar
  [WARNING]
  [WARNING] Some problems were encountered while building the effective settings
  ...
  2016-05-08 16:03:25.022  INFO 7920 --- [           main] com.he.Application                       : Started Application in 3.842 seconds (JVM running for 4.332)
  register {"protrol":"http","host":"100.78.143.183","port":59491} to /servers/test-me/0000000825
  ```


* 启动客户端

  ```
   2016-05-08 16:05:39 ☆  heshaoyideMacBook-Pro in ~/IdeaProjects/zookeeper-server
  ± |master U:2 ?:2 ✗| → cd zookeeper-client/

   2016-05-08 16:05:42 ☆  heshaoyideMacBook-Pro in ~/IdeaProjects/zookeeper-server/zookeeper-client
  ± |master U:2 ?:2 ✗| → npm start

  > zookeeper-client@1.0.0 start /Users/heshaoyi/IdeaProjects/zookeeper-server/zookeeper-client
  > node ./app.js

  listening 3000
  find new serverDatas http://100.78.143.183:54487,http://100.78.143.183:59491,http://100.78.143.183:54490
  ```


* 浏览器打开`http://localhost:3000/`，效果如下：

  ![服务发现效果](http://shaoyihe.github.io/assets/images/zookeeper-example-efect.gif))

  每次调用客户端服务，就会就会轮训调用不同的服务提供方。
