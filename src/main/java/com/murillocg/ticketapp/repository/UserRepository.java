package com.murillocg.ticketapp.repository;

import com.murillocg.ticketapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByLogin(String login);

}
