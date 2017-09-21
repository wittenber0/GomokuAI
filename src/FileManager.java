import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Ryan on 9/20/2017.
 */
public class FileManager {
    File moveFile;

    public FileManager(String moveFileName){
        moveFile = new File(moveFileName);
    }

    public void writeMove(Move m){

        try {
            PrintWriter writer = new PrintWriter(moveFile);
            writer.print(m.toString());
            writer.close();
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
            return new Move(column, row);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

}
