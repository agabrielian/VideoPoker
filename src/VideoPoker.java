/**
 * Created by alexgabrielian on 1/27/16.
 */

import java.util.*;

public class VideoPoker {

    private static int deck[];
    private static final String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final String[] suit = {"S", "H", "C", "D"};
    private static final int NUMBER_OF_CARDS_AT_PLAY = 5;
    private static final int NUMBER_OF_RANKS = rank.length;     // = 13
    private static final int NUMBER_OF_SUITS = suit.length;     // = 4
    private static final int NUMBER_OF_CARDS_IN_DECK = NUMBER_OF_RANKS * NUMBER_OF_SUITS;   // = 52

    public VideoPoker(){
        deck = new int[NUMBER_OF_CARDS_IN_DECK];
        for(int i = 0; i < deck.length; i++){
            deck[i] = i;
        }
    }

    public VideoPoker(String a){        //FOR TESTING ONLY
        deck = new int[NUMBER_OF_CARDS_IN_DECK];
        Scanner scan = new Scanner(a);
        for(int i = 0; i < deck.length; i++){
            if(scan.hasNextInt()) deck[i] = scan.nextInt();
        }
    }

    public void shuffle(){
        Random r = new Random();
        for(int i = 0; i < deck.length; i++){
            swap(deck, i, r.nextInt(NUMBER_OF_CARDS_IN_DECK));
        }
    }

    public void playersCards(){
        sortPlayersCards();
        for(int i = 1; i <= NUMBER_OF_CARDS_AT_PLAY; i++){
            System.out.println(i + ") " + cardFace(deck[i - 1]));
        }
    }


    private String cardFace(int cardIndex){
        int s = cardIndex % NUMBER_OF_SUITS;
        int r = cardIndex / NUMBER_OF_SUITS;
        return rank[r] + suit[s];
    }

    private static void swap(int[] a, int first, int second){
        int temp = a[first];
        a[first] = a[second];
        a[second] = temp;
    }

    public static String printDeck(){
        return Arrays.toString(deck);
    }

    public void replace(String entry){
        Scanner s = new Scanner(entry);
        boolean[] notBeenReplacedYet = {true, true, true, true, true};  //to avoid replacing more than once
        while(s.hasNext()){
            if(s.hasNextInt()){
                int replaceable = s.nextInt();
                if(replaceable >= 1 && replaceable <= 5 && notBeenReplacedYet[replaceable - 1]) {
                    swap(deck, replaceable - 1, NUMBER_OF_CARDS_AT_PLAY);
                    int temp = deck[NUMBER_OF_CARDS_AT_PLAY];
                    for (int i = NUMBER_OF_CARDS_AT_PLAY; i < NUMBER_OF_CARDS_IN_DECK - 1; i++) {
                        deck[i] = deck[i + 1];
                    }
                    deck[NUMBER_OF_CARDS_IN_DECK - 1] = temp;
                    notBeenReplacedYet[replaceable - 1] = false;
                }
            }
            else {
                s.next();
            }
        }
    }

    public static void sortPlayersCards(){
        for(int i = 0; i < NUMBER_OF_CARDS_AT_PLAY - 1; i++){
            if(deck[i] > deck[i + 1]){
                int unsorted = deck[i + 1];
                for(int j = i + 1; j > 0; j--){
                    if(unsorted <= deck[j - 1]){
                        deck[j] = deck[j - 1];
                        if(j == 1) deck[0] = unsorted;
                    }
                    else{
                        deck[j] = unsorted;
                        break;
                    }
                }
            }
        }
    }

    public String analyze(){
        if(royalFlush()) return "Royal Flush";
        else if(straightFlush()) return "Straight Flush";
        else if(fourOfAKind()) return "Four of a Kind";
        else if(fullHouse()) return "Full House";
        else if(flush()) return "Flush";
        else if(aceHighStraight() || straight()) return "Straight";
        else if(threeOfAKind()) return "Three of a Kind";
        else if(twoPair()) return "Two Pair";
        else if(pair()) return "Pair";
        else return "High Card";
    }

