package com.creative.jobboard.repository;

import com.creative.jobboard.pojo.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditRepository extends MongoRepository<Audit, String> {


    List<Audit> findByAuditTrackingId(String auditTrackingId);
}
