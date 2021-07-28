package com.msb;

import java.util.Random;

/**
 * @Auther： WangLei
 * @Date： 2021/5/17-05-17-22:51
 * @Description: com.msb
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        for (int i = 1; i <= 5; i++) {
            Thread.sleep(10);
            tf.tanks.add(new Tank(50 + 50 * i, 200, 3, Group.BAD, tf, Dir.values()[new Random().nextInt(Dir.values().length)],3));
        }

        while (true) {
            tf.repaint();
            Thread.sleep(50);
        }
    }
}
