package com.ysh;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoutingTableTest {

    @Test
    void testTableCompressorAPI() {
        RoutingTrieTree tree = new RoutingTable.Builder().setFile("r2.txt")
                .setRoutingParserProvider(RoutingTblParserFactory.getParser("IOS_ROUTER"))
                .build();
        List<RoutingTableRow> rows = tree.exportCompressionRTable();
        assertEquals(123, rows.size());
    }

}