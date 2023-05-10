package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.AboutUs;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Integer> {
		

		

	}