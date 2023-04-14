package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
		
		@Query("select a from Article a  where a.title like %:x%")
		List<Article> rechercherParMc(@Param("x") String mc);

		

	}