1、Zuul 的核心是Filters，在请求转发的各个时期Zuul都自定义了一些Filter用来对原请求进行封装，从而转发。由于尚未引入Ribbon
   ，所以没有被 RibbonRoutingFilter 拦截。转发的处理是发生在 route 时期，所有的Filter都要继承ZuulFilter
   
2、Zuul 的核心是 Filters，根据执行时期分为以下几类：
  PRE：这种过滤器在请求被路由之前调用
  ROUTING：这种过滤器将请求路由到微服务
  POST：这种过滤器在路由到微服务以后执行
  ERROR：在其他阶段发生错误时执行该过滤器   