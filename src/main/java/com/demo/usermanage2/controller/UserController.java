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
import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "term", required = false) String term,
                       @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection,
                       @RequestParam(value = "sortField", defaultValue = "id", required = false) String sortField, Model model) {
        if(term == null) {
            term = "";
        }

        return viewPage(model, 1, sortField, sortDirection, term);
    }

    @GetMapping("/list/{pageNum}")
    public String viewPage(Model model, @PathVariable(name="pageNum") int pageNum,
                           @RequestParam(value = "sortField", required = false) String sortField,
                           @RequestParam(value = "sortDirection", required = false) String sortDirection,
                           @RequestParam(value = "term", required = false) String term) {

        Map<String, String> myMap = new LinkedHashMap<>();

        if(term == null){
            term = "";
        }

        String[] fields = sortField.split(",");

        String[] directions = sortDirection.split(",");

        for(int i=0; i < fields.length; i++){
            myMap.put(fields[i], directions[i]);
        }
        String[] fieldsList= new String[]{"id","firstName", "lastName", "email"};
        for (String field : fieldsList) {
            if(!myMap.containsKey(field)){
                 myMap.put(field, "asc");
            }
        }

        Page<User> page = userService.listAll(pageNum, myMap, term);

        List<User> listUsers = page.getContent();

        model.addAttribute("mapDirection", myMap);

        model.addAttribute("term", term);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", listUsers);

        return "list";
    }


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
        User newUser = userService.save(user);
        redirect.addFlashAttribute("successMessage", "Saved contact successfully!");
        return "redirect:/users/list";
    }

    @GetMapping("/list/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        userService.delete(id);
        redirect.addFlashAttribute("successMessage", "Deleted contact successfully!");
        return "redirect:/users/list";
    }
//    @GetMapping("list/search")
//    public List<User> search(){
//        List<User> temp = new ArrayList<>();
//        temp = userService.search("xuan");
//        return temp;
//    }


}