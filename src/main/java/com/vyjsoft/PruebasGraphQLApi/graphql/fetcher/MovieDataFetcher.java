package com.vyjsoft.PruebasGraphQLApi.graphql.fetcher;

import com.vyjsoft.PruebasGraphQLApi.model.Movie;
import com.vyjsoft.PruebasGraphQLApi.repository.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieDataFetcher implements DataFetcher<Movie> {

    @Autowired
    private MovieRepository repository;


    @Override
    public Movie get(DataFetchingEnvironment environment) {
        String id = environment.getArgument("id");
        return repository.findById(id).orElse(null);
    }
}
