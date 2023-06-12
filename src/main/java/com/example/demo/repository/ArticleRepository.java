package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Article;
import com.example.demo.entities.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
		
		@Query("select a from Article a  where a.title like %:x%")
		List<Article> rechercherParMc(@Param("x") String mc);
		 @Query("select a from Article a join a.category c where c.nom = :categoryName")
		    List<Article> searchByTitleAndCategoryName( @Param("categoryName") String categoryName);

		 List<Article> findByUser(User user);
		 List<Article> findByPriceAndExchange(String price, String exchange);
		 List<Article> getArticlesByUserId(Long userId);
	}