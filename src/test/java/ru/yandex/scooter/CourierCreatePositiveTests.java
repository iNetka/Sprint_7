package ru.yandex.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.courier.Courier;
import ru.yandex.scooter.courier.CourierClient;
import ru.yandex.scooter.courier.CourierCredentials;
import ru.yandex.scooter.courier.CourierGenerator;

import static org.junit.Assert.*;

public class CourierCreatePositiveTests {

    private CourierClient courierClient;
    private Integer courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @DisplayName("Успешное создание курьера")
    @Test
    public void courierCanBeCreatedTest() {

        ValidatableResponse createResponse = courierClient.create(courier);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(201, statusCode);

        boolean isCourierCreated = createResponse.extract().path("ok");
        assertTrue(isCourierCreated);

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }
}
