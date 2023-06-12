package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
@Service
public class ProfileServiceImp implements ProfileService {
	@Autowired 
	UserRepository us;
	
	public void addImage(User user,MultipartFile mf) throws IOException{
	String photo;

	if (!mf.getOriginalFilename().equals("")) {
		photo = saveImage(mf);

	}
	us.save(user);
}
	
	public User updateUser(long id,MultipartFile photo ,String username,String email, String phone)throws IOException{
		Optional<User> existingUser=us.findById(id);
		if (!existingUser.isPresent()) {
			// If the publication does not exist, return null
			System.out.println("User not found");
			return null;
		}
		User updatedUser = existingUser.get();
		
		// Update the publication fields
		updatedUser.setUsername(updatedUser.getUsername());;
	
		updatedUser.setEmail(updatedUser.getEmail());

		// Update the photos
		if (photo != null) {
		saveImage(photo);
		}
		if(phone != null) {
			updatedUser.setPhoneNumber(updatedUser.getPhoneNumber());
		}
		// Save the updated publication to the database
		return us.save(updatedUser);
	}
	
	@Override
	public String saveImage(MultipartFile mf) throws IOException {
		String nameFile = mf.getOriginalFilename(); // Get the original filename
		String tab[] = nameFile.split("\\."); // Split the filename
		String fileExt = tab[1]; // Get the file extension
		String fileName = tab[0]; // Get the filename without extension
		String fileModif = fileName + "_" + System.currentTimeMillis() + "." + fileExt; // Add current time stamp
		String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/"; // Set the path to save the
																							// file
		Path p = Paths.get(chemin, nameFile); // Create the path
		Files.write(p, mf.getBytes()); // Write the file to disk
		return fileModif.substring(0, fileModif.lastIndexOf('_')) + "." + fileExt; // Remove the appended time stamp and
																					// return the filename with the
																					// original extension
	}
	@Override
	public byte[] getImage(long id) throws IOException {
	    User user = us.getById(id);
	    if (user == null || user.getPhoto() == null || user.getPhoto().isEmpty()) {
	        // Return a default or placeholder image
	        return getDefaultImage();
	    }
	    
	    String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/";
	    Path p = Paths.get(chemin, user.getPhoto());
	    
	    if (Files.exists(p)) {
	        return Files.readAllBytes(p);
	    } else {
	        // Return a default or placeholder image
	        return getDefaultImage();
	    }
	}
	@Override
	public byte[] getDefaultImage() throws IOException {
	    // Read the default image file from the file system or resource
	    String defaultImagePath = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/default.jpg";
	    Path defaultImageFilePath = Paths.get(defaultImagePath);
	    if (Files.exists(defaultImageFilePath)) {
	        return Files.readAllBytes(defaultImageFilePath);
	    } else {
	        // If the default image file doesn't exist, you can use a fallback approach
	        // For example, load the default image from the classpath resources
	        InputStream defaultImageStream = getClass().getClassLoader().getResourceAsStream("default.jpg");
	        return IOUtils.toByteArray(defaultImageStream);
	    }
	}

}
