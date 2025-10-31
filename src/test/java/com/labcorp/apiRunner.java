package com.labcorp.apitests;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.labcorp.apitests",
        plugin = {"pretty", "html:target/api-report.html"},
        monochrome = true
)
public class ApiRunner { }

