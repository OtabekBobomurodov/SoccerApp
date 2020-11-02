package com.example.soccerapp.respository;

import com.example.soccerapp.model.entity.Players;
import com.example.soccerapp.model.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Players, Integer> {
    List<?> getPlayersByTeamOrderByRatingDesc(Teams teams);

    @Query("select coalesce(sum(rating),0) from Players where team=?1")
    Integer overallRating(Teams teams);

    List<Players> findByName(String name);
}
