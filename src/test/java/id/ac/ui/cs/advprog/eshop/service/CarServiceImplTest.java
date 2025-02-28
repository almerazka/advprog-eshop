package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        car1 = new Car();
        car1.setCarId("CAR-001");
        car1.setCarName("Toyota Avanza");
        car1.setCarColor("Silver");
        car1.setCarQuantity(10);

        car2 = new Car();
        car2.setCarId("CAR-002");
        car2.setCarName("Honda Civic");
        car2.setCarColor("Black");
        car2.setCarQuantity(5);
    }

    @Test
    void testCreate() {
        when(carRepository.create(car1)).thenReturn(car1);
        Car result = carService.create(car1);

        assertNotNull(result);
        assertEquals(car1.getCarId(), result.getCarId());
        assertEquals(car1.getCarName(), result.getCarName());
        assertEquals(car1.getCarColor(), result.getCarColor());
        assertEquals(car1.getCarQuantity(), result.getCarQuantity());
        verify(carRepository, times(1)).create(car1);
    }

    @Test
    void testFindAll() {
        List<Car> carList = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(carList.iterator());
        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(car1.getCarId(), result.get(0).getCarId());
        assertEquals(car2.getCarId(), result.get(1).getCarId());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindAllWithEmptyList() {
        when(carRepository.findAll()).thenReturn(Collections.emptyIterator());
        List<Car> result = carService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("CAR-001")).thenReturn(car1);
        Car result = carService.findById("CAR-001");

        assertNotNull(result);
        assertEquals(car1.getCarId(), result.getCarId());
        assertEquals(car1.getCarName(), result.getCarName());
        assertEquals(car1.getCarColor(), result.getCarColor());
        assertEquals(car1.getCarQuantity(), result.getCarQuantity());
        verify(carRepository, times(1)).findById("CAR-001");
    }

    @Test
    void testFindByIdNotFound() {
        when(carRepository.findById("NONEXISTENT")).thenReturn(null);
        Car result = carService.findById("NONEXISTENT");

        assertNull(result);
        verify(carRepository, times(1)).findById("NONEXISTENT");
    }

    @Test
    void testUpdate() {
        carService.update("CAR-001", car1);
        verify(carRepository).update("CAR-001", car1);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("CAR-001");
        verify(carRepository).delete("CAR-001");
    }
}