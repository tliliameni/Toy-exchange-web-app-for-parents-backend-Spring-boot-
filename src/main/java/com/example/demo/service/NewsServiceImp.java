package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Article;
import com.example.demo.entities.News;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.NewsRepository;
@Service
public class NewsServiceImp implements NewsService{
	@Autowired
	NewsRepository et;

	@Override
	public void ajouterNews(News e,MultipartFile mf) throws IOException {
		
		String photo;	
		
		if(!mf.getOriginalFilename().equals("")) {
			photo = saveImage(mf);
		
		}
            et.save(e);
	}

	@Override
	public List<News> getAllNews() {
		
		return et.findAll();
	}

	@Override
	public News getNewsById(int Id) {

		return et.findById(Id).get();
	}

	@Override
	public List<News> getNewsBMC(String motcle) {

		return et.rechercherParMc(motcle);
	}

	@Override
	public void supprimerNews(int Id)throws IOException {

		News news = et.getById(Id);
		//String  chemin = System.getProperty("user.home") + "/images2022/";
		String  chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/";
		//Path p = Paths.get(chemin,article.getPhoto());
		//Files.delete(p);
		et.delete(news);
	}

	@Override
	public void mettreAJourNews(News e) {

		et.save(e);

	}
	
	/**************image****/
	
	@Override
	public String saveImage(MultipartFile mf) throws IOException {
	    String nameFile = mf.getOriginalFilename(); // Get the original filename
	    String tab[] = nameFile.split("\\."); // Split the filename
	    String fileExt = tab[1]; // Get the file extension
	    String fileName = tab[0]; // Get the filename without extension
	    String fileModif = fileName + "_" + System.currentTimeMillis() + "." + fileExt; // Add current time stamp
	    String chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/"; // Set the path to save the file
	    Path p = Paths.get(chemin, nameFile	); // Create the path
	    Files.write(p, mf.getBytes()); // Write the file to disk
	    return fileModif.substring(0, fileModif.lastIndexOf('_')) + "." + fileExt; // Remove the appended time stamp and return the filename with the original extension
	}

	@Override
	public byte[] getImage(int id) throws IOException {
		// TODO Auto-generated method stub
		News news = et.getById(id);
		//String  chemin = System.getProperty("user.home") + "/images2022/";
		String  chemin = System.getProperty("user.dir") + "/src/main/webapp/imagesdata/";
		Path p = Paths.get(chemin,news.getPhoto());
		 return Files.readAllBytes(p);
	}




}
