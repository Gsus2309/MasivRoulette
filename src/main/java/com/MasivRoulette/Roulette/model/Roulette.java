package com.MasivRoulette.Roulette.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Roulette")
public class Roulette {

    @Id
    private int idRoulette;
    private String state;
    private String description;
    private String winningNumber;

    public void open()
    {
        state = "opened";
    }
    public void close(){
        state = "closed";
    }


}
