public class Main {

    public static void main(String[] args){
        GameBoard.TileType myColor;
        GameBoard.TileType opponentColor;

        GameBoard board = new GameBoard();
        FileManager f = new FileManager("move_file", "RIPHughHefner.go");

        if(f.readMove() == null){
            myColor = GameBoard.TileType.WHITE;
            opponentColor = GameBoard.TileType.BLACK;
        }else{
            myColor = GameBoard.TileType.BLACK;
            opponentColor = GameBoard.TileType.WHITE;
        }
        board.ourColor = myColor;
        board.theirColor = opponentColor;

        boolean boardFull = false;
        boolean win = false;
        while(boardFull == false && win == false) {



            while (!f.checkForGo()) {}
            System.out.println("Found Go File");

            Move lastMove = f.readMove();

            board.saveMove(lastMove);


            Move myMove = board.getBestMove();
            boardFull = board.saveMove(myMove);

            if(boardFull == false) {
                f.writeMove(myMove);
            }
        }


    }


}
