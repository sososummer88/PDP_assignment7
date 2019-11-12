package image;

import java.awt.*;

public class transformationImpl implements transformation{
  filteringImpl newImage = new filteringImpl("");
  int[][] image2DMatrix;

  public transformationImpl() {
    image2DMatrix = newImage.myPicture2DMatrix;
  }

  @Override
  public void transform(double[][] transMatrix) {
    for (int i = 0; i < image2DMatrix.length; i++) {
      for (int j = 0; j < image2DMatrix[0].length; j++) {
        Color color = new Color(image2DMatrix[i][j]);
        double red = transMatrix[0][0] * color.getRed()
                   + transMatrix[0][1] * color.getGreen()
                   + transMatrix[0][2] * color.getBlue();
        double green = transMatrix[1][0] * color.getRed()
                + transMatrix[1][1] * color.getGreen()
                + transMatrix[1][2] * color.getBlue();
        double blue = transMatrix[2][0] * color.getRed()
                + transMatrix[2][1] * color.getGreen()
                + transMatrix[2][2] * color.getBlue();
        image2DMatrix[i][j] = new Color(clamp(red), clamp(green), clamp(blue)).getRGB();
      }
    }
  }

  private int clamp(double value) {
    if (value < 0.0) {
      return 0;
    } else {
      if (value > 255.0) {
        return 255;
      } else {
        return (int)value;
      }
    }
  }

  @Override
  public void getGreyScale() {
    double[][] greyScale = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}
    };
    transform(greyScale);
  }

  @Override
  public void getSepiaTone() {
    double[][] sepiaTone = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    transform(sepiaTone);
  }
}
