package com.exp.service;

import com.exp.model.Player;
import com.exp.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;



@Service
public class PlayerService {

    AtomicInteger id = new AtomicInteger(0);

    private List<Player> players = new ArrayList<>();





    public List<Player> findAll() {
        return players;
    }

    public Optional<Player> findOne(Integer id) {
        return players.stream().filter(player -> Objects.equals(player.id(), id)).findFirst();
    }

    public Player create(String name, Team team) {
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }

    public Player delete(Integer id) {
        Player player = players.stream().filter(plyr -> Objects.equals(plyr.id(), id)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
        ;
        players.remove(player);
        return player;
    }

    public Player update(Integer id, String name, Team team) {
        Player updatedPlayer = new Player(id, name, team);
        Optional<Player> optional = players.stream().filter(plyr -> Objects.equals(plyr.id(), id)).findFirst();

        if (optional.isPresent()) {
            Player player = optional.get();
            int index = players.indexOf(player);
            players.set(index, updatedPlayer);
        } else {
            throw new IllegalArgumentException("invalid player");
        }

        return updatedPlayer;
    }

    @PostConstruct
    public void init(){
        players.add( new Player((id.incrementAndGet()), "Dhoni", Team.CSk));
        players.add(  new Player((id.incrementAndGet()), "Rohit", Team.MI));
        players.add( new Player((id.incrementAndGet()), "Pant", Team.DC));
        players.add( new Player((id.incrementAndGet()), "Gill", Team.GT));
    }




}
