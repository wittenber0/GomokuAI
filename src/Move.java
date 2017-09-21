/**
 * Created by Ryan on 9/20/2017.
 */
public class Move {
    int column;
    int row;

    public Move(int x, int y){
        column = x;
        row = y;
    }

    public Move(String x, int y){
        column = getColumnNum(x);
        row = y;
    }

    public String toString(){
        String s = getColumnChar(column) + row;
        return s;
    }

    public int getColumnNum(String s){
        switch(s){
            case "A" : return 0;
            case "B" : return 1;
            case "C" : return 2;
            case "D" : return 3;
            case "E" : return 4;
            case "F" : return 5;
            case "G" : return 6;
            case "H" : return 7;
            case "I" : return 8;
            case "J" : return 9;
            case "K" : return 10;
            case "L" : return 11;
            case "M" : return 12;
            case "N" : return 13;
            case "O" : return 14;
            default: return -1;
        }
    }

    public String getColumnChar(int i) {
        switch (i) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            case 8:
                return "I";
            case 9:
                return "J";
            case 10:
                return "K";
            case 11:
                return "L";
            case 12:
                return "M";
            case 13:
                return "N";
            case 14:
                return "O";
            default:
                return null;

        }
    }
}
