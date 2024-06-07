package com.supinfo.League_Application.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "matches")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("day")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_seq")
    @SequenceGenerator(name = "match_seq", allocationSize = 1, initialValue = 1000)
    private Long id;
    @Column(name = "match_status", nullable = false)
    private String matchStatus;


    @ManyToOne(optional = false)
    private Day day;
    @ManyToOne(optional = false)
    private Team teamHome;
    @ManyToOne(optional = false)
    private Team teamAway;
    @OneToMany(mappedBy = "match")
    @JsonBackReference
    private List<Comment> comments;

    public void setStatus(String status) {
        this.matchStatus = status;
    }

    public String getStatus() {
        return this.matchStatus;
    }
}
