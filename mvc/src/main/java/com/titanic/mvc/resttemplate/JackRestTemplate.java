package com.titanic.mvc.resttemplate;

import java.util.concurrent.TimeUnit;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JackRestTemplate {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public JackRestTemplate(
        @Value("${something:default}") String baseUrl) {

        // socket configuration
        SocketConfig socketConfig = SocketConfig.custom()
            .setSoKeepAlive(true)
            .setSoReuseAddress(true)
            .build();

        // TCP connection pool manager configuration. Better to set MaxPerRoute or MaxTotal through application.yml
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setDefaultMaxPerRoute(10);
        connectionManager.setMaxTotal(200);

        // Timeout for connection, read. Better to set timeout value to specific value
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(1000)
            .setSocketTimeout(1000)
            .build();

        // httpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(connectionManager)
            .evictIdleConnections(10, TimeUnit.SECONDS)
            .build();

        /**
         * Use BufferingClientHttpRequestFactory for reading multiple response body.
         */
        ClientHttpRequestFactory clientHttpRequestFactory = new BufferingClientHttpRequestFactory(
            new HttpComponentsClientHttpRequestFactory(closeableHttpClient)
        );

        /**
         * Use HttpComponentsClientHttpRequestFactory when need not to reading multiple response body.
         */
        ClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            closeableHttpClient);

        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
    }
}
