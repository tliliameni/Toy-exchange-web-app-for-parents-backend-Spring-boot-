package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.PageNews;

public interface PageNewsService {

	public void ajouterPageNews(PageNews e,MultipartFile mf) throws IOException ;
	public PageNews getPageNewsById(int Id);
	public void supprimerPageNews(int Id)throws IOException;
	public PageNews mettreAJourPageNews(int id,MultipartFile photo ,String title, String description)throws IOException;
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
}