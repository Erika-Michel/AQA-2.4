package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MoneyTransferPage {
    private SelenideElement heading = $("[class=\"heading heading_size_xl heading_theme_alfa-on-white\"]");
    private SelenideElement sum = $$("[type=\"text\"]").first();
    private SelenideElement from = $("[type=\"tel\"]");
    private SelenideElement to = $$("[type=\"text\"]").last();
    private SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"]");

    String firstCard = "5559 0000 0000 0001";
    String secondCard = "5559 0000 0000 0002";

    public DashboardPage moneyTransferFrom2To1 (String amountOfTransfer){
        //todo убедиться что тут стринг в аргументе
        sum.setValue(amountOfTransfer);
        from.setValue(secondCard);
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage moneyTransferFrom1To2 (String amountOfTransfer){
        //todo убедиться что тут стринг
        sum.setValue(amountOfTransfer);
        from.setValue(firstCard);
        transferButton.click();
        return new DashboardPage();
    }
}
