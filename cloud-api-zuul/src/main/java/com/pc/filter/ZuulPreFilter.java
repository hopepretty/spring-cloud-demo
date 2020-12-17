package com.pc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul前置过滤
 *
 * @author pc
 * @Date 2020/12/17
 **/
@Component
public class ZuulPreFilter extends ZuulFilter {

    /**
     * 返回过滤类型，有：pre，route，post，error
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 返回执行优先级，数字越小，优先级越高，越早执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return -9999;
    }

    /**
     * 设置过滤条件
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
//        return RequestContext.getCurrentContext().getRouteHost() != null &&
//                RequestContext.getCurrentContext().sendZuulResponse();
        return true;
    }

    /**
     * 具体的过滤操作
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //鉴权
        //埋点监控
        //限流
        HttpServletRequest request = currentContext.getRequest();
        //业务处理
        //在这个过滤器中，对原请求进行了重组，但是却因为清空了Cookie，使得原本可能存在于Session中的一些重要数据丢失。
        // 比如登录的用户信息就保存在Session中，转发后Session丢失，使得微服务不能获取到请求用户的信息
        //这里我们将request对象中的信息取出来，然后放入zuul上下文对象中，zuul转发的时候将我们所需的参数一并重新塞入新的请求中去
//        currentContext.addZuulRequestHeader(USER_ID_KEY, String.valueOf(userId));
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)) {
            return null;
        } else {
            responseError(currentContext, "无权限访问");
        }
        return null;
    }

    private void responseError(RequestContext requestContext, String errorMessage) {
        //不对请求进行路由
        requestContext.setSendZuulResponse(false);
        requestContext.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
        requestContext.setResponseStatusCode(401);
        //响应信息
        requestContext.setResponseBody(errorMessage);
    }
}
