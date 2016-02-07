/**
 * Created by alexgabrielian on 1/27/16.
 */

import java.util.Scanner;

public class Tester {

    public static final String FLUSH = "0 51 12 16 2 32 36"; //replace 5 2
    public static final String FLUSH2 = "47 7 43 15 17 51 "; //replace 3
    public static final String STRAIGHT_ACE = "36 48 46 3 25 41"; //replace 2
    public static final String STRAIGHT_5 = "4 18 24 3 43 11 15"; //replace 4 5
    public static final String ROYAL = "2 50 38 46 42";
    public static final String STRAIGHTFLUSH = "13 17 9 1 5";
    public static final String PAIR1 = "0 3 48 28 8";
    public static final String PAIR2 = "21 36 37 40 44";
    public static final String PAIR3 = "16 36 42 46 47";
    public static final String TWOPAIR1 = "1 2 4 5 20";
    public static final String TWOPAIR2 = "20 21 28 46 47";
    public static final String TWOPAIR3 = "28 40 41 50 51";
    public static final String THREEOFAKIND1 = "20 8 22 3 21";
    public static final String THREEOFAKIND2 = "44 28 29 31 0";
    public static final String THREEOFAKIND3 = "4 5 6 32 36";
    public static final String FOUROFAKIND1 = "12 13 14 28 15";
    public static final String FOUROFAKIND2 = "48 50 51 2 49";
    public static final String FULLHOUSE1 = "30 0 28 1 29";
    public static final String FULLHOUSE2 = "4 51 6 49 7";


    public static void main(String[] args){
        VideoPoker vp = new VideoPoker(FULLHOUSE2);
        //vp.shuffle();
        System.out.println(vp.printDeck());
        vp.playersCards();
        System.out.println("Which cards would you like to replace?");
        Scanner entry = new Scanner(System.in);
        vp.replace(entry.nextLine());
        System.out.println(vp.printDeck());
        vp.playersCards();
        System.out.println(vp.analyze());
    }
}
