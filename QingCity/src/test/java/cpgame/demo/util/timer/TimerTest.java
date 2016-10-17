package cpgame.demo.util.timer;

import java.util.Timer;

public class TimerTest {
    Timer timer;
    
    public TimerTest(){
        timer = new Timer();
        timer.schedule(new TimerTaskTest(), 1000, 2000);
    }
    
    public static void main(String[] args) {
        new TimerTest();
    }
}