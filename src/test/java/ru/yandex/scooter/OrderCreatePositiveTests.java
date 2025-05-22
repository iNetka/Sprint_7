package ru.yandex.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.scooter.order.Order;
import ru.yandex.scooter.order.OrderClient;
import ru.yandex.scooter.order.OrderGenerator;
import ru.yandex.scooter.order.OrderTrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreatePositiveTests {

    private OrderClient orderClient;
    private Integer track;
    private Order order;
    private String[] color;
    private int expectedStatusCode;

    public OrderCreatePositiveTests(String[] color, int expectedStatusCode) {
        this.color = color;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getRandomOrder(color);
    }

    @After
    public void cleanUp() {
        OrderTrack orderTrack = new OrderTrack(track);
        orderClient.cancel(orderTrack);
    }

    @Parameterized.Parameters(name = "Тестовые данные: Color = {0}, statusCode = {1}")
    public static Object[][] orderData() {
        return new Object[][] {
                {new String[]{"BLACK", "GREY"}, 201},
                {new String[]{"BLACK"}, 201},
                {new String[]{"GREY"}, 201},
                {null, 201}
        };
    }

    @DisplayName("Заказ успешно создан, при заполнении всех полей")
    @Test
    public void orderCanBeCreatedTest() {

        ValidatableResponse createResponse = orderClient.create(order);

        int actualStatusCode = createResponse.extract().statusCode();
        assertEquals(expectedStatusCode, actualStatusCode);

        track = createResponse.extract().path("track");
        assertNotNull(track);
    }
}
