package com.ysh;

import java.util.LinkedList;
import java.util.List;

public class RoutingTrieTree {

    private final TrieNode root;

    public RoutingTrieTree() {
        root = new TrieNode();
    }

    public void addRule(RoutingTableRow routingTableRow) {
        if (routingTableRow == null) {
            return;
        }
        long[] subnet = IPUtils.parsePrefix(routingTableRow.getPrefix());
        long bit;
        TrieNode current = root;
        for (int i = 31; i > 31 - subnet[1]; i--) {
            bit = (subnet[0] >> i) & 0x1;
            if (bit == 0) {
                if (current.leftZero == null) {
                    current.leftZero = new TrieNode();
                }
                current = current.leftZero;
            } else {
                if (current.rightOne == null) {
                    current.rightOne = new TrieNode();
                }
                current = current.rightOne;
            }
        }
        current.nextHop = routingTableRow.getNextHop();
    }

    public void compress() {
        postOrderCompress(root, null);
    }

    private void postOrderCompress(TrieNode node, String parentAction) {
        if (node.nextHop != null) {
            parentAction = node.nextHop;
        }
        mergeParentKid(node, true, parentAction);
        mergeParentKid(node, false, parentAction);

        if (shouldEscalate(node)) {
            node.nextHop = node.leftZero.nextHop;
            node.resetChildren();
        }
    }


    private boolean shouldEscalate(TrieNode node) {
        return node.has2Kids()
                && node.leftZero.nextHop != null
                && node.leftZero.nextHop.equals(node.rightOne.nextHop);
    }

    private void mergeParentKid(TrieNode p, boolean isLeft, String parentAction) {
        TrieNode k = isLeft ? p.leftZero : p.rightOne;
        if (k == null) return;
        postOrderCompress(k, parentAction);
        if (k.nextHop == null) return;
        if (k.nextHop.equals(parentAction)) {
            if (isLeft) {
                p.resetLeft();
            } else {
                p.resetRight();
            }
            p.nextHop = parentAction;
        }
    }

    public void addAllRules(Iterable<RoutingTableRow> rows) {
        rows.forEach(this::addRule);
    }

    public List<RoutingTableRow> exportCompressionRTable() {
        List<RoutingTableRow> rows = new LinkedList<>();
        postOrderTraverse(root, 32, 0L, rows);
        return rows;
    }

    private void postOrderTraverse(TrieNode node, int position, long ip, List<RoutingTableRow> rows) {
        if (node == null) {
            return;
        }
        if (node.leftZero != null) {
            postOrderTraverse(node.leftZero, position - 1, ip, rows);
        }
        if (node.rightOne != null) {
            postOrderTraverse(node.rightOne, position - 1, ip + (1L << (position - 1)), rows);
        }
        if (node.nextHop != null) {
            String prefix = IPUtils.longToIp(ip) + "/" + (32 - position);
            rows.add(new RoutingTableRow(prefix, node.nextHop));
        }
    }

}
