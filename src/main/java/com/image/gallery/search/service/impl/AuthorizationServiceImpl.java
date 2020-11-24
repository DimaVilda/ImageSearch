package com.image.gallery.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.image.gallery.search.exc.AuthException;
import com.image.gallery.search.model.auth.Credential;
import com.image.gallery.search.model.auth.Token;
import com.image.gallery.search.service.AuthorizationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private final RestTemplate restTemplate;
    private final String url;
    private final Credential credential;
    private Token token;

    public AuthorizationServiceImpl(String url, String apiKey) {
        this.restTemplate = new RestTemplate();
        this.url = Objects.requireNonNull(url);
        this.credential = new Credential(Objects.requireNonNull(apiKey));
    }

    @SneakyThrows
    @Override
    public Token authorise() {
        //// TODO: 21.11.20
        log.info("[AuthorizationServiceImpl] : authorise triggered");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "*/*");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("Connection", "keep-alive");
        final HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(credential), headers);
        final ResponseEntity<Token> tokenResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Token.class);
        if (tokenResponse.getStatusCode().is2xxSuccessful()) {
            this.token = tokenResponse.getBody();
            return this.token;
        }
        final AuthException authException = new AuthException(
                String.format("Auth error. Status code : %s", tokenResponse.getStatusCode())
        );
        log.error("[AuthorizationServiceImpl] : authorise error", authException);
        throw authException;
    }

    @Override
    public String getToken() {
        log.info("[AuthorizationServiceImpl] : getToken triggered");
        if (token != null) {
            log.info("[AuthorizationServiceImpl] : token is not null");
            return token.getToken();
        }
        log.info("[AuthorizationServiceImpl] : token is null");
        return authorise().getToken();
    }
}
