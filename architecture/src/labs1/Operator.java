package labs1;
public class Operator {
    private int ID;
    private double talkingCharge;
    private double messageCost;
    private double networkCharge;
    private int discountRate;


    public Operator(int ID, double talkingCharge, double messageCost, double networkCharge, int discountRate) {
        this.ID = ID;
        this.talkingCharge = talkingCharge;
        this.messageCost = messageCost;
        this.networkCharge = networkCharge;
        this.discountRate = discountRate;
    }

    public double calculateTalkingCost(int minute, Customer customer) {
        double cost = minute * talkingCharge;

        // Apply discount based on customer's age
        if (customer.getAge() < 18) {
            cost -= (cost * (discountRate / 100.0));
        }

        return cost;
    }

    public double calculateMessageCost(int quantity, Customer sender, Customer receiver) {
        double cost = quantity * messageCost;

        double discountedCost = cost;
        if (sender.getAge() < 18 || sender.getAge() > 65) {
            discountedCost -= (cost * discountRate / 100.0);
        }
          if (sender.getOperator() == receiver.getOperator() ){
              discountedCost -= (cost * discountRate / 100.0);
          }
       // sender.getBill().add(discountedCost);

        if (sender.getBill().check(discountedCost)) {
            System.out.println("Messages exceeded the sender's limiting amount.");
            return 0; // Return 0 cost
        }
        else{return discountedCost;}
    }




    public double calculateNetworkCost(double amount) {
        double cost = amount * networkCharge;
        return cost;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getTalkingCharge() {
        return talkingCharge;
    }

    public void setTalkingCharge(double talkingCharge) {
        this.talkingCharge = talkingCharge;
    }

    public double getMessageCost() {
        return messageCost;
    }

    public void setMessageCost(double messageCost) {
        this.messageCost = messageCost;
    }

    public double getNetworkCharge() {
        return networkCharge;
    }

    public void setNetworkCharge(double networkCharge) {
        this.networkCharge = networkCharge;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }
}
