package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class PageContact {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "photo")
	private String photo;
	private String title;
	private String description;
	
 




	public PageContact(int id, String photo,String title,String description) {
		super();
		this.id = id;
		this.photo = photo;
		this.title=title;
		this.description=description;
	
	}
    
   



	public PageContact() {
		super();
	}
	public PageContact(String title2, String description2, String fileName) {
	title=title2;
	description=description2;
	photo=fileName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String gettitle() {
		// TODO Auto-generated method stub
		return title;
	}
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
	public void settitle(String title2) {
		// TODO Auto-generated method stub
		 title=title2;
	}
	public void setDescription(String description2) {
		// TODO Auto-generated method stub
		description=description2;
	}
}