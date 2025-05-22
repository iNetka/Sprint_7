package ru.yandex.scooter.order;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

import static ru.yandex.scooter.order.OrderConstant.*;

public class OrderGenerator {

    public static Order getDefaultOrder() {
        return new Order(FIRSTNAME, LASTNAME, ADDRESS, METROSTATION, PHONE, RENTTIME, DELIVERYDATE, COMMENT, COLLOR);
    }

    public static Order getRandomOrder(String[] color) {
        final String firstName = RandomStringUtils.randomAlphabetic(7);
        final String lastName = RandomStringUtils.randomAlphabetic(7);
        final String address = RandomStringUtils.randomAlphanumeric(7);
        final String metroStation = RandomStringUtils.randomAlphabetic(7);
        final String phone = RandomStringUtils.randomNumeric(11);

        final int rentTime = (int) (Math.random() * 9 + 1);
        final String deliveryDate = LocalDateTime.now().toLocalDate().toString();
        final String comment = RandomStringUtils.randomAlphabetic(7);
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
