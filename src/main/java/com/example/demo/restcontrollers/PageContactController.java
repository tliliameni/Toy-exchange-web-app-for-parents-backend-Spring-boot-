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

import com.example.demo.entities.News;
import com.example.demo.entities.PageContact;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.PageContactRepository;
import com.example.demo.restcontrollers.NewsController.CreateArticleResponse;
import com.example.demo.restcontrollers.NewsController.UpdateArticleResponse;
import com.example.demo.service.DashboardService;
import com.example.demo.service.NewsServiceImp;
import com.example.demo.service.PageContactServiceImp;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("ContactPage")
public class PageContactController {
	@Autowired
    PageContactRepository n;
	@Autowired
   PageContactServiceImp s;
	@GetMapping("getById/{id}")
	public PageContact getPageContact(@PathVariable("id") int id) {
		return s.getPageContactById(id);
	}
	@GetMapping(path="/getImage/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.ALL_VALUE, MediaType.IMAGE_PNG_VALUE })
	public byte[] getImage(@PathVariable("id") int id) throws IOException {
	    return s.getImage(id);
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
	public class UpdateArticleResponse {
		  private String message;

		  public String getMessage() {
		    return message;
		  }

		  public void setMessage(String message) {
		    this.message = message;
		  }
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
	      PageContact a=new PageContact(title,description,fileName);
	   s.ajouterPageContact(a, file);
	   n.save(a);
	   CreateArticleResponse response = new CreateArticleResponse();
	    response.setMessage("PageContact created successfully!");
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	    } catch (Exception e) {
	      e.printStackTrace();
	      CreateArticleResponse errorResponse = new CreateArticleResponse();
	      errorResponse.setMessage("Failed to create PageContact.");
	      // set any other properties as needed
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	  }
	  }

		@DeleteMapping("/delete/{id}")
		public void deleteNews(@PathVariable  int id) throws IOException 
		{
			s.supprimerPageContact(id);
		}
		
		
		@PutMapping("/update/{id}")
		public ResponseEntity<UpdateArticleResponse> updatePageContact(@PathVariable int id,
		        @RequestParam(value = "file", required=false) MultipartFile file,
		        @RequestParam("title") String title,
		        @RequestParam("description") String description) {
		    try {
		        // Get the article by id
		        Optional<PageContact> optionalPageContact = n.findById(id);
		        if (optionalPageContact.isEmpty()) {
		            // Return 404 Not Found if the article is not found
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		        }

		        PageContact PageContact = optionalPageContact.get();

		        // Update the fields of the article
		        PageContact.settitle(title);
		        PageContact.setDescription(description);

		        // Save the updated image file to the server's file system
		        String fileName = PageContact.getPhoto();
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
		            PageContact.setPhoto(fileName);
		            s.saveImage(file);
		        }
		      

		        // Save the updated article to the database

		        n.save(PageContact);

		        UpdateArticleResponse response = new UpdateArticleResponse();
		        response.setMessage("PageContact updated successfully!");
		        return ResponseEntity.status(HttpStatus.OK).body(response);
		    } catch (Exception e) {
		        e.printStackTrace();
		        UpdateArticleResponse errorResponse = new UpdateArticleResponse();
		        errorResponse.setMessage("Failed to update PageContact.");
		        // set any other properties as needed
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		    }
		}


}


