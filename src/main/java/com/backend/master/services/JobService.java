package com.backend.master.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.master.domain.dto.response.ResponseAPI;
import com.backend.master.repository.JobRepository;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    public ResponseAPI getList() {
        ResponseAPI response = new ResponseAPI();
        try {
            List<Object> list = jobRepository.getListJobs();
            response.setSuccess(true);
            response.setMessages("Get list job success!");
            response.setResult(list);
            response.setAdditionalEntity(null);
        } catch (Exception e) {
            System.out.println(e.toString());
            response.setSuccess(false);
            response.setMessages("Internal Server Error");
            response.setResult(null);
            response.setAdditionalEntity(e.toString());
        }
        return response;
    }

    public ResponseAPI getDetail(String jobId) {
        ResponseAPI response = new ResponseAPI();
        try {
            Object detail = jobRepository.getDetailJob(jobId);
            response.setSuccess(true);
            response.setMessages("Get detail job success!");
            response.setResult(detail);
            response.setAdditionalEntity(null);
        } catch (Exception e) {
            System.out.println(e.toString());
            response.setSuccess(false);
            response.setMessages("Internal Server Error");
            response.setResult(null);
            response.setAdditionalEntity(e.toString());
        }
        return response;
    }
}
