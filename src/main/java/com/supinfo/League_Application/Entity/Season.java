package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "season")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_seq")
    @SequenceGenerator(name="season_seq",allocationSize = 1,initialValue = 1000)
    private Long id;
    @Column(name = "label",unique = true,nullable = false)
    private String label;

    @OneToMany(mappedBy = "season")
    private List<Day> days = new ArrayList<>();

    @ManyToMany(mappedBy = "seasons")
    private List<Team> teams;
}
