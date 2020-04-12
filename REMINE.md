一、注册中心（最核心的东西就是 高可用性）
服务调用方基本上每隔30s从服务注册中心将服务的实例信息拉去下来，放入JVM内存中
##### Eureka （已停更，AP）
服务端：其实是新建一个服务出来，加上@EurekaServer注解，将此服务作为
        服务端，其他的客户端直接在自己的application文件中配置此server的
        地址就行
集群原理：互相注册，相互守望

##### Nacos （推荐）
服务端：是nacos自己提供的一个服务端，我们自己写的服务都是需要注册上去的

##### Zookeeper （CP）
1、引入starter包，并使用@EnableDiscoveryClient注解
2、zk默认的注册的节点是临时的，当我们服务宕机后，大概30s后自动删除该节点

##### consul （CP）
提供了服务治理、配置中心、控制总线等功能 

##CAP
C：consistency 强一致性
A：Availability 可用性
P：Partition tolerance 分区容错性
CAP理论关注粒度是数据，而不是整体系统设计的策略
      
1、在分布式架构中，都是要保证p，所以一般采用 ap，保证最终一致性即可