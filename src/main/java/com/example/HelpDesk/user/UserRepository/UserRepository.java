package com.example.HelpDesk.user.UserRepository;

import com.example.HelpDesk.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(String role);
    List<User> findByDepartmentId(Long id);
}
