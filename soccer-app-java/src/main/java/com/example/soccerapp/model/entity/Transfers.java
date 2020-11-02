package com.example.soccerapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Players player1;
    @OneToOne(fetch = FetchType.LAZY)
    private Players player2;

    public Transfers(Players player1, Players player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
