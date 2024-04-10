package com.firmahub.firmahub.modules.person.repository;

import org.springframework.stereotype.Repository;
import com.firmahub.firmahub.modules.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
