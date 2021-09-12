package com.MasivRoulette.Roulette.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bet {

    private int idUser;
    private String typeBet;
    private long betAmount;
    private int betNumber;
    private boolean isWinner = false;
    private long betPrize;

}
