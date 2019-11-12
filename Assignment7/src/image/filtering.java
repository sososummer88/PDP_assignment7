package image;

/**
 * This is a filtering interface for filteringImpl.
 */
public interface filtering {
  /**
   * This method takes a kernel and apply it to image which is represented as
   * a 2D matrix.
   * @param kernel
   * @param matrix
   */
  void filter(double[][] kernel, int[][] matrix);

  /**
   * This method uses filter method to blur the image.
   */
  void blur();

  /**
   * This method uses filter method to sharpen the image.
   */
  void sharpen();
}
