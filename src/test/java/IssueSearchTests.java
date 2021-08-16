import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;


@Feature("Issue")
public class IssueSearchTests {

    private static final String BASE_URL = "https://github.com";
    private static final String REPOSITORY = "eroshenkoam/allure-example";
    private static final String ISSUE_NAME = "с днем археолога!";

    private WebSteps steps = new WebSteps();

    @Test
    @Story("Issue Search")
    @Tags({@Tag("Smoke"), @Tag("Web")})
    void IssueSearchTestSelenideOnly(){
            SelenideLogger.addListener("allure", new AllureSelenide());

            open(BASE_URL);

            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();

            $(By.linkText(REPOSITORY)).click();

            $(withText("Issues")).click();

            $(withText(ISSUE_NAME)).should(Condition.visible);
    }

    @Test
    @Story("Issue Search")
    @Tags({@Tag("Regress"), @Tag("Web")})
    void IssueSearchTestWithLambdaSteps(){
        step("Открываем главную страницу " + BASE_URL, (s) -> {
            s.parameter("URL", BASE_URL);
            open(BASE_URL);
        });

        step("Ищем репозиторий " + REPOSITORY, (s) -> {
            s.parameter("repository", REPOSITORY);
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });

        step("Переходим в репозиторий " + REPOSITORY, (s) -> {
            s.parameter("repository", REPOSITORY);
            $(By.linkText(REPOSITORY)).click();
        });

        step("Открываем таб Issues в репозитории", () -> {
            $(withText("Issues")).click();
        });

        step("Проверяем, что Issue с названием \"" + ISSUE_NAME + "\" существует", (s) -> {
            s.parameter("name", ISSUE_NAME);
            $(withText(ISSUE_NAME)).should(Condition.visible);
        });
    }

    @Test
    @Story("Issue Search")
    @Tags({@Tag("Regress"), @Tag("Web")})
    void IssueSearchTestWithAnnotatedSteps(){
        steps.openMainPage(BASE_URL);
        steps.searchForRepository(REPOSITORY);
        steps.goToRepository(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE_NAME);
    }



}
