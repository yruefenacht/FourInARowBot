# connect-four-client-client-java ![Build Status](https://travis-ci.org/lakermann/connect-four-challenge-client-java.svg)

This is a Java client for the [connect four challenge server](https://github.com/lakermann/connect-four-challenge-server).
This client allows you to easily develop a bot for the connect four challenge.

## Getting started

Clone this repository and start the [ConnectFourClientApplication](src/main/java/ch/lukasakermann/connectfourchallenge/ConnectFourClientApplication.java) class:

``` java
public class ConnectFourClientApplication {
    private static final String SERVER_URL = "http://localhost:8080";
    private static final int NUMBER_OF_GAMES = 1_000;

    public static void main(String[] args) {
        ConnectFourAdapter connectFourAdapter = new ConnectFourAdapter(SERVER_URL);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new GameRunner(connectFourAdapter, "Alice", new RandomStrategy(), NUMBER_OF_GAMES));
        executor.submit(new GameRunner(connectFourAdapter, "Bob", new RandomStrategy(), NUMBER_OF_GAMES));
    }
}

```

## Implement your own bot

To implement your own bot you need to provide an implementation of the
[ConnectFourStrategy](src/main/java/ch/lukasakermann/connectfourchallenge/game/strategy/ConnectFourStrategy.java) interface:

``` java
public interface ConnectFourStrategy {
    int dropDisc(Game game);

    default void win(Game game) {
    }

    default void loose(Game game) {
    }

    default void draw(Game game) {
    }
```
