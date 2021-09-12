package com.MasivRoulette.Roulette.repository;

import com.MasivRoulette.Roulette.model.Roulette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouletteDao {

    @Autowired
    private RedisTemplate redisTemplate;
    private static final String HASH_KEY = "Roulette";

    public Roulette save(Roulette roulette) {
        redisTemplate.opsForHash().put("Roulette", roulette.getIdRoulette(), roulette);
        return roulette;
    }

    public List<Roulette> findAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Roulette findRouletteById(int id){
        return (Roulette) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteRoulette(int id){
        redisTemplate.opsForHash().delete(HASH_KEY,id);
        return "Roulette eliminated.";
    }
}
