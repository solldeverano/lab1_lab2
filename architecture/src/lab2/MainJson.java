package lab2;

import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainJson {

    private static List<Port> ports = new ArrayList<>();
    private static List<Ship> ships = new ArrayList<>();
    private static List<Container> containers = new ArrayList<>();

    public static void loadDataFromJson(String jsonFilePath) {
        try {
            FileReader fileReader = new FileReader(jsonFilePath);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(fileReader);

            // Завантаження контейнерів
            if (jsonObject.has("containers")) {
                JsonArray containerArray = jsonObject.getAsJsonArray("containers");
                for (int i = 0; i < containerArray.size(); i++) {
                    JsonObject containerObject = containerArray.get(i).getAsJsonObject();
                    if (containerObject.has("ID") && containerObject.has("type")) {
                        int containerID = containerObject.get("ID").getAsInt();
                        String type = containerObject.get("type").getAsString();

                        // Створюємо контейнер з базового класу
                        // Створюємо контейнер з базового класу
                        Container container = null;
                        if ("Basic".equals(type)) {
                            container = new BasicContainer(containerID, containerObject.get("weight").getAsInt(), type); // Встановлюємо початкову вагу як 0
                        } else if ("Heavy".equals(type)) {
                            container = new HeavyContainer(containerID, containerObject.get("weight").getAsInt(), type);
                        } else if ("Liquid".equals(type)) {
                            container = new LiquidContainer(containerID, containerObject.get("weight").getAsInt(), type);
                        } else if ("Refrigerated".equals(type)) {
                            container = new RefrigeratedContainer(containerID, containerObject.get("weight").getAsInt(), type);
                        }


                        if (container != null) {
                            // Оновлюємо вагу контейнера з урахуванням типу
                            int containerWeight = (int) container.consumption();
                            container.setWeight(containerWeight);

                            containers.add(container);
                        } else {
                            System.out.println("Помилка: Невідомий тип контейнера.");
                        }
                    } else {
                        System.out.println("Помилка: Недостатньо даних для завантаження контейнера.");
                    }
                }
            } else {
                System.out.println("Помилка: Відсутні дані про контейнери.");
            }

            // Завантаження портів
            if (jsonObject.has("ports")) {
                JsonArray portArray = jsonObject.getAsJsonArray("ports");
                for (int i = 0; i < portArray.size(); i++) {
                    JsonObject portObject = portArray.get(i).getAsJsonObject();
                    if (portObject.has("ID") && portObject.has("latitude") && portObject.has("longitude")) {
                        int portID = portObject.get("ID").getAsInt();
                        double latitude = portObject.get("latitude").getAsDouble();
                        double longitude = portObject.get("longitude").getAsDouble();

                        Port port = new Port(portID, latitude, longitude);
                        ports.add(port);
                    } else {
                        System.out.println("Помилка: Недостатньо даних для завантаження порту.");
                    }
                }
            } else {
                System.out.println("Помилка: Відсутні дані про порти.");
            }

            // Завантаження кораблів
            if (jsonObject.has("ships")) {
                JsonArray shipArray = jsonObject.getAsJsonArray("ships");
                for (int i = 0; i < shipArray.size(); i++) {
                    JsonObject shipObject = shipArray.get(i).getAsJsonObject();
                    int shipID = shipObject.get("ID").getAsInt();
                    double fuel = shipObject.get("fuel").getAsDouble();
                    int totalWeightCapacity = shipObject.get("totalWeightCapacity").getAsInt();
                    int maxNumberOfAllContainers = shipObject.get("maxNumberOfAllContainers").getAsInt();
                    int maxNumberOfHeavyContainers = shipObject.get("maxNumberOfHeavyContainers").getAsInt();
                    int maxNumberOfLiquidContainers = shipObject.get("maxNumberOfLiquidContainers").getAsInt();
                    int maxNumberOfRefrigeratedContainers = shipObject.get("maxNumberOfRefrigeratedContainers").getAsInt();
                    double fuelConsumptionPerKM = shipObject.get("fuelConsumptionPerKM").getAsDouble();

                    // Перевірка наявності ключа "currentPort" і отримання інформації про порт
                    Port currentPort = null;
                    if (shipObject.has("currentPort") && !shipObject.get("currentPort").isJsonNull()) {
                        JsonObject portObject = shipObject.getAsJsonObject("currentPort");
                        int portID = portObject.get("ID").getAsInt();

                        Port foundPort = Main.findPortById(ports, portID);
                        if (foundPort != null) {
                            currentPort = foundPort;
                        } else {
                            System.out.println("Помилка: Порт з ID " + portID + " не знайдений.");
                        }
                    }

                    Ship ship = new Ship(shipID, fuel, currentPort, totalWeightCapacity, maxNumberOfAllContainers, maxNumberOfHeavyContainers, maxNumberOfLiquidContainers, maxNumberOfRefrigeratedContainers, fuelConsumptionPerKM);
                    ships.add(ship);
                }
            } else {
                System.out.println("Помилка: Відсутні дані про кораблі.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveDataToJson(String jsonFilePath) {
        try (Writer writer = new FileWriter(jsonFilePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = new JsonObject();

            // Серіалізувати порти
            JsonArray portArray = new JsonArray();
            for (Port port : ports) {
                JsonObject portObject = new JsonObject();
                portObject.addProperty("ID", port.getID());
                portObject.addProperty("latitude", port.getLatitude());
                portObject.addProperty("longitude", port.getLongitude());

                // Серіалізувати кораблі в поточному порту
                JsonArray currentShipArray = new JsonArray();
                for (Ship ship : port.getCurrentShips()) {
                    currentShipArray.add(ship.getID());
                }
                portObject.add("currentShips", currentShipArray);

                // Серіалізувати контейнери в поточному порту
                JsonArray currentContainerArray = new JsonArray();
                for (Container container : port.getContainers()) {
                    currentContainerArray.add(container.getID());
                }
                portObject.add("currentContainers", currentContainerArray);

                // Серіалізувати кораблі в історії порту
                JsonArray historyShipArray = new JsonArray();
                for (Ship ship : port.getHistory()) {
                    historyShipArray.add(ship.getID());
                }
                portObject.add("historyShips", historyShipArray);

                portArray.add(portObject);
            }

            // Серіалізувати кораблі
            JsonArray shipArray = new JsonArray();
            for (Ship ship : ships) {
                JsonObject shipObject = new JsonObject();
                shipObject.addProperty("ID", ship.getID());
                shipObject.addProperty("fuel", ship.getFuel());
                shipObject.addProperty("totalWeightCapacity", ship.getTotalWeightCapacity());
                shipObject.addProperty("maxNumberOfAllContainers", ship.getMaxNumberOfAllContainers());
                shipObject.addProperty("maxNumberOfHeavyContainers", ship.getMaxNumberOfHeavyContainers());
                shipObject.addProperty("maxNumberOfLiquidContainers", ship.getMaxNumberOfLiquidContainers());
                shipObject.addProperty("maxNumberOfRefrigeratedContainers", ship.getMaxNumberOfRefrigeratedContainers());
                shipObject.addProperty("fuelConsumptionPerKM", ship.getFuelConsumptionPerKM());

                // Серіалізувати поточний порт корабля
                if (ship.getCurrentPort() != null) {
                    JsonObject portObject = new JsonObject();
                    portObject.addProperty("ID", ship.getCurrentPort().getID());
                    shipObject.add("currentPort", portObject);
                } else {
                    shipObject.add("currentPort", null);
                }

                shipArray.add(shipObject);
            }

            // Серіалізувати контейнери
            JsonArray containerArray = new JsonArray();
            for (Container container : containers) {
                JsonObject containerObject = new JsonObject();
                containerObject.addProperty("ID", container.getID());
                containerObject.addProperty("type", container.getType());
                containerObject.addProperty("weight", container.getWeight());
                containerArray.add(containerObject);
            }

            jsonObject.add("ports", portArray);
            jsonObject.add("ships", shipArray);
            jsonObject.add("containers", containerArray);

            gson.toJson(jsonObject, writer);

            System.out.println("Дані збережено у файл " + jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Помилка при збереженні даних у файл.");
        }
    }

    public static List<Port> getPorts() {
        return ports;
    }

    public static List<Ship> getShips() {
        return ships;
    }

    public static List<Container> getContainers() {
        return containers;
    }
}
