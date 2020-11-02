package com.example.soccerapp.respository;

import com.example.soccerapp.model.entity.Fixtures;
import com.example.soccerapp.model.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixtures, Integer> {

    @Query("select coalesce(sum(goalTeam1),0) from Fixtures where team1=?1")
    void sumOfGoalsScoredHome(Teams teams);
    @Query("select coalesce(sum(goalTeam2),0) from Fixtures where team2=?1")
    void sumOfGoalsScoredAway(Teams teams);

    @Query("select coalesce(sum(goalTeam2),0) from Fixtures where team1=?1")
    void sumOfGoalsMissedHome(Teams teams);
    @Query("select coalesce(sum(goalTeam1),0) from Fixtures where team2=?1")
    void sumOfGoalsMissedAway(Teams teams);

    List<?> getAllByFixtureNumber(Integer fixtureNumber);

    @Query("from Fixtures where fixtureNumber=(select min(fixtureNumber) from Fixtures where goalTeam1=0)")
    List<Fixtures> nextGame();

    @Query("from Fixtures where fixtureNumber=?1")
    List<Fixtures> prevGame(Integer n);


}
