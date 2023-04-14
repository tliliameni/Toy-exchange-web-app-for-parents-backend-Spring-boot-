package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entities.News;

public interface NewsService {
	public void ajouterNews(News e,MultipartFile mf) throws IOException ;
	public List<News> getAllNews();
	public News getNewsById(int Id);
	public List<News> getNewsBMC(String motcle);
	public void supprimerNews(int Id)throws IOException;
	public void mettreAJourNews(News e);
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
	
}