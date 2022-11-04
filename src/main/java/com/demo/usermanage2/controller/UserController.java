package com.demo.usermanage2.controller;

import com.demo.usermanage2.entity.User;
import com.demo.usermanage2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(Model model) {

        return viewPage(model, 1, "firstName", "asc");
    }

    @GetMapping("/list/{pageNum}")
    public String viewPage(Model model, @PathVariable(name="pageNum") int pageNum,
                           @RequestParam(value = "sortField", required = false) String sortField,
                           @RequestParam(value = "sortDirection", required = false) String sortDirection ) {
        Map<String, String> myMap = new HashMap<>();

        String[] fields = sortField.split(",");

        String[] directions = sortDirection.split(",");

        for(int i=0; i < fields.length; i++){
            myMap.put(fields[i], directions[i]);
        }

        Page<User> page = userService.listAll(pageNum, myMap);

        List<User> listUsers = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", listUsers);

        return "list";
    }


//    @GetMapping("/list/{pageNum}")
//    public String viewPage(Model model, @PathVariable(name="pageNum") int pageNum,
//                           @RequestParam(value = "sortField", required = false) String sortField,
//                           @RequestParam(value = "sortDirection", required = false) String sortDirection ) {
//
//        Page<User> page = userService.listAll(pageNum, sortField, sortDirection);
//
//        List<User> listUsers = page.getContent();
//
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDirection);
//        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
//
//        model.addAttribute("users", listUsers);
//
//        return "list";
//    }


    @GetMapping("/list/add")
    public String add(Model model) {
        User user = new User();
        System.out.println(user);
        model.addAttribute("user", user);
        return "form";
    }

    @GetMapping("/list/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "form";
    }

    @PostMapping("/list/save")
    public String save(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirect) {
        System.out.println(user);
        System.out.println(result);
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            System.out.println("Has error  !! ");
            return "form";
        }
//        User newUser = userService.save(user);
        redirect.addFlashAttribute("successMessage", "Saved contact successfully!");
        return "redirect:/users/list";
    }

    @GetMapping("/list/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        userService.delete(id);
        redirect.addFlashAttribute("successMessage", "Deleted contact successfully!");
        return "redirect:/users/list";
    }



}