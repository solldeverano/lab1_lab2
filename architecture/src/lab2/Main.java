package lab2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static Port findPortById(List<Port> ports, int id) {
        for (Port port : ports) {
            if (port.getID() == id) {
                return port;
            }
        }
        System.out.println("Помилка: Порт з ID " + id + " не знайдений.");
        return null; // Повертаємо null, якщо порт не знайдено
    }
    public static void main(String[] args) {
        MainJson.loadDataFromJson("resources\\file.json");
        List<Port> ports = MainJson.getPorts();
        List<Ship> ships = MainJson.getShips();
        List<Container> containers = MainJson.getContainers();

        // Перевіряємо, чи дані були завантажені успішно
        if (ports.isEmpty() || ships.isEmpty() || containers.isEmpty()) {
            System.out.println("Помилка завантаження даних з JSON.");
            return;
        }

        // Розміщення кораблів у відповідних портах на підставі даних з JSON
        for (Ship ship : ships) {
            if (ship.getCurrentPort() != null) {
                Port currentPort = findPortById(ports, ship.getCurrentPort().getID());
                if (currentPort != null) {
                    currentPort.incomingShip(ship);
                } else {
                    System.out.println("Помилка: Порт з ID " + ship.getCurrentPort().getID() + " не знайдений.");
                }
            } else {
                Port initialPort = ports.get(0);
                initialPort.incomingShip(ship);
            }
        }
        for (Port port : ports) {
            System.out.println("Порт " + port.getID() + " координати: Latitude = " + port.getLatitude() + ", Longitude = " + port.getLongitude());
        }


// Розподілення контейнерів між портами рівномірно
        List<Container> unplacedContainers = new ArrayList<>(containers);
        int totalContainers = unplacedContainers.size();
        int containersPerPort = totalContainers / ports.size();

        for (Port port : ports) {
            for (int i = 0; i < containersPerPort; i++) {
                if (unplacedContainers.isEmpty()) {
                    break;
                }
                Container container = unplacedContainers.remove(0);
                port.addContainer(container);
            }
        }


        // Виведення інформації про поточні контейнери в портах
        System.out.println("Список поточних контейнерів у портах:");
        for (Port port : ports) {
            System.out.println("Порт " + port.getID() + ":");
            List<Container> currentContainers = port.getContainers();
            for (Container container : currentContainers) {
                System.out.println("Контейнер ID: " + container.getID() + ", Вага: " + container.getWeight());
            }
        }
        // Виведення інформації про поточні кораблі у портах
        System.out.println("Список поточних кораблів у портах:");
        for (Port port : ports) {
            System.out.println("Порт " + port.getID() + ":");
            List<Ship> currentShips = port.getCurrentShips();
            for (Ship ship : currentShips) {
                System.out.println("Корабель ID: " + ship.getID());
            }
        }



        // Завантаження контейнерів на кораблі з порту
        for (Ship ship : ships) {
            if (ship.getCurrentPort() != null) {
                Port sourcePort = ship.getCurrentPort();
                List<Container> containersInPort = sourcePort.getContainers();

                if (!containersInPort.isEmpty()) {
                    Container containerToLoad = containersInPort.get(0);
                    if (ship.load(containerToLoad)) {
                        System.out.println("Контейнер ID: " + containerToLoad.getID() + " був успішно завантажений на корабель ID: " + ship.getID());
                    } else {
                        System.out.println("Завантаження контейнера на корабель ID: " + ship.getID() + " не вдалося.");
                    }
                } else {
                    System.out.println("Порт " + sourcePort.getID() + " не має контейнерів для завантаження.");
                }
            }
        }

        // Заправка кораблів
        for (Ship ship : ships) {
            ship.reFuel(100000);
        }
        System.out.println("Список поточних контейнерів у портах:");
        for (Port port : ports) {
            System.out.println("Порт " + port.getID() + ":");
            List<Container> currentContainers = port.getContainers();
            for (Container container : currentContainers) {
                System.out.println("Контейнер ID: " + container.getID() + ", Вага: " + container.getWeight());
            }
        }
        // Відправка кораблів у інший порт
        if (!ports.isEmpty() && !ships.isEmpty()) {
            Port sourcePort = ports.get(0);
            Port targetPort = ports.get(1);

            for (Ship ship : ships) {
                if (ship.getCurrentPort() == sourcePort) {
                    if (ship.sailTo(targetPort)) {
                        System.out.println("Корабель ID: " + ship.getID() + " був відправлений на порт " + targetPort.getID());
                    } else {
                        System.out.println("Не вдалося відправити корабель ID: " + ship.getID() + " на порт " + targetPort.getID());
                    }
                } else {
                    System.out.println("Корабель ID: " + ship.getID() + " не може бути відправлений, оскільки він не знаходиться в початковому порту.");
                }
            }
        }

        // Розвантаження кораблів у порту, до якого вони прибули
        for (Port port : ports) {
            List<Container> portContainers = port.getContainers(); // Отримуємо список контейнерів для поточного порту

            for (int i = 0; i < containersPerPort; i++) {
                if (unplacedContainers.isEmpty()) {
                    break;
                }
                Container container = unplacedContainers.remove(0);

                // Перевірка, чи контейнер з таким айді вже розміщений в поточному порту
                boolean containerExistsInPort = false;
                for (Container portContainer : portContainers) {
                    if (portContainer.getID() == container.getID()) {
                        containerExistsInPort = true;
                        break;
                    }
                }

                if (!containerExistsInPort) {
                    port.addContainer(container);
                    portContainers.add(container); // Додати контейнер до списку контейнерів поточного порту
                }
            }
        }




        System.out.println("Список поточних контейнерів у портах:");
        for (Port port : ports) {
            System.out.println("Порт " + port.getID() + ":");
            List<Container> currentContainers = port.getContainers();
            for (Container container : currentContainers) {
                System.out.println("Контейнер ID: " + container.getID() + ", Вага: " + container.getWeight());
            }
        }

        MainJson.saveDataToJson("resources\\output.json");

    }




}
