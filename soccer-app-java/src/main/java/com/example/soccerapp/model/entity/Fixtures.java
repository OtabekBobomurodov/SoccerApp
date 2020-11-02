package com.example.soccerapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fixtures")
public class Fixtures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teams team1;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teams team2;

    private Integer goalTeam1;
    private Integer goalTeam2;
    private Integer fixtureNumber;


    public Fixtures(Teams team1, Teams team2, Integer goalTeam1, Integer goalTeam2, Integer fixtureNumber) {
        this.team1 = team1;
        this.team2 = team2;
        this.goalTeam1 = goalTeam1;
        this.goalTeam2 = goalTeam2;
        this.fixtureNumber = fixtureNumber;
    }
}
