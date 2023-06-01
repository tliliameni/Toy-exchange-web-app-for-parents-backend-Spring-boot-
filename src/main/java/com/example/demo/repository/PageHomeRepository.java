package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.HomePage;

@Repository
public interface PageHomeRepository extends JpaRepository<HomePage, Integer>{

}
