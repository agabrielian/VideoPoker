/**
 * Created by alexgabrielian on 2/6/16.
 */

public class JavaDollar {

    private int balance;

    public JavaDollar(){
        balance = 0;
    }

    public JavaDollar(int initialBalance){
        balance = initialBalance;
    }

    public int getBalance(){
        return balance;
    }

    public void win(int amount){
        balance += amount;
    }

    public void bet(int amount){
        balance -= amount;
    }
}
