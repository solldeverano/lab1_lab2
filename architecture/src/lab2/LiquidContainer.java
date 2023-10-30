package lab2;

public class LiquidContainer extends HeavyContainer {
    public LiquidContainer(int ID, int weight, String type) {
        super(ID, weight, type);
    }

    @Override
    public double consumption() {
        return 4.00 * weight;
    }
}
