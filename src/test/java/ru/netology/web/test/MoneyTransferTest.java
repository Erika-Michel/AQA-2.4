package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    String firstCard = "5559 0000 0000 0001";
    String secondCard = "5559 0000 0000 0002";
    int initialFirstCardBalance;
    int initialSecondCardBalance;
    int amountOfTransfer;
    DashboardPage dashboardPage;
    MoneyTransferPage moneyTransferPage;


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);


        if (dashboardPage.getFirstCardBalance() < 10_000) {
            int i = dashboardPage.getSecondCardBalance() - 10_000;
            moneyTransferPage = dashboardPage.transferToFirstCard();
            moneyTransferPage.moneyTransferSuccess(Integer.toString(i), secondCard);
        }
        if (dashboardPage.getSecondCardBalance() < 10_000) {
            int ii = dashboardPage.getFirstCardBalance() - 10_000;
           moneyTransferPage = dashboardPage.transferToSecondCard();
            moneyTransferPage.moneyTransferSuccess(Integer.toString(ii), firstCard);
        }

        initialFirstCardBalance = dashboardPage.getFirstCardBalance();
        initialSecondCardBalance = dashboardPage.getSecondCardBalance();
    }

    @Test
    void shouldTransferMoneyToFirstCard() {
        amountOfTransfer = 3_000;
        moneyTransferPage = dashboardPage.transferToFirstCard();
        moneyTransferPage.moneyTransferSuccess(Integer.toString(amountOfTransfer), secondCard);
        int final1CardBalance = initialFirstCardBalance + amountOfTransfer;
        int final2CardBalance = initialSecondCardBalance - amountOfTransfer;
        assertEquals(final1CardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(final2CardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldNotTransferMoneyToFirstCardOverLimit() {
        amountOfTransfer = initialSecondCardBalance + 500;
        moneyTransferPage = dashboardPage.transferToFirstCard();
        moneyTransferPage.moneyTransferFailure(Integer.toString(amountOfTransfer), secondCard);
    }


    @Test
    void shouldNotTransferMoneyToFirstCardZeroAmount() {
        amountOfTransfer = 0;
        moneyTransferPage = dashboardPage.transferToFirstCard();
        moneyTransferPage.moneyTransferSuccess(Integer.toString(amountOfTransfer), secondCard);
        int final1CardBalance = initialFirstCardBalance + amountOfTransfer;
        int final2CardBalance = initialSecondCardBalance - amountOfTransfer;
        assertEquals(final1CardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(final2CardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferAllMoneyToFirstCard() {
        amountOfTransfer = initialSecondCardBalance;
        moneyTransferPage = dashboardPage.transferToFirstCard();
        moneyTransferPage.moneyTransfer(Integer.toString(amountOfTransfer), secondCard);
        int final1CardBalance = initialFirstCardBalance + amountOfTransfer;
        int final2CardBalance = initialSecondCardBalance - amountOfTransfer;
        assertEquals(final1CardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(final2CardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyToSecondCard() {
        amountOfTransfer = 10;
        moneyTransferPage = dashboardPage.transferToSecondCard();
        moneyTransferPage.moneyTransferSuccess(Integer.toString(amountOfTransfer), firstCard);
        int final1CardBalance = initialFirstCardBalance - amountOfTransfer;
        int final2CardBalance = initialSecondCardBalance + amountOfTransfer;
        assertEquals(final1CardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(final2CardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldNotTransferMoneyToSecondCardOverLimit() {
        amountOfTransfer = initialFirstCardBalance + 500;
        moneyTransferPage = dashboardPage.transferToSecondCard();
        moneyTransferPage.moneyTransferFailure(Integer.toString(amountOfTransfer), firstCard);
    }

    @Test
    void shouldTransferAllMoneyToSecondCard() {
        amountOfTransfer = initialFirstCardBalance;
        moneyTransferPage = dashboardPage.transferToSecondCard();
        moneyTransferPage.moneyTransferSuccess(Integer.toString(amountOfTransfer), firstCard);
        int final1CardBalance = initialFirstCardBalance - amountOfTransfer;
        int final2CardBalance = initialSecondCardBalance + amountOfTransfer;
        assertEquals(final1CardBalance, dashboardPage.getFirstCardBalance());
        assertEquals(final2CardBalance, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldIgnoreMinusWhenTransfer() {
        amountOfTransfer = -10;
        moneyTransferPage = dashboardPage.transferToSecondCard();
        moneyTransferPage.moneyTransferSuccess(Integer.toString(amountOfTransfer), firstCard);
        assertEquals(9_990, dashboardPage.getFirstCardBalance());
        assertEquals(10_010, dashboardPage.getSecondCardBalance());
    }
}