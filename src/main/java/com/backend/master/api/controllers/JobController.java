package com.backend.master.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.master.domain.dto.response.ResponseAPI;
import com.backend.master.services.JobService;

@RestController
@RequestMapping(value = "api/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseAPI getList() {
        return jobService.getList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseAPI getDetail(@PathVariable("id") String jobId) {
        return jobService.getDetail(jobId);
    }
}
