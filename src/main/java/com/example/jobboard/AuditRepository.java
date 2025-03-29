package com.example.jobboard;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditRepository extends MongoRepository<Audit, String> {


    List<Audit> findByAuditTrackingId(String auditTrackingId);
}
