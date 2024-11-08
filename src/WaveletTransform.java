public class WaveletTransform {

  /**
   * Forward Haar Wavelet Transform (RHAAR_FWD) for a 3D matrix.
   * Applies the transform row-wise, then column-wise, for each image in the 3D array.
   *
   * @param input The 3D matrix representing multiple images.
   * @return The transformed 3D matrix.
   */
  public static int[][][] RHAAR_FWD(int[][][] input) {
    int numImages = input.length;
    int rows = input[0].length;
    int cols = input[0][0].length;

    // Output storage for transformed images
    int[][][] output = new int[numImages][rows][cols];

    for (int img = 0; img < numImages; img++) {
      int[][] temp = new int[rows][cols];

      // Apply the 1D Haar transform row-wise
      for (int i = 0; i < rows; i++) {
        haarTransform1D(input[img][i], temp[i]);
      }

      // Apply the 1D Haar transform column-wise
      for (int j = 0; j < cols; j++) {
        int[] col = new int[rows];
        for (int i = 0; i < rows; i++) {
          col[i] = temp[i][j];
        }
        int[] transformedCol = new int[rows];
        haarTransform1D(col, transformedCol);
        for (int i = 0; i < rows; i++) {
          output[img][i][j] = transformedCol[i];
        }
      }
    }
    return output;
  }

  /**
   * Inverse Haar Wavelet Transform (RHAAR_INV) for a 3D matrix.
   * Applies the inverse transform column-wise, then row-wise, for each image.
   *
   * @param input The transformed 3D matrix.
   * @return The original 3D matrix after inverse transformation.
   */
  public static int[][][] RHAAR_INV(int[][][] input) {
    int numImages = input.length;
    int rows = input[0].length;
    int cols = input[0][0].length;

    int[][][] output = new int[numImages][rows][cols];

    for (int img = 0; img < numImages; img++) {
      int[][] temp = new int[rows][cols];

      // Apply the inverse transform column-wise
      for (int j = 0; j < cols; j++) {
        int[] col = new int[rows];
        for (int i = 0; i < rows; i++) {
          col[i] = input[img][i][j];
        }
        int[] invertedCol = new int[rows];
        inverseHaarTransform1D(col, invertedCol);
        for (int i = 0; i < rows; i++) {
          temp[i][j] = invertedCol[i];
        }
      }

      // Apply the inverse transform row-wise
      for (int i = 0; i < rows; i++) {
        inverseHaarTransform1D(temp[i], output[img][i]);
      }
    }
    return output;
  }

  /**
   * 1D Haar Transform for a single row or column.
   * @param input The input vector to transform.
   * @param output The output vector to store transformed data.
   */
  private static void haarTransform1D(int[] input, int[] output) {
    int n = input.length;
    while (n > 1) {
      for (int i = 0; i < n / 2; i++) {
        output[i] = (input[2 * i] + input[2 * i + 1]) / 2;        // Average (low-pass)
        output[n / 2 + i] = (input[2 * i] - input[2 * i + 1]) / 2; // Difference (high-pass)
      }
      System.arraycopy(output, 0, input, 0, n);
      n /= 2;
    }
  }

  /**
   * 1D Inverse Haar Transform for a single row or column.
   * @param input The input vector (transformed data) to invert.
   * @param output The output vector to store original data.
   */
  private static void inverseHaarTransform1D(int[] input, int[] output) {
    int n = 1;
    while (n < input.length) {
      for (int i = 0; i < n; i++) {
        output[2 * i] = input[i] + input[n + i];    // Restore original value (low-pass + high-pass)
        output[2 * i + 1] = input[i] - input[n + i]; // Restore original value (low-pass - high-pass)
      }
      System.arraycopy(output, 0, input, 0, 2 * n);
      n *= 2;
    }
  }
}
