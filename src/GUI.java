import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {
        
    public void perform(Game game) {
        
        JFrame window = setFrame();
        JPanel panel = setPanel(window);
        
        JTextField betText = new JTextField("Value");
        
        JPanel subPanelSouth = setSubPanel();
        JPanel subPanelNorth = setSubPanel();
        JPanel subPanelEast = setSubPanel();
        JPanel subPanelWest = setSubPanel();
        
        JLabel labelNorth = new JLabel();
        JLabel labelEast = new JLabel();
        JLabel labelWest = new JLabel();
        JLabel betLabel = new JLabel();
        JLabel humanCards = new JLabel();
        JLabel croupierCards = new JLabel();
        
        // NORTH        
        addToSubPanel(subPanelNorth, setLabel("Your cards:                                ", labelEast));
        addToSubPanel(subPanelNorth, setLabel("Your bet: ", labelNorth));
        addToSubPanel(subPanelNorth, setLabel("     ",betLabel));
        addToSubPanel(subPanelNorth, setLabel("                           Croupier cards: ", labelWest));
        
        // EAST
        addToSubPanel(subPanelEast, setLabel(" ", croupierCards));
        
        // WEST
        addToSubPanel(subPanelWest, setLabel(" ", humanCards));
        
        // SOUTH
        JButton hitButton = setHitButton(game, humanCards);
        JButton stopButton = setStopButton(game, betLabel, croupierCards);
        
        JButton insurance = setInsuranceButton(game, subPanelSouth, betLabel);
        addBetText(subPanelSouth, betText);
        addToSubPanel(subPanelSouth, setBetButton(game, subPanelSouth, betText, betLabel, hitButton, stopButton, insurance, humanCards, croupierCards));
        addToSubPanel(subPanelSouth, hitButton);
        addToSubPanel(subPanelSouth, stopButton);
        addToSubPanel(subPanelSouth, insurance);
        
        // set main panel
        setNorthPanel(subPanelNorth, panel);
        setEastPanel(subPanelEast, panel);
        setWestPanel(subPanelWest, panel);
        setSouthPanel(subPanelSouth, panel);
        
    }
    
    JLabel setLabel(String text, JLabel label) {
        label.setText(text);
        return label;
    }
    
    void addInsuranceText(JPanel subPanel, JTextField textField) {
        textField.setVisible(false);
        subPanel.add(textField);
    }
    
    void addBetText(JPanel subPanel, JTextField textField) {
        textField.setVisible(true);
        subPanel.add(textField);
    } 
    
    void setNorthPanel(JPanel subPanel, JPanel panel) {
        panel.add(subPanel, BorderLayout.NORTH);
    }
    
    void setSouthPanel(JPanel subPanel, JPanel panel) {
        panel.add(subPanel, BorderLayout.SOUTH);
    }
    
    void setEastPanel(JPanel subPanel, JPanel panel) {
        panel.add(subPanel, BorderLayout.EAST);
        
    }
    
    void setWestPanel(JPanel subPanel, JPanel panel) {
        panel.add(subPanel, BorderLayout.WEST);
    }
    
    void addToSubPanel(JPanel subPanel, JButton button) {
        subPanel.add(button);
    }
    
    void addToSubPanel(JPanel subPanel, JLabel label) {
        subPanel.add(label);
    }
    
    void addToSubPanel(JPanel subPanel, JTextField textField) {
        subPanel.add(textField);
    }
    
    JPanel setSubPanel() {
        return new JPanel();
    }
    
    JFrame setFrame() {
        JFrame window = new JFrame("Black Jack");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setSize(450,150);
        window.setResizable(false);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        return window;
    }
    
    JPanel setPanel(JFrame window) {
        JPanel panel = new JPanel(new BorderLayout());
        window.add(panel);
        return panel;
    }
    
    JButton setHitButton(Game game, JLabel humanCards) {
      JButton hitButton = new JButton("Hit");
      hitButton.setVisible(false);
      
      hitButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              Actions.hit(game.pot, game.human);
              StringBuilder strbul  = new StringBuilder();
              //iter = game.croupier.getValue(i);
              for (int i = 0; i < game.human.getSize(); i++) {
                  strbul.append(game.human.getValue(i));
                  strbul.append(" ");
              }
              humanCards.setText(strbul.toString());
          }
      });
      return hitButton;
    }
    
    JButton setStopButton(Game game, JLabel betLabel, JLabel croupierCards) {
        JButton stopButton = new JButton("Stop");
        stopButton.setVisible(false);
        
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (Actions.sum(game.croupier) <= 17) {
                    Actions.hit(game.pot, game.croupier);
                    while (Actions.sum(game.croupier) > 21 && Actions.hasAce(game.croupier))
                            Actions.changeAceValue(game.croupier);
                }
                
                StringBuilder strbul  = new StringBuilder();
                //iter = game.croupier.getValue(i);
                for (int i = 0; i < game.croupier.getSize(); i++) {
                    strbul.append(game.croupier.getValue(i));
                    strbul.append(" ");
                }
                croupierCards.setText(strbul.toString());
                
                
                // sprawdzenie czy gracz ma BJ //
                while (Actions.sum(game.human) > 21 && Actions.hasAce(game.human))
                    Actions.changeAceValue(game.human);
               // System.out.println(Game.ifPlayerHasBJ(game.human));
                if (Game.ifPlayerHasBJ(game.human) && !Game.ifPlayerHasBJ(game.croupier)) {
                    double temp = game.bet.getBet() * 2.5;
                    game.bet.setBet((int) temp);
                    
                    //bet.setInsuranceValue(0);
                    showMessage((int)game.bet.getBet(), betLabel);
                }
                
                
             // sprawdzenie czy krupier posiada +21, gdy gracz +21 to zwrot zakladu
                //                                      gdy gracz -21 to podwojenie zakladu
                if (Game.ifCroupierLost(game.human, game.croupier)) {
                    System.out.println("krupier +21");
                    if (!Game.ifHumanLost(game.human, game.croupier)) {
                        if (Game.ifPlayerHasBJ(game.human))
                            game.bet.setBet((int) (game.bet.getBet() * 2.5));
                        else
                        game.bet.setBet((int) game.bet.getBet() * 2);
                    }
                    else
                        game.bet.setBet((int) (game.bet.getBet() * 1.5));
                    showMessage((int)game.bet.getBet(), betLabel);
                    // showMessage();
                } 
                
             // sprawdzenie czy gracz posiada + 21
                if (Game.ifHumanLost(game.human, game.croupier)) {
                    System.out.println("gracz +21");
                    
                    if (game.bet.getInsuranceValue() == 0)
                        game.bet.setBet(0);
                   // printStatistics(human, croupier, bet, initBet);
                    showMessage((int)game.bet.getBet(), betLabel);
                    //showMessage();
                } 
                
                // sprawdzenie remisu
                if (Actions.isDraw(game.human, game.croupier)) { 
                    System.out.println("Remis.");
                    //game.bet.setBet((int)(game.bet.getBet() * 1.5));
                    //printStatistics(human, croupier, bet, initBet);
                    showMessage((int)game.bet.getBet(), betLabel);
                    //showMessage();
                }
                
                // wylonienie zwyciezcy
                if (Game.getWinner(game.human, game.croupier) == game.human) {
                    System.out.println("gracz wygrywa");
                    game.bet.setBet((int)game.bet.getBet() * 2);
                   // printStatistics(human, croupier, bet, initBet);
                    showMessage((int)game.bet.getBet(), betLabel);
                    //showMessage();
                } else {
                    if (game.bet.getInsuranceValue() == 0)
                        game.bet.setBet(0);
                    //printStatistics(human, croupier, bet, initBet);
                    showMessage((int)game.bet.getBet(), betLabel);
                    //showMessage();
                }
            }
        });   
        return stopButton;
    }
    
    JButton setInsuranceButton(Game game, JPanel subPanel, JLabel label) {
        JButton insurance = new JButton("Insurance");
        insurance.setVisible(false);    
        insurance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = label.getText();
                int value = Integer.valueOf(text) / 2 ;
                label.setText(String.valueOf(value + Integer.valueOf(text)));  
                game.bet.setInsuranceValue(game.bet.getBet() / 2);
                subPanel.revalidate();
                subPanel.repaint();
                insurance.setVisible(false);
           }
        });      
        return insurance;
    }
    
    JButton setBetButton(Game game, JPanel  subPanel, JTextField betText, JLabel label, JButton hit, 
                         JButton stop, JButton insuranceButton, JLabel humanCards, JLabel croupierCards) {
        JButton bet = new JButton("Bet");
        
        bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = betText.getText();
                game.bet.setBet(Integer.valueOf(text));                
                label.setText(text);
                bet.setVisible(false);
                betText.setVisible(false);
                hit.setVisible(true);
                stop.setVisible(true);
                
                StringBuilder strbul  = new StringBuilder();
                //iter = game.croupier.getValue(i);
                for (int i = 0; i < game.human.getSize(); i++) {
                    strbul.append(game.human.getValue(i));
                    strbul.append(" ");
                }
                humanCards.setText(strbul.toString());
                
                StringBuilder strbul1  = new StringBuilder();
                //iter = game.croupier.getValue(i);
                    strbul1.append(game.croupier.getValue(0));
                    strbul1.append(" ");
                croupierCards.setText(strbul1.toString());
                
                // obsluga przycisku ubezpieczenia  
                if (game.ifCroupierHasAce(game.croupier)) 
                   insuranceButton.setVisible(true);
               
                game.bet.setInsuranceValue(0);
                 
                subPanel.revalidate();
                subPanel.repaint();
            }
        });  
        return bet;   
    }
    
    void showMessage(int bet, JLabel betLabel) {
    
        int temp = Integer.valueOf(betLabel.getText());
        String msg = "Twoj zaklad: " + betLabel.getText() +
                "\nWygrana: " + bet + "\nZysk: " + (bet - temp);
        JOptionPane optionPane = new NarrowOptionPane();
        optionPane.setMessage(msg);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Wynik");
        dialog.setVisible(true);
        System.exit(0);
      }
} 

class NarrowOptionPane extends JOptionPane {

    NarrowOptionPane() {
    }

    public int getMaxCharactersPerLineCount() {
      return 100;
    }
  }