package com.demo.usermanage2.repository;

import com.demo.usermanage2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface IUserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstNameContaining(String firstname);
    List<User> findByLastName(String lastname);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.email LIKE %?1% OR convert(u.age , char(50)) LIKE %?1%")
    Page<User> search(String term, Pageable pageable);

    Page<User> findByFirstNameContainingOrLastNameContainingOrEmailContaining(String s1, String s2, String s3, Pageable pageable);

}
