package com.msb;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Auther： WangLei
 * @Date： 2021/5/24-05-24-22:33
 * @Description: com.msb
 * @version: 1.0
 */
public class Bullet {
    private int pX,pY,width,height,speed=20;
    private Color color = Color.red; private Dir bDir = Dir.UP;
    private Group group = Group.GOOD;
    private boolean lived = true;
    private BufferedImage bImage = null;
    private Rectangle rectangle = new Rectangle(this.pX,this.pY,this.width,this.height);


    public Bullet(int x,int y,int w,int h,Dir d,Group g){
        this.pX = x;
        this.pY = y;
        this.width = w;
        this.height = h;
        this.bDir = d;
        this.group = g;
    }


    public void paint(Graphics g){
        if(!this.lived) return;

        generatBulletImg(this.bDir);

        move();
//        g.setColor(color);
//        g.fillRect(this.pX,this.pY,this.width,this.height);
        g.drawImage(this.bImage,this.pX,this.pY,null);

    }

    /**
     * 生成不同方向子弹图片
     * @param bDir
     * @return
     */
    private void generatBulletImg(Dir bDir) {
        BufferedImage img = ResourceMgr.bulletU;
        switch (bDir){
            case LEFT:bImage = ImageUtil.rotateImage(img,-90); break;
            case RIGHT:bImage = ImageUtil.rotateImage(img,90); break;
            case UP:bImage = img; break;
            case DOWN:bImage = ImageUtil.rotateImage(img,180); break;
            case LEFT_UP:bImage = ImageUtil.rotateImage(img,-45); break;
            case LEFT_DOWN:bImage = ImageUtil.rotateImage(img,-135); break;
            case RIGHT_UP:bImage = ImageUtil.rotateImage(img,45); break;
            case RIGHT_DOWN:bImage = ImageUtil.rotateImage(img,135); break;
            default:break;
        }

    }

    private void move() {

        switch(this.bDir){
            case UP: this.pY -= this.speed;break;
            case DOWN: this.pY += this.speed;break;
            case LEFT: this.pX -= this.speed;break;
            case RIGHT: this.pX += this.speed;break;
            case LEFT_UP: this.pX -= this.speed;this.pY -=speed;break;
            case LEFT_DOWN: this.pX -= this.speed;this.pY += this.speed;break;
            case RIGHT_UP: this.pX += speed;this.pY -= speed;break;
            case RIGHT_DOWN: this.pX += speed;this.pY += speed;break;
            default:break;
        }

        /**
         *
         */
        if(this.pX < 0 || this.pY < 0 || this.pX > TankFrame.frameW || this.pY > TankFrame.frameH)
            this.lived = false;

    }

    public Rectangle getRectangle() {
        this.rectangle.setLocation(this.pX,this.pY);
        this.rectangle.setSize(this.width,this.height);
        return this.rectangle;
    }

    public int getpX() {
        return pX;
    }

    public void setpX(int pX) {
        this.pX = pX;
    }

    public int getpY() {
        return pY;
    }

    public void setpY(int pY) {
        this.pY = pY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Dir getbDir() {
        return bDir;
    }

    public void setbDir(Dir bDir) {
        this.bDir = bDir;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public boolean isLived() {
        return lived;
    }

    public void setLived(boolean lived) {
        this.lived = lived;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
