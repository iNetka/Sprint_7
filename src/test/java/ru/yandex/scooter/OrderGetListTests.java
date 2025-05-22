package ru.yandex.scooter;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.order.OrderClient;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderGetListTests {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Тело ответа возвращается список заказов")
    @Test
    public void orderListCanShowTest() {

        ValidatableResponse orderListResponse = orderClient.orderList();
        List<Object> orders = orderListResponse.extract().path("orders");

        int statusCode = orderListResponse.extract().statusCode();
        assertEquals(200, statusCode);

        assertFalse(orders.isEmpty());
    }
}
