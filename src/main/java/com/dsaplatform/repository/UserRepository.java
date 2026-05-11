package com.dsaplatform.repository;

import com.dsaplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Ye line Spring Boot ko batati hai ki email se user kaise dhoondhna hai
    Optional<User> findByEmail(String email);
}
