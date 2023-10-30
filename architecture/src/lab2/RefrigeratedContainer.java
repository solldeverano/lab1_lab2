package lab2;

public class RefrigeratedContainer extends HeavyContainer {
    public RefrigeratedContainer(int ID, int weight, String type) {
        super(ID, weight, type);
    }

    @Override
    public double consumption() {
        return 5.00 * weight;
    }
}
