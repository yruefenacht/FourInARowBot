package ch.lukasakermann.connectfourchallenge.game.contest2019;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.Player;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecondStrategy implements ConnectFourStrategy {

    int a = 15;
    int b = 30;
    int getHeight(List<List<String>> board,int pos){

        for(int i = 5;i>=0;i--){
            if(board.get(i).get(pos).equals(ChipStates.EMPTY_CELL)){
                return i;
            }
        }
        return -1;
    }

    boolean canWinInOneMove(List<List<String>> board, int pos, String color){

        int columnheight = getHeight(board,pos);
        if(columnheight == -1){
            return false;
        }
        //überprüfen waagrecht
        List<String> row = board.get(columnheight);
        int consecutive = 0;
        for(int i = 0; i < 7; i++){
            if(row.get(i).equals(color) || i == pos){
                consecutive++;
                if(consecutive >= 4){
                    return true;
                }
            }
            else{
                consecutive = 0;
            }

        }
        //überprüfen senkrecht

        if (columnheight < 3){
            int consecutive2 = 0;
            for(int i = 5;i>=0;i--){


                if (board.get(i).get(pos).equals(color)){
                    consecutive2++;
                }
                else if(board.get(i).get(pos).equals(ChipStates.EMPTY_CELL) && consecutive2 == 3){
                    return true;
                }
                else{
                    consecutive2 = 0;
                }

            }
        }

        //überprüfen diagonal

        int diagonalCounter = 0;

        int x = pos;
        int y = columnheight;




        return false;
    }


    //private boolean isInBounds(int x, int y, List<List> board) {


    //}

    int costfunctionEasy(List<List<String>> board, int pos, String color){
        if(canWinInOneMove(board,pos,color)){
            int r = 0;
            return 100000000;
        }
        String alternateColor;
        if(color.equals("YELLOW")){
            alternateColor = "RED";
        }
        else{
            alternateColor = "YELLOW";
        }
        if(canWinInOneMove(board,pos,alternateColor)){
            return 1000000;
        }
        int result = 0;
        int height = getHeight(board,pos);
        if(height != 5){
            if(!board.get(height+1).get(pos).equals(alternateColor)){
                result += b;
            }

        }
        if(pos > 0){
            if(board.get(height).get(pos-1).equals(color)&&getHeight(board,pos-1)==getHeight(board,pos)){
                result += a;
            }
        }
        int sdkj = ((pos-3)*height);
        result = result + Math.abs(sdkj);
        return result;
    }
    String getColor(Game game){

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
        for(int i = 0;i<validMoves.size();i++){
            int gh= costfunctionEasy(board,validMoves.get(i),getColor(game));
            if ( gh> maxi){
                finalMove = validMoves.get(i);
                maxi = gh;
            }
        }


        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return finalMove;
    }

    @Override
    public void win(Game game) {

    }

    @Override
    public void loose(Game game) {
        Random rand = new Random();
        a = rand.nextInt(10);
        b = rand.nextInt(5);
    }

    @Override
    public void draw(Game game) {

    }
}
