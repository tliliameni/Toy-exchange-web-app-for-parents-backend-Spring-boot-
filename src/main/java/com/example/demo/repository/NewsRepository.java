package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
	
	@Query("select a from News a  where a.title like %:x%")
	List<News> rechercherParMc(@Param("x") String mc);

	


}
