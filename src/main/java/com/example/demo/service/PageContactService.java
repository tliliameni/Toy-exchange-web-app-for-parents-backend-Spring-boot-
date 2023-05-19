package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.PageContact;


public interface PageContactService {

	public void ajouterPageContact(PageContact e,MultipartFile mf) throws IOException ;
	public PageContact getPageContactById(int Id);
	public void supprimerPageContact(int Id)throws IOException;
	public PageContact mettreAJourPageContact(int id,MultipartFile photo ,String title, String description)throws IOException;
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
	
}