package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Article;

public interface ArticleService {

	public void ajouterArticle(Article e,MultipartFile mf) throws IOException ;
	public List<Article> getAllArticles();
	public Article getArticleById(int Id);
	public List<Article> getArticleBMC(String motcle);
	public void supprimerArticle(int Id)throws IOException;
	public void mettreAJourArticle(Article e);
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
}
