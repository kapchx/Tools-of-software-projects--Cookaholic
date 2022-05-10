package com.toolsofsoftwareprojects.cookaholic_backend.controller;

import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import com.toolsofsoftwareprojects.cookaholic_backend.repo.CookaholicUserRepo;
import com.toolsofsoftwareprojects.cookaholic_backend.service.CookaholicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/CookaholicUser")
public class CookaholicUserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CookaholicUserRepo cookaholicUserRepo;
    public final CookaholicUserService cookaholicUserService;

    public CookaholicUserController(CookaholicUserService cookaholicUserService) {
        this.cookaholicUserService = cookaholicUserService;
    }

    //Admin
    @PostMapping("/register/admin")
    public ResponseEntity<CookaholicUser> registerCookaholiAdmin(@RequestBody CookaholicUser cookaholicUser) {
        cookaholicUser.setUserTitle(CookaholicUser.UserTitle.ROLE_ADMIN);
        return register(cookaholicUser);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/find/all")
    public ResponseEntity<List<CookaholicUser>> getAllCookaholicUsers() {
        List<CookaholicUser> CookaholicUsers = cookaholicUserService.findAllCookaholicUsers();
        return new ResponseEntity<>(CookaholicUsers, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/find/{id}")
    public ResponseEntity<CookaholicUser> getCookaholicUserById(@PathVariable("id") Long id) {
        CookaholicUser CookaholicUser = cookaholicUserService.findCookaholicUserById(id);
        return new ResponseEntity<>(CookaholicUser, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateCookaholicUser(@PathVariable("id") Long id) {
        cookaholicUserService.deleteCookaholicUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //User
    @PostMapping("/register/user")
    public ResponseEntity<CookaholicUser> registerCookaholiUser(@RequestBody CookaholicUser cookaholicUser) {
        cookaholicUser.setUserTitle(CookaholicUser.UserTitle.ROLE_USER);
        return register(cookaholicUser);
    }

    //Guest
    @PostMapping("/register/guest")
    public ResponseEntity<CookaholicUser> registerCookaholiGuest(@RequestBody CookaholicUser cookaholicUser) {
        cookaholicUser.setUserTitle(CookaholicUser.UserTitle.ROLE_GUEST);
        return register(cookaholicUser);
    }

    //Shared
    @PutMapping("/update")
    public ResponseEntity<CookaholicUser> updateCookaholicUser(@RequestBody CookaholicUser cookaholicUser) {
        cookaholicUser.setId(authUser().getId());
        CookaholicUser updateCookaholicUser = cookaholicUserService.updateCookaholicUser(cookaholicUser);
        return new ResponseEntity<>(updateCookaholicUser, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<?> updateCookaholicUser() {
        cookaholicUserService.deleteCookaholicUser(authUser().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }










    public CookaholicUser authUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return cookaholicUserService.findCookaholicUserByName(username);
    }

    public List<String> authUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public ResponseEntity<CookaholicUser> register(@RequestBody CookaholicUser cookaholicUser) {
        cookaholicUser.setPassword(passwordEncoder.encode(cookaholicUser.getPassword()));
        CookaholicUser newCookaholicUser = cookaholicUserService.addCookaholicUser(cookaholicUser);
        return new ResponseEntity<>(newCookaholicUser, HttpStatus.CREATED);
    }
}
