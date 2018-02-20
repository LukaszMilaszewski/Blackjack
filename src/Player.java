import java.util.ArrayList;

public class Player {
    ArrayList<Card> player = new ArrayList<Card>();
    
    
    Player(Pot pot) {
        Card firstCard = pot.getRandom();
        Card secondCard = pot.getRandom();
        
        addCard(firstCard);
        addCard(secondCard);
    }
    Player(Pot pot, int i) {
        // 49 40           40 40    - blackjack krupiera, przegrana gracza
        // 49 40           49 40    - dwa BJ
        // 40 40           49 40    - BJ gracza, krupier +21
        // 49 2            49 40 40 - BJ gracza, krupier -21
        // 40 40           40 40    - remis
        
        // krupier 
        if (i == 0) {
            Card firstCard = pot.getCard(40);
            Card secondCard = pot.getCard(3);
            addCard(firstCard);
            addCard(secondCard);
        }
        // gracz
        if (i == 1) {
            Card firstCard = pot.getCard(40);
            Card secondCard = pot.getCard(40);
            addCard(firstCard);
            addCard(secondCard);
        }
    }
    
   void addCard(Card card) {
       player.add(card);
   }
   
   int getSize() {
       return player.size();
   }
   void removeCard(int i) {
       player.remove(i);     
   }
   
   int getValue(int i) {
       return player.get(i).getCardValue();
   }
   
   void printCard(int i) {
       System.out.print(getValue(i)+" ");
   }
   
} 
