package com.image.gallery.search.config;

import com.image.gallery.search.service.AuthorizationService;
import com.image.gallery.search.service.FileAnalyzerService;
import com.image.gallery.search.service.ImageService;
import com.image.gallery.search.service.impl.AuthorizationServiceImpl;
import com.image.gallery.search.service.impl.FileAnalyzerServiceImpl;
import com.image.gallery.search.task.ScheduledTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    RestTemplate restTemplate(AuthorizationService authorizationService) {
        final RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RequestInterceptor(authorizationService));
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    FileAnalyzerService fileAnalyzerService() {
        return new FileAnalyzerServiceImpl();
    }

    @Bean
    ScheduledTask scheduledTask(ImageService imageService, FileAnalyzerService fileAnalyzerService,
                                RestTemplate restTemplate, @Value("${sync.url}") String url) {
        return new ScheduledTask(imageService, fileAnalyzerService, restTemplate, url);
    }

    @Bean
    AuthorizationService authorizationService(@Value("${auth.url}") String url,
                                              @Value("${auth.apiKey}") String apiKey) {
        return new AuthorizationServiceImpl(url, apiKey);
    }

    @Bean
    String string() {
        return "";
    }
}
