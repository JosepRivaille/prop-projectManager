package edu.upc.fib.prop.test.unit.business;


import edu.upc.fib.prop.business.search.SearchBooleanExpression;
import edu.upc.fib.prop.business.search.impl.SearchBooleanExpressionImpl;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchBooleanExpressionTest {

    @Test
    public void test_whenCheckSimpleExpression1_withInvalidString_thenIsIncorrect() {
        String booleanExpression = "p & q |";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertFalse(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

    @Test
    public void test_whenCheckSimpleExpression2_withInvalidString_thenIsIncorrect() {
        String booleanExpression = "&";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertFalse(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

    @Test
    public void test_whenCheckSimpleExpression1_withValidString_thenIsCorrect() {
        String booleanExpression = "p | q & !e";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertTrue(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

    @Test
    public void test_whenCheckSimpleExpression2_withValidString_thenIsCorrect() {
        String booleanExpression = "p | (q & !e)";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertTrue(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

    @Test
    public void test_whenCheckSimpleExpression3_withValidString_thenIsCorrect() {
        String booleanExpression = "{a b c} | !(\"Mira mira\")";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertTrue(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

    @Test
    public void test_whenCheckComplexExpression_withInvalidString_thenIsIncorrect() {
        String booleanExpression = "{a b c} | (a & b & !c & \"Foo)";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertFalse(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

    @Test
    public void test_whenCheckComplexExpression_withValidString_thenIsCorrect() {
        String booleanExpression = "{a b c} | (a & b & !c & \"Bon dia i bona hora\")";

        SearchBooleanExpression searchBooleanExpression = new SearchBooleanExpressionImpl();
        assertTrue(searchBooleanExpression.checkValidBooleanExpression(booleanExpression));
    }

}
