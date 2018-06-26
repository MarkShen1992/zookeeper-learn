0. [zookeeper](http://zookeeper.apache.org/)学习

- 使用软件提供的`zkCli.cmd/zkCli.sh`来连接`zookeeper`
    - 创建(create)
    
          create /zk-book 123
         
      上面的语句表示创建路径`/zk-book`，并且把数据`123`存放到`/zk-book`路径下
      
    - 读取(get)
    
          get /zk-book
         
      上面的语句表示读取路径`/zk-book`下的值
      
    - 修改(set)
    
          set /zk-book 456
         
      上面的语句表示将路径`/zk-book`下的值修改为`456`
      
    - 删除(delete)
    
          delete /zk-book
         
      上面的语句表示删除路径`/zk-book`
