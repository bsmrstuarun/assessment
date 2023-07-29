package com.assessment.service.imp;

import com.assessment.common.utils.Defs;
import com.assessment.common.utils.Utils;
import com.assessment.model.Lead;
import com.assessment.repository.LeadRepository;
import com.assessment.service.LeadService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class LeadServiceImplementation implements LeadService {
    @Autowired
    LeadRepository leadRepository;

    @Override
    public String upload(MultipartFile file) throws InterruptedException, ExecutionException, IOException {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            List<Future<Void>> futures = new ArrayList<>();
            List<Lead> leadChunk = new ArrayList<>(Defs.BatchSize);
            for (CSVRecord record : csvParser) {
                leadChunk.add(processChunk(record));
                if (leadChunk.size() >= Defs.BatchSize) {
                    final List<Lead> leadsToInsert = new ArrayList<>(leadChunk);
                    Future<Void> future = executorService.submit(() -> {
                        saveRecordToDB(leadsToInsert);
                        return null;
                    });
                    futures.add(future);
                    leadChunk.clear();
                }
            }

            // Process any remaining leads
            if (!leadChunk.isEmpty()) {
                final List<Lead> leadsToInsert = new ArrayList<>(leadChunk);
                Future<Void> future = executorService.submit(() -> {
                    saveRecordToDB(leadsToInsert);
                    return null;
                });
                futures.add(future);
            }

            // Wait for all threads to finish
            for (Future<Void> future : futures) {
                future.get();
            }

            executorService.shutdown();

        } catch (Exception ex) {
           throw ex;
        }

        return "Successful";
    }

    private Lead processChunk(CSVRecord record) {
        return Lead.builder().name(Utils.isOk(record.get(0)) ? record.get(0) : "N/A").fatherName(Utils.isOk(record.get(1)) ? record.get(1) : "N/A").motherName(Utils.isOk(record.get(2)) ? record.get(2) : "N/A").email(Utils.isOk(record.get(3)) ? record.get(3) : "N/A").phoneNumber(Utils.isOk(record.get(4)) ? record.get(4) : "N/A").passportNumber(Utils.isOk(record.get(5)) ? record.get(5) : "N/A").createdAt(new Date()).build();
    }

    private void saveRecordToDB(List<Lead> leadList) {
        try {
            leadRepository.saveAll(leadList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
