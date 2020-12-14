package com.vyjsoft.PruebasGraphQLApi.graphql.fetcher;


import com.vyjsoft.PruebasGraphQLApi.model.Movie;
import com.vyjsoft.PruebasGraphQLApi.repository.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllMoviesDataFetcher implements DataFetcher<List<Movie>> {

    @Autowired
    private MovieRepository repository;

    @Override
    public List<Movie> get(DataFetchingEnvironment environment) {
        return repository.findAll();
    }
}
