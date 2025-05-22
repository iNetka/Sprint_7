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

import static org.junit.Assert.assertEquals;

public class CourierLoginNegativeTests {

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

    @Test
    @DisplayName("Ошибка при авторизации с неверным логином")
    public void courierCanNotLoginWithInvalidLoginTest() {

        courierClient.create(courier);

        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("invalidLogin", courier.getPassword()));

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", actualMessage);
    }

    @Test
    @DisplayName("Ошибка при авторизации с неверным паролем")
    public void courierCanNotLoginWithInvalidPasswordTest() {


        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), "invalidPass"));

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", actualMessage);
    }

    @Test
    @DisplayName("Курьер не может войти в систему без логина")
    public void courierCanNotLoginWithoutLoginTest() {


        courierClient.create(courier);

        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", actualMessage);
    }

    @Test
    @DisplayName("Курьер не может войти в систему без пароля")
    public void courierCanNotLoginWithoutPasswordTest() {

        courierClient.create(courier);

        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", actualMessage);
    }

    @Test
    @DisplayName("Курьер не может войти в систему без созданного аккаунта")
    public void courierCanNotLoginWithoutCreatedAccountTest() {

        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("noCreatedUser", "noCreatedUser"));

        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", actualMessage);

    }
}
