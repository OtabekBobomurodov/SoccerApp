package com.example.soccerapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class nextGameModel {
    private List<?> list;
    private Integer fixtureNumber;
}
