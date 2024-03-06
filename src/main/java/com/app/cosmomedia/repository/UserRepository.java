package com.app.cosmomedia.repository;

import com.app.cosmomedia.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);

    Page<Users> findByDeletedAtNullOrderByCreatedAtDesc(Pageable pageable);

    Optional<Users> findByCIN(String CIN);

    Page<Users> findByDeletedAtNotNullOrderByDeletedAtDesc(Pageable pageable);

    Page<Users> findAll(Specification<Users> specification, Pageable pageable);
}
