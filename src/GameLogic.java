import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private String WHITE_PLAYER = "W";
    private String BLACK_PLAYER = "B";
    private String EMPTY_CELL   = "'";
    private List<List<Integer>> DIRECTIONS;

    void setDIRECTIONS()
    {
        List<Integer> northWest = new ArrayList<Integer>();
        List<Integer> north = new ArrayList<Integer>();
        List<Integer> northEast = new ArrayList<Integer>();
        List<Integer> west = new ArrayList<Integer>();
        List<Integer> east = new ArrayList<Integer>();
        List<Integer> southWest = new ArrayList<Integer>();
        List<Integer> south = new ArrayList<Integer>();
        List<Integer> southEast = new ArrayList<Integer>();

        // northWest coordinates
        northWest.add(-1);
        northWest.add(-1);

        // north coordinates
        north.add(-1);
        north.add(0);

        // northEast coordinates
        northEast.add(-1);
        northEast.add(1);

        // west coordinates
        west.add(0);
        west.add(-1);

        //east coordinates
        east.add(0);
        east.add(1);


        // southWest coordinates
        southWest.add(1);
        southWest.add(-1);

        // south coordinates
        south.add(1);
        south.add(0);

        // southEast coordinates
        southEast.add(1);
        southEast.add(1);

        // add to DIRECTIONS
        DIRECTIONS.add(northWest);
        DIRECTIONS.add(north);
        DIRECTIONS.add(northEast);
        DIRECTIONS.add(west);
        DIRECTIONS.add(east);
        DIRECTIONS.add(southWest);
        DIRECTIONS.add(south);
        DIRECTIONS.add(southEast);

    }

    String getOpposingPlayer(String player) {
        if(player == WHITE_PLAYER)
        {
            return BLACK_PLAYER;
        }
        return WHITE_PLAYER;
    }


    void createInitialState(int r, int c)
    {
        Board board = new Board(r, c);
        List<List<String>> state = board.getBoard();
        state.get((Integer)((r/2) - 1)).set((Integer)((c/2) -1), WHITE_PLAYER);
        state.get((Integer)((r/2))).set((Integer)((c/2) -1), BLACK_PLAYER);
        state.get((Integer)((r/2) - 1)).set((Integer)((c/2)), BLACK_PLAYER);
        state.get((Integer)((r/2))).set((Integer)((c/2)), WHITE_PLAYER);

        board.updateBoard(state);




        board.printBoard();


        
    }


    public static void main(String[] args){

        GameLogic game = new GameLogic();
        game.createInitialState(8,8);
    }





}
