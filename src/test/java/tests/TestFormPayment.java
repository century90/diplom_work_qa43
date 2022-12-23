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
    @DisplayName("Оплата по карте c невалидным номером карты (15 цифр), обычная покупка")
    void shouldNoPayInvalidCardNumber15Field() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером карты (кириллица), обычная покупка")
    void shouldNoPayInvalidCardNumberCyrillicField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером карты (латиница), обычная покупка")
    void shouldNoPayInvalidCardNumberLatinField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером карты (спецсимволы), обычная покупка")
    void shouldNoPayInvalidCardNumberSpecificField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером месяца (00), обычная покупка")
    void shouldNoPayInvalidMonthNullField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером месяца (13), обычная покупка")
    void shouldNoPayInvalidMonthMoreField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером месяца (символы), обычная покупка")
    void shouldNoPayInvalidMonthSymbolsField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером месяца (одна цифра), обычная покупка")
    void shouldNoPayInvalidMonthOneField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером года (00), обычная покупка")
    void shouldNoPayInvalidYearNullField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером года (19), обычная покупка")
    void shouldNoPayInvalidYearLastField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером года (Символами), обычная покупка")
    void shouldNoPayInvalidYearSymbolsField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером года (одна цифра), обычная покупка")
    void shouldNoPayInvalidYearOneField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным номером года (далекое будущее), обычная покупка")
    void shouldNoPayInvalidYearMoreField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем владелец (одно слово), обычная покупка")
    void shouldNoPayInvalidCardOwnerOneWordField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем владелец (три слова), обычная покупка")
    void shouldNoPayInvalidCardOwnerThreeWordsField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем владелец (кириллица), обычная покупка")
    void shouldNoPayInvalidCardOwnerCyrillicField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем владелец (цифры), обычная покупка")
    void shouldNoPayInvalidCardOwnerNumbersField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем владелец (спецсимволы), обычная покупка")
    void shouldNoPayInvalidCardOwnerSpecificField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем владелец (1000 символов), обычная покупка")
    void shouldNoPayInvalidCardOwnerSpecific1000Field() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем CVV (9), обычная покупка")
    void shouldNoPayInvalidCVVOneField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем CVV (99), обычная покупка")
    void shouldNoPayInvalidCVVTwoField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем CVV (000), обычная покупка")
    void shouldNoPayInvalidCVVNullField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем CVV (символы), обычная покупка")
    void shouldNoPayInvalidCVVSymbolField() throws SQLException {
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
    @DisplayName("Оплата по карте c невалидным полем CVV (спецсимволы), обычная покупка")
    void shouldNoPayInvalidCVVSpecificField() throws SQLException {
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