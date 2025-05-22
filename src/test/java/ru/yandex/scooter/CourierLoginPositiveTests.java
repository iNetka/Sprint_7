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

public class CourierLoginPositiveTests {

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


    @DisplayName("Курьер может авторизоваться")
    @Test
    public void courierCanLoginTest() {

        courierClient.create(courier);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);

        courierId = loginResponse.extract().path("id");
        assertNotNull(courierId);
    }
}
