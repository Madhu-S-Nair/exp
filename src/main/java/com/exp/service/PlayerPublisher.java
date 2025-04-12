package com.exp.service;

import com.exp.model.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.sql.DataSource;

@Component
public class PlayerPublisher {

    private final Sinks.Many<Player> sink = Sinks.many().multicast().onBackpressureBuffer();

    public Flux<Player> getPublisher() {
        return sink.asFlux();
    }

    public void emit(Player player) {
        sink.tryEmitNext(player);
    }

    PlayerService playerService = new PlayerService();


}