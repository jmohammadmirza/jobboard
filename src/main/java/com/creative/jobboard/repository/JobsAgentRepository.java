package com.creative.jobboard.repository;

import com.creative.jobboard.pojo.Job;
import com.creative.jobboard.pojo.JobsAgent;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface JobsAgentRepository extends MongoRepository<JobsAgent, String> {
    @Query("{ 'title' : { $regex : ?0, $options: 'i' } }")
    List<JobsAgent> findByTitleRegexSortedByIdDesc(String regex, Sort sort);

    List<JobsAgent>  findByTitleOrderByIdDesc(String title);
}
