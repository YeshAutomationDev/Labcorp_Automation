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

