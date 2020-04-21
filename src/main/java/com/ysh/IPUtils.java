package com.ysh;

import java.security.InvalidParameterException;

public class IPUtils {

    public static long ipToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }
        return result;
    }

    public static String longToIp(long ip) {

        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);

    }

    // 10.3.0.0/16
    // 10.3.0.0 -> 167968768
    //return [167968768, 16]
    public static long[] parsePrefix(String prefix) {
        String[] ipSubnet = prefix.split("/");
        if (ipSubnet.length != 2) {
            throw new InvalidParameterException("invalid parameter");
        }
        long ip = ipToLong(ipSubnet[0]);
        long mask = Long.parseLong(ipSubnet[1]);
        return new long[]{ip, mask};
    }
}
