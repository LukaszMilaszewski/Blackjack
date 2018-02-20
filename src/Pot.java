import java.util.ArrayList;
import java.util.Random;

public class Pot {
    private ArrayList<Card> pot = new ArrayList<Card>();

    Pot(int potsQuantity) {
        for (int k = 0; k < potsQuantity; k++) {
            for (int i = 2; i <= 10; i++) {
                for (int j = 0; j < 4; j++) 
                    addCard(i);
            }
            
            for (int i = 0; i < 12; i++)
                addCard(10);
            
            for (int i = 0; i < 4; i++)
                addCard(11);
        }
    }
    
    
        
    int getSize() {
        return pot.size();
    }
    
    void addCard(int cardValue) {
        Card card = new Card(cardValue);
        pot.add(card);
    }
    
    int getValue(int i) {
        return pot.get(i).getCardValue();
    }
    
    void printPot() {
        for (int i = 0; i < pot.size(); i++) 
            System.out.println(getValue(i));
    }
    
    Card getCard(int i) {
        Card card = pot.get(i);
        pot.remove(i);
        return card;
    }
    
    Card getRandom() {
        Random random = new Random();
        int randomNumber = random.nextInt(pot.size());
        Card card = new Card(getValue(randomNumber));
        pot.remove(randomNumber);
        return card;
    }
    
}
