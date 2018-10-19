package ch.lukasakermann.connectfourchallenge.connectFourService;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    private String playerId;
    private String disc;

    @JsonCreator
    public Player(@JsonProperty("playerId") String playerId,
                  @JsonProperty("disc") String disc) {
        this.playerId = playerId;
        this.disc = disc;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getDisc() {
        return disc;
    }
}
