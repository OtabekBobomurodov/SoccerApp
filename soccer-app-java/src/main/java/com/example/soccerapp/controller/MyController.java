package com.example.soccerapp.controller;

import com.example.soccerapp.model.entity.*;
import com.example.soccerapp.respository.FixtureRepository;
import com.example.soccerapp.respository.PlayerRepository;
import com.example.soccerapp.respository.TeamRepository;
import com.example.soccerapp.respository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class MyController {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    FixtureRepository fixtureRepository;
    @Autowired
    TransferRepository transferRepository;


    @GetMapping("/getPlayers")
    public GetPlayerModel getPlayers(@RequestParam Integer teamId) {
        List<?> list = playerRepository.getPlayersByTeamOrderByRatingDesc(teamRepository.findById(teamId).get());
        int rating = playerRepository.overallRating(teamRepository.findById(teamId).get());
        int teamBudget = teamRepository.findById(teamId).get().getBudget();
        return new GetPlayerModel(list, rating/11, teamBudget);
    }

    @GetMapping("/teams")
    public List<?> Teams(@RequestParam String teamName) {
        return teamRepository.findAllByNameContainingIgnoreCaseOrderByPointsDesc(teamName);
    }


    @GetMapping("/fixtures")
    public List<?> fixtures(@RequestParam Integer fixtureNumber) {
        return fixtureRepository.getAllByFixtureNumber(fixtureNumber);
    }

    @GetMapping("/nextGame")
    public nextGameModel nextGame() {
        return new nextGameModel(fixtureRepository.nextGame(), fixtureRepository.nextGame().get(0).getFixtureNumber());
    }




    @PutMapping("/playGame")
    public List<?> playGame() {
        List<Fixtures> nextGames = fixtureRepository.nextGame();
        if(nextGames.get(0).getGoalTeam1()==0) {
            Fixtures fixture = nextGames.get(0);
            Fixtures fixture2 = nextGames.get(1);
            Fixtures fixture3 = nextGames.get(2);
            Fixtures fixture4 = nextGames.get(3);
            fixture.setGoalTeam1((int) (1 + Math.random()*3 + Math.abs((playerRepository.overallRating(fixture.getTeam1()))/11-75)/2));
            fixture.setGoalTeam2((int) (Math.random()*3 + Math.abs((playerRepository.overallRating(fixture.getTeam2()))/11-75)/2));
            fixture2.setGoalTeam1((int) (1 + Math.random()*3 + Math.abs((playerRepository.overallRating(fixture2.getTeam1()))/11-75)/2));
            fixture2.setGoalTeam2((int) (Math.random()*3 + Math.abs((playerRepository.overallRating(fixture2.getTeam2()))/11-75)/2));
            fixture3.setGoalTeam1((int) (1 + Math.random()*3 + Math.abs((playerRepository.overallRating(fixture3.getTeam1()))/11-75)/2));
            fixture3.setGoalTeam2((int) (Math.random()*3 + Math.abs((playerRepository.overallRating(fixture3.getTeam2()))/11-75)/2));
            fixture4.setGoalTeam1((int) (1 + Math.random()*3 + Math.abs((playerRepository.overallRating(fixture4.getTeam1()))/11-75)/2));
            fixture4.setGoalTeam2((int) (Math.random()*3 + Math.abs((playerRepository.overallRating(fixture4.getTeam2()))/11-75)/2));

            fixtureRepository.save(fixture);
            fixtureRepository.save(fixture2);
            fixtureRepository.save(fixture3);
            fixtureRepository.save(fixture4);


            for(int i=0; i<4; i++) {
                if(nextGames.get(i).getGoalTeam1()>nextGames.get(i).getGoalTeam2()) {
                    Teams team = teamRepository.findByName(nextGames.get(i).getTeam1().getName()).get(0);
                    Teams team2 = teamRepository.findByName(nextGames.get(i).getTeam2().getName()).get(0);
                    team.setPoints(team.getPoints()+3);
                    team.setBudget(team.getBudget()+7000000);
                    team.setGoals(team.getGoals() + nextGames.get(i).getGoalTeam1() - nextGames.get(i).getGoalTeam2());
                    team2.setGoals(team2.getGoals() + nextGames.get(i).getGoalTeam2() - nextGames.get(i).getGoalTeam1());
                    team2.setBudget(team2.getBudget()-2000000);
                    teamRepository.save(team);
                    teamRepository.save(team2);
                }

                if(nextGames.get(i).getGoalTeam1()<nextGames.get(i).getGoalTeam2()) {
                    Teams team = teamRepository.findByName(nextGames.get(i).getTeam2().getName()).get(0);
                    Teams team2 = teamRepository.findByName(nextGames.get(i).getTeam1().getName()).get(0);
                    team.setPoints(team.getPoints()+3);
                    team.setGoals(team.getGoals() + nextGames.get(i).getGoalTeam2() - nextGames.get(i).getGoalTeam1());
                    team.setBudget(team.getBudget()-2000000);
                    team2.setGoals(team2.getGoals() + nextGames.get(i).getGoalTeam1() - nextGames.get(i).getGoalTeam2());
                    team2.setBudget(team2.getBudget()+7000000);
                    teamRepository.save(team);
                    teamRepository.save(team2);
                }

                if(nextGames.get(i).getGoalTeam1()==nextGames.get(i).getGoalTeam2()) {
                    Teams team = teamRepository.findByName(nextGames.get(i).getTeam1().getName()).get(0);
                    Teams team2 = teamRepository.findByName(nextGames.get(i).getTeam2().getName()).get(0);
                    team.setPoints(team.getPoints()+1);
                    team2.setPoints(team2.getPoints()+1);
                    team.setGoals(team.getGoals() + nextGames.get(i).getGoalTeam1() - nextGames.get(i).getGoalTeam2());
                    team2.setGoals(team2.getGoals() + nextGames.get(i).getGoalTeam2() - nextGames.get(i).getGoalTeam1());
                    team.setBudget(team.getBudget()+3000000);
                    team2.setBudget(team2.getBudget()+3000000);
                    teamRepository.save(team);
                    teamRepository.save(team2);
                }
            }

            return fixtureRepository.prevGame(fixture.getFixtureNumber());
        }
        return null;
    }

    @PutMapping("/restart")
    public void restartSeason() {
        for(int i=2; i<58; i++) {
            Fixtures f1 = fixtureRepository.findById(i).get();
            f1.setGoalTeam1(0);
            f1.setGoalTeam2(0);
            fixtureRepository.save(f1);
        }

        for(int i=1; i<9; i++) {
            Teams t1 = teamRepository.findById(i).get();
            t1.setPoints(0);
            t1.setGoals(0);
            t1.setBudget(50000000);
            t1.setChampion(0);
            teamRepository.save(t1);
        }
    }

    @PutMapping("/nextSeason")
    public void nextSeason() {

        Integer maxPoints = teamRepository.getMaxPoints();
        Integer maxGoals = teamRepository.getMaxGoals(maxPoints);
        Teams champ = teamRepository.getChampion(maxPoints, maxGoals);
        champ.setChampion(champ.getChampion()+1);
        teamRepository.save(champ);

        for(int i=2; i<58; i++) {
            Fixtures f1 = fixtureRepository.findById(i).get();
            f1.setGoalTeam1(0);
            f1.setGoalTeam2(0);
            fixtureRepository.save(f1);
        }

        for(int i=1; i<9; i++) {
            Teams t1 = teamRepository.findById(i).get();
            t1.setPoints(0);
            t1.setGoals(0);
            teamRepository.save(t1);
        }
    }

    @PutMapping("/transfer")
    public Response transferPlayer(@RequestParam Integer buyPlayerId, @RequestParam Integer sellPlayerId) {
        Integer buyTeamId = playerRepository.findById(buyPlayerId).get().getTeam().getId();
        Integer sellTeamId = playerRepository.findById(sellPlayerId).get().getTeam().getId();

        if((buyTeamId!=sellTeamId &&
                playerRepository.findById(buyPlayerId).get().getPosition().
                        equals(playerRepository.findById(sellPlayerId).get().getPosition())) &&
                teamRepository.findById(sellTeamId).get().getBudget()>=playerRepository.findById(buyPlayerId).get().getTransferFee()) {
            Players buyPlayer = playerRepository.findById(buyPlayerId).get();
            Players sellPlayer = playerRepository.findById(sellPlayerId).get();

            buyPlayer.setTeam(teamRepository.findById(sellTeamId).get());
            sellPlayer.setTeam(teamRepository.findById(buyTeamId).get());

            Teams t1 = teamRepository.findById(buyTeamId).get();
            t1.setBudget(teamRepository.findById(buyTeamId).get().getBudget()+
                    playerRepository.findById(buyPlayerId).get().getTransferFee());

            Teams t2 = teamRepository.findById(sellTeamId).get();
            t2.setBudget(teamRepository.findById(sellTeamId).get().getBudget()-
                    playerRepository.findById(sellPlayerId).get().getTransferFee());

            teamRepository.save(t1);
            teamRepository.save(t2);

            playerRepository.save(buyPlayer);
            playerRepository.save(sellPlayer);

            return new Response("Transfer successfully made!", true);
        }
        else {
            return new Response("Teams should differ//Player positions should be the same//Budget is not enough!", false);
        }
    }

//    @PostMapping("/loginn")
//    public Response login(@RequestParam String username, @RequestParam String password) {
//        Integer id = playerRepository.findByName(username).get(0).getId();
//        String position = playerRepository.findByName(username).get(0).getPosition();
//        if(position.equals(password)) {
//            return new Response("Login successful", true);
//        }
//        else {
//            return new Response("Login failed", false);
//        }
//    }

    public void uploadImg() {

    }
}
