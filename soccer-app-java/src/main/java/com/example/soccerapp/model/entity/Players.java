package com.example.soccerapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Players {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teams team;

    @Column(nullable = false)
    private short rating;
    @Column(nullable = false)
    private String position;
    @Column(nullable = false)
    private Integer transferFee;

    public Players(String name, Teams team, short rating, String position, Integer transferFee) {
        this.name = name;
        this.team = team;
        this.rating = rating;
        this.position = position;
        this.transferFee = transferFee;
    }
}
