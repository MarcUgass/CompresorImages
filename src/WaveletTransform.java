import static java.lang.Math.abs;

public class WaveletTransform {

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
  public int[][][] RHAAR_INV_LVL(int[][][] matrix, int levels) {
    int numImages = matrix.length;
    int numRows = matrix[0].length;
    int numCols = matrix[0][0].length;
    int[][][] output = new int[numImages][numRows][numCols];
    int[][][] output2 = new int[numImages][numRows][numCols];
    int detall = 0;
    int potencia = 0;

    for (int l = levels; l >= 1; l--) {
      potencia = (int) Math.pow(2, l);
      for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows / potencia; j++) {
          for (int k = 0; k < numCols; k++) {
            detall = matrix[i][j + (numRows / potencia)][k];
            output[i][j * 2][k] = matrix[i][j][k] + ((int) Math.floor(detall / 2));
            output[i][j * 2 + 1][k] = output[i][j * 2][k] + detall;
          }
        }
      }

      for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows; j++) {
          for (int k = 0; k < numCols / potencia; k++) {
            detall = output[i][j][k + (numCols / potencia)];
            output2[i][j][k * 2] = output[i][j][k] + ((int) Math.floor(detall / 2));
            output2[i][j][k * 2 + 1] = output2[i][j][k * 2] + detall;
          }
        }
      }
    }
    return output2;
  }

  public int[][][] RHAAR_FWD_LVL(int[][][] matrix, int levels) {
    int numImages = matrix.length;
    int numRows = matrix[0].length;
    int numCols = matrix[0][0].length;
    int[][][] output = new int[numImages][numRows][numCols];
    int[][][] output2 = new int[numImages][numRows][numCols];
    int detall = 0;
    int potencia = 0;

    for (int l = 1; l <= levels; l++) {
      potencia = (int) Math.pow(2,l);
      for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows; j++) {
          for (int k = 0; k < numCols / potencia; k++) {

            detall = abs(matrix[i][j][2*k] - matrix[i][j][2*k + 1]);
            output[i][j][k + (numCols / potencia)] = detall;
            output[i][j][k] = abs(matrix[i][j][k * potencia] - ((int) Math.floor(detall / 2)));

          }
        }
      }

      for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows / potencia; j++) {
          for (int k = 0; k < numCols; k++) {

            detall = abs (output[i][2*j][k] - output[i][2*j + 1][k]);
            output2[i][j + (numRows / potencia)][k] = detall;
            output2[i][j][k] = abs(output[i][j * potencia][k] - ((int) Math.floor(detall / 2)));

          }
        }
      }
    }
    return output2;
  }
}



