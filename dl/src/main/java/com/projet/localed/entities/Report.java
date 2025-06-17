package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Report extends BaseEntity {

    @Column(length = 1000)
    private String reason;

    @Column(length = 3000)
    private String description;

    @ManyToOne(optional = false)
    private User reporter;

    @ManyToOne
    private User reportedUser;

    @ManyToOne
    private Ad reportedAd;

}
