package com.example.demo.restcontrollers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

import com.example.demo.entities.Article;
import com.example.demo.entities.News;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.ArticleServiceImp;
import com.example.demo.service.NewsServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("News")

public class NewsController {
	@Autowired
     NewsRepository n;
	@Autowired
    NewsServiceImp s;
	@GetMapping("/all")
	public List<News> getAllNews() {
		
		return s.getAllNews();
		
	}
	
	@GetMapping("/rechercheParMc/{mc}")
	public List<News> getAllNewsByMc(@PathVariable("mc") String mot) {
	
		return s.getNewsBMC(mot);	
	}
	
	@GetMapping("getById/{id}")
	public News getNews(@PathVariable("id") int id) {
		return s.getNewsById(id);
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
	   News a=new News(title,description,fileName);
	   s.ajouterNews(a, file);
	   n.save(a);
	   CreateArticleResponse response = new CreateArticleResponse();
	    response.setMessage("News created successfully!");
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	    } catch (Exception e) {
	      e.printStackTrace();
	      CreateArticleResponse errorResponse = new CreateArticleResponse();
	      errorResponse.setMessage("Failed to create article.");
	      // set any other properties as needed
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	  }
	  }

		@DeleteMapping("/delete/{id}")
		public void deleteNews(@PathVariable  int id) throws IOException 
		{
			s.supprimerNews(id);
		}
		
		
		@PutMapping("/update/{id}")
		public ResponseEntity<UpdateArticleResponse> updateNews(@PathVariable int id,
		                                                         @RequestParam(value="file") MultipartFile file,
		                                                         @RequestParam("title") String title,
		                                                         @RequestParam("description") String description) {
		    try {
		        // Get the article by id
		        Optional<News> optionalNews = n.findById(id);
		        if (optionalNews.isEmpty()) {
		            // Return 404 Not Found if the article is not found
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		        }

		        News news = optionalNews.get();

		        // Update the fields of the article
		        news.settitle(title);
		        news.setDescritption(description);

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
		        news.setPhoto(fileName);

		        // Save the updated article to the database
		        s.ajouterNews(news, file);
		        n.save(news);

		        UpdateArticleResponse response = new UpdateArticleResponse();
		        response.setMessage("News updated successfully!");
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


