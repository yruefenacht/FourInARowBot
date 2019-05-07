package ch.lukasakermann.connectfourchallenge.game.contest2019;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FirstStrategy implements ConnectFourStrategy {

    private int a;
    private int b;


    private int getHeight(List<List<String>> board, int pos){

        for(int i = 5;i>=0;i--){
            if(board.get(i).get(pos).equals(ChipStates.EMPTY_CELL))
                return i;
        }
        return -1;
    }


    private boolean canWinInOneMove(Game game, List<List<String>> board, int pos, String color){

        int columnHeight = getHeight(board,pos);
        if(columnHeight == -1)
            return false;

        //check horizontal
        List<String> row = board.get(columnHeight);
        int consecutive = 0;
        for(int i = 0; i < 7; i++){
            if(row.get(i).equals(color) || i == pos){
                consecutive++;
                if(consecutive >= 4) return true;
            }
            else{
                consecutive = 0;
            }
        }
        //Check vertical
        if (columnHeight < 3){
            int consecutive2 = 0;
            for(int i = 5;i>=0;i--){
                if (board.get(i).get(pos).equals(color))
                    consecutive2++;
                else if(board.get(i).get(pos).equals(ChipStates.EMPTY_CELL) && consecutive2 == 3)
                    return true;
                else
                    consecutive2 = 0;
            }
        }

        //check diagonal
        int x = pos;
        int y = columnHeight;

        //go down left
        while(isInBounds(x-1, y+1, board)) {
            x--;
            y++;
        }

        //go up right
        int coinCounterConsecutive = 0;
        while(isInBounds(x, y, board)) {

            if(getCoinColorAt(x, y, board).equals(color))
                coinCounterConsecutive++;
            else if(x == pos)
                coinCounterConsecutive++;
            else
                coinCounterConsecutive = 0;

            if(coinCounterConsecutive >= 4) return true;

            x++;
            y--;
        }
        // diagonal inverse
        x = pos;
        y = columnHeight;
        while(isInBounds(x+1, y+1, board)) {
            x++;
            y++;
        }
        while(isInBounds(x, y, board)) {

            if(getCoinColorAt(x, y, board).equals(color))
                coinCounterConsecutive++;
            else if(x == pos)
                coinCounterConsecutive++;
            else
                coinCounterConsecutive = 0;

            if(coinCounterConsecutive >= 4) return true;
            x--;
            y--;
        }

        return false;
    }


    private String getCoinColorAt(int x, int y, List<List<String>> board) {

        return board.get(y).get(x);
    }


    private boolean isInBounds(int x, int y, List<List<String>> board) {

        return (x >= 0 && x < 7 && y >= 0 && y < 6);
    }


    private List<List<String>> copyBoard(List<List<String>> board) {
        List<List<String>> board2 = new ArrayList<List<String>>();
        for(int i = 0;i<6;i++)
            board2.add(new ArrayList<>(board.get(i)));
        return board2;
    }


    private int costFunctionEasy(Game game, List<List<String>> board, int pos, String color) {

        if(canWinInOneMove(game, board,pos,color)) return 100000000;

        String alternateColor = color.equals("YELLOW") ? "RED" : "YELLOW";

        if(canWinInOneMove(game, board, pos, alternateColor)) return 1000000;

        int result = 0;
        int height = getHeight(board,pos);
        if(height != 5)
            if(!board.get(height+1).get(pos).equals(color))
                result += b;

        if(height != 5)
            if(!board.get(height+1).get(pos).equals(alternateColor))
                result += 8;

        if(pos > 0)
            if(board.get(height).get(pos-1).equals(color) && getHeight(board, pos-1) == height)
                result += a;

        if(pos < 6)
            if(board.get(height).get(pos+1).equals(color) && getHeight(board, pos+1) == height)
                result += a;

        if (getHeight(board,pos) < 4)
            if(board.get(height+1).get(pos).equals(color) && board.get(height+2).get(pos).equals(color))
                result += 1000;

        if (getHeight(board,pos) < 4)
            if(board.get(height+1).get(pos).equals(alternateColor) && board.get(height+2).get(pos).equals(alternateColor))
                result += 50;

        if(height < 2) result -= 15;

        int spat = (height*(pos-3)*4);
        result -= Math.abs(spat)+height*3;
        List<List<String>> board2 = copyBoard(board);
        board2.get(height).set(pos, color);
        for(int i = 0; i<7; i++)
            if(canWinInOneMove(game, board2, i, alternateColor)) return -10000000;

        return result;
    }


    private String getColor(Game game){

        Optional<Player> any = game.getPlayers().stream()
                .filter(p -> p.getPlayerId().equals(game.getCurrentPlayerId()))
                .findAny();
        return any.get().getDisc();
    }


    @Override
    public int dropDisc(Game game) {
        List<List<String>> board = game.getBoard();
        List<String> columns = board.get(0);
        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(ChipStates.EMPTY_CELL))
                .collect(Collectors.toList());

        Random rand = new Random();
        int finalMove = validMoves.get(rand.nextInt(validMoves.size()));

        int maxi = 0;
        //int currentBestMove = 0;
        for (Integer validMove : validMoves) {
            int gh = costFunctionEasy(game, board, validMove, getColor(game));
            if (gh > maxi) {
                finalMove = validMove;
                maxi = gh;
            }
        }

        return finalMove;
    }

    @Override
    public void win(Game game) {

    }

    @Override
    public void loose(Game game) {
        Random rand = new Random();
        a = rand.nextInt(5);
        b = rand.nextInt(6);
    }

    @Override
    public void draw(Game game) {

    }

}
