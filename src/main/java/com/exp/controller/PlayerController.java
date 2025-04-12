package com.exp.controller;

import com.exp.model.Player;
import com.exp.model.Team;
import com.exp.service.PlayerPublisher;
import com.exp.service.PlayerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@Controller
public class PlayerController {


    private  final PlayerService playerService;
    private final PlayerPublisher publisher;



    public PlayerController(PlayerService playerService,PlayerPublisher publisher) {
        this.playerService = playerService;
        this.publisher=publisher;
    }

    @QueryMapping
    public List<Player> findAll(){
        return playerService.findAll();
    }

    @QueryMapping
    public Optional<Player> findOne(@Argument Integer id){
        return playerService.findOne(id);
    }

    @MutationMapping
    public Player create(@Argument String name, @Argument Team team){
        Player player=  playerService.create(name, team);
        System.out.println("Emitting player: " + player.name());
        publisher.emit(player);
        return player;
    }

    @MutationMapping
    public Player delete(@Argument Integer id){
        Player player = playerService.delete(id);
        publisher.emit(player);
        return player;
    }

    @MutationMapping
    public Player update(@Argument Integer id,@Argument String name, @Argument Team team){
        Player player = playerService.update(id,name, team);
        publisher.emit(player);
        return player;
    }


    @SubscriptionMapping("newMessage")
    public Flux<Player> playerAdded() {

        return publisher.getPublisher();
    }





}


