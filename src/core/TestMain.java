package core;

public class TestMain {
    public static void main(String[] args) {
        System.out.println(Integer.toHexString(0x37)+ " : " + Integer.toHexString(((0x37ffffff>>>24))));
    }
}
