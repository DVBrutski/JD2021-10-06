package by.it.serkutsyev.jd02_02.helper;

public class Timeout {

    public static final int K_SPEED=100;

    public static void sleep (int milis){
        try {
            Thread.sleep(milis/K_SPEED);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}