package com.supinfo.League_Application.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "day", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dayNumber", "season_id"})
})
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

    //verifications de l'unicité du couple (day-saison)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Day day)) return false;
        return Objects.equals(dayNumber, day.dayNumber) &&
                Objects.equals(season, day.season);
    }
    @Override
    public int hashCode() {
        return Objects.hash(dayNumber, season);
    }

}
