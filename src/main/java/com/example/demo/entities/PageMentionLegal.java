package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class PageMentionLegal{
@Id
@Column(name = "id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@Column(name = "photo")
private String photo;
private String title;
private String description;


public PageMentionLegal(int id, String photo,String title,String description) {
	super();
	this.id = id;
	this.photo = photo;
	this.title=title;
	this.description=description;

}
public PageMentionLegal() {
	super();
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

public PageMentionLegal(String title, String description,String photo) {
	super();
	this.photo = photo;
	this.title = title;
	this.description = description;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public void setTitle(String title) {
	this.title = title;
}
public String getTitle() {
	return title;
}
}