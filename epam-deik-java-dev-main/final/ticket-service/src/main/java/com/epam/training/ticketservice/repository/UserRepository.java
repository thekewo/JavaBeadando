package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User getById(Integer id);
}
