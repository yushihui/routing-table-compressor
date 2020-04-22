package com.ysh;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class RoutingTblParserFactory {

    private final static Map<String, Supplier<Function<String, RoutingTableRow>>> map =
            Map.of("IOS_ROUTER", () -> RoutingTblParserFactory::iosParser);

    public static Supplier<Function<String, RoutingTableRow>> getParser(String type) {
        if (map.containsKey(type)) {
            return map.get(type);
        }
        throw new InvalidParameterException("wrong type");
    }

    private static RoutingTableRow iosParser(String line) {
        RoutingTableRow row = null;
        String[] cells = line.split("\\s+");
        if (cells.length > 0 && IPUtils.isValidCIDR(cells[0])) {
            row = new RoutingTableRow(cells[0]);
            if (cells.length > 1) {
                row.setNextHop(cells[1]);
            }
        }
        return row;
    }
}
