package com.example.SpringPostgressDocker.repository;



import com.example.SpringPostgressDocker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
    boolean existsByEmail(String email);
}

