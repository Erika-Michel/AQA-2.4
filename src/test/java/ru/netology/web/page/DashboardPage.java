package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement reloadButton = $("[data-test-id=action-reload]");
    private SelenideElement depositFirst = $$("[data-test-id=action-deposit]").first();
    private SelenideElement depositSecond = $$("[data-test-id=action-deposit]").last();
    private SelenideElement firstCardBalance = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private SelenideElement secondCardBalance = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public MoneyTransferPage transferToFirstCard() {
        depositFirst.click();
        return new MoneyTransferPage();
    }

    public MoneyTransferPage transferToSecondCard() {
        depositSecond.click();
        return new MoneyTransferPage();
    }

    public DashboardPage reloadPage() {
        reloadButton.click();
        return new DashboardPage();
    }

    public int getFirstCardBalance() {
        val text = firstCardBalance.text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = secondCardBalance.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}