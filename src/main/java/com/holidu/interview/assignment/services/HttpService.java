package com.holidu.interview.assignment.services;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class HttpService {

    /**
     * Get Request
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getRequest(URI uri, Class<T> clazz) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, clazz);

    }

}
