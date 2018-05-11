package cloud.dispatcher.base.framework.utils;

import java.net.*;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;

public final class LocalizeEnvUtil {

    private LocalizeEnvUtil() {}

    public static String getMachineIpv4Address() {
        try {
            String ipAddress = StringUtils.EMPTY;
            Enumeration e1 = NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) e1.nextElement();
                for (Enumeration e2 = networkInterface.getInetAddresses(); e2.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) e2.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress instanceof Inet4Address) {
                            ipAddress = inetAddress.getHostAddress();
                            break;
                        }
                    }
                }
            }
            return ipAddress;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMachineIpv6Address() {
        try {
            String ipAddress = StringUtils.EMPTY;
            Enumeration e1 = NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) e1.nextElement();
                for (Enumeration e2 = networkInterface.getInetAddresses(); e2.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) e2.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress instanceof Inet6Address) {
                            ipAddress = inetAddress.getHostAddress();
                        }
                    }
                }
            }
            return ipAddress;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
