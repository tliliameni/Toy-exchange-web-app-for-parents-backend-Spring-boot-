package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Category;


public interface CategoryService {

	public void ajouterCategory(Category g);
	public List<Category> getAllCategories();
	public Category getCategoryById(int Id);
	public List<Category> getCategoryBMC(String motcle);
	public void supprimerCategory(int Id);
	public void mettreAJourCategory(Category g);
}