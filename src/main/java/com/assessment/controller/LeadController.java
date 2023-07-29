package com.assessment.controller;

import com.assessment.common.utils.Utils;
import com.assessment.service.LeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class LeadController {
    Logger logger = LoggerFactory.getLogger(LeadController.class);
    @Autowired
    LeadService leadService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadLeads( @RequestParam("csvFile") MultipartFile file) {
        try {
            leadService.upload(file);
        } catch (Exception ex) {
           ex.printStackTrace();
            return "error";
        }
        return "success";
    }


}
