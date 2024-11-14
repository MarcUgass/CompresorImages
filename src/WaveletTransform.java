public class WaveletTransform {

  public int[][][] RHAAR_FWD(int[][][] matrix) {
    int numImages = matrix.length;
    int numRows = matrix[0].length;
    int numCols = matrix[0][0].length;
    int[][][] output = new int[numImages][numRows][numCols];
    int[][][] output2 = new int[numImages][numRows][numCols];

    int detall = 0;

    for (int i = 0; i < numImages; i++) {
      for (int j = 0; j < numRows; j++) {
        for (int k = 0; k < numCols / 2; k++) {
          detall = matrix[i][j][k] - matrix[i][j][k + 1];
          output[i][j][k + (numCols / 2)] = detall;
          output[i][j][k] = matrix[i][j][k * 2] - ((int) Math.floor(detall / 2));

        }
      }
    }

    for (int i = 0; i < numImages; i++) {
      for (int j = 0; j < numRows / 2; j++) {
        for (int k = 0; k < numCols; k++) {
          detall = output[i][j][k] - output[i][j + 1][k];
          output2[i][j + (numRows / 2)][k] = detall;
          output2[i][j][k] = output[i][j * 2][k] - ((int) Math.floor(detall / 2));

        }
      }
    }
    return output2;
  }

  public int[][][] RHAAR_INV(int[][][] matrix) {
    int numImages = matrix.length;
    int numRows = matrix[0].length;
    int numCols = matrix[0][0].length;
    int[][][] output = new int[numImages][numRows][numCols];
    int[][][] output2 = new int[numImages][numRows][numCols];

    int detall = 0;

    for (int i = 0; i < numImages; i++) {
      for (int j = 0; j < numRows / 2; j++) {
        for (int k = 0; k < numCols; k++) {
          detall = matrix[i][j + (numRows / 2)][k];
          output[i][j * 2][k] = matrix[i][j][k] + ((int) Math.floor(detall / 2));
          output[i][j * 2 + 1][k] = output[i][j * 2][k] + detall;
        }
      }
    }

    for (int i = 0; i < numImages; i++) {
      for (int j = 0; j < numRows; j++) {
        for (int k = 0; k < numCols / 2; k++) {
          detall = output[i][j][k + (numCols / 2)];
          output2[i][j][k * 2] = output[i][j][k] + ((int) Math.floor(detall / 2));
          output2[i][j][k * 2 + 1] = output2[i][j][k * 2] + detall;
        }
      }
    }
    return output2;
  }
}
