package com.creative.jobboard.repository;

import java.util.List;

import com.creative.jobboard.pojo.Job;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface JobRepository extends MongoRepository<Job, String> {

    @Query("{ 'title' : { $regex : ?0, $options: 'i' } }")
    List<Job> findByTitleRegexSortedByIdDesc(String regex,Sort sort);

    List<Job>  findByTitleOrderByIdDesc(String title);


}
