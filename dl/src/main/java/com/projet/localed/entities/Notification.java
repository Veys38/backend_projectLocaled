package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {

    @Column(length = 1000)
    private String message;

    private boolean read = false;

    @ManyToOne(optional = false)
    private User user;

}
