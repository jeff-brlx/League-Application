package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Teams")
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
}
