package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import com.projet.localed.listeners.MessageEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@EntityListeners(MessageEntityListener.class)
public class Message extends BaseEntity<Long> {

    @Column(nullable = false, length = 500)
    private String content;

    @Column(length = 255)
    private String photoUrl;

    private boolean seen = false;

    @Column(nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();


    // Les relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(optional = false)
    private Ad ad;


}
