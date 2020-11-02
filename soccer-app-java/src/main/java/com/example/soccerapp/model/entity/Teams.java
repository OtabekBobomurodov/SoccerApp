package com.example.soccerapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer budget;
    @Column(nullable = false)
    private String stadium;

    private Integer points;
    private Integer goals;
    private Integer champion;

    public Teams(String name, Integer budget, String stadium, Integer points, Integer goals, Integer champion) {
        this.name = name;
        this.budget = budget;
        this.stadium = stadium;
        this.points = points;
        this.goals = goals;
        this.champion = champion;
    }
}
