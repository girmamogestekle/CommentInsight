package com.comment.insight.api.gateway.exception;

import com.comment.insight.common.dto.ErrorResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class GatewayExceptionHandler implements org.springframework.cloud.gateway.filter.GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        return chain.filter(exchange)
                .onErrorResume(ex -> {

                    ErrorResponse error = new ErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Gateway Error",
                            ex.getMessage(),
                            exchange.getRequest().getPath().toString()
                    );

                    String body = """
                            {
                              "status": %d,
                              "error": "%s",
                              "message": "%s",
                              "path": "%s"
                            }
                            """.formatted(
                            error.getStatus(),
                            error.getError(),
                            error.getMessage(),
                            error.getPath()
                    );

                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                    DataBuffer buffer = exchange.getResponse()
                            .bufferFactory()
                            .wrap(body.getBytes(StandardCharsets.UTF_8));

                    return exchange.getResponse().writeWith(Mono.just(buffer));
                });
    }
}
