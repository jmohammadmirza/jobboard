package com.example.jobboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.jobboard.service.JobService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.UUID;

@RestController
public class JobController {


    @Autowired
    JobService jobService;

    int success= HttpStatus.OK.value();
    String sucessMsg="response sent to user";
    int error=HttpStatus.NO_CONTENT.value();

 
    @GetMapping("/api/jobs/live")
    public List<Job> getJobs(@RequestParam String query,HttpServletRequest request)  {
        List<Job> alljobs=new ArrayList<>();
        String auditTrackingId= UUID.randomUUID().toString();
       try {

            jobService.logUserActivity(auditTrackingId,query,request);
            alljobs=jobService.fetchJobs(query);
           jobService.updateAuditResponse(auditTrackingId,sucessMsg,success);

        } catch (IOException e) {
           jobService.updateAuditResponse(auditTrackingId,e.getLocalizedMessage(),error);
            e.printStackTrace();
           
        }

      return alljobs;
    }



    @GetMapping("/api/jobs")
     public List<Job> getAllJobs(@RequestParam String query,HttpServletRequest request) {

       return jobService.findByTitleLike(query);

     }
}
