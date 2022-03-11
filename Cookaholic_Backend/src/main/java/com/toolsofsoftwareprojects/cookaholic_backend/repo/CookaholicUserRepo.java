package com.toolsofsoftwareprojects.cookaholic_backend.repo;

import com.toolsofsoftwareprojects.cookaholic_backend.model.CookaholicUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CookaholicUserRepo extends JpaRepository<CookaholicUser, Long> {
    void deleteCookaholicUserById(Long id);

    Optional<CookaholicUser> findCookaholicUserById(Long id);
}