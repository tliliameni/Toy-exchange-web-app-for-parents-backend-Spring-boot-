package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.PageMentionLegal;

public interface MentionLegalService {

	public void ajouterPageMentionLegal(PageMentionLegal e,MultipartFile mf) throws IOException ;
	public PageMentionLegal getPageMentionLegalById(int Id);
	public void supprimerPageMentionLegal(int Id)throws IOException;
	public PageMentionLegal mettreAJourPageMentionLegal(int id,MultipartFile photo ,String title, String description)throws IOException;
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(int id)throws IOException;
}
