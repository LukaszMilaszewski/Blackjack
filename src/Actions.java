
public class Actions {
        
    static void hit(Pot pot, Player player) {
        Card card = pot.getRandom();
        player.addCard(card);    
    }
    
    static boolean hasAce(Player player) {
        for (int i = 0; i < player.getSize(); i++) 
            if (player.getValue(i) == 11)
                return true;
        return false;   
    }
    
    static int acePosition(Player player) {
        for (int i = 0; i < player.getSize(); i++) 
            if (player.getValue(i) == 11)
                return i;
        return -1;
    }
    
    static void printHumanStartingCards(Player human) {   
        human.printCard(0);
        human.printCard(1);
    }
    
    static void printCroupierStartingCard(Player croupier) {
        croupier.printCard(0);
    }
    
    static void printPlayerCards(Player player) {
        for (int i = 0; i < player.getSize(); i++) 
            System.out.print(player.getValue(i) + " ");
    }
    
    static int sum(Player player) {
        int sum = 0;
        for (int i = 0; i < player.getSize(); i++)
            sum += player.getValue(i);
        return sum;
    }
    
    static boolean ifPlayerLost(Player player) {
        while (sum(player) > 21 && hasAce(player)) 
            changeAceValue(player);
        return sum(player) > 21 ? true : false;
    }
    
    static void changeAceValue(Player player) {
        int position = acePosition(player);
        player.removeCard(position);
        Card card = new Card(1);
        player.addCard(card);
    }
    
    static boolean isDraw(Player player1, Player player2) {
        return ( sum(player1) == sum(player2) ) ? true : false;
    }
    
    static Player getWinner(Player player1, Player player2) {
        return (sum(player1) > sum(player2)) ? player1 : player2;
    }
}
