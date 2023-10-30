package labs1;
public class Customer {
    private int ID;
    private String name;
    private int age;
    private Operator operator;
    private Bill bill;



    public Customer(int ID, String name, int age, Operator operator, Bill bill) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.operator = operator;
        this.bill = bill;
    }


    public void talk(int minute, Customer other) {
        double cost = operator.calculateTalkingCost(minute, this);

        if (bill.check(getBill().getCurrentDebt() + cost)) {
            System.out.println("Talk would exceed the limiting amount.");
            return;
        }

        if (!bill.check(cost)) {
            bill.add(cost);
        } else {
            System.out.println("Talk exceeded the limiting amount.");
        }

    }

    public void message(int quantity, Customer receiver) {
        double cost = operator.calculateMessageCost(quantity, this, receiver);

        if (cost > 0) {
            bill.add(cost);
        } else {
            System.out.println("Messages exceeded the limiting amount.");
        }
    }





    public void connection(double amount) {
        if (!bill.check(amount)) {
            double cost = operator.calculateNetworkCost(amount);
            bill.add(cost);
        } else {
            System.out.println("Internet connection exceeded the limiting amount.");
        }
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
