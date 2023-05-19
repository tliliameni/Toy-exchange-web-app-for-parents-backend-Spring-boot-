package com.example.demo.restcontrollers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.NewUserCount;
import com.example.demo.entities.User;
import com.example.demo.service.DashboardService;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/articles-count")
	public ResponseEntity<Long> getArticlesCount() {
		Long count = dashboardService.getArticlesCount();
		return ResponseEntity.ok(count);
	}
	 @GetMapping("/user-count")
	    public ResponseEntity<Long> getUserCount() {
	        long userCount = dashboardService.getUserCount();
	        return ResponseEntity.ok(userCount);
	    }
	 @GetMapping("/allUsers")
	    public ResponseEntity< List<User>> getAllUsers() {
	   
		 List<User> usernames = dashboardService.getAllUsernames();
		 return ResponseEntity.ok(usernames);
	    }
	 @DeleteMapping("/deleteUser/{id}")
	 public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		 dashboardService.deleteUser(id);
	     return ResponseEntity.noContent().build();
	 }

	@GetMapping("/users-count")
	public ResponseEntity<Long> getUsersCount() {
		Long count = dashboardService.getUsersCount();
		return ResponseEntity.ok(count);
	}

	@GetMapping("/new-users")
	public ResponseEntity<List<NewUserCount>> getNewUsersByDays(@RequestParam(required = false) Integer days) {
		 if (days == null) {
		        days = 7; // default value
		    }
		List<NewUserCount> newUserCounts = dashboardService.getNewUsersByDays(days);
		return ResponseEntity.ok(newUserCounts);
	}
	
}
