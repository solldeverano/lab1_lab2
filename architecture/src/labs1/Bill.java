package labs1;
public class Bill {
    private double limitingAmount;
    private double currentDebt;


    public Bill(double limitingAmount, double currentDebt) {
        this.limitingAmount = limitingAmount;
        this.currentDebt = currentDebt;

    }
    public boolean check(double amount) {


        return currentDebt + amount > limitingAmount;
    }


    public void add(double amount) {
        currentDebt += amount;
    }

    // Метод для оплати рахунку
    public void pay(double amount) {
        if (amount <= currentDebt) {
            currentDebt -= amount;
        } else {
            currentDebt = 0.0;
        }
    }


    public void changeTheLimit(double amount) {
        limitingAmount = amount;
    }


    public double getLimitingAmount() {
        return limitingAmount;
    }

    public void setLimitingAmount(double limitingAmount) {
        this.limitingAmount = limitingAmount;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(double currentDebt) {
        this.currentDebt = currentDebt;
    }
}
