package image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class generateImgImpl {
  BufferedImage myPicture;
  public generateImgImpl(int width, int height) throws IOException {
    myPicture = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    System.out.println("Successfully created image!");
  }


  public BufferedImage getBufferedImage() {
    return this.myPicture;
  }


}
