package com.example.demo.restcontrollers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Article;
import com.example.demo.entities.Category;
import com.example.demo.entities.News;
import com.example.demo.entities.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.restcontrollers.NewsController.CreateArticleResponse;
import com.example.demo.restcontrollers.NewsController.UpdateArticleResponse;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ArticleServiceImp;
import com.example.demo.service.DashboardService;
import com.example.demo.service.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("Articles")
public class ArticleController {
	@Autowired
	ArticleServiceImp se;
	@Autowired
	UserRepository us;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private ArticleRepository ArticleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
	@GetMapping("/all")
	public List<Article> getAllArticles() {
	    List<Article> articles = se.getAllArticles();
	    for (Article article : articles) {
	        User user = userRepository.findById(article.getUser().getId()).orElse(null);
	        article.setUser(user);
	    }
	    return articles;
	}
	@GetMapping("/articles-count")
	public ResponseEntity<Long> getArticlesCount() {
		Long count = dashboardService.getArticlesCount();
		return ResponseEntity.ok(count);
	}

	@GetMapping("/search")
	public List<Article> searchByCategoryName( @RequestParam(value="categoryName")String categoryName) {
	    return se.searchByTitleAndCategoryName(categoryName);
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
	public Article getNews(@PathVariable("id") int id) {
		return se.getArticleById(id);
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

	@GetMapping("/rechercheParMc/{mc}")
	public List<Article> getAllArticlesByMc(@PathVariable("mc") String mot) {

		return se.getArticleBMC(mot);
	}
	 @GetMapping("/users/{userId}/articles")
	    public List<Article> getUserArticles(@PathVariable Long userId) {
	       User user= us.getById(userId);
	        return ArticleRepository.findByUser(user);
	    }
	@GetMapping(path = "/getImage/{id}", produces = MediaType.ALL_VALUE)
	public byte[] getImage(@PathVariable("id") int id) throws IOException {
		return se.getImage(id);
	}

	@PostMapping("/create")
	public ResponseEntity<CreateArticleResponse> createItem(
	        @RequestParam("image") MultipartFile image,
	        @RequestParam("title") String title,
	        @RequestParam("description") String description,
	        @RequestParam("exchange") String exchange,
	        @RequestParam("price") String price,
	        @RequestParam("categoryId") Integer categoryId,
	        @RequestParam("userId") Long userId) {

		   try {
		        // Save the image file to the server's file system
		        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		        Path uploadPath = Paths.get("uploads");
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);
		        }
		        try (InputStream inputStream = image.getInputStream()) {
		            Path filePath = uploadPath.resolve(fileName);
		            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		        }
	        // Get the Category instance from the database
	        Category category = categoryRepository.getById(categoryId);

	        // Get the User instance from the database
	        User user = userRepository.getById(userId);

	        // Create a new Article entity and save it to the database
	        Article article = new Article(title, description, exchange, price, fileName);
	     
	        article.setCategory(category);
	        article.setUser(user);
	        se.ajouterArticle(article, image);
	        ArticleRepository.save(article);

	        CreateArticleResponse response = new CreateArticleResponse();
	        response.setMessage("Article created successfully!");
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }catch (Exception e) {
	        e.printStackTrace();
	        CreateArticleResponse errorResponse = new CreateArticleResponse();
	        errorResponse.setMessage("Failed to create article.");
	        // set any other properties as needed
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	


	@DeleteMapping("/delete/{id}")
	public void deleteNews(@PathVariable int id) throws IOException {
		se.supprimerArticle(id);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<UpdateArticleResponse> updateNews(@PathVariable int id,
			@RequestParam(value="file",required=false) MultipartFile file, @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("exchange") String exchange,
			@RequestParam("price") String price, @RequestParam("categoryId") Integer categoryId) {
		try {
// Get the article by id
			Optional<Article> optionalArticle = ArticleRepository.findById(id);
			if (optionalArticle.isEmpty()) {
// Return 404 Not Found if the article is not found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			Article a = optionalArticle.get();
			Category cat=categoryRepository.getById(categoryId);
// Update the fields of the article
			a.settitle(title);
			a.setDescritption(description);
			a.setExchange(exchange);
			a.setPrice(price);
            a.setCategory(cat);
// Save the updated image file to the server's file system
            String fileName = a.getPhoto();
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
	            a.setPhoto(fileName);
	            se.saveImage(file);
	        }
	      
			ArticleRepository.save(a);

			UpdateArticleResponse response = new UpdateArticleResponse();
			response.setMessage("Artical updated successfully!");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			UpdateArticleResponse errorResponse = new UpdateArticleResponse();
			errorResponse.setMessage("Failed to update article.");
// set any other properties as needed
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
	
	@GetMapping("/recommendations")
	public List<Article> getArticlesRecommendations(@RequestParam Long userId) {
	    List<Article> articles = ArticleRepository.getArticlesByUserId(userId); 

	    List<Article> recommendations = new ArrayList<>();

	    for (Article article : articles) {
	        if (article.getExchange().equalsIgnoreCase("yes")) {
	            List<Article> recommendedArticles = ArticleRepository.findByPriceAndExchange(
	                    article.getPrice(), "yes");
	            recommendedArticles.remove(article); 
	            recommendations.addAll(recommendedArticles);
	        }
	    }

	    return recommendations;
	}

}
