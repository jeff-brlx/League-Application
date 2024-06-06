package com.supinfo.League_Application.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name="role_seq", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
