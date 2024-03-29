package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Article;
import com.example.demo.entities.Category;
import com.example.demo.entities.News;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired //injection de dependance consite à eviter la dependance entre deux classes
	CategoryRepository gp;

	@Override
	public void ajouterCategory(Category g, MultipartFile mf) throws IOException {
		String photo;
		if (!mf.getOriginalFilename().equals("")) {
			photo = saveImage(mf);

		}
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
	public Category mettreAJourCategory(int id, MultipartFile photo, String nom) throws IOException {
		// Retrieve the existing publication by its ID
		Optional<Category> existingPublication=gp.findById(id);
		if (!existingPublication.isPresent()) {
			// If the publication does not exist, return null
			System.out.println("Publication not found");
			return null;
		}
		Category updatedPublication = existingPublication.get();
		
		// Update the publication fields
		updatedPublication.setNom(updatedPublication.getNom());;
		

		// Update the photos
		if (photo != null) {
		saveImage(photo);
		}
		// Save the updated publication to the database
		return gp.save(updatedPublication);
	}


	@Override
	public String saveImage(MultipartFile mf) throws IOException {
		String nameFile = mf.getOriginalFilename(); // Get the original filename
		String tab[] = nameFile.split("\\."); // Split the filename
		String fileExt = tab[1]; // Get the file extension
		String fileName = tab[0]; // Get the filename without extension
		String fileModif = fileName + "_" + System.currentTimeMillis() + "." + fileExt; // Add current time stamp
		String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/"; // Set the path to save the
																							// file
		Path p = Paths.get(chemin, nameFile); // Create the path
		Files.write(p, mf.getBytes()); // Write the file to disk
		return fileModif.substring(0, fileModif.lastIndexOf('_')) + "." + fileExt; // Remove the appended time stamp and
																					// return the filename with the
																					// original extension
	}

	@Override
	public byte[] getImage(int id) throws IOException {
		// TODO Auto-generated method stub
		Category cat = gp.getById(id);
		// String chemin = System.getProperty("user.home") + "/images2022/";
		String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/";
		Path p = Paths.get(chemin, cat.getPhoto());
		return Files.readAllBytes(p);
	}

}
