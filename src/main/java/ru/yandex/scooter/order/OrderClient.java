package ru.yandex.scooter.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.scooter.GeneralData;

import static io.restassured.RestAssured.given;

public class OrderClient extends GeneralData {
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String ORDER_CANCEL = "/api/v1/orders/cancel";
    private static final String ORDER_LIST = "/api/v1/orders";

    public OrderClient() {

    }

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Отмена заказа с трек-номером {orderTrack}")
    public ValidatableResponse cancel(OrderTrack orderTrack) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(orderTrack)
                .put(ORDER_CANCEL)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse orderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_LIST)
                .then();
    }
}