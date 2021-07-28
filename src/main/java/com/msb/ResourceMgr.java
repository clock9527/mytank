package com.msb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Auther： WangLei
 * @Date： 2021/5/19-05-19-23:00
 * @Description: com.msb
 * @version: 1.0
 */
public class ResourceMgr {
    public static BufferedImage goodTankU,badTankU,bulletU;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
            //装载Good坦克图片
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            /*tankL = ImageUtil.rotateImage(tankU,-90);
            tankR = ImageUtil.rotateImage(tankU,90);
            tankD = ImageUtil.rotateImage(tankU,180);
            tankLU = ImageUtil.rotateImage(tankU,-45);
            tankLD = ImageUtil.rotateImage(tankU,-135);
            tankRU = ImageUtil.rotateImage(tankU,45);
            tankRD = ImageUtil.rotateImage(tankU,135);*/

            //装载Bad坦克图片
            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            /*badTankL = ImageUtil.rotateImage(badTankU,-90);
            badTankR = ImageUtil.rotateImage(badTankU,90);
            badTankD = ImageUtil.rotateImage(badTankU,180);
            badTankLU = ImageUtil.rotateImage(badTankU,-45);
            badTankLD = ImageUtil.rotateImage(badTankU,-135);
            badTankRU = ImageUtil.rotateImage(badTankU,45);
            badTankRD = ImageUtil.rotateImage(badTankU,135);*/

            //装载子弹图片
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            /*bulletD = ImageUtil.rotateImage(bulletU,180);
            bulletL = ImageUtil.rotateImage(bulletU,-90);
            bulletR = ImageUtil.rotateImage(bulletU,90);
            bulletLU = ImageUtil.rotateImage(bulletU,-45);
            bulletLD = ImageUtil.rotateImage(bulletU,-135);
            bulletRU = ImageUtil.rotateImage(bulletU,45);
            bulletRD = ImageUtil.rotateImage(bulletU,135);*/

            //装载爆炸图片
            for(int i = 0; i < 16; i++){
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
