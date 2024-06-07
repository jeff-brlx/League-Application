package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "matches")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_seq")
    @SequenceGenerator(name="match_seq",allocationSize = 1,initialValue = 1000)
    private Long id;
    @Column(name = "score_team_home",nullable = true) //pas demander dans le sujet
    private Integer scoreTeamHome;
    @Column(name = "score_team_away",nullable = true) // pas demander dans le sujet
    private Integer scoreTeamAway;
    @Column(name = "match_date", nullable = false)
    private LocalDateTime matchDate;
    ;
    @ManyToOne(optional = false)
    private Day day;
    @ManyToOne(optional = false)
    private Team teamHome;
    @ManyToOne(optional = false)
    private Team teamAway;

}
