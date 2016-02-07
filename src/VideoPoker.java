/**
 * Created by alexgabrielian on 1/27/16.
 */

import java.util.*;

public class VideoPoker {

    private JavaDollar javaDollars;
    private static int deck[];
    private static final String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final String[] suit = {"S", "H", "C", "D"};
    private static final int NUMBER_OF_CARDS_AT_PLAY = 5;
    private static final int NUMBER_OF_RANKS = rank.length;     // = 13
    private static final int NUMBER_OF_SUITS = suit.length;     // = 4
    private static final int NUMBER_OF_CARDS_IN_DECK = NUMBER_OF_RANKS * NUMBER_OF_SUITS;   // = 52

    private static final int ROYAL_FLUSH_PAYOUT = 250;
    private static final int STRAIGHT_FLUSH_PAYOUT = 50;
    private static final int FOUR_OF_A_KIND_PAYOUT = 25;
    private static final int FULL_HOUSE_PAYOUT = 6;
    private static final int FLUSH_PAYOUT = 5;
    private static final int STRAIGHT_PAYOUT = 4;
    private static final int THREE_OF_A_KIND_PAYOUT = 3;
    private static final int TWO_PAIR_PAYOUT = 2;
    private static final int PAIR_OF_JACKS_OR_BETTER_PAYOUT = 1;

    public VideoPoker(){
        deck = new int[NUMBER_OF_CARDS_IN_DECK];
        for(int i = 0; i < deck.length; i++){
            deck[i] = i;
        }
    }

    public VideoPoker(int amount){
        deck = new int[NUMBER_OF_CARDS_IN_DECK];
        for(int i = 0; i < deck.length; i++){
            deck[i] = i;
        }
        javaDollars = new JavaDollar(amount);
        System.out.println("Starting balance: " + javaDollars.getBalance());
    }

    public VideoPoker(String a){        //FOR TESTING ONLY
        deck = new int[NUMBER_OF_CARDS_IN_DECK];
        Scanner scan = new Scanner(a);
        for(int i = 0; i < deck.length; i++){
            if(scan.hasNextInt()) deck[i] = scan.nextInt();
        }
    }

    public void shuffleAndBet(){
        Random r = new Random();
        for(int i = 0; i < deck.length; i++){
            swap(deck, i, r.nextInt(NUMBER_OF_CARDS_IN_DECK));
        }
        javaDollars.bet(1);
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

    public static String printDeck(){   //FOR TESTING ONLY
        String deckToString = "";
        int s, r;
        for(int i = 5; i < 10; i++){
            s = deck[i] % NUMBER_OF_SUITS;
            r = deck[i] / NUMBER_OF_SUITS;
            deckToString += rank[r] + suit[s] + " ";
        }
        return deckToString;
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
        if(royalFlush()){
            javaDollars.win(ROYAL_FLUSH_PAYOUT);
            return "Royal Flush, you win " + ROYAL_FLUSH_PAYOUT;
        }
        else if(straightFlush()){
            javaDollars.win(STRAIGHT_FLUSH_PAYOUT);
            return "Straight Flush, you win " + STRAIGHT_FLUSH_PAYOUT;
        }
        else if(fourOfAKind()){
            javaDollars.win(FOUR_OF_A_KIND_PAYOUT);
            return "Four of a Kind, you win " + FOUR_OF_A_KIND_PAYOUT;
        }
        else if(fullHouse()){
            javaDollars.win(FULL_HOUSE_PAYOUT);
            return "Full House, you win " + FULL_HOUSE_PAYOUT;
        }
        else if(flush()){
            javaDollars.win(FLUSH_PAYOUT);
            return "Flush, you win " + FLUSH_PAYOUT;
        }
        else if(aceHighStraight() || straight()){
            javaDollars.win(STRAIGHT_PAYOUT);
            return "Straight, you win " + STRAIGHT_PAYOUT;
        }
        else if(threeOfAKind()){
            javaDollars.win(THREE_OF_A_KIND_PAYOUT);
            return "Three of a Kind, you win " + THREE_OF_A_KIND_PAYOUT;
        }
        else if(twoPair()){
            javaDollars.win(TWO_PAIR_PAYOUT);
            return "Two Pair, you win " + TWO_PAIR_PAYOUT;
        }
        else if(pairOfJacksOrBetter()){
            javaDollars.win(PAIR_OF_JACKS_OR_BETTER_PAYOUT);
            return "Pair of Jacks or better, you win " + PAIR_OF_JACKS_OR_BETTER_PAYOUT;
        }
        else if(pair()){
            return "Pair, no win";
        }
        else return "High Card, no win";
    }

    public int balance(){
        return javaDollars.getBalance();
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

    private static boolean pairOfJacksOrBetter(){
        for(int i = 0; i < NUMBER_OF_CARDS_AT_PLAY - 1; i++)
            if(deck[i] / 4 == deck[i + 1] / 4 && (deck[i] / 4 == 0 || deck[i] / 4 >= 10)){
                //System.out.println("\n" + deck[i] + ", " + deck[i+1] + "\n");
                return true;
            }
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