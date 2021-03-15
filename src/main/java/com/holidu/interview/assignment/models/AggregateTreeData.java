package com.holidu.interview.assignment.models;

import java.util.Map;

public class AggregateTreeData {

    private Map<String, Integer> trees;

    public AggregateTreeData(Map<String, Integer> trees) {
        this.trees = trees;
    }

    public Map<String, Integer> getTrees() {
        return trees;
    }

    public void setTrees(Map<String, Integer> trees) {
        this.trees = trees;
    }
}
