package com.vyjsoft.PruebasGraphQLApi.model;


import lombok.*;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Movie {

    @Id
    private String id;
    private String title;
    private String[] directors;
    private String[] actors;
    private String releaseDate;
}
