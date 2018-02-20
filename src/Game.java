import java.util.Scanner;

public class Game {
    Bet bet;
    Player human;
    Player croupier;
    Pot pot;
    
    Game() {
        bet = new Bet();
        pot = new Pot(1);
        human = new Player(pot);
        croupier = new Player(pot);
    }
    
    void setBet(int value) {
        bet.setBet(value);
    }
    
    void perform() {
        
        printStartingDraw(human, croupier);
        /*
        // sprawdzenie czy krupier ma asa, opcja z ubezpieczeniem
        if (ifCroupierHasAce(croupier))
            performInsurance(bet);
        
        double initBet = bet.getBet() + bet.getInsuranceValue();
            
        performHumanMoves(pot, human, croupier);  
        performCroupierMoves(pot, croupier);
        // tutaj, rozkminic dialog boxy i wylaczenie apki po nich !!!!!!!
        
        
        // sprawdzenie czy gracz ma blackjacka
      
        
        // sprawdzenie czy krupier posiada +21, gdy gracz +21 to zwrot zakladu
        //                                      gdy gracz -21 to podwojenie zakladu
        if (ifCroupierLost(human, croupier)) {
            System.out.println("krupier +21");
            if (!ifHumanLost(human, croupier))
                bet.setBet((int)bet.getBet() * 2);
            else
                bet.setBet(bet.getBet() * 1.5);
            printStatistics(human, croupier, bet, initBet);    
            return;
        } 
          
        // sprawdzenie czy gracz posiada + 21
        if (ifHumanLost(human, croupier)) {
            System.out.println("gracz +21");
            bet.setBet(0);
            printStatistics(human, croupier, bet, initBet);
            return;
        } 
        
        // sprawdzenie remisu
        if (Actions.isDraw(human, croupier)) { 
            System.out.println("Remis.");
            bet.setBet(bet.getBet() * 1.5);
            printStatistics(human, croupier, bet, initBet);
            return;
        }
        
        // wylonienie zwyciezcy
        if (getWinner(human, croupier) == human) {
            System.out.println("gracz wygrywa");
            bet.setBet(bet.getBet() * 2);
            printStatistics(human, croupier, bet, initBet);
            return;
        } else {
            bet.setBet(0);
            printStatistics(human, croupier, bet, initBet);
            return;
        } */
    }
    
    static boolean ifPlayerHasBJ(Player player) {
        return Actions.sum(player) == 21 ? true : false;
    }
    
    void performInsurance(Bet bet) {
        System.out.println("\n\nCzy chcesz wykupic ubezpieczenie? t/n");
        Scanner reader = new Scanner(System.in);
        char input = reader.next().charAt(0);
        if ( input == 't') 
            bet.setInsuranceValue(bet.getBet() / 2);
         else
            bet.setInsuranceValue(0);
    }
    
    boolean ifCroupierHasAce(Player croupier) {
        return croupier.getValue(0) == 11 ? true : false;
    }
    
    double performBet(Player player) { 
        System.out.println("Podaj wartosc zakladu: ");
        Scanner reader = new Scanner(System.in);
        return reader.nextDouble();
    }
    
    static Player getWinner(Player human, Player croupier) {  
        if (human == Actions.getWinner(human, croupier)) { 
            printHumanWon(human, croupier);
            return human;
        }
        printHumanLost(human, croupier);
        return croupier;
    }
    
    
    boolean isDraw(Player human, Player croupier) {
        if (Actions.isDraw(human, croupier)) {
            System.out.println("Remis.");
            return true;
        }
        return false;
    }
    
    static boolean ifCroupierLost(Player human, Player croupier) {
        if (Actions.ifPlayerLost(croupier)) {
            printHumanWon(human, croupier);
            return true;
        }
        return false;
    }
    
    void performCroupierMoves(Pot pot, Player croupier) {
        while (Actions.sum(croupier) <= 17) {
            Actions.hit(pot, croupier);
            while (Actions.sum(croupier) > 21 && Actions.hasAce(croupier))
                    Actions.changeAceValue(croupier);
        }
    }
    void printCroupierFirstCard(Player croupier) {
        System.out.println("Karta krupiera: " + croupier.getValue(0));
    }
    
    void performHumanMoves(Pot pot, Player human, Player croupier) {
        Scanner reader = new Scanner(System.in);
          while (pot.getSize() != 0) {
            System.out.println("\n\nCzy chcesz dobrac karte? t/n");
            char input = reader.next().charAt(0);
            if (input == 'n') {
                break;
            } else  if (input == 't'){
                Actions.hit(pot, human);
                printCroupierFirstCard(croupier);
                printHumanCards(human);
            }
        }
    }
    
    void printCroupierCards(Player croupier) {
        System.out.print("Karty krupiera: ");
        Actions.printPlayerCards(croupier);
    }
    
    void printHumanCards(Player human) {
        System.out.print("Twoje karty: ");
        Actions.printPlayerCards(human);  
    }
    
    static void printHumanWon(Player human, Player croupier) {
        System.out.println("\nWygrales.");
    }
    
    static void printHumanLost(Player human, Player croupier) {
        System.out.println("\n\nPrzegrales.");
        
    }
    
    void printStatistics(Player human, Player croupier, Bet bet, double initBet) {
        System.out.println("Suma Twoich kart wynosi: " + Actions.sum(human));
        printHumanCards(human);
        System.out.println("\nSuma kart krupiera wynosi: " + Actions.sum(croupier));
        printCroupierCards(croupier);
        System.out.println("\n------------------------------------");
        System.out.println("Zaklad: " + initBet);
        System.out.println("Twoje saldo: " + bet.getBet());
        System.out.println("Zysk: " + (bet.getBet() - initBet));
    }
    
    void printStartingDraw(Player human, Player croupier) {
        System.out.print("\nKarta krupiera: ");
        Actions.printCroupierStartingCard(croupier);
        System.out.print("\nTwoje karty startowe: ");
        Actions.printHumanStartingCards(human);
    }
    
    static boolean ifHumanLost(Player human, Player croupier) {
        if (Actions.ifPlayerLost(human)) {
            printHumanLost(human, croupier);
            return true;
        }
        return false;
    }
}
