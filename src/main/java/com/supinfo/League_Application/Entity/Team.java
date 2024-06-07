package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="team")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @SequenceGenerator(name="team_seq",allocationSize = 1,initialValue = 1000)
    private Long id;
    @Column(name="name", nullable = false,unique = true)
    private String name;
    @ManyToMany
    private List<Season> seasons;
}
