/**
 * Created by alexgabrielian on 1/27/16.
 */

import java.util.Scanner;

public class Runner {
    public static void main(String[] args){
        System.out.println("Starting Video Poker...");
        VideoPoker vp = new VideoPoker(100);
        while(vp.balance() > 0) {
            System.out.println("\nShuffling.......");
            vp.shuffleAndBet();
            System.out.println("\nYour cards...");
            vp.playersCards();
            System.out.println("\nPlease choose which cards 1-5 (separated by spaces) you would like to replace.\nNote: all other entries including duplicates will be ignored");
            Scanner entry = new Scanner(System.in);
            vp.replace(entry.nextLine());
            vp.playersCards();
            System.out.println("\n" + vp.analyze() + ". Your new balance: " + vp.balance() + "\n-----------------");
        }
        System.out.println("Game over.");
    }
}
