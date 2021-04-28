package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Screening;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends CrudRepository<Screening, String> {
}
