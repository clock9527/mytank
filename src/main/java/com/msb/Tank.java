package com.msb;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Auther： WangLei
 * @Date： 2021/5/17-05-17-23:23
 * @Description: com.msb
 * @version: 1.0
 */
public class Tank {
    private int pX=300,pY=400,width=20,height=20,speed=10;
    private Color tankColor = Color.YELLOW;
    private Dir tankDir = Dir.UP;
    private Group group = Group.GOOD;
    private boolean moving = true;
    private BufferedImage tankImg = ResourceMgr.goodTankU;
    private int[] muzzlePos = new int[2]; //炮口
    private boolean lived = true;
    private Rectangle rectangle = new Rectangle(this.pX,this.pY,this.width,this.height);
    private Boolean boundaryFlag = false;   //false 未到达边界
    private TankFrame tf = null;
    private int lifeblood = 1;//生命值


    public Tank(Group g,TankFrame tf,int lifeblood){
        this.group = g;
        this.tf = tf;
        this.lifeblood = lifeblood;
        initMuzzlePos();
    }


    public Tank(int pX,int pY,int speed,Group g,TankFrame tf,Dir dir,int lifeblood) {
        this.pX = pX;
        this.pY = pY;
        this.speed = speed;
        this.group = g;
        this.tf = tf;
        this.tankDir = dir;
        this.lifeblood = lifeblood;
    }


    /**
     * 初始化炮口位置
     */
    private void initMuzzlePos(){
        switch (this.tankDir){
            case UP: muzzlePos[0] = this.pX + this.tankImg.getWidth() / 2;
                muzzlePos[1] = this.pY;
                break;
            case DOWN: muzzlePos[0] = this.pX + this.tankImg.getWidth() / 2;
                muzzlePos[1] = this.pY+this.tankImg.getHeight();
                break;
            case LEFT: muzzlePos[0] = this.pX;
                muzzlePos[1] = this.pY  + this.tankImg.getHeight() / 2;
                break;
            case RIGHT: muzzlePos[0] = this.pX + this.tankImg.getWidth();
                muzzlePos[1] = this.pY + this.tankImg.getHeight() / 2;
                break;
            case LEFT_UP: muzzlePos[0] = this.pX ;
                muzzlePos[1] = this.pY ;
                break;
            case LEFT_DOWN: muzzlePos[0] = this.pX ;
                muzzlePos[1] = this.pY + this.tankImg.getHeight();
                break;
            case RIGHT_UP: muzzlePos[0] = this.pX + this.tankImg.getWidth();
                muzzlePos[1] = this.pY ;
                break;
            case RIGHT_DOWN:R: muzzlePos[0] = this.pX + this.tankImg.getWidth();
                muzzlePos[1] = this.pY + this.tankImg.getHeight();
                break;
            default:break;
        }
    }

    /**
     * 画出坦克
     * @param g
     */
    public void paint(Graphics g) {

        generatTankImg(this.getTankDir());
        this.width = tankImg.getWidth();
        this.height = tankImg.getHeight();
        move();
        initMuzzlePos();
        g.drawImage(tankImg,this.pX,this.pY,this.width,this.height,null);

    }

    /**
     * 生成不同方向坦克图片
     * @param tankDir
     * @return
     */
    private void generatTankImg(Dir tankDir) {
        BufferedImage img = null;
        if(this.getGroup().equals(Group.GOOD))
            img = ResourceMgr.goodTankU;
        else if(this.getGroup().equals(Group.BAD))
            img = ResourceMgr.badTankU;

        switch (tankDir){
            case LEFT:tankImg = ImageUtil.rotateImage(img,-90); break;
            case RIGHT:tankImg = ImageUtil.rotateImage(img,90); break;
            case DOWN:tankImg = ImageUtil.rotateImage(img,180); break;
            case UP:tankImg = img; break;
            case LEFT_UP:tankImg = ImageUtil.rotateImage(img,-45); break;
            case LEFT_DOWN:tankImg = ImageUtil.rotateImage(img,-135); break;
            case RIGHT_UP:tankImg = ImageUtil.rotateImage(img,45); break;
            case RIGHT_DOWN:tankImg = ImageUtil.rotateImage(img,135); break;
            default:break;
        }

    }

    /**
     * 坦克按照一定方向，一定速度移动
     */
    private void move() {
        boundaryFlag = checkBoundary();
        if(!isMoving()|| boundaryFlag) return;
        switch(this.getTankDir()){
            case UP: this.pY -= this.speed; break;
            case DOWN: this.pY += this.speed; break;
            case LEFT: this.pX -= this.speed; break;
            case RIGHT: this.pX += this.speed; break;
            case LEFT_UP: this.pX -= this.speed; this.pY -= this.speed; break;
            case LEFT_DOWN: this.pX -= this.speed; this.pY += this.speed; break;
            case RIGHT_UP:this.pX += this.speed; this.pY -= this.speed; break;
            case RIGHT_DOWN: this.pX += this.speed; this.pY += this.speed; break;
            default:break;
        }
    }


    public void fire(TankFrame tf) {
        System.out.println("This tank is lived?"+this.lived+" fire...."+this.group.toString());
        if(!this.lived) return;
        tf.getBullets().add(new Bullet(this.muzzlePos[0],this.muzzlePos[1],5,5,this.getTankDir(),this.group));
    }

    public Rectangle getRectangle() {
        this.rectangle.setLocation(this.pX,this.pY);
        this.rectangle.setSize(this.width,this.height);
        return this.rectangle;
    }

    /**
     * 边界检测
     * @return
     */
    public boolean checkBoundary(){
        if((this.tankDir == Dir.LEFT || this.tankDir == Dir.LEFT_UP || this.tankDir == Dir.LEFT_DOWN) && this.pX <= TankFrame.frameX-100){
            return true;
        }else if((this.tankDir == Dir.RIGHT || this.tankDir == Dir.RIGHT_UP || this.tankDir == Dir.RIGHT_DOWN) && this.pX >= TankFrame.frameX+TankFrame.frameW-150){
            return true;
        }else if((this.tankDir == Dir.UP || this.tankDir == Dir.LEFT_UP || this.tankDir == Dir.RIGHT_UP) && this.pY <= TankFrame.frameY -80){
            return true;
        }else if((this.tankDir == Dir.DOWN || this.tankDir == Dir.LEFT_DOWN || this.tankDir == Dir.RIGHT_DOWN) && this.pY >= TankFrame.frameY+TankFrame.frameH-150){
            return true;
        }else {
            return false;
        }
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

    public Color getTankColor() {
        return tankColor;
    }

    public void setTankColor(Color tankColor) {
        this.tankColor = tankColor;
    }

    public Dir getTankDir() {
        return tankDir;
    }

    public void setTankDir(Dir tankDir) {
        this.tankDir = tankDir;
    }


    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public BufferedImage getTankImg() {
        return tankImg;
    }

    public Boolean getBoundaryFlag() {
        return boundaryFlag;
    }

    public int getLifeblood() {
        return lifeblood;
    }

    public void setLifeblood(int lifeblood) {
        this.lifeblood = lifeblood;
    }
}
