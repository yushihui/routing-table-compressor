package com.ysh;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutingTrieTreeTest {

    @Test
    void addAllRules() {
        RoutingTrieTree tree = new RoutingTrieTree();
        tree.addAllRules(List.of(
                new RoutingTableRow("192.168.208.0/24", "172.20.4.1"),
                new RoutingTableRow("192.168.209.0/30", "172.20.4.1"),
                new RoutingTableRow("192.168.208.0/20", "172.20.4.1"),
                new RoutingTableRow("192.168.212.0/23", "172.20.4.1"),
                new RoutingTableRow("192.168.236.0/24", "172.20.4.161"),
                new RoutingTableRow("192.168.219.0/24", "172.20.4.1")));
        tree.optimize();
        List<RoutingTableRow> results = tree.exportCompressionRTable();
        assertAll(
                () -> assertEquals(2, results.size()),
                () -> assertEquals("192.168.208.0/20", results.get(0).getPrefix()),
                () -> assertEquals("172.20.4.1", results.get(0).getNextHop()),
                () -> assertEquals("192.168.236.0/24", results.get(1).getPrefix()),
                () -> assertEquals("172.20.4.161", results.get(1).getNextHop())
        );
    }
}