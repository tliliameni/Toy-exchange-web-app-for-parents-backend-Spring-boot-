package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.AboutUs;
import com.example.demo.entities.Article;

public interface AboutUsService {

	public void ajouterArticle(AboutUs e,MultipartFile mf) throws IOException ;
	public List<AboutUs> getAllArticles();
	public AboutUs getArticleById(int Id);
	public List<AboutUs> getArticleBMC(String motcle);
	public void supprimerArticle(int Id)throws IOException;
	public void mettreAJourArticle(AboutUs e);
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
}
