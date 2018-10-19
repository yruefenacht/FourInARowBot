package ch.lukasakermann.connectfourchallenge.connectFourService;

import ch.lukasakermann.connectfourchallenge.rest.RestClientFactory;

import java.util.UUID;

public class ConnectFourAdapter {

    private final ConnectFourApiClient connectFourApiClient;

    public ConnectFourAdapter(String url) {
        connectFourApiClient = RestClientFactory.createClient(url, ConnectFourApiClient.class);
    }

    public GameId getJoin(String playerId) {
        Join join = new Join(playerId);
        return connectFourApiClient.join(join);
    }

    public Game getGame(UUID gameId) {
        return connectFourApiClient.getGame(gameId);
    }

    public Game dropDisc(UUID gameId, int column, String playerId) {
        DropDisc dropDisc = new DropDisc(playerId, column);
        return connectFourApiClient.makeMove(gameId, dropDisc);
    }
}
