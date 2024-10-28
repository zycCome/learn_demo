package com.zyc.learn_demo.algorithm.timewheel2;


public class TimerLauncherTest {


    public static void main(String[] args) throws InterruptedException {


        Timer timer = new TimerLauncher();
        TimerTask timerTask = new TimerTask("a",30);
        timer.add(timerTask);

        Thread.sleep(50);
        TimerTask timerTask2 = new TimerTask("b",60);
        timer.add(timerTask2);

        TimerTask timerTask3 = new TimerTask("c",1000);
        timer.add(timerTask3);


        TimerTask timerTask4 = new TimerTask("d",80);
        timer.add(timerTask4);

        TimerTask timerTask5 = new TimerTask("e",120);
        timer.add(timerTask5);


        TimerTask timerTask6 = new TimerTask("f",70000);
        timer.add(timerTask6);


        TimerTask timerTask7 = new TimerTask("g",55);
        timer.add(timerTask7);

//        Scanner scanner = new Scanner(System.in);
//        String s= scanner.nextLine();
        Thread.sleep(100);
    }

}

