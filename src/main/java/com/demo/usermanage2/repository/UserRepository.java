package com.demo.usermanage2.repository;

import com.demo.usermanage2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findByNameContaining(String term);

}
