package cn.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1)  //在过滤器链中，此值越小优先级越高（因为可能有多个过滤器链）
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //1、获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        //2、获取参数中的authorization参数
        String authorization = queryParams.getFirst("authorization");
        //3、判断参数值是否等于admin
        if ("admin".equals(authorization)){ //4、如果是则放行
            return chain.filter(exchange); //为什么放行需要这么写？因为放行只是这个过滤器放行了，然后进入了过滤器链的下一个过滤器过滤
        }
        //5、否，拦截
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);  //这一步就是设置状态码为401，也可以不设置。但是最好设置(用户体验)
        return exchange.getResponse().setComplete();


    }
}
