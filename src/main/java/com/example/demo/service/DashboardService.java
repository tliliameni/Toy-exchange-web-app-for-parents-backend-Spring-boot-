package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.example.demo.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.NewUserCount;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;

@Service
public class DashboardService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private RoleRepository userRoleRepository;
	private NewsRepository newsRepository;

	@Autowired
	private UserRepository userRepository;

	public long getArticlesCount() {
		return articleRepository.count();
	}

	public long getNewssCount() {
		return newsRepository.count();
	}

	public long getUsersCount() {
		return userRepository.count();
	}
	public long getUserCount() {
        return userRepository.count();
    }
	public List<NewUserCount> getNewUsersByDays(int days) {
		LocalDate today = LocalDate.now();
		LocalDate fromDate = today.minusDays(days);

		List<NewUserCount> newUserCounts = new ArrayList<>();

		for (LocalDate date = fromDate; date.isBefore(today); date = date.plusDays(1)) {
			int count = userRepository.countByCreatedAtBetween(date, date.plusDays(1));
			newUserCounts.add(new NewUserCount(date, count));
		}

		return newUserCounts;
	}
	 public List<User> getAllUsernames() {
	        List<User> users = userRepository.findAll();
	        List<User> usernames = new ArrayList<>();
	        for (User user : users) {
	            usernames.add(user);
	        }
	        return usernames;
	    }
	 @Transactional
	 public void deleteUser(Long userId) {
	     Optional<User> user = userRepository.findById(userId);
	     if (user.isPresent()) {
	         userRepository.deleteById(userId);
	     } else {
	         throw new NotFoundException("User not found with id: " + userId);
	     }
	 }


	
	  public class NotFoundException extends RuntimeException {
		    public NotFoundException(String message) {
		        super(message);
		    }
		}

}
