package com.projet.localed.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Embeddable
public class Address {

    @Column(length = 100)
    private String street;

    @Column(length = 5)
    private String houseNumber;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 6)
    private String zip;

    @Column(length = 50)
    private String country;
}

