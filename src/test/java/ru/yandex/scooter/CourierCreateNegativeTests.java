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

public class CourierCreateNegativeTests {

    private CourierClient courierClient;
    private Courier courierWithoutPassword;
    private Courier courierWithoutLogin;
    private Courier courierDefault;
    private Courier courierWithExistLogin;
    private Integer courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courierWithoutPassword = CourierGenerator.getRandomWithoutPassword();
        courierWithoutLogin = CourierGenerator.getRandomWithoutLogin();
        courierDefault = CourierGenerator.getDefaultCourier();
        courierWithExistLogin = CourierGenerator.getExistLoginCourier();
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьера не создать без пароля")
    public void courierCanNotBeCreatedWithoutPasswordTest() {

        ValidatableResponse createResponse = courierClient.create(courierWithoutPassword);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", actualMessage);
    }

    @Test
    @DisplayName("Курьера не создать без логина")
    public void courierCanNotBeCreatedWithoutLoginTest() {

        ValidatableResponse createResponse = courierClient.create(courierWithoutLogin);

        int statusCode = createResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", actualMessage);
    }

    @Test
    @DisplayName("Нельзя создать курьера с уже существующими данными")
    public void courierCanNotBeCreatedWithExistingCourierTest() {


        courierClient.create(courierDefault);
        ValidatableResponse createResponse = courierClient.create(courierDefault);

        courierId = courierClient.login(CourierCredentials.from(courierDefault)).extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        assertEquals(409, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", actualMessage);
    }

    @Test
    @DisplayName("Курьера нельзя создать, если логин уже существует")
    public void courierCanNotBeCreatedWithExistingLoginTest() {


        courierClient.create(courierWithExistLogin);
        ValidatableResponse createResponse = courierClient.create(courierWithExistLogin);

        courierId = courierClient.login(CourierCredentials.from(courierWithExistLogin)).extract().path("id");

        int statusCode = createResponse.extract().statusCode();
        assertEquals(409, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", actualMessage);
    }
}
