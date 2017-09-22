import java.io.File;
import java.util.Scanner;

/**
 * Created by Ryan on 9/20/2017.
 */
public class Main {

    public static void main(String[] args){

        FileManager f = new FileManager("move_file_test", "test.go");
        f.writeMove(new Move(3, 5));

        while(!f.checkForGo()){

        }
        System.out.println("Found Go File");


    }


}
