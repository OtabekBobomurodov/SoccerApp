package com.example.soccerapp.respository;

import com.example.soccerapp.model.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Teams, Integer> {
    List<?> findAllByNameContainingIgnoreCaseOrderByPointsDesc(String name);


    List<Teams> findByName(String name);

    @Query("select max(points) from Teams")
    Integer getMaxPoints();

    @Query("select max(goals) from Teams where points=?1")
    Integer getMaxGoals(Integer points);

    @Query("from Teams where points=?1 and goals=?2")
    Teams getChampion(Integer points, Integer goals);
}
