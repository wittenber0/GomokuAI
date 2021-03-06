import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
    private File goFile;
    private File moveFile;

    public FileManager(String moveFileName, String goFileName){
        moveFile = new File(moveFileName);
        goFile = new File(goFileName);
    }

    public void writeMove(Move m){

        try {
            PrintWriter writer = new PrintWriter(moveFile);
            writer.print(m.toString());
            writer.close();
            System.out.println(m.toString());
        }catch(Exception e){
            System.out.println(e);
        }

    }

    public Move readMove(){
        try {
            Scanner scanner = new Scanner(moveFile).useDelimiter(" ");
            String opponentName = scanner.next();
            String column = scanner.next();
            int row = Integer.parseInt(scanner.next());
            return new Move(column, row, false);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean checkForGo(){
        return goFile.exists();
    }

}
