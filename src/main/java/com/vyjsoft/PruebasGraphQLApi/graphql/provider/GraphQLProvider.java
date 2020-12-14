package com.vyjsoft.PruebasGraphQLApi.graphql.provider;

import com.vyjsoft.PruebasGraphQLApi.graphql.fetcher.AllMoviesDataFetcher;
import com.vyjsoft.PruebasGraphQLApi.graphql.fetcher.MovieDataFetcher;
import com.vyjsoft.PruebasGraphQLApi.model.Movie;
import com.vyjsoft.PruebasGraphQLApi.repository.MovieRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class GraphQLProvider {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private AllMoviesDataFetcher allMoviesDataFetcher;

    @Autowired
    private MovieDataFetcher movieDataFetcher;

    @Value("classpath:schema.graphql")
    private Resource resource;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        File file = resource.getFile();
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        loadDataIntoHSQL();
    }

    private void loadDataIntoHSQL() {

        Stream.of(Movie.builder().id("mov1001").title("Guason")
                    .actors(new String[]{"Joaquin Phoenix", "Robert De Niro"})
                    .directors(new String[]{"Todd Phillips"}).releaseDate("3 de Octubre de 2019")
                    .build(),
                Movie.builder().id("mov1002").title("Avengers: Endgame")
                    .actors(new String[]{"Robert Downey Jr.", "Scarlett Johansson", "Chris Evans"})
                    .directors(new String[]{"Robert Downey Jr.", "Scarlett Johansson", "Chris Evans"})
                    .releaseDate("22 de Abril 2019")
                    .build()
        ).forEach(movie -> repository.save(movie));
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                            .dataFetcher("allMovies", allMoviesDataFetcher)
                            .dataFetcher("movie", movieDataFetcher))
                    .build();
    }

    @Bean
    public GraphQL graphQL(){
        return graphQL;
    }
}
