package com.MasivRoulette.Roulette.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Roulette")
public class Roulette implements Serializable {

    @Id
    private int idRoulette;
    private String state = "closed";
    private String description;
    private int winningNumber;

    @Autowired
    private ArrayList<Bet> betList = new ArrayList<>();

    public void open()
    {
        state = "opened";
    }
    public void close(){
        state = "closed";
    }

    public void addBet(Bet bet) {
        betList.add(bet);
    }
}
