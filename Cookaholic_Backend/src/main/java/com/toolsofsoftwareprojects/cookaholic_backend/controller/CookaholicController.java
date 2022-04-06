package com.toolsofsoftwareprojects.cookaholic_backend.controller;



import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import com.toolsofsoftwareprojects.cookaholic_backend.service.CookaholicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CookaholicUser")
public class CookaholicController {

    public final CookaholicUserService cookaholicUserService;

    public CookaholicController(CookaholicUserService cookaholicUserService) {
        this.cookaholicUserService = cookaholicUserService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<CookaholicUser>> getAllCookaholicUsers() {
        List<CookaholicUser> CookaholicUsers = cookaholicUserService.findAllCookaholicUsers();
        return new ResponseEntity<>(CookaholicUsers, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CookaholicUser> getCookaholicUserById(@PathVariable("id") Long id) {
        CookaholicUser CookaholicUser = cookaholicUserService.findCookaholicUserById(id);
        return new ResponseEntity<>(CookaholicUser, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CookaholicUser> addCookaholicUser(@RequestBody CookaholicUser CookaholicUser) {
        com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser newCookaholicUser = cookaholicUserService.addCookaholicUser(CookaholicUser);
        return new ResponseEntity<>(newCookaholicUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CookaholicUser> updateCookaholicUser(@RequestBody CookaholicUser CookaholicUser) {
        CookaholicUser updateCookaholicUser = cookaholicUserService.updateCookaholicUser(CookaholicUser);
        return new ResponseEntity<>(updateCookaholicUser, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateCookaholicUser(@PathVariable("id") Long id) {
        cookaholicUserService.deleteCookaholicUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
