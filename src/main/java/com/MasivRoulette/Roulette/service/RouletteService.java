package com.MasivRoulette.Roulette.service;

import com.MasivRoulette.Roulette.model.Bet;
import com.MasivRoulette.Roulette.model.Roulette;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouletteService {
    
    Roulette save(Roulette roulette);

    List<Roulette> findAll();

    Roulette findRouletteById(int id);

    String deleteRoulette(int id);

    ResponseEntity openRoulette(int id);

    ResponseEntity closeRoulette(int id);

    ResponseEntity addBet(int idRoulette, Bet bet, int userId);
}
