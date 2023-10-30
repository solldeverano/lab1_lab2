package lab2;

public abstract class Container {

    int ID;
    int weight;
    String type;

    public Container(int ID, int weight, String type) {
        this.ID = ID;
        this.weight = weight;
        this.type = type;
    }

    public abstract double consumption();

    public int getID() {
        return ID;
    }

    public int getWeight() {
        return weight;
    }

    public boolean equals(Container other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == this.getClass() && other.ID == this.ID && other.weight == this.weight) {
            return true;
        }

        return false;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }
}
