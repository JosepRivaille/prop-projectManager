package edu.upc.fib.prop.models;

import java.util.*;

public class BooleanExpressionNode {

    private Set<Integer> matchingSentences;
    private Operator operator;
    private Type type;
    private String dealText;
    private Map<String, Map<Integer, Set<Integer>>> documentPositions;
    private List<BooleanExpressionNode> children;


    public BooleanExpressionNode() {
        this.matchingSentences = new HashSet<>();
        this.children = new ArrayList<>();
    }

    public void setDealText(String dealText) {
        this.dealText = dealText;
        buildTree();
    }

    //////////

    /**
     * Builds boolean tree nodes from the original expression.
     */
    private void buildTree() {

    }

    /**
     * Checks if document matches the tree expression.
     * @param documentPositions Document term positions information.
     * @return Boolean indicating if document matches or not.
     */
    private boolean checkDocumentMatches(Map<String, Map<Integer, Set<Integer>>> documentPositions) {
        this.documentPositions = documentPositions;
        return true;
    }

    private Map<Integer, Set<Integer>> recursiveChecker(BooleanExpressionNode root) {
        if (root.children.size() == 0) {
            return documentPositions.get(root.dealText);
        } else {

        }
        return null;
    }

    @SafeVarargs
    private final Map<Integer, Set<Integer>> applyOperatorReduction(BooleanExpressionNode node,
                                                                    Map<Integer, Set<Integer>>... children) {
        Map<Integer, Set<Integer>> leftChild = children[0];
        Map<Integer, Set<Integer>> rightChild = children[1];
        Map<Integer, Set<Integer>> totalChildren = new HashMap<>();
        switch (node.type) {
            case WORD:
                switch (node.operator) {
                    case AND:

                        break;
                    case OR:
                }
                break;
            case SENTENCE:
                switch (node.operator) {
                    case AND:
                        break;
                    case OR:
                }
        }
        return null;
    }

    //////////

    private enum Operator {
        AND, OR
    }

    private enum Type {
        WORD, SENTENCE
    }
}
