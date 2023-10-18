package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NewTest {
    static {
        Configuration.browser = "chrome";

    }
    static void setUpAll() {

        getWebDriver().manage().window().maximize();
    }
    @Test
    void shouldBeSuccessfullResalt() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79398229211");
        $("[data-test-id='agreement']").click();
        $("button").click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = $("[data-test-id='order-success']").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeBagWithNameWithNumber() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Иванов 1234");
        $("[data-test-id='phone'] input").setValue("+79398229211");
        $("[data-test-id='agreement']").click();
        $(By.tagName("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id='name'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeBagWithShortNumberPhone() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+7939822921");
        $("[data-test-id='agreement']").click();
        $(By.tagName("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id='phone'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeBagWithoutName() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+7939822921");
        $("[data-test-id='agreement']").click();
        $(By.tagName("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id='name'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeBagWithoutNumberPhone() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $(By.tagName("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = $("[data-test-id='phone'].input_invalid .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeBagWihtoutAgreement() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79398220921");
        $(By.tagName("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = $("checkbox__text.input_invalid .input__sub").getText().trim();

        assertEquals(expected, actual);
    }
}

