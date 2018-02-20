
public class Bet {
    private int bet;
    private double insuranceValue;
    
    Bet() {
        setInsuranceValue(0);
    }
    
    public double getBet() {
        return bet;
    }

    public void setBet(int d) {
        this.bet = d;
    }
    public double getInsuranceValue() {
        return insuranceValue;
    }
    public void setInsuranceValue(double d) {
        this.insuranceValue = d;
    }
    
}
