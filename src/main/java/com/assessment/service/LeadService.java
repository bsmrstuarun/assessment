package com.assessment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public interface LeadService {
    String upload(MultipartFile file) throws InterruptedException, ExecutionException, IOException;
}
