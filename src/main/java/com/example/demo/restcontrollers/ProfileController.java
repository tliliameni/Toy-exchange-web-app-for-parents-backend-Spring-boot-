package com.example.demo.restcontrollers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProfileServiceImp;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("Profile")
public class ProfileController {
	@Autowired
	UserRepository u;
	@Autowired
	ProfileServiceImp p;
	@GetMapping(path="/getImage/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.ALL_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] getImage(@PathVariable("id") int id) throws IOException {
		if(p.getImage(id)==null) {
			return p.getDefaultImage();
		}
	    return p.getImage(id);
	}
	@GetMapping(path="/phone/{id}")
	public String getPhoneNumber(@PathVariable("id") long id) {
	    Optional<User> us=u.findById(id);
	    User user = us.get();
	    return user.getPhoneNumber();
	}
	@GetMapping(path="/user/{id}")
	public User getUser(@PathVariable("id") long id) {
	    Optional<User> us=u.findById(id);
	    User user = us.get();
	    return user;
	}
	public class UpdateArticleResponse {
		  private String message;

		  public String getMessage() {
		    return message;
		  }

		  public void setMessage(String message) {
		    this.message = message;
		  }
		}
	@PutMapping("/update/{id}")
	public ResponseEntity<UpdateArticleResponse> updatePageContact(@PathVariable long id,
	        @RequestParam(value = "file", required=false) MultipartFile file,
	        @RequestParam("username") String username,
	        @RequestParam("email") String email,
	        @RequestParam (value = "phone", required=false) String phone) {
	    try {
	        // Get the article by id
	        Optional<User> optionalUser = u.findById(id);
	        if (optionalUser.isEmpty()) {
	            // Return 404 Not Found if the article is not found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }

	        User user = optionalUser.get();

	        // Update the fields of the article
	        user.setUsername(username);;
			
			user.setEmail(email);

	        // Save the updated image file to the server's file system
	        String fileName = user.getPhoto();
	        if (file != null) {
	            fileName = StringUtils.cleanPath(file.getOriginalFilename());
	            Path uploadPath = Paths.get("uploads");
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }
	            try (InputStream inputStream = file.getInputStream()) {
	                Path filePath = uploadPath.resolve(fileName);
	                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	            }
	            user.setPhoto(fileName);
	            p.saveImage(file);
	        }
	        if (phone != null) {
	        	user.setPhoneNumber(phone);
	        }

	        // Save the updated article to the database

	        u.save(user);

	        UpdateArticleResponse response = new UpdateArticleResponse();
	        response.setMessage("Profile updated successfully!");
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        UpdateArticleResponse errorResponse = new UpdateArticleResponse();
	        errorResponse.setMessage("Failed to update Profile.");
	        // set any other properties as needed
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}

}
