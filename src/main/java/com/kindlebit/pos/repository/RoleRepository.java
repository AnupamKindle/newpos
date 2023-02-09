package com.kindlebit.pos.repository;

import java.util.Optional;

import com.kindlebit.pos.models.ERole;
import com.kindlebit.pos.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
