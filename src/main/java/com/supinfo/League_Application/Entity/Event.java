package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name="event_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @ManyToOne(optional = false)
    private Match match;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    private Team team;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;
}

