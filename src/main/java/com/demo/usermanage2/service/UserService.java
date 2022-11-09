package com.demo.usermanage2.service;

import com.demo.usermanage2.entity.User;
import com.demo.usermanage2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public Page<User> listAll(int pageNum, Map<String, String> myMap, String term){

        List<Sort.Order> orders = new ArrayList<>();

        for (String field: myMap.keySet()) {
            String direction = myMap.get(field);
            Sort.Order item = direction.equals("asc") ? Sort.Order.asc(field) : Sort.Order.desc(field);
            orders.add(item);
        }

        Pageable pageable = PageRequest.of(pageNum -1, 3,
                Sort.by(orders));

//        return userRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(term, term, term, pageable);
        return userRepository.search(term, pageable);
    }

//    public Page<User> listAll(int pageNum, Map<String, String> myMap){
//
//        List<Sort.Order> orders = new ArrayList<>();
//
//        for (String field: myMap.keySet()) {
//            String direction = myMap.get(field);
//            Sort.Order item = direction.equals("asc") ? Sort.Order.asc(field) : Sort.Order.desc(field);
//                orders.add(item);
//        }
//
//        Pageable pageable = PageRequest.of(pageNum -1, 5,
//                Sort.by(orders));
//
//        return userRepository.findAll(pageable);
//    }

    public User findOne(Long id) {
        return userRepository.findById(id).get();
    }



    public User save(User user) {
        return  userRepository.save(user);
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
//    public List<User> findAllContaining(String s1, String s2, String s3){
//        if(s1 != null){
//            return userRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(s1, s2, s3);
//        }
//        return userRepository.findAll();
//    }
//    public List<User> search(String keyword){
//        if(keyword != null){
//            return userRepository.search(keyword);
//        }
//        return userRepository.findAll();
//    }





}
