package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.AboutUs;
import com.example.demo.entities.PageArticle;

@Repository
public interface BanderoleArticleRepository extends JpaRepository<PageArticle, Integer> {

}
