package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;
    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();

        car1 = new Car();
        car1.setCarName("Toyota Avanza");
        car1.setCarColor("Silver");
        car1.setCarQuantity(10);

        car2 = new Car();
        car2.setCarName("Honda Civic");
        car2.setCarColor("Black");
        car2.setCarQuantity(5);
    }

    @Test
    void testCreateWithoutId() {
        Car result = carRepository.create(car1);
        assertNotNull(result.getCarId());
        assertEquals("Toyota Avanza", result.getCarName());
    }

    @Test
    void testCreateWithId() {
        String predefinedId = UUID.randomUUID().toString();
        car1.setCarId(predefinedId);

        Car result = carRepository.create(car1);
        assertEquals(predefinedId, result.getCarId());
    }

    @Test
    void testFindAllEmpty() {
        Iterator<Car> iterator = carRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllWithData() {
        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        int count = 0;

        while (iterator.hasNext()) {
            Car car = iterator.next();
            assertNotNull(car);
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testFindByIdFound() {
        Car created = carRepository.create(car1);
        String id = created.getCarId();

        Car found = carRepository.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getCarId());
        assertEquals("Toyota Avanza", found.getCarName());
    }

    @Test
    void testFindByIdNotFound() {
        carRepository.create(car1);
        Car found = carRepository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testUpdateFound() {
        Car created = carRepository.create(car1);
        String id = created.getCarId();

        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Name");
        updatedCar.setCarColor("Updated Color");
        updatedCar.setCarQuantity(20);

        Car result = carRepository.update(id, updatedCar);
        assertNotNull(result);
        assertEquals(id, result.getCarId());
        assertEquals("Updated Name", result.getCarName());
        assertEquals("Updated Color", result.getCarColor());
        assertEquals(20, result.getCarQuantity());

        Car verifyUpdate = carRepository.findById(id);
        assertEquals("Updated Name", verifyUpdate.getCarName());
    }

    @Test
    void testUpdateNotFound() {
        carRepository.create(car1);
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Name");

        Car result = carRepository.update("non-existent-id", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteFound() {
        Car created = carRepository.create(car1);
        String id = created.getCarId();
        carRepository.delete(id);

        Car verifyDeleted = carRepository.findById(id);
        assertNull(verifyDeleted);
    }

    @Test
    void testDeleteNotFound() {
        carRepository.create(car1);
        int initialSize = 0;
        Iterator<Car> iterator = carRepository.findAll();
        while (iterator.hasNext()) {
            iterator.next();
            initialSize++;
        }

        carRepository.delete("non-existent-id");

        int finalSize = 0;
        Iterator<Car> afterIterator = carRepository.findAll();
        while (afterIterator.hasNext()) {
            afterIterator.next();
            finalSize++;
        }
        assertEquals(initialSize, finalSize);
    }
}