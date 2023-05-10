package com.example.demo.restcontrollers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.AboutUs;
import com.example.demo.entities.Article;
import com.example.demo.entities.News;
import com.example.demo.repository.AboutUsRepository;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.restcontrollers.ArticleController.CreateArticleResponse;
import com.example.demo.restcontrollers.ArticleController.UpdateArticleResponse;
import com.example.demo.service.AbouUsServiceIMP;
import com.example.demo.service.ArticleServiceImp;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("AboutUs")
public class AboutUsController {
	@Autowired
	private AbouUsServiceIMP ab;
	@Autowired
	private AboutUsRepository ArticleRepository;

	public class UpdateArticleResponse {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	public class CreateArticleResponse {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	@GetMapping("getById/{id}")
	public AboutUs getById(@PathVariable("id") int id) {
		return ArticleRepository.getById(id);
	}
	@GetMapping(path = "/getImage/{id}", produces = MediaType.ALL_VALUE)
	public byte[] getImage(@PathVariable("id") int id) throws IOException {
		return ab.getImage(id);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteNews(@PathVariable int id) throws IOException {
		ArticleRepository.deleteById(id);
	}
	  @PostMapping("/create")
	  public ResponseEntity<CreateArticleResponse> createItem(@RequestParam("image") MultipartFile file,
	                                           @RequestParam("title") String title,
	                                           @RequestParam("description") String description
	                                        ) {
	    try {
	      // Save the image file to the server's file system
	      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	      Path uploadPath = Paths.get("uploads");
	      if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	      }
	      try (InputStream inputStream = file.getInputStream()) {
	        Path filePath = uploadPath.resolve(fileName);
	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	      }
	      
	      // Create a new Item entity and save it to the database
	   AboutUs a=new AboutUs(title,description,fileName);
	   ab.ajouterArticle(a, file);
	   ArticleRepository.save(a);
	   CreateArticleResponse response = new CreateArticleResponse();
	    response.setMessage("about us created successfully!");
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	    } catch (Exception e) {
	      e.printStackTrace();
	      CreateArticleResponse errorResponse = new CreateArticleResponse();
	      errorResponse.setMessage("Failed to create about us.");
	      // set any other properties as needed
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	  }
	  }
	@PutMapping("/update/{id}")
	public ResponseEntity<UpdateArticleResponse> updateNews(@PathVariable int id,
			@RequestParam(value="file",required=false) MultipartFile file, @RequestParam("title") String title,
			@RequestParam("description") String description){
		try {
// Get the article by id
			Optional<AboutUs> optionalArticle = ArticleRepository.findById(id);
			if (optionalArticle.isEmpty()) {
// Return 404 Not Found if the article is not found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

AboutUs a = optionalArticle.get();
		
// Update the fields of the article
			a.settitle(title);
			a.setDescritption(description);
// Save the updated image file to the server's file system
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path uploadPath = Paths.get("uploads");
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}
			a.setPhoto(fileName);

// Save the updated article to the database
			ab.ajouterArticle(a, file);
			ArticleRepository.save(a);

			UpdateArticleResponse response = new UpdateArticleResponse();
			response.setMessage("About us updated successfully!");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			UpdateArticleResponse errorResponse = new UpdateArticleResponse();
			errorResponse.setMessage("Failed to update article.");
// set any other properties as needed
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
}