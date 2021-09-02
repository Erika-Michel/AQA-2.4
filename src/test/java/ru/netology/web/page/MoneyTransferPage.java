package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MoneyTransferPage {
    private SelenideElement heading = $("[class=\"heading heading_size_xl heading_theme_alfa-on-white\"]");
    private SelenideElement sum = $$("[type=\"text\"]").first();
    private SelenideElement from = $("[type=\"tel\"]");
    private SelenideElement to = $$("[type=\"text\"]").last();
    private SelenideElement transferButton = $("[data-test-id=\"action-transfer\"]");
    private SelenideElement cancelButton = $("[data-test-id=\"action-cancel\"]");
    private SelenideElement errorPopUp = $("[data-test-id=\"error-notification\"]");

    public MoneyTransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public void moneyTransfer(String amountOfTransfer, String fromCard) {
        sum.sendKeys(Keys.CONTROL, "A", Keys.DELETE);
        sum.setValue(amountOfTransfer);
        from.sendKeys(Keys.CONTROL, "A", Keys.DELETE);
        from.setValue(fromCard);
        transferButton.click();
    }

    public DashboardPage moneyTransferSuccess(String amountOfTransfer, String fromCard) {
        moneyTransfer(amountOfTransfer, fromCard);
        return new DashboardPage();
    }

    public void moneyTransferFailure(String amountOfTransfer, String fromCard) {
        sum.sendKeys(Keys.CONTROL, "A", Keys.DELETE);
        sum.setValue(amountOfTransfer);
        from.sendKeys(Keys.CONTROL, "A", Keys.DELETE);
        from.setValue(fromCard);
        transferButton.click();
        errorPopUp.shouldBe(Condition.visible);
    }

    public DashboardPage cancelation() {
        cancelButton.click();
        return new DashboardPage();
    }
}
