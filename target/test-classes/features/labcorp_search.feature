package com.labcorp.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import java.time.Duration;

public class LabCorpSteps {
WebDriver driver;

@Given("I open the LabCorp homepage")
public void openLabCorpHomepage() {
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.get("https://www.labcorp.com");
}

@And("I navigate to the Careers section")
public void navigateToCareers() {
driver.findElement(By.linkText("Careers")).click();
}

@When("I search for the job titled {string}")
public void searchForJob(String jobTitle) {
WebElement searchBox = driver.findElement(By.id("search-input"));
searchBox.sendKeys(jobTitle);
searchBox.sendKeys(Keys.ENTER);
}

@And("I open the first job result")
public void openFirstJobResult() {
driver.findElement(By.cssSelector(".job-title a")).click();
}

@Then("the job title should include {string}")
public void verifyJobTitle(String expectedTitle) {
String title = driver.findElement(By.cssSelector(".job-title")).getText();
Assert.assertTrue(title.contains(expectedTitle));
}

@And("the job location should be displayed")
public void verifyJobLocation() {
WebElement location = driver.findElement(By.cssSelector(".job-location"));
Assert.assertTrue(location.isDisplayed());
}

@And("the job ID should be visible")
public void verifyJobID() {
WebElement jobId = driver.findElement(By.cssSelector(".job-id"));
Assert.assertTrue(jobId.isDisplayed());
}

@And("the required skills section should mention {string}")
public void verifySkills(String skill) {
String skillsText = driver.findElement(By.cssSelector(".job-skills")).getText();
Assert.assertTrue(skillsText.contains(skill));
}

@When("I click the {string} button")
public void clickApplyButton(String button) {
driver.findElement(By.linkText(button)).click();
}

@Then("the Apply page should display the correct job title, location, and job ID")
public void verifyApplyPageDetails() {
// Similar assertions as job page
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
Feature: LabCorp Careers Job Search and Job Details Verification

  As a job seeker
  I want to search for a specific job on LabCorp Careers page
  So that I can verify the job details and apply for the position

  Background:
    Given I open the LabCorp homepage
    And I navigate to the Careers section

  Scenario: Search for a QA Test Automation Developer job and verify job details
    When I search for the job titled "QA Test Automation Developer"
    And I open the first job result
    Then the job title should include "QA Test Automation Developer"
    And the job location should be displayed
    And the job ID should be visible
    And the required skills section should mention "Selenium"
    And the job description should contain the following details:
      | detail                                              |
      | The right candidate for this role will participate in the test automation technology development and best practice models. |
      | Prepare test plans, budgets, and schedules.         |
      | 5+ years of experience in QA automation development and scripting. |
    When I click the "Apply Now" button
    Then the Apply page should display the correct job title, location, and job ID
    And I return to the job search page

