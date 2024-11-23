package ru.netology.test;

import ru.netology.data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;



public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransfer() {
        var LoginPage = new LoginPage();
        var authInfo = getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(getFirstCardNumber().getCardNumber());
        var secondCardBalance = dashboardPage.getCardBalance(getSecondCardNumber().getCardNumber());
        var transferPage = dashboardPage.depositToFirstCard();
        var amount =generateValidAmount(firstCardBalance);
        transferPage.transferMoney(amount, getSecondCardNumber());
        var expectedFirstCardBalanceAfter = firstCardBalance + amount;
        var expectedSecondCardBalanceAfter = secondCardBalance - amount;
        Assertions.assertEquals(expectedFirstCardBalanceAfter, dashboardPage.getCardBalance(getFirstCardNumber().getCardNumber()));
        Assertions.assertEquals(expectedSecondCardBalanceAfter, dashboardPage.getCardBalance(getSecondCardNumber().getCardNumber()));
    }

    @Test
    void shouldErrorBalanc() {
        var LoginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = LoginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var secondCardBalance = dashboardPage.getCardBalance(getSecondCardNumber().getCardNumber());
        var transferPage = dashboardPage.depositToFirstCard();
        int amount = DataHelper.generateInvalidAmount(secondCardBalance);
        transferPage.transferMoney(amount, DataHelper.getSecondCardNumber());
    }

}