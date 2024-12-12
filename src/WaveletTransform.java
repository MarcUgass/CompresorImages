import java.io.IOException;

import static java.lang.Math.abs;

  public class WaveletTransform {

    public int[][][] RHAAR_FWD_LVL(int[][][] matrix, int levels) throws IOException {
      int numImages = matrix.length;
      int numRows = matrix[0].length;
      int numCols = matrix[0][0].length;

      // Copiem la matriu original
      int[][][] currentMatrix = new int[numImages][numRows][numCols];
      for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows; j++) {
          System.arraycopy(matrix[i][j], 0, currentMatrix[i][j], 0, numCols);
        }
      }

      int[][][] tempMatrix = new int[numImages][numRows][numCols];
      int[][][] output = new int[numImages][numRows][numCols];

      for (int level = 0; level < levels; level++) {
        int potencia = (int) Math.pow(2, level + 1);
        int halfRows = numRows / potencia; // Files actives
        int halfCols = numCols / potencia;// Columnes actives

        int numRows2 = numRows / potencia*2;
        int numCols2 = numCols / potencia*2;

        // Operació horitzontal (càlcul de promedis i detalls en columnes)
        for (int i = 0; i < numImages; i++) {
          for (int j = 0; j < numRows2; j++) {
            for (int k = 0; k < halfCols; k++) {
              int promedio = (currentMatrix[i][j][2 * k] + currentMatrix[i][j][2 * k + 1]) / 2;
              int detalle = currentMatrix[i][j][2 * k] - promedio;
              tempMatrix[i][j][k] = promedio;                // Promedios en la izquierda
              tempMatrix[i][j][k + halfCols] = detalle;     // Detalles en la derecha
            }
          }
        }

        // Operació vertical (càlcul de promedis i detalls en files)
        for (int i = 0; i < numImages; i++) {
          for (int j = 0; j < halfRows; j++) {
            for (int k = 0; k < numCols2; k++) {
              int promedio = (tempMatrix[i][2 * j][k] + tempMatrix[i][2 * j + 1][k]) / 2;
              int detalle = tempMatrix[i][2 * j][k] - promedio;
              output[i][j][k] = promedio;                // Promedios en la part superior
              output[i][j + halfRows][k] = detalle;     // Detalles en la part inferior
            }
          }
        }

        // Actualitzem currentMatrix amb el resultat parcial
        for (int i = 0; i < numImages; i++) {
          for (int j = 0; j < numRows; j++) {
            System.arraycopy(output[i][j], 0, currentMatrix[i][j], 0, numCols);
          }
        }
      }
      return output;
    }

    public int[][][] RHAAR_INV_LVL(int[][][] transformedMatrix, int levels) throws IOException {
      int numImages = transformedMatrix.length;
      int numRows = transformedMatrix[0].length;
      int numCols = transformedMatrix[0][0].length;

      // Copiem la matriu transformada
      int[][][] currentMatrix = new int[numImages][numRows][numCols];
      for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows; j++) {
          System.arraycopy(transformedMatrix[i][j], 0, currentMatrix[i][j], 0, numCols);
        }
      }

      int[][][] tempMatrix = new int[numImages][numRows][numCols];

      // Inversión de niveles desde el último al primero
      for (int level = levels - 1; level >= 0; level--) {
        int potencia = (int) Math.pow(2, level + 1);
        int halfRows = numRows / potencia; // Files actives
        int halfCols = numCols / potencia; // Columnes actives

        int numRows2 = numRows / potencia * 2;
        int numCols2 = numCols / potencia * 2;

        // Operació inversa vertical (reconstrucció de files)
        for (int i = 0; i < numImages; i++) {
          for (int j = 0; j < halfRows; j++) {
            for (int k = 0; k < numCols2; k++) {
              int promedio = currentMatrix[i][j][k];
              int detalle = currentMatrix[i][j + halfRows][k];

              tempMatrix[i][2 * j][k] = promedio + detalle;       // Reconstrucció fila superior
              tempMatrix[i][2 * j + 1][k] = promedio - detalle;   // Reconstrucció fila inferior
            }
          }
        }

        // Operació inversa horitzontal (reconstrucció de columnes)
        for (int i = 0; i < numImages; i++) {
          for (int j = 0; j < numRows2; j++) {
            for (int k = 0; k < halfCols; k++) {
              int promedio = tempMatrix[i][j][k];
              int detalle = tempMatrix[i][j][k + halfCols];

              currentMatrix[i][j][2 * k] = promedio + detalle;       // Reconstrucció columna esquerra
              currentMatrix[i][j][2 * k + 1] = promedio - detalle;   // Reconstrucció columna dreta
            }
          }
        }
      }

      return currentMatrix;
    }
}