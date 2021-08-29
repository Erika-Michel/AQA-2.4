package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement reloadButton = $("[data-test-id=action-reload]");
    private SelenideElement depositFirst = $$("[data-test-id=action-deposit]").first();
    private SelenideElement depositSecond = $$("[data-test-id=action-deposit]").last();

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public MoneyTransferPage transferToFirstCard(){
        depositFirst.click();
        return new MoneyTransferPage();
    }

    public MoneyTransferPage transferToSecondCard(){
        depositSecond.click();
        return new MoneyTransferPage();
    }

    public DashboardPage reloadPage(){
        reloadButton.click();
        return new DashboardPage();
    }
}