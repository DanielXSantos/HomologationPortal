package com.ctl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctl.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByDeletedFalseAndEmailIgnoreCase(String email);
	 User findByDeletedTrueAndEmailIgnoreCase(String email);
	 List findAllByDeletedFalse();
	 User findByDeletedFalseAndId(Long id);
}
