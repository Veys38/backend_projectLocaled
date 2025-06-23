package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(name = "favorite", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "ad_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Favorite extends BaseEntity<Long> {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Ad ad;
}
