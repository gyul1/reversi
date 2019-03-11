import java.util.ArrayList;
import java.util.List;

public class GameLogic {

    private String WHITE_PLAYER = "W";
    private String BLACK_PLAYER = "B";
    private String EMPTY_CELL   = "'";
    private String CURRENT_PLAYER = "W";
    private List<List<Integer>> DIRECTIONS = new ArrayList<List<Integer>>();
    Board GaemeBoard;


    public GameLogic(int r, int c) {
        // constructor
        setDIRECTIONS();
        this.GaemeBoard = new Board(r, c);
        List<List<String>> state = this.GaemeBoard.getBoard();
        state.get((Integer) ((r / 2) - 1)).set((Integer) ((c / 2) - 1), WHITE_PLAYER);
        state.get((Integer) ((r / 2))).set((Integer) ((c / 2) - 1), BLACK_PLAYER);
        state.get((Integer) ((r / 2) - 1)).set((Integer) ((c / 2)), BLACK_PLAYER);
        state.get((Integer) ((r / 2))).set((Integer) ((c / 2)), WHITE_PLAYER);

        this.GaemeBoard.updateBoard(state);
        //board.printBoard();
    }



    void setDIRECTIONS() {
        // sets the directions
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
        this.DIRECTIONS.add(northWest);
        this.DIRECTIONS.add(north);
        this.DIRECTIONS.add(northEast);
        this.DIRECTIONS.add(west);
        this.DIRECTIONS.add(east);
        this.DIRECTIONS.add(southWest);
        this.DIRECTIONS.add(south);
        this.DIRECTIONS.add(southEast);

    }

    String getOpposingPlayer(String player) {
        // returns the opposite player
        if(player == this.WHITE_PLAYER)
        {
            return this.BLACK_PLAYER;
        }
        return this.WHITE_PLAYER;
    }

    int numOfBlackPieces() {
        // returns the number of black pieces on the board
        int count = 0;
        for(int rIndex = 0; rIndex < this.GaemeBoard.getRows(); rIndex++)
        {
            for(int cIndex = 0; cIndex < this.GaemeBoard.getCols(); cIndex++) {
                if(this.GaemeBoard.getBoard().get(rIndex).get(cIndex) == "B")
                {
                    count += 1;
                }
            }

        }

        return count;
    }

    int numOfWhitePieces() {
        // returns the number of white pieces on the board
        int count = 0;
        for(int rIndex = 0; rIndex < this.GaemeBoard.getRows(); rIndex++)
        {
            for(int cIndex = 0; cIndex < this.GaemeBoard.getCols(); cIndex++) {
                if(this.GaemeBoard.getBoard().get(rIndex).get(cIndex) == "W")
                {
                    count += 1;
                }
            }
        }
        return count;
    }

    String getWinner() {
        // returns the winnter by comparing the number of black pieces vs white pieces
        int whiteCount = numOfWhitePieces();
        int blackCount = numOfBlackPieces();

        if(blackCount > whiteCount){
            return BLACK_PLAYER;
        }
        return WHITE_PLAYER;
    }

    boolean isCellAvailable(int row, int col){
        // returns true if cell is empty
        return this.GaemeBoard.getBoard().get(row).get(col) == this.EMPTY_CELL;
    }

    boolean isOnBoard(int row, int col){
        // checks to see if the row col combo is out of index or not
        return row >= 0 && col >= 0 && row < this.GaemeBoard.getRows() && col < this.GaemeBoard.getCols();
    }

    List<List<Integer>> getFlippedItems(int row, int col, String player) {
        // getting the board pieces that would need to be flipped if the move was made
        List<List<Integer>> flipList = new ArrayList<List<Integer>>();
        if(!isCellAvailable(row, col))
        {
            return flipList;
        }
        // opposing player
        String oppPlayer = getOpposingPlayer(player);

        for(int direction = 0; direction < this.DIRECTIONS.size(); direction++){
            boolean foundOppPiece = false;
            int currRow = row + DIRECTIONS.get(direction).get(0);
            int currCol = col +  DIRECTIONS.get(direction).get(1);
            List<List<Integer>> currentFlipList = new ArrayList<List<Integer>>();

            while(isOnBoard(currRow, currCol)) {
                if(this.GaemeBoard.getBoard().get(currRow).get(currCol) == oppPlayer) {
                    foundOppPiece = true;
                    List<Integer> toAdd = new ArrayList<Integer>();
                    toAdd.add(currRow, currCol);
                    currentFlipList.add(toAdd);
                    currRow = currRow + DIRECTIONS.get(direction).get(0);
                    currCol = currCol + DIRECTIONS.get(direction).get(1);
                }
                else if(this.GaemeBoard.getBoard().get(currRow).get(currCol) == player){
                    if(foundOppPiece){
                        flipList.addAll(currentFlipList);
                    }
                    break;
                }

                else{
                    break;
                }
            }
        }
        return flipList;
    }

    boolean hasValidMoves(String player){
        for(int row = 0; row < this.GaemeBoard.getRows(); row++){
            for(int col = 0; col < this.GaemeBoard.getCols(); col ++){
                List<List<Integer>> flipList = getFlippedItems(row, col, player);
                if(flipList.size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean currentPlayerHasValidMove()
    {
        return hasValidMoves(this.CURRENT_PLAYER);
    }

    void flipTiles(List<List<Integer>> flipList){

        List<List<String>> state = this.GaemeBoard.getBoard();

        for(int i = 0; i < flipList.size(); i++) {
            List<Integer> position = new ArrayList<>();
            position.add(flipList.get(i).get(0));
            position.add(flipList.get(i).get(1));
            state.get(position.get(0)).set(position.get(1), this.CURRENT_PLAYER);
        }

        this.GaemeBoard.updateBoard(state);
    }

    void makeMove(int row, int col){
        List<List<Integer>> flipList = getFlippedItems(row, col, this.CURRENT_PLAYER);
        List<List<String>> state = this.GaemeBoard.getBoard();
        state.get(row).set(col, this.CURRENT_PLAYER);
        this.GaemeBoard.updateBoard(state);
        flipTiles(flipList);

        String oppPlayer = getOpposingPlayer(this.CURRENT_PLAYER);
        if(hasValidMoves(oppPlayer)){
            this.CURRENT_PLAYER = oppPlayer;
        }

    }

    boolean isGameOver()
    {
        if(!hasValidMoves(this.CURRENT_PLAYER) && !hasValidMoves(getOpposingPlayer(this.CURRENT_PLAYER)))
        {
            return true;
        }
        return false;
    }




    public static void main(String[] args){

        GameLogic game = new GameLogic(8,8);


    }


}
