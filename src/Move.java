/**
 * Created by Ryan on 9/20/2017.
 */
public class Move {
    private int column;
    private int row;

    public Move(int x, int y){
        column = x;
        row = y;
    }

    public Move(String x, int y){
        column = getColumnNum(x);
        row = y;
    }

    public String toString(){
        int outRow = row+1;
        String s = "TeamName " + getColumnChar(column) + " " + outRow;
        return s;
    }

    public int getColumnNum(String s){
        switch(s) {
            case "A" : return 1;
            case "B" : return 2;
            case "C" : return 3;
            case "D" : return 4;
            case "E" : return 5;
            case "F" : return 6;
            case "G" : return 7;
            case "H" : return 8;
            case "I" : return 9;
            case "J" : return 10;
            case "K" : return 11;
            case "L" : return 12;
            case "M" : return 13;
            case "N" : return 14;
            case "O" : return 15;
            default: return -1;
        }
    }

    public String getColumnChar(int i) {
        switch (i) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "F";
            case 7:
                return "G";
            case 8:
                return "H";
            case 9:
                return "I";
            case 10:
                return "J";
            case 11:
                return "K";
            case 12:
                return "L";
            case 13:
                return "M";
            case 14:
                return "N";
            case 15:
                return "O";
            default:
                return null;

        }
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }
}
