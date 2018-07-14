package com.thewangzl.clientresttemplate.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ClientUser, Long> {

	Optional<ClientUser> findByUsername(String username);
}
