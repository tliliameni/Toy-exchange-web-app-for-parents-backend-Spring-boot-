package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HomePage {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "photo")
	private String photo;
	private String title;
	private String description;
	private String subtitle;
	
	public HomePage() {
		super();
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public HomePage( String title, String subtitle,String description,String photo) {
		super();
		this.photo = photo;
		this.title = title;
		this.description = description;
		this.subtitle = subtitle;
	}
	public HomePage(int id,String title, String subtitle,String description,String photo) {
		super();
		this.id=id;
		this.photo = photo;
		this.title = title;
		this.description = description;
		this.subtitle = subtitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
}