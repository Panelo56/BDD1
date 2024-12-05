package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;

import java.time.Duration;

import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class MoneyTransferPage {
    private SelenideElement transferAmount = $("[data-test-id='amount'] .input__control");
    private SelenideElement fromField = $("[data-test-id='from'] .input__control");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public DashboardPage transferMoney(int amount, DataHelper.CardInfo from) {
        transferAmount.setValue(valueOf(amount));
        fromField.setValue(valueOf(from));
        transferButton.click();
        return new DashboardPage();
    }
    public void findErrorNotification(String error) {
        errorNotification.shouldHave(exactText(error)).shouldBe(visible);
    }
}