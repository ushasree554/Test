package com.molcon.escalex.microbiology.services;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.molcon.escalex.microbiology.pojo.HeaderDocuments;

@Repository
public interface HeaderRepository extends MongoRepository<HeaderDocuments, String> {

	   HeaderDocuments findByType(String type);
}
