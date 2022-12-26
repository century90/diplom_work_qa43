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
    @DisplayName("Оплата по карте c невалидным номером карты (15 цифр), покупка в кредит")
    void shouldNoPayInvalidCardNumber15FieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444 4444 4444 444");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером карты (кириллица), покупка в кредит")
    void shouldNoPayInvalidCardNumberCyrillicFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("ааааа");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером карты (латиница), покупка в кредит")
    void shouldNoPayInvalidCardNumberLatinFieldInCredit() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером карты (спецсимволы), покупка в кредит")
    void shouldNoPayInvalidCardNumberSpecificFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("%%%^&");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
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
    @DisplayName("Оплата по карте c невалидным номером месяца (00), покупка в кредит")
    void shouldNoPayInvalidMonthNullFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("00");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца (13), покупка в кредит")
    void shouldNoPayInvalidMonthMoreFieldInCredit() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером месяца (символы), покупка в кредит")
    void shouldNoPayInvalidMonthSymbolsFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("month");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером месяца (одна цифра), покупка в кредит")
    void shouldNoPayInvalidMonthOneFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("1");
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
    @DisplayName("Оплата по карте c невалидным номером года (00), покупка в кредит")
    void shouldNoPayInvalidYearNullFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("00");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года (19), покупка в кредит")
    void shouldNoPayInvalidYearLastFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("19");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageOverDate();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года (Символами), покупка в кредит")
    void shouldNoPayInvalidYearSymbolsFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("year");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года (одна цифра), покупка в кредит")
    void shouldNoPayInvalidYearOneFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("1");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным номером года (далекое будущее), покупка в кредит")
    void shouldNoPayInvalidYearMoreFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("45");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongDate();
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
    @DisplayName("Оплата по карте c невалидным полем владелец (одно слово), покупка в кредит")
    void shouldNoPayInvalidCardOwnerOneWordFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец (три слова), покупка в кредит")
    void shouldNoPayInvalidCardOwnerThreeWordsFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Sergeevich Petrov");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец (кириллица), покупка в кредит")
    void shouldNoPayInvalidCardOwnerCyrillicFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Иван Петров");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец (цифры), покупка в кредит")
    void shouldNoPayInvalidCardOwnerNumbersFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("123456");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец (спецсимволы), покупка в кредит")
    void shouldNoPayInvalidCardOwnerSpecificFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner(";%:№;");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем владелец (1000 символов), покупка в кредит")
    void shouldNoPayInvalidCardOwnerSpecific1000FieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("оооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооооо");
        formPage.setCardCVV("999");
        formPage.pushСontinueButton();
        formPage.checkMessageError();
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
    @DisplayName("Оплата по карте c невалидным полем CVV (9), покупка в кредит")
    void shouldNoPayInvalidCVVOneFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("9");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV (99), покупка в кредит")
    void shouldNoPayInvalidCVVTwoFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("99");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV (000), покупка в кредит")
    void shouldNoPayInvalidCVVNullFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("000");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV (символы), покупка в кредит")
    void shouldNoPayInvalidCVVSymbolFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("ррр");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
    }

    @Test
    @DisplayName("Оплата по карте c невалидным полем CVV (спецсимволы), покупка в кредит")
    void shouldNoPayInvalidCVVSpecificFieldInCredit() throws SQLException {
        formPage.buyForYourMoney();
        formPage.setCardNumber("4444444444444441");
        formPage.setCardMonth("01");
        formPage.setCardYear("25");
        formPage.setCardOwner("Ivan Petrov");
        formPage.setCardCVV("&&&");
        formPage.pushСontinueButton();
        formPage.checkMessageWrongFormat();
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