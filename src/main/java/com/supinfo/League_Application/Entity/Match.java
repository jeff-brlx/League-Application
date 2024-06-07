package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "matchs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_seq")
    @SequenceGenerator(name = "match_seq", allocationSize = 1, initialValue = 1000)
    private Long id;
    @ManyToOne(optional = false)
    private Day day;
    @ManyToOne(optional = false)
    private Team teamHome;
    @ManyToOne(optional = false)
    private Team teamAway;

    public void setStatus(String status) {
        this.matchStatus = status;
    }
}
