package com.holidu.interview.assignment.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

    @Value("${datasource}")
    private String datasource;

    public String getDatasource() {
        return datasource;
    }
}
