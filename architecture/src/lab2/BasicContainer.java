package lab2;

public class BasicContainer extends Container {
    public BasicContainer(int ID, int weight, String type) {
        super(ID, weight, type);
    }

    @Override
    public double consumption() {
        return 2.50 * weight;
    }
}
