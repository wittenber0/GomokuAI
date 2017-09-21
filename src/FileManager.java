import java.io.File;
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
            Scanner scanner = new Scanner(moveFile, " ");


        }catch(Exception e){
            System.out.println(e);
        }

    }
}
