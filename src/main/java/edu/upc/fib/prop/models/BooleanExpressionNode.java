package edu.upc.fib.prop.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BooleanExpressionNode {

    private Set<Integer> matchingSentences;
    private Operator operator;
    private Type type;
    private String dealText;
    private List<BooleanExpressionNode> children;


    public BooleanExpressionNode(String booleanExpression) {
        List<BooleanExpressionNode> childrenNodes = buildNodes(booleanExpression);
        for (BooleanExpressionNode childNode : childrenNodes) {
            this.children.add(childNode);
        }
    }

    private List<BooleanExpressionNode> buildNodes(String dealText) {
        return new ArrayList<>();
    }

    private enum Operator {
        AND, OR, NOT
    }

    private enum Type {
        WORD, SENTENCE
    }
}
