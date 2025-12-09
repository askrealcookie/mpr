package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testy wikipedi selenium")
class WikipediaTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://pl.wikipedia.org";

    private static final By SEARCH_INPUT = By.name("search");
    private static final By ARTICLE_HEADING = By.id("firstHeading");
    private static final By ARTICLE_IMAGE = By.cssSelector(".mw-parser-output img");
    private static final By WIKIPEDIA_LOGO = By.cssSelector(".mw-logo-icon");
    private static final By ARTICLE_FIRST_LINK = By.cssSelector(".mw-parser-output p a");
    private static final By ARTICLE_LINKS = By.cssSelector(".mw-parser-output a");

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @DisplayName("Should display search input on main page")
    void shouldDisplaySearchInputOnMainPage() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));

        assertThat(searchInput.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("Should redirect to article page when searching for Java")
    void shouldRedirectToArticlePageWhenSearchingForJava() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Java");
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("/wiki/Java"));

        assertThat(driver.getCurrentUrl()).contains("/wiki/Java");
    }

    @Test
    @DisplayName("Should display search phrase in page title when searching")
    void shouldDisplaySearchPhraseInPageTitleWhenSearching() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Java");
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.titleContains("Java"));

        assertThat(driver.getTitle()).contains("Java");
    }

    @Test
    @DisplayName("Should accept text input in search field")
    void shouldAcceptTextInputInSearchField() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Programowanie");

        String inputValue = searchInput.getAttribute("value");

        assertThat(inputValue).contains("Programowanie");
    }

    @Test
    @DisplayName("Should change URL when clicking link in article")
    void shouldChangeUrlWhenClickingLinkInArticle() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Java");
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_HEADING));
        String initialUrl = driver.getCurrentUrl();

        WebElement firstLink = wait.until(ExpectedConditions.elementToBeClickable(ARTICLE_FIRST_LINK));
        firstLink.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(initialUrl)));

        assertThat(driver.getCurrentUrl()).isNotEqualTo(initialUrl);
    }

    @Test
    @DisplayName("Should navigate to main page when clicking home link")
    void shouldNavigateToMainPageWhenClickingHomeLink() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Java");
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_HEADING));

        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(WIKIPEDIA_LOGO));
        logo.click();

        wait.until(ExpectedConditions.urlContains("Strona"));

        assertThat(driver.getCurrentUrl()).contains("Strona");
    }

    @Test
    @DisplayName("Should navigate to random article page")
    void shouldNavigateToRandomArticlePage() {

        driver.get(BASE_URL + "/wiki/Specjalna:Losowa_strona");

        wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_HEADING));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("Specjalna:Losowa_strona")));

        assertThat(driver.getCurrentUrl()).doesNotContain("Specjalna:Losowa_strona");
    }

    @Test
    @DisplayName("Should display heading matching search phrase")
    void shouldDisplayHeadingMatchingSearchPhrase() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Java");
        searchInput.sendKeys(Keys.ENTER);

        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_HEADING));

        assertThat(heading.getText()).containsIgnoringCase("Java");
    }

    @Test
    @DisplayName("Should display at least one image in article")
    void shouldDisplayAtLeastOneImageInArticle() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Polska");
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_HEADING));
        WebElement image = wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_IMAGE));

        assertThat(image).isNotNull();
    }

    @Test
    @DisplayName("Should display multiple links in article")
    void shouldDisplayMultipleLinksInArticle() {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        searchInput.sendKeys("Java");
        searchInput.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.presenceOfElementLocated(ARTICLE_HEADING));
        int linksCount = driver.findElements(ARTICLE_LINKS).size();

        assertThat(linksCount).isGreaterThan(2);
    }
}

