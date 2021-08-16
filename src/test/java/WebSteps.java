import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebSteps {

    @Step("Открываем главную страницу {url}")
    public void openMainPage(String url){
        open(url);
    }

    @Step("Ищем репозиторий {repository}")
    public void searchForRepository(String repository){
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(repository);
        $(".header-search-input").submit();
    }

    @Step("Переходим в репозиторий {repository}")
    public void goToRepository(String repository){
        $(By.linkText("eroshenkoam/allure-example")).click();
    }

    @Step("Открываем таб Issues в репозитории")
    public void openIssuesTab(){
        $(withText("Issues")).click();
    }

    @Step("Проверяем, что Issue с названием \"{name}\" существует")
    public void shouldSeeIssueWithNumber(String name){
        $(withText(name)).should(Condition.visible);
    }

}
