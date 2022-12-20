package tests;

import library.*;

import java.sql.SQLException;

import lombok.val;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.List;

public class TestFormPayment {
    private FormPage formPage;

    @BeforeEach
    void setUpPage() {
        formPage = new FormPage();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearAll() throws SQLException {
        DBUtils.clearAllData();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Оплата по активной карте, обычная покупка, валидные данные")
    void shouldPayByApprovedCard() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Оплата по неактивной карте, обычная покупка, валидные данные")
    void shouldNoPayByDeclinedCard() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, обычная покупка, валидные данные")
    void shouldNoPayByUnknownCard() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444443");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером карты, обычная покупка")
    void shouldNoPayInvalidCardNumberField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("3333 2323 DSDF ASSD");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца, обычная покупка")
    void shouldNoPayInvalidMonthField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("13");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года, обычная покупка")
    void shouldNoPayInvalidYearField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("18");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец, обычная покупка")
    void shouldNoPayInvalidCardOwnerField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Bdfy 1213 Петров 12");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV, обычная покупка")
    void shouldNoPayInvalidCVVField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("12D");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером карты, обычная покупка")
    void shouldNoPayEmptyCardNumberField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером месяца, обычная покупка")
    void shouldNoPayEmptyMonthField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером года, обычная покупка")
    void shouldNoPayEmptyYearField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем владелец, обычная покупка")
    void shouldNoPayEmptyCardOwnerField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageRequiredField();
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем CVV, обычная покупка")
    void shouldNoPayEmptyCVVField() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }


    @Test
    @DisplayName("Оплата по активной карте, обычная покупка, валидные данные, проверка записи в БД")
    void shouldPayByApprovedCardStatusInDB() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkPaymentStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Оплата по неактивной карте, обычная покупка, валидные данные, проверка записи в БД")
    void shouldNoPayByDeclinedCardStatusInDB() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkPaymentStatus(Status.DECLINED);
    }
}