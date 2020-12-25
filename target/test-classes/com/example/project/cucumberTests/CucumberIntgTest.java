package com.example.project.cucumberTests;

import com.example.project.ProjectApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest(classes = ProjectApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberOptions(
        features = "/Users/humahmed1/Documents/RestAPIProject/SubscriptionRestAPI/src/test/java/com/example/project/cucumberTests/features/",
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"})
public class CucumberIntgTest {


}
