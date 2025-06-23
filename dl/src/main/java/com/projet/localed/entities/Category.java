package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity<Long> {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String icon;

    @Column(length = 1000)
    private String description;

}
