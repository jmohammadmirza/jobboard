package com.creative.jobboard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.creative.jobboard.pojo.IpGeoData;

@Repository
public interface IpGeoDataRepository extends MongoRepository<IpGeoData, String> {
    
}