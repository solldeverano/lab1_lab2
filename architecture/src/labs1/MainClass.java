package labs1;

import java.util.Scanner;

public class MainClass {



    public static void main(String[] args) {
        Operator operator1 = new Operator(0, 1, 1, 0.5, 5);
        Operator operator2 = new Operator(1, 1.5, 2, 0.2, 10);

        Bill bill1 = new Bill(500, 0); // Set initial currentDebt to 0
        Bill bill2 = new Bill(400, 0); // Set initial currentDet to 0

        Customer customer1 = new Customer(0, "Sasha", 17, operator1, bill1);
        Customer customer2 = new Customer(1, "Nastya", 22, operator1, bill2);

        System.out.println(customer1.getName() + "'s current debt: " + customer1.getBill().getCurrentDebt());
        System.out.println(customer2.getName() + "'s current debt: " + customer2.getBill().getCurrentDebt());

        customer1.talk(25, customer2); // Simulate talking between customer1 and customer2
        System.out.println("Customer " + customer1.getName()+ " spoke with "+ customer2.getName()+" for 30 minute ");
        System.out.println(customer1.getName() + "'s Final Bill: " + customer1.getBill().getCurrentDebt());

        System.out.println("Customer " + customer2.getName() + " sent 10 messages to " + customer1.getName());
        customer2.message(10, customer1); // Simulate messaging between customer1 and customer2
        System.out.println(customer2.getName() + "'s current debt: " + customer2.getBill().getCurrentDebt());

        customer2.connection(300);
        System.out.println("Customer " + customer2.getName() + " used 300 mb  " );
        System.out.println(customer2.getName() + "'s current debt: " + customer2.getBill().getCurrentDebt());

        System.out.println("Change limit");
        System.out.println("Choose customer Sasha - 1 , Nastya - 2 ");
        Scanner scanner = new Scanner(System.in);
        int cus = scanner.nextInt();
        if(cus == 1){
            System.out.println("Castomer Sasha's limit: " + customer1.getBill().getLimitingAmount() + ", enter new limit:" );
            Scanner sc = new Scanner(System.in);
            double customer1NewLimit = sc.nextDouble();
            customer1.getBill().changeTheLimit(customer1NewLimit);
            System.out.println("Castomer Sasha's new limit: " + customer1.getBill().getLimitingAmount());
        } else if (cus == 2) {
            System.out.println("Castomer Nastya's limit: " + customer2.getBill().getLimitingAmount() + ", enter new limit:" );
            Scanner sc = new Scanner(System.in);
            double customer2NewLimit = sc.nextDouble();
            customer2.getBill().changeTheLimit(customer2NewLimit);
            System.out.println("Castomer Nastya's new limit: " + customer2.getBill().getLimitingAmount());

        } else{
            System.out.println("Your input is wrong!");
        }


    }
}
