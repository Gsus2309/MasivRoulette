package com.MasivRoulette.Roulette.service;

import com.MasivRoulette.Roulette.model.Bet;
import com.MasivRoulette.Roulette.model.Roulette;
import com.MasivRoulette.Roulette.repository.RouletteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class RouletteServiceImpl implements RouletteService {

    private final RouletteDao rouletteDao;

    @Autowired
    public RouletteServiceImpl(RouletteDao rouletteDao) {
        this.rouletteDao = rouletteDao;
    }


    @Override
    public Roulette save(Roulette roulette) {
        return rouletteDao.save(roulette);
    }

    @Override
    public List<Roulette> findAll() {
        return rouletteDao.findAll();
    }

    @Override
    public Roulette findRouletteById(int id) {
        return rouletteDao.findRouletteById(id);
    }

    @Override
    public String deleteRoulette(int id) {
        return rouletteDao.deleteRoulette(id);
    }

    @Override
    public ResponseEntity openRoulette(int id) {
        Roulette roulette =  findRouletteById(id);
        if (roulette != null) {
            if (roulette.getState().equals("closed")) {
                changeState(roulette, "open");
                return new ResponseEntity(HttpStatus.OK);
            }
            else {
                return new ResponseEntity("The Roulette has been opened before", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity addBet(int idRoulette, Bet bet, int userId) {
        Roulette roulette = findRouletteById(idRoulette);
        if (roulette != null){
            if (roulette.getState().equals("opened")){
                if (reviewBetData(bet)){
                    bet.setIdUser(userId);
                    roulette.addBet(bet);
                    save(roulette);
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            else
            {
                return new ResponseEntity("The roulette is disable to make a bet", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private boolean reviewBetData(Bet bet) {
        if((0 < bet.getBetNumber() && bet.getBetNumber() < 36) && bet.getBetAmount() < 10000){
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity closeRoulette(int id) {
        Roulette roulette =  findRouletteById(id);
        if (roulette != null) {
            if (roulette.getState().equals("opened")) {
                changeState(roulette, "close");
                return new ResponseEntity(generateUserWinners(generateWinnerNumber(roulette)), HttpStatus.OK);
            }
            else {
                return new ResponseEntity("The roulette is already closed", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private Roulette generateUserWinners(Roulette roulette) {
        for (Bet bet : roulette.getBetList()){
            if (bet.getTypeBet().equals("number")){
                if (bet.getBetNumber() == roulette.getWinningNumber()){
                    bet.setBetPrize(bet.getBetAmount()*5);
                    bet.setWinner(true);
                }
            }else {
                if (bet.getBetNumber() % 2 == 0 && roulette.getWinningNumber() % 2 == 0 ||
                        bet.getBetNumber() % 2 != 0 && roulette.getWinningNumber() % 2 != 0 ) {
                    bet.setBetPrize((long) Math.floor(bet.getBetAmount()*1.8));
                }
            }
        }
        save(roulette);
        return roulette;
    }

    private Roulette generateWinnerNumber(Roulette roulette){
        int winningNumber = (int) Math.floor(Math.random()*36+1);
        roulette.setWinningNumber(winningNumber);
        return roulette;
    }

    private void changeState(Roulette roulette, String action) {
        if (action.equals("open")) {
            roulette.open();
        }else {
            roulette.close();
        }
        rouletteDao.save(roulette);
    }
}
