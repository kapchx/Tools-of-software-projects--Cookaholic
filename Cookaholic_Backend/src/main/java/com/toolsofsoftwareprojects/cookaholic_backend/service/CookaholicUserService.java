package com.toolsofsoftwareprojects.cookaholic_backend.service;

import com.toolsofsoftwareprojects.cookaholic_backend.exceptions.CookaholicUserNotFoundException;
import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import com.toolsofsoftwareprojects.cookaholic_backend.repo.CookaholicUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CookaholicUserService {
    private final CookaholicUserRepo cookaholicUserRepo;

    @Autowired
    public CookaholicUserService(CookaholicUserRepo cookaholicUserRepo) {
        this.cookaholicUserRepo = cookaholicUserRepo;
    }

    public CookaholicUser addCookaholicUser(CookaholicUser cookaholicUser) {
        cookaholicUser.setUserCode(UUID.randomUUID().toString());
        return cookaholicUserRepo.save(cookaholicUser);
    }

    public List<CookaholicUser> findAllCookaholicUsers() {
        return cookaholicUserRepo.findAll();
    }

    public CookaholicUser updateCookaholicUser(CookaholicUser cookaholicUser) {
        return cookaholicUserRepo.save(cookaholicUser);
    }

    public CookaholicUser findCookaholicUserById(Long id) {
        return cookaholicUserRepo.findCookaholicUserById(id)
                .orElseThrow(() -> new CookaholicUserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteCookaholicUser(Long id) {
        cookaholicUserRepo.deleteCookaholicUserById(id);
    }
}
