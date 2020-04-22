package com.ysh;

public class RoutingTableRow {
    private String prefix;
    private String nextHop;

    public RoutingTableRow(String prefix, String nextHop) {
        this.prefix = prefix;
        this.nextHop = nextHop;
    }

    public RoutingTableRow(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }
}
