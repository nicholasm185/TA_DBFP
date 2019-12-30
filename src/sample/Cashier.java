package sample;

public class Cashier {

    private int cashierID;
    private String cashierName;

    public Cashier(int cashierID, String cashierName) {
        this.cashierID = cashierID;
        this.cashierName = cashierName;
    }

    public int getCashierID() {
        return cashierID;
    }

    public void setCashierID(int cashierID) {
        this.cashierID = cashierID;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }
}
