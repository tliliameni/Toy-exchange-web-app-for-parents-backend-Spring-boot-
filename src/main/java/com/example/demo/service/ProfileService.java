package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.User;

public interface ProfileService {

	public void addImage(User user,MultipartFile mf) throws IOException ;
	public User updateUser(long id,MultipartFile photo ,String username,String email, String phone)throws IOException;
	String saveImage(MultipartFile mf)throws IOException ;
	byte[]  getImage(long id)throws IOException;
	byte[] getDefaultImage() throws IOException;
}
