一、注册中心（最核心的东西就是 高可用性）
服务调用方基本上每隔30s从服务注册中心将服务的实例信息拉去下来，放入JVM内存中
##### Eureka （已停更，AP）
服务端：其实是新建一个服务出来，加上@EurekaServer注解，将此服务作为
        服务端，其他的客户端直接在自己的application文件中配置此server的
        地址就行
集群原理：互相注册，相互守望

##### Nacos （推荐）
服务端：是nacos自己提供的一个服务端，我们自己写的服务都是需要注册上去的

自己自带引入了 Ribbon包（新版本）
 <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
      <version>2.2.1.RELEASE</version>
      <scope>compile</scope>
    </dependency>
所以不用加Ribbon依赖，当然如果使用其他的注册中心，则可以手动依赖

##### Zookeeper （CP）
1、引入starter包，并使用@EnableDiscoveryClient注解
2、zk默认的注册的节点是临时的，当我们服务宕机后，大概30s后自动删除该节点

##### consul （CP）
提供了服务治理、配置中心、控制总线等功能 
``
##CAP
C：consistency 强一致性
A：Availability 可用性
P：Partition tolerance 分区容错性
CAP理论关注粒度是数据，而不是整体系统设计的策略
      
1、在分布式架构中，都是要保证p，所以一般采用 ap，保证最终一致性即可

###Ribbon（客户端远程调用  + restTemplate） 存在于eurake包中，也就是eurake是用的ribbon进行负载均衡的
Ribbon是进程内的本地负载均衡 在一个服务调用服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，
      从而在本地实现RPC远程服务调用技术
nginx 客户端的请求都交给nginx，然后由nginx进行转发请求，即负载均衡由服务端实现的
1、是客户端实现负载均衡的工具
2、提供客户端的软件负载均衡算法和服务调用，它提供了一系列完善的配置项如连接超时、重拾等。
3、核心组件是IRule，有七种策略

### Feign
定义：是一个声明式的Web服务客户端，让编写Web服务客户端变的非常容易，只需要创建一个接口并在接口上添加注解即可
      openFeign是spring基于feign进行二次强化开发
      
为什么要用到Feign(已经有了eureka与ribbon)
Fn指在使编写Java http客户端变得更加容易
前面使用ribbon+restTemplate时, 需要使用get/post去模板化的请求，当一个接口请求多次时，我们需要自己封装起来
fn在此基础上进行了二次封装，我们只要定义一个接口即可，在上面加上@Feign注解即可关联绑定使用
能够很优雅而简单的实现服务的调用

同样feign也是基于Ribbon进行客户端的负载均衡

默认等待1秒钟，超过后报错


###### Hystrix
1、服务降级（fallback）：服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好的提示，fallback
       eg:1、程序运行异常 2、超时  3、服务熔断触发服务降级  4、线程池/信号量打满也会导致服务降级,tomcat默认10线程
2、服务熔断（break）：类比保险丝达到最大访问服务后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示
        熔断打开：请求不再进行调用当前服务，内部设置时钟一般为MTTR(平均故障处理时间)，当打开时长达到所设时钟则进入半熔断状态
        熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断、
        熔断关闭：熔断关闭不会对服务进行熔断
3、服务限流（flowlimit）:秒杀高并发等操作，严禁一窝蜂的过来拥挤，进行排队，一秒钟一个，有序进行

概念：
    它是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统中，许多依赖不可避免的会调用失败，比如
    超时、异常等，Hystrix能够保证一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的有弹性
    当某个服务单元故障发生后，通过断路器的故障监控，向调用方返回一个符合预期的、可处理的备选相应（fallback），而不是长时间
    的等待或者抛出调用方法无法处理的异常，这样保证了服务调用方的线程不会被长时间的、不必要的占用、从而避免了故障在分布式
    系统中的蔓延
    
##### zuul与gateway
序论：zuul逐渐被gateway淘汰，gateway网络方面使用netty，集成spring5.0的特性，使用了webflux
        zuul淘汰的原因在于zuul2的研发一直跳票，未能及时更新
gateway：是在Spring生态系统上构建的API网关服务，基于Spring5，Springboot2.x和project Reactor等技术
            旨在提供一种简单而有效的方式对APIj进行路由，以及提供一些强大的过滤器功能，例如：熔断、限流、重试等
          springCloud gateway是springcloud的生态系统中的网关，目标是提代zuul,在spring cloud 2.x以上的版本中，没有
           对新版本的Zuul2.0以上最新高性能版本进行集成，仍然还是使用Zuul1.x非Reactor模式老版本，而为了提升网关的性能，
           springCloud gateway是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty
           它的目标提供统一的路由方式且基于Filter链的方式提供了网关基本功能，例如：安全、监控/指标，限流
    基本原理 ： 构建路由-路由匹配（断言）-过滤器（过滤前与后）-实际服务处理
    三大概念：Route(路由)：路由是构建网关的基本模块，它是由ID，目标URI，一系列的断言和过滤器组成
              Predicate(断言)：可以匹配HTTP请求中的所有内容（例如请求头或者请求参数）
              Filter(过滤)：指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者后进行判定
              
##  sleuth 与zipkin（http://localhost:9411/zipkin）链路追踪    http://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/          