package lab2;

import java.util.ArrayList;
import java.util.List;

public class Port implements IPort{

    int ID;

    double latitude;
    double longitude;
    private List<Container> containers = new ArrayList<>();
    private ArrayList<Ship> history = new ArrayList<>();
    private ArrayList<Ship> current = new ArrayList<>();

    public Port(int ID, double latitude, double longitude) {
        this.ID = ID;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public void incomingShip(Ship s){
        s.setCurrentPort(this);
        current.add(s);

        // Додайте контейнери, які були розвантажені з корабля, до порту
        for (Container container : s.getCurrentContainers()) {
            containers.add(container);
        }

    }
    @Override
    public void outgoingShip(Ship s) {
        s.setCurrentPort(null);
        current.remove(s);
        history.add(s);
    }
    public void removeContainer(Container container) {
        containers.remove(container);
    }

    public double getDistance(Port other) {
        final int R = 6371;
        double latitude1 = Math.toRadians(this.latitude);
        double longitude1 = Math.toRadians(this.longitude);
        double latitude2 = Math.toRadians(other.latitude);
        double longitude2 = Math.toRadians(other.longitude);

        double deltaLat = latitude2 - latitude1;
        double deltaLong = longitude2 - longitude1;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(deltaLong / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R*c;
        return distance;
    }





    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    // Додайте метод, який дозволить додавати контейнери до порту
    public void addContainer(Container container) {
        containers.add(container);
    }

    // Додайте метод, який дозволить отримувати список контейнерів у порту
    public List<Container> getContainers() {
        return containers;
    }


    public ArrayList<Ship> getCurrentShips() {
        return current;
    }


    public void setCurrentShips(ArrayList<Ship> ships) {
        this.current = ships;
    }

    public ArrayList<Ship> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Ship> history) {
        this.history = history;
    }

    public ArrayList<Ship> getCurrent() {
        return current;
    }

    public void setCurrent(ArrayList<Ship> current) {
        this.current = current;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
