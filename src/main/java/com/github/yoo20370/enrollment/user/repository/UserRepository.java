package com.github.yoo20370.enrollment.user.repository;

import com.github.yoo20370.enrollment.user.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
}
