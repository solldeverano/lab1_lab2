package lab2;

public class HeavyContainer extends Container {
    public HeavyContainer(int ID, int weight, String type) {
        super(ID, weight, type);
    }

    @Override
    public double consumption() {
        return 3.00 * weight;
    }
}
