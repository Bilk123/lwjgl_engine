package util;

public class Time {
    public static final int SECOND = 1000000000;
    public static final int MILLISECOND = 1000000;

    public static long getTime(){
        return System.nanoTime();
    }
}
