package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Category;


public interface CategoryService {

	public void ajouterCategory(Category g,MultipartFile mf) throws IOException;
	public List<Category> getAllCategories();
	public Category getCategoryById(int Id);
	public List<Category> getCategoryBMC(String motcle);
	public void supprimerCategory(int Id);
	public Category mettreAJourCategory(int id, MultipartFile photo, String nom) throws IOException;
	public String saveImage(MultipartFile mf) throws IOException;
	public byte[] getImage(int id) throws IOException;
}