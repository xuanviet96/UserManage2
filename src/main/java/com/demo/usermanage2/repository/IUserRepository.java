package com.demo.usermanage2.repository;

import com.demo.usermanage2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface IUserRepository extends JpaRepository<User, Long> {

}
