package com.vyjsoft.PruebasGraphQLApi.repository;

import com.vyjsoft.PruebasGraphQLApi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
}
