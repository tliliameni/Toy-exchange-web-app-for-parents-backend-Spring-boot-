package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query("select a from Category a  where a.nom like %:x%")
	List<Category> rechercherParMc(@Param("x") String mc);
	
	@Query("SELECT c FROM Category c WHERE c.nom = :nom")
	Category findByNom(@Param("nom") String nom);
	
	

}
