public class TestRunner {
    @RunWith(Cucumber.class)
    @CucumberOptions(
            features = "src/test/resources/features",
            glue = "com.labcorp.stepdefinitions",
            plugin = {"pretty", "html:target/cucumber-report.html"},
            monochrome = true
    )
