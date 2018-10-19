package ch.lukasakermann.connectfourchallenge.connectFourService;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class GameId {

    private final UUID gameId;

    @JsonCreator
    public GameId(@JsonProperty("gameId") UUID gameId) {
        this.gameId = gameId;
    }

    public UUID gameId() {
        return gameId;
    }
}
