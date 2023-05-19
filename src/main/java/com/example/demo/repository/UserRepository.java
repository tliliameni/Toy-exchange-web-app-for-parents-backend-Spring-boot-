package com.example.demo.repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.NewUserCount;
import com.example.demo.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  int countByCreatedAtBetween(LocalDate date, LocalDate localDate);
User findByEmail(String email);


  List<NewUserCount> countByCreatedAtBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

}
