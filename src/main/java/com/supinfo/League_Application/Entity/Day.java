package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "day")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_seq")
    @SequenceGenerator(name="day_seq",allocationSize = 1,initialValue = 1000)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    @ManyToOne(optional = false)
    private Season season;

    @OneToMany(mappedBy = "day")
    private List<Match> matches;

}
