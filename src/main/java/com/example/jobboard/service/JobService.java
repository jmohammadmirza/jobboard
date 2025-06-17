package com.example.jobboard.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.jobboard.Audit;
import com.example.jobboard.AuditRepository;
import com.example.jobboard.Job;
import com.example.jobboard.JobRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class JobService {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private IpGeoService ipGeoService;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_KEY = "c9dwvGcDNNek15Azs3Rd6QQR";

    public List<Job> fetchJobs(String query) throws IOException {

        String url = "https://www.searchapi.io/api/v1/search?api_key=" + API_KEY + "&engine=google_jobs&q=" + query + "+ fully remote";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            List<Map<String, Object>> jobs = (List<Map<String, Object>>) responseMap.get("jobs");

            if(jobs == null){
                return new ArrayList<>(); 
            }

            List<Job> jobList = new ArrayList<>();
            for (Map<String, Object> job : jobs) {
                Job jobEntity = new Job();
                jobEntity.setTitle((String) job.get("title"));
                jobEntity.setCompanyName((String) job.get("company_name"));
                jobEntity.setLocation((String) job.get("location"));
                jobEntity.setDescription((String) job.get("description"));
                jobEntity.setApplyLink((String) job.get("apply_link"));
                jobEntity.setUserInput(query);
                jobList.add(jobEntity);
            }

            saveAll(jobList);

            return jobList;
        }
    }

    @Async
    public void logUserActivity(String auditTrackingId,String query, HttpServletRequest request) throws IOException {

        // GET IP address
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
       
             String location = ipGeoService.saveIpGeoData(ipAddress);
             String userAgent = request.getHeader("User-Agent");

            // Thread.sleep(10000);

                 Audit audit = new Audit();
                 audit.setQuery(query);
                 audit.setTimestamp(new Date());
                 audit.setIpAddress(ipAddress);
                 audit.setLocation(location.isBlank()?"UNKNOWN":location);
                 audit.setUserAgent(userAgent);
                 audit.setAuditTrackingId(auditTrackingId);
                 auditRepository.save(audit);
                 
    }
    @Async
    public void updateAuditResponse(String auditTrackingId, String responseStatus,int responseCode)  {
       // Thread.sleep(10000);
        List<Audit> audits = auditRepository.findByAuditTrackingId(auditTrackingId);
        if(!audits.isEmpty()){
            Audit audit = audits.get(0);
            audit.setResponseSentToUser(responseStatus);
            audit.setResponseCode(responseCode);
            auditRepository.save(audit);
        }
    }
      
    @Async
    public void saveAll(List<Job> jobList) {

       System.out.println("data size" +jobList.size());
            jobRepository.saveAll(jobList);
    }
    
    public List<Job> fetchAll(){

         List<Job> allJobs=jobRepository.findAll();

        return allJobs;
    }
    
    public List<Job> findByTitleLike(String query){

        String regexQuery = ".*" + query + ".*"; 
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Job> allJobs=jobRepository.findByTitleRegexSortedByIdDesc(regexQuery,sort);

       return allJobs;
   }
   
}
