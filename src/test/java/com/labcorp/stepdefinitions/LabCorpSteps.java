package com.labcorp.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LabCorpSteps {
    WebDriver driver;
    WebDriverWait wait;
    String jobTitleOnJobPage;
    String jobLocationOnJobPage;
    String jobIdOnJobPage;

    @Given("I open the LabCorp homepage")
    public void openLabCorpHomepage() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.labcorp.com");
    }

    @And("I navigate to the Careers section")
    public void navigateToCareers() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Careers"))).click();
    }

    @When("I search for the job titled {string}")
    public void searchForJob(String jobTitle) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-input")));
        searchBox.clear();
        searchBox.sendKeys(jobTitle);
        searchBox.sendKeys(Keys.ENTER);
    }

    @And("I open the first job result")
    public void openFirstJobResult() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".job-title a"))).click();
    }

    @Then("the job title should include {string}")
    public void verifyJobTitle(String expectedTitle) {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".job-title")));
        jobTitleOnJobPage = titleElement.getText();
        Assert.assertTrue("Job title does not contain expected text", jobTitleOnJobPage.contains(expectedTitle));
    }

    @And("the job location should be displayed")
    public void verifyJobLocation() {
        WebElement location = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".job-location")));
        jobLocationOnJobPage = location.getText();
        Assert.assertTrue("Job location is not displayed", location.isDisplayed());
    }

    @And("the job ID should be visible")
    public void verifyJobID() {
        WebElement jobId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".job-id")));
        jobIdOnJobPage = jobId.getText();
        Assert.assertTrue("Job ID is not displayed", jobId.isDisplayed());
    }

    @And("the required skills section should mention {string}")
    public void verifySkills(String skill) {
        WebElement skillsSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".job-skills")));
        String skillsText = skillsSection.getText();
        Assert.assertTrue("Skills section does not mention " + skill, skillsText.contains(skill));
    }

    @When("I click the {string} button")
    public void clickApplyButton(String button) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(button))).click();
    }

    @Then("the Apply page should display the correct job title, location, and job ID")
    public void verifyApplyPageDetails() {
        String applyJobTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".apply-job-title"))).getText();
        String applyJobLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".apply-job-location"))).getText();
        String applyJobId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".apply-job-id"))).getText();

        Assert.assertEquals("Job Title mismatch on Apply page", jobTitleOnJobPage, applyJobTitle);
        Assert.assertEquals("Job Location mismatch on Apply page", jobLocationOnJobPage, applyJobLocation);
        Assert.assertEquals("Job ID mismatch on Apply page", jobIdOnJobPage, applyJobId);
    }

    @And("I return to the job search page")
    public void returnToJobSearch() {
        driver.navigate().back();
    }

    @After
    public void tearDown() {
        if(driver != null) driver.quit();
    }
}
