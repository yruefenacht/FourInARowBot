package ch.lukasakermann.connectfourchallenge.connectFourService;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DropDisc {

    private final String playerId;
    private final int column;

    @JsonCreator
    public DropDisc(@JsonProperty("playerId") String playerId,
                    @JsonProperty("column") int column) {
        this.playerId = playerId;
        this.column = column;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getColumn() {
        return column;
    }
}
