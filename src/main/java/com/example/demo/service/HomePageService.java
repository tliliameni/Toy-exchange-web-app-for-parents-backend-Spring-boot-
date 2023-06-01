package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.HomePage;

public interface HomePageService {
	public void ajouterHomePage(HomePage e,MultipartFile mf) throws IOException ;
	public HomePage getHomePageById(int Id);
	public List<HomePage> getAll();
	public void supprimerHomePage(int Id)throws IOException;
	public HomePage mettreAJourHomePage(int id,MultipartFile photo ,String title,String subtitle,String description)throws IOException;
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;

}
