package com.backend.master.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JobRepository {
    public List<Object> getListJobs() {
        final String uri = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";

        RestTemplate restTemplate = new RestTemplate();
        Object[] result = restTemplate.getForObject(uri, Object[].class);

        return Arrays.asList(result);
    }

    public Object getDetailJob(String jobId) {
        final String uri = "http://dev3.dansmultipro.co.id/api/recruitment/positions/" + jobId;

        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(uri, Object.class);

        return result;
    }
}
