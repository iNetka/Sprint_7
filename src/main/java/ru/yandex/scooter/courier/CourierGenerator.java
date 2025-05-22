package ru.yandex.scooter.courier;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    @Step("Создание случайного курьера")
    public static Courier getRandomCourier() {
        final String login = RandomStringUtils.randomAlphabetic(6);
        final String password = RandomStringUtils.randomAlphabetic(6);
        final String firstName = RandomStringUtils.randomAlphabetic(6);
        return new Courier(login, password, firstName);
    }

    @Step("Создание курьера по умолчанию")
    public static Courier getDefaultCourier() {
        final String login = "kyrier";
        final String password = "12345";
        final String firstName = "Nikolai";
        return new Courier(login, password, firstName);
    }

    @Step("Создание курьера без пароля")
    public static Courier getRandomWithoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, null, firstName);
    }

    @Step("Создание курьера без логина")
    public static Courier getRandomWithoutLogin() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(null, password, firstName);
    }

    @Step("Создание курьера с уже существующим логином")
    public static Courier getExistLoginCourier() {
        final String login = "kyrier";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
}
