package net.rickiekarp.core.util;

public class OSValidator {

    public enum OperatingSystem {
        WINDOWS,
        MAC,
        UNIX,
        SOLARIS
    }

    public static void main(String[] args) {
        OperatingSystem os = getOS();
        if (os != null) {
            switch (os) {
                case WINDOWS:   System.out.println("This is Windows");          break;
                case MAC:       System.out.println("This is Mac");              break;
                case UNIX:      System.out.println("This is Unix or Linux");    break;
                case SOLARIS:   System.out.println("This is Solaris");          break;
            }
        }
    }

    private static boolean isWindows(String os) {
        return (os.contains("win"));
    }

    private static boolean isMac(String os) {
        return (os.contains("mac"));
    }

    private static boolean isUnix(String os) {
        return (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0 );
    }

    private static boolean isSolaris(String os) {
        return (os.contains("sunos"));
    }

    public static OperatingSystem getOS(){
        String OS = System.getProperty("os.name").toLowerCase();
        if (isWindows(OS)) {
            return OperatingSystem.WINDOWS;
        } else if (isMac(OS)) {
            return OperatingSystem.MAC;
        } else if (isUnix(OS)) {
            return OperatingSystem.UNIX;
        } else if (isSolaris(OS)) {
            return OperatingSystem.SOLARIS;
        } else {
            System.out.println("Could not detect operating system!");
            return null;
        }
    }

}