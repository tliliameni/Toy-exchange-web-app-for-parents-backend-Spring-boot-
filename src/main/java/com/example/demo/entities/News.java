package com.example.demo.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
@Entity @Table
public class News {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "photo")
	private String photo;
	  @Column(name = "created_at")
	  private LocalDate createdAt;
	public News(String title2, String description2, String fileName) {
		super();
		title=title2;
		description=description2;
		photo=fileName;
	}
	public News(int idd,String title2, String description2,String fileName) {
		super();
		id=idd;
		title=title2;
		description=description2;
		photo=fileName;
	}
	 @PrePersist
	  protected void onCreate() {
	      createdAt = LocalDate.now();
	  }
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	
	public News() {
		super();
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	//@Column(name = "name")
	//private String name;
	/*public Article(String title, String name, String description, String exchange, Double price, byte[] picByte) {
		super();
		this.title = title;
		this.name = name;
		this.description = description;
		this.exchange = exchange;
		this.price = price;
		this.picByte = picByte;
	}
*/
	@Column(name = "description",length = 1000)
	private String description;



    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
/*	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}*/
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	public void setDescritption(String description1) {
		description=description1;
		
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
	
	

	public void settitle(String title1) {
		title=title1;
		
	}

	public String gettitle() {
		// TODO Auto-generated method stub
		return title;
	}
}
