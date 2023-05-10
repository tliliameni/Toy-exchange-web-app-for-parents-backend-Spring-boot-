package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Article;
import com.example.demo.entities.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired //injection de dependance consite à eviter la dependance entre deux classes
	CategoryRepository gp;

	@Override
	public void ajouterCategory(Category g) {

		gp.save(g);
	}

	@Override
	public List<Category> getAllCategories() {

		return gp.findAll();
	}

	@Override
	public Category getCategoryById(int id) {

		return gp.findById(id).get();
	}

	@Override
	public List<Category> getCategoryBMC(String motcle) {

		return gp.rechercherParMc(motcle);
	}

	@Override
	public void supprimerCategory(int Id) {
		
		gp.deleteById(Id);
		
	}
	public void supprimerCategoryy(int Id)throws IOException {

		Category category = gp.getById(Id);
		gp.delete(category);
	}	
	@Override
	public void mettreAJourCategory(Category g) {
		
		gp.save(g);
		
	}

}
