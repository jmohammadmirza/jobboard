package com.example.jobboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.jobboard.service.JobService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

@RestController
public class JobController {


    @Autowired
    JobService jobService;

 
    @GetMapping("/api/jobs/live")
    public List<Job> getJobs(@RequestParam String query,HttpServletRequest request)  {
        List<Job> alljobs=new ArrayList<>(); 
       try {
            jobService.logUserActivity(query,request);
            alljobs=jobService.fetchJobs(query);
        } catch (IOException e) {

            e.printStackTrace();
           
        }

      return alljobs;
    }



    @GetMapping("/api/jobs")
     public List<Job> getAllJobs(@RequestParam String query,HttpServletRequest request) {

       return jobService.findByTitleLike(query);

     }
}
