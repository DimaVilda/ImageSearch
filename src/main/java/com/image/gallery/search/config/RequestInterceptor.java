package com.image.gallery.search.config;

import com.image.gallery.search.service.AuthorizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

    public static final String BEARER = "Bearer ";
    private final AuthorizationService authorizationService;

    public RequestInterceptor(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION, BEARER + authorizationService.getToken());
        final ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, bytes);

        if (clientHttpResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION, BEARER + authorizationService.authorise().getToken());
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }
        return clientHttpResponse;
    }
}
