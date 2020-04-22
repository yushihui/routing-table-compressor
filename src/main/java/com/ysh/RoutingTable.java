package com.ysh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;

public class RoutingTable {

    public static class Builder {
        private Supplier<Function<String, RoutingTableRow>> routingParserProvider;
        private String file;

        public RoutingTrieTree build() {
            Function<String, RoutingTableRow> f = this.routingParserProvider.get();
            RoutingTrieTree tree = new RoutingTrieTree();
            try {
                Files.lines(Paths.get(this.file))
                        .forEachOrdered(line -> tree.addRule(f.apply(line)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tree.compress();
            return tree;
        }

        public Builder setRoutingParserProvider(Supplier<Function<String, RoutingTableRow>> routingParserProvider) {
            this.routingParserProvider = routingParserProvider;
            return this;
        }

        public Builder setFile(String file) {
            this.file = file;
            return this;
        }

    }
}