    private static boolean royalFlush(){
        if(flush() && aceHighStraight()) return true;
        else return false;
    }

    private static boolean straightFlush(){
        if(flush() && straight()) return true;
        else return false;
    }

    private static boolean flush(){
        if( deck[0] % NUMBER_OF_SUITS == deck[1] % NUMBER_OF_SUITS &&
                deck[1] % NUMBER_OF_SUITS == deck[2] % NUMBER_OF_SUITS &&
                deck[2] % NUMBER_OF_SUITS == deck[3] % NUMBER_OF_SUITS &&
                deck[3] % NUMBER_OF_SUITS == deck[4] % NUMBER_OF_SUITS )
            return true;
        else
            return false;
    }

    private static boolean straight(){
        if( deck[0] / NUMBER_OF_SUITS + 1 == deck[1] / NUMBER_OF_SUITS &&
                deck[1] / NUMBER_OF_SUITS + 1 == deck[2] / NUMBER_OF_SUITS &&
                deck[2] / NUMBER_OF_SUITS + 1 == deck[3] / NUMBER_OF_SUITS &&
                deck[3] / NUMBER_OF_SUITS + 1 == deck[4] / NUMBER_OF_SUITS )
            return true;
        else
            return false;
    }

    private static boolean aceHighStraight(){
        if( deck[0] / NUMBER_OF_SUITS + 9 == deck[1] / NUMBER_OF_SUITS &&   //first card + 9 = second card (aka A, 10)
                deck[1] / NUMBER_OF_SUITS + 1 == deck[2] / NUMBER_OF_SUITS &&   //second card + 1 = third card (aka 10, J)
                deck[2] / NUMBER_OF_SUITS + 1 == deck[3] / NUMBER_OF_SUITS &&   //third card + 1 = fourth card (aka J, Q)
                deck[3] / NUMBER_OF_SUITS + 1 == deck[4] / NUMBER_OF_SUITS )    //fourth card + 1 = fifth card (aka Q, K)
            return true;
        else
            return false;
    }

    private static boolean pair(){
        for(int i = 0; i < NUMBER_OF_CARDS_AT_PLAY - 1; i++)
            if(deck[i] / 4 == deck[i + 1] / 4) return true;
        return false;
    }

    private static boolean twoPair(){
        for(int i = 0; i < NUMBER_OF_CARDS_AT_PLAY - 3; i++)
            if(deck[i] / 4 == deck[i + 1] / 4){                                                 //if there is a pair in the first 3 cards
                for(int j = NUMBER_OF_CARDS_AT_PLAY - 3; j < NUMBER_OF_CARDS_AT_PLAY; j++)
                    if(deck[j] / 4 == deck[j + 1] / 4) return true;                             //if there is another pair in the last 3 cards
            }
        return false;
    }

    private static boolean threeOfAKind(){
        for(int i = 0; i < NUMBER_OF_CARDS_AT_PLAY - 2; i++)
            if(deck[i] / 4 == deck[i + 1] / 4 && deck[i + 1] / 4 == deck[i + 2] / 4) return true;
        return false;
    }

    private static boolean fourOfAKind(){
        for(int i = 0; i < NUMBER_OF_CARDS_AT_PLAY - 3; i++)
            if(deck[i] / 4 == deck[i + 1] / 4 && deck[i + 1] / 4 == deck[i + 2] / 4 && deck[i + 2] / 4 == deck[i + 3] / 4) return true;
        return false;
    }

    private static boolean fullHouse(){
        if( (deck[0] / 4 == deck[1] / 4 && deck[2] / 4 == deck[3] / 4 && deck[3] / 4 == deck[4] / 4) ||
                (deck[0] / 4 == deck[1] / 4 && deck[1] / 4 == deck [2] / 4 && deck[3] / 4 == deck[4] / 4))
            return true;
        else
            return false;
    }
}