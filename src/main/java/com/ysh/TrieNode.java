package com.ysh;

// it's also a binary tree
public class TrieNode {
    TrieNode leftZero;
    TrieNode rightOne;
    boolean isEnd; //unused
    String nextHop;

    public TrieNode() {
    }
    public boolean isLeaf() {
        return leftZero == null && rightOne == null;
    }

    public boolean has2Kids() {
        return leftZero != null && rightOne != null;
    }

    public void resetChildren() {
        resetLeft();
        resetRight();
    }

    public void resetLeft() {
        if (this.leftZero.isLeaf()) {
            this.leftZero = null;
        } else {
            this.leftZero.nextHop = null;
        }
    }

    public void resetRight() {
        if (this.rightOne.isLeaf()) {
            this.rightOne = null;
        } else {
            this.rightOne.nextHop = null;
        }
    }
}
