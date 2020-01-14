/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myorg.junit5demo.arithmetic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;

/**
 *
 * @author chiraranw
 * @ Date Jan 11, 2020
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//one instance per class
public class ArithmeticTest {

    //for every test method, JUnit Creates an instance of ArithmeticTest
    // BeforeAll is execured even before the instance is created - static
    Arithmatic arithmatic;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Some special initialization...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Some special clean up...");
    }

    @BeforeEach
    public void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        arithmatic = new Arithmatic();
    }

    @AfterEach
    void cleaning() {
        System.out.println("Clean-up code...");
    }

    @Nested
    @Tag("Math")
    class AddTest {

        //test cases inside this class will be grouped together
        @Test
        @DisplayName("Testing add method for positive numbers")
        void testSumPositiveNumbers() {
            //the lambda function will exec only if the test fails
            assertEquals(2, arithmatic.sum(1, 1), () -> "sum method should add two numbers");
        }

        @Test
        @DisplayName("Testing add method for negative numbers")
        void testSumNegagtiveNumber() {
            assertEquals(-2, arithmatic.sum(-1, -1), "sum method should add two numbers");
        }
    }

    @RepeatedTest(5)
    @Tag("Circle")
    @DisplayName("Testing method to compute area of circle")
    void testComputeCircleArea(RepetitionInfo repetitionInfo) {
        System.out.println("You can do something with this infor:" + repetitionInfo.getCurrentRepetition());
        System.out.println("You can do something with this infor:" + repetitionInfo.getTotalRepetitions());
        assertEquals(Math.PI, arithmatic.computeCircleArea(1), "Should return area of circle");
    }

    @Test
    @DisplayName("Testing a method that throws an exception")
    void testDivide() {
        System.out.println("Running "+this.testInfo.getDisplayName() +" with "+testInfo.getTags());
        assertThrows(ArithmeticException.class, () -> arithmatic.divide(20, 0), "Must throw an Arithmetic Exc");
    }

    @Test
    @DisplayName("TDD method should not run")
    @Disabled
    void testDisabled() {
        fail("failing...");
    }

    @Test
    @DisplayName("Assumptions demo")
    void testAssuptions() {
        //continue to run this method if my assuption is true
        boolean serverStatus = true;
        assumeTrue(serverStatus);
        assertEquals(12, arithmatic.sum(12, 0));
    }

    @Test
    @DisplayName("Test multiplication")
    void testMultiply() {
        assertAll(
                () -> assertEquals(2, arithmatic.multiply(2, 1)),
                () -> assertEquals(-2, arithmatic.multiply(-2, 1)),
                () -> assertEquals(0, arithmatic.multiply(200, 0))
        );

    }

}
