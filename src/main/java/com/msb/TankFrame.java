package com.msb;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther： WangLei
 * @Date： 2021/5/17-05-17-22:51
 * @Description: com.msb
 * @version: 1.0
 */
public class TankFrame extends Frame {

    public static int frameX = 100, frameY = 100, frameW = 800, frameH = 600;
    private boolean up = false, down = false, left = false, right = false, ctrl = false;
    private Tank tank = new Tank(Group.GOOD, this,10);
    private List<Bullet> bullets = new ArrayList<Bullet>();
    public List<Tank> tanks = new ArrayList<>();
    private Random random = new Random(1);
    private List<Exploded> explodes = new ArrayList<>();
    {
        this.tank.setMoving(false);
    }
    public TankFrame() {
        setSize(frameW, frameH);
        setResizable(false);
        setTitle("TANK WAR");
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        if (this.tank.isLived()) this.tanks.add(this.tank);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new MyKeyListener());
    }

    /**
     * 双缓冲
     *
     * @param g
     */
    Image offScreanImage = null;

    @Override
    public void update(Graphics g) {
//        System.out.println("update  ...");
        if (offScreanImage == null) {
            offScreanImage = this.createImage(frameW, frameH);
        }
        Graphics gOffScreen = offScreanImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, frameW, frameH);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreanImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量：" + bullets.size(), 20, 50);
        g.drawString("坦克数量：" + tanks.size(), 20, 80);
        g.drawString("爆炸场面数：" + explodes.size(), 20, 110);
        g.setColor(c);

        /**
         * 碰撞测试
         */
        for (Tank t : tanks) {
            for (Bullet b : bullets) {
                collision(t, b);
            }
        }

//        tank.setTankColor(Color.BLUE);
//        tank.paint(g);

        if (!bullets.isEmpty())
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                if (!bullet.isLived()) {
                    bullets.remove(bullet);
                    continue;
                }
                bullet.paint(g);

            }

        if (!tanks.isEmpty())
            for (int i = 0; i < tanks.size(); i++) {
                Tank tk = tanks.get(i);
                if (!tk.isLived()) {
                    tanks.remove(tk);
                    continue;
                }

                if (tk.getBoundaryFlag() && tk.getGroup().equals(Group.BAD))
                    tk.setTankDir(Dir.values()[random.nextInt(Dir.values().length)]);
                if (tk.getGroup().equals(Group.BAD) && random.nextInt(18) == 9) tk.fire(this);
                tk.paint(g);
            }

        if (!explodes.isEmpty()) {
            for (int i = 0; i < explodes.size(); i++) {
                Exploded exploded = explodes.get(i);
                exploded.paint(g);
            }
        }
    }

    /**
     * 碰撞
     *
     * @param t
     * @param b
     */
    private void collision(Tank t, Bullet b) {
        if (!t.getGroup().equals(b.getGroup()) && t.getRectangle().intersects(b.getRectangle())) {
            b.setLived(false);
            t.setLifeblood(t.getLifeblood() - 1);
            if (t.getLifeblood() <= 0) {
                t.setLived(false);
                explodes.add(new Exploded(t.getpX() - t.getTankImg().getWidth() / 4, t.getpY() - t.getTankImg().getHeight() / 2, this));
            }
        }
    }


    class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            saveKeyStatus(e, true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            saveKeyStatus(e, false);
        }


    }

    /**
     * 确定按键状态
     *
     * @param e
     * @param b
     */
    private void saveKeyStatus(KeyEvent e, boolean b) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.up = b;
                break;
            case KeyEvent.VK_DOWN:
                this.down = b;
                break;
            case KeyEvent.VK_LEFT:
                this.left = b;
                break;
            case KeyEvent.VK_RIGHT:
                this.right = b;
                break;
            case KeyEvent.VK_CONTROL:
                this.ctrl = b;
                break;
            default:
                break;
        }
        tank.setMoving(this.up || this.down || this.left || this.right);
        orient();
        if (this.ctrl) tank.fire(this);

    }

    /**
     * 确定方向
     */
    private void orient() {

        if (this.up && !this.left && !this.right) tank.setTankDir(Dir.UP);
        if (this.down && !this.left && !this.right) tank.setTankDir(Dir.DOWN);
        if (this.left && !this.up && !this.down) tank.setTankDir(Dir.LEFT);
        if (this.right && !this.up && !this.down) tank.setTankDir(Dir.RIGHT);
        if (this.up && this.left && !this.right) tank.setTankDir(Dir.LEFT_UP);
        if (this.down && this.left && !this.right) tank.setTankDir(Dir.LEFT_DOWN);
        if (this.up && !this.left && this.right) tank.setTankDir(Dir.RIGHT_UP);
        if (this.down && !this.left && this.right) tank.setTankDir(Dir.RIGHT_DOWN);
        System.out.println(tank.getTankDir().toString());

    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Exploded> getExplodes() {
        return explodes;
    }

}
