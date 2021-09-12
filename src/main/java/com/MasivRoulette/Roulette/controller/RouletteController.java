package com.MasivRoulette.Roulette.controller;

import com.MasivRoulette.Roulette.model.Bet;
import com.MasivRoulette.Roulette.model.Roulette;
import com.MasivRoulette.Roulette.service.RouletteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/roulette")
public class RouletteController {

    private RouletteServiceImpl rouletteService;

    @Autowired
    public RouletteController(RouletteServiceImpl rouletteService) {
        this.rouletteService = rouletteService;
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Roulette roulette){
        Roulette roulette2 = rouletteService.save(roulette);
        HashMap idRoulette = new HashMap<>();
        idRoulette.put("id", roulette2.getIdRoulette());
        return new ResponseEntity<>(idRoulette, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Roulette>> getAllProducts(){
        return new ResponseEntity<>(rouletteService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/open/{id}")
    public ResponseEntity openRoullete(@PathVariable int id){
        return rouletteService.openRoulette(id);
    }

    @PostMapping("/close/{id}")
    public ResponseEntity closeRoullete(@PathVariable int id){
        return rouletteService.closeRoulette(id);
    }

    @DeleteMapping("/{id}")
    public String deleteRoulette(@PathVariable int id){
        return rouletteService.deleteRoulette(id);
    }

    @PostMapping("/bet/{idRoulette}")
    public ResponseEntity makeBet(@PathVariable int idRoulette, @RequestBody Bet bet, @RequestHeader int userId){
        return rouletteService.addBet(idRoulette, bet, userId);
    }
}
