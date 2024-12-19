package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.batch.jobs.JobProduct;
import com.prod.facades.IBatchFacade;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/batch")
@Slf4j
public class BatchController extends Controller<String> {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobProduct jobProduct;
    @Autowired
    private IBatchFacade batchFacade;
    @GetMapping("/product")
    public ResponseEntity<ResponseObject<String>> insertProduct(HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")){
                Map<String, JobParameter<?>> map = new HashMap<>();
                map.put("time", new JobParameter(System.currentTimeMillis(), JobParameter.class));
                JobParameters parameters = new JobParameters(map);
                JobExecution jobExecution = jobLauncher.run(jobProduct.getJob(), parameters);
                String message = "Batch Status : " + jobExecution.getStatus();
                log.info(message);
                return ResponseEntity.ok().body(
                        ResponseObject.<String>builder()
                                .isSuccess(true)
                                .message(message)
                                .data(null)
                                .build()
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }
    @GetMapping("/detail")
    public ResponseEntity<ResponseObject<String>> insertDetail(HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")){
                return ResponseEntity.ok().body(
                        batchFacade.insertDetail()
                );
            } else return notOwner();
        } catch (Exception e) {
            return serverError(e);
        }
    }
    @Override
    public ResponseEntity<ResponseObject<List<String>>> notOwners() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject<List<String>>> serverErrors() {
        return null;
    }

}
