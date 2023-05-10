package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Article;
import com.example.demo.entities.BanderoleArticle;
import com.example.demo.entities.BanderoleNews;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BanderoleArticleRepository;
import com.example.demo.repository.BanderoleNewsRepository;

@Service
public class BanderoleService {
	@Autowired
	BanderoleNewsRepository news;
	@Autowired
	BanderoleArticleRepository art;

	public void ajouterBanderoleNews(BanderoleNews e, MultipartFile mf) throws IOException {

		String photo;

		if (!mf.getOriginalFilename().equals("")) {
			photo = saveImage(mf);

		}
		news.save(e);
	}

	public void ajouterBanderoleArticle(BanderoleArticle e, MultipartFile mf) throws IOException {

		String photo;

		if (!mf.getOriginalFilename().equals("")) {
			photo = saveImage(mf);

		}
		art.save(e);
	}

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
	}

	public byte[] getImageArticle(int id) throws IOException {
		BanderoleArticle article = art.getById(id);
		// String chemin = System.getProperty("user.home") + "/images2022/";
		String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/";
		Path p = Paths.get(chemin, article.getPhoto());
		return Files.readAllBytes(p);
	}
	public byte[] getImageNews(int id) throws IOException {
		BanderoleNews article = news.getById(id);
		// String chemin = System.getProperty("user.home") + "/images2022/";
		String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/";
		Path p = Paths.get(chemin, article.getPhoto());
		return Files.readAllBytes(p);
	}
	

}
