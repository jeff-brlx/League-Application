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
    @SequenceGenerator(name="match_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Column(name = "score_team_home", nullable = true)
    private Integer scoreTeamHome;

    @Column(name = "score_team_away", nullable = true)
    private Integer scoreTeamAway;

    @ManyToOne(optional = false)
    private Day day;

    @ManyToOne(optional = false)
    private Team teamHome;

    @ManyToOne(optional = false)
    private Team teamAway;

    @Column(name = "start_time", nullable = true)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = true)
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MatchStatus status;
}

