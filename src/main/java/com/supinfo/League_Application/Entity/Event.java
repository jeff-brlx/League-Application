package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Event {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String player;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

}
