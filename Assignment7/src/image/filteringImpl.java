package image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class filteringImpl implements filtering {
  BufferedImage myPicture = null;
  int[][] myPicture2DMatrix;
  public static void main(String[] args) {
    // load image
  }

  /**
   * First constructor for this class that import an image and change it to Graphics2D image.
   * @param ref
   */
  // load image
  public filteringImpl(String ref) {
    String imagePath = ref; // add path
    try {
      myPicture = ImageIO.read(new File(imagePath));
      myPicture2DMatrix = generate2D(myPicture);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Success");
  }

  /**
   * Second constructor for this class that generates an image by itself.
   * @param height
   * @param width
   * @throws IOException
   */
  // generate image
  public filteringImpl(int height, int width) throws IOException {
    generateImgImpl generator = new generateImgImpl(height, width);
    myPicture = generator.getBufferedImage();
    myPicture2DMatrix = generate2D(myPicture);
  }

  public int[][] generate2D(BufferedImage inputImage) {
    int row = inputImage.getHeight();
    int col = inputImage.getWidth();
    int[][] imageMatrix = new int[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        imageMatrix[i][j] = inputImage.getRGB(i, j);
      }
    }
    return imageMatrix;
  }
  /**
   * A method to use filter for image, which is represented as a 2D matrix.
   * @param kernel
   * @param imageMatrix
   */

  @Override
  public void filter(double[][] kernel, int[][] imageMatrix) {
    int kernelRow = kernel.length;
    int kernelCol = kernel[0].length;
    offset[][] kernelOffset = new offset[kernelRow][kernelCol];
    int centerPositionX = kernelRow / 2;
    int centerPositionY = kernelCol / 2;

    // build offset matrix for kernel
    for (int i = 0; i < kernelRow; i++) {
      for (int j = 0; j < kernelCol; j++) {
        int positionX = i - centerPositionX;
        int positionY = j - centerPositionY;
        offset element = new offset(positionX, positionY, kernel[i][j]);
        kernelOffset[i][j] = element;
      }
    }

    // make a new matrix the same size as imageMatrix
    int imageMatrixRow = imageMatrix.length;
    int imageMatrixCol = imageMatrix[0].length;
    int[][] copyImageMatrix = new int[imageMatrixRow][imageMatrixCol];

    // filer the imageMatrix
    for (int i = 0; i < imageMatrixRow; i++) {
      for (int j = 0; j < imageMatrixCol; j++) {
        int value = filterHelper(kernelOffset, imageMatrix, i, j);
        copyImageMatrix[i][j] = value;
      }
    }

    // replace the value in imageMatrix with the value in copyImageMatrix
    for (int i = 0; i < imageMatrixRow; i++) {
      for (int j = 0; j < imageMatrixCol; j++) {
        imageMatrix[i][j] = copyImageMatrix[i][j];
      }
    }
  }


  public int filterHelper(offset[][] kernelOffset, int[][] matrix, int x, int y) {
    int kernelRow = kernelOffset.length;
    int kernelCol = kernelOffset[0].length;

    // calculate the value for spot in matrix
    double red = 0.0;
    double green = 0.0;
    double blue = 0.0;
    for (int i = 0; i < kernelRow; i++) {
      for (int j = 0; j < kernelCol; j++) {
        int positionX = kernelOffset[i][j].x + x;
        int positionY = kernelOffset[i][j].y + y;
        double weight = kernelOffset[i][j].weight;
        if (positionX >= 0 && positionX < matrix.length
                && positionY >= 0 && positionY < matrix[0].length) {
          Color color = new Color(matrix[positionX][positionY]);
          red += weight * color.getRed();
          green += weight * color.getGreen();
          blue += weight * color.getBlue();
        }
      }
    }
    return new Color(clamp(red), clamp(green), clamp(blue)).getRGB();
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

  public static class offset {
    int x;
    int y;
    double weight;
    public offset(int x, int y, double weight) {
      this.x = x;
      this.y = y;
      this.weight = weight;
    }
  }


  @Override
  public void blur() {
    double[][] blurMatrix = {
            {0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}
    };
    filter(blurMatrix, myPicture2DMatrix);
  }

  @Override
  public void sharpen() {
    double[][] sharpenMatrix = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125},
    };
    filter(sharpenMatrix, myPicture2DMatrix);
  }
}
