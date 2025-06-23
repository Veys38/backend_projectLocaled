package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_ad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Ad extends BaseEntity<Long> {

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 3000)
    private String description;

    private BigDecimal price;

    private LocalDateTime expirationDate;

    private boolean isAvailable = true;

    private boolean isHighlighted = false;

    private LocalDateTime highlightStartDate;

    private LocalDateTime highlightEndDate;

    @Embedded
    private Address location;


    // Relations
    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();


}

