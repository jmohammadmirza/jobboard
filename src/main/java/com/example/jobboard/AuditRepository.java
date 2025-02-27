package com.example.jobboard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditRepository extends MongoRepository<Audit, String> {

    
}
