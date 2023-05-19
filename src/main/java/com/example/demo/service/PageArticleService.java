package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.PageArticle;


public interface PageArticleService {

	public void ajouterPageArticle(PageArticle e,MultipartFile mf) throws IOException ;
	public PageArticle getPageArticleById(int Id);
	public void supprimerPageArticle(int Id)throws IOException;
	public PageArticle mettreAJourPageArticle(int id,MultipartFile photo ,String title, String description)throws IOException;
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
}
