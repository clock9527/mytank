package com.msb;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Auther： WangLstep
 * @Date： 2021/7/6-07-06-22:29
 * @Description: com.msb
 * @version: 1.0
 */
public class Exploded {
    private int x,y;
    private boolean isLive = true;
    private BufferedImage[] explodes = ResourceMgr.explodes;
    private int step = 0;
    private TankFrame tf;

    public Exploded (int x,int y,TankFrame tf){
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g){
        if(step >= explodes.length) {
            tf.getExplodes().remove(this);
            return;
        }
        g.drawImage(explodes[step],this.x,this.y,null);
        step ++;
    }




    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
