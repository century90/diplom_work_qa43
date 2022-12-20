package tests;

import library.*;

import java.sql.SQLException;

import lombok.val;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.List;

public class TestFormPaymentCredit {
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
    @DisplayName("Оплата по активной карте, покупка в кредит, валидные данные")
    void shouldPayByApprovedCardInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
    }

    @Test
    @DisplayName("Оплата по неактивной карте, покупка в кредит, валидные данные")
    void shouldNoPayByDeclinedCardInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по неизвестной карте, покупка в кредит, валидные данные")
    void shouldNoPayByUnknownCardInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444443");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером карты, покупка в кредит")
    void shouldNoPayInvalidCardNumberFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("3333 2323 DSDF ASSD");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца, покупка в кредит")
    void shouldNoPayInvalidMonthFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("13");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года, покупка в кредит")
    void shouldNoPayInvalidYearFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("18");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец, покупка в кредит")
    void shouldNoPayInvalidCardOwnerFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Bdfy 1213 Петров 12");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV, покупка в кредит")
    void shouldNoPayInvalidCVVFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("12D");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером карты, покупка в кредит")
    void shouldNoPayEmptyCardNumberFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером месяца, покупка в кредит")
    void shouldNoPayEmptyMonthFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c пустым номером года, покупка в кредит")
    void shouldNoPayEmptyYearFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем владелец, покупка в кредит")
    void shouldNoPayEmptyCardOwnerFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageRequiredField();
    }

    @Test
    @DisplayName("Оплата по карте c пустым полем CVV, покупка в кредит")
    void shouldNoPayEmptyCVVFieldInCredit() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по активной карте, покупка в кредит, валидные данные, проверка записи в БД")
    void shouldPayByApprovedCardInCreditStatusInDB() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkCreditStatus(Status.APPROVED);
    }

    @Test
    @DisplayName("Оплата по неактивной карте, покупка в кредит, валидные данные, проверка записи в БД")
    void shouldPayByDeclinedCardInCreditStatusInDB() throws SQLException {
        formPage.buyOnCredit();
        formPage.setCardNumber("4444444444444442");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageSuccess();
        DBUtils.checkCreditStatus(Status.DECLINED);
    }
}