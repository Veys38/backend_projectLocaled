package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import com.projet.localed.listeners.ImageEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@EntityListeners(ImageEntityListener.class)
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseEntity<Long> {

    @Column(nullable = false)
    private String url;

    private String altText;

    private boolean isCover = false;


    // Les Relations
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
