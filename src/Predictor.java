public class Predictor {
  /**
   * Applies the predictor to each row in each image and returns a new matrix with the computed values.
   * @param matrix The original 3D matrix representing the pixel values.
   * @return A new 3D matrix where each row has been replaced by the predictor values.
   */
  public int[][][] Predictor1(int[][][] matrix) {
    int numImages = matrix.length;          // Number of images
    int numRows = matrix[0].length;         // Number of rows per image
    int numCols = matrix[0][0].length;      // Number of columns per row (depth dimension)

    // Initialize a new 3D matrix to store the predictor results
    int[][][] predictedMatrix = new int[numImages][numRows][numCols];

    // Iterate over each image, row, and column to calculate the predictor values
    for (int i = 0; i < numImages; i++) {
      for (int j = 0; j < numRows; j++) {
        for (int k = 0; k < numCols; k++) {
          // Apply the predictor to each element in the row
          if (k == 0) {
            // For the first element in the row
            predictedMatrix[i][j][k] = matrix[i][j][k];
          } else if (k == 1) {
            // For the second element in the row
            predictedMatrix[i][j][k] = matrix[i][j][k - 1];
          } else {
            // For elements beyond the second in the row
            predictedMatrix[i][j][k] = (2 * matrix[i][j][k - 1] + matrix[i][j][k - 2]) / 3;
          }
        }
      }
    }
    return predictedMatrix;
  }

  public int[][][] Predcitor2(int[][][] matrix) {
    int numImages = matrix.length;
    int numRows = matrix[0].length;
    int numCols = matrix[0][0].length;

    int[][][] predictedMatrix = new int[numImages][numRows][numCols];

    // Iterate over each image
    for (int i = 0; i < numImages; i++) {
        for (int j = 0; j < numRows; j++) {
            for (int k = 0; k < numCols; k++) {
                if (j == 0 || k == 0) {
                    // For the first row and first column, keep original values
                    predictedMatrix[i][j][k] = matrix[i][j][k];
                } else {
                    // JPEG-LS predictor implementation
                    int a = matrix[i][j][k-1];    // Left pixel
                    int b = matrix[i][j-1][k];    // Upper pixel
                    int c = matrix[i][j-1][k-1];  // Diagonal pixel

                    // Prediction using local context
                    int prediction;
                    if (c >= Math.max(a, b)) {
                        prediction = Math.min(a, b);
                    } else if (c <= Math.min(a, b)) {
                        prediction = Math.max(a, b);
                    } else {
                        prediction = a + b - c;
                    }
                    
                    // Store the difference between actual value and prediction
                    predictedMatrix[i][j][k] = matrix[i][j][k] - prediction;
                }
            }
        }
    }
    return predictedMatrix;
}

public int[][][] InversePredictor2(int[][][] predictedMatrix) {
  int numImages = predictedMatrix.length;
  int numRows = predictedMatrix[0].length;
  int numCols = predictedMatrix[0][0].length;

  int[][][] originalMatrix = new int[numImages][numRows][numCols];

  // Iterate over each image
  for (int i = 0; i < numImages; i++) {
      for (int j = 0; j < numRows; j++) {
          for (int k = 0; k < numCols; k++) {
              if (j == 0 || k == 0) {
                  // Restaurar valores originales de primera fila y columna
                  originalMatrix[i][j][k] = predictedMatrix[i][j][k];
              } else {
                  // Obtener los píxeles vecinos ya reconstruidos
                  int a = originalMatrix[i][j][k-1];    // Píxel izquierdo
                  int b = originalMatrix[i][j-1][k];    // Píxel superior
                  int c = originalMatrix[i][j-1][k-1];  // Píxel diagonal

                  // Calcular la predicción usando el mismo método
                  int prediction;
                  if (c >= Math.max(a, b)) {
                      prediction = Math.min(a, b);
                  } else if (c <= Math.min(a, b)) {
                      prediction = Math.max(a, b);
                  } else {
                      prediction = a + b - c;
                  }
                  
                  // Reconstruir el valor original sumando la predicción y la diferencia almacenada
                  originalMatrix[i][j][k] = predictedMatrix[i][j][k] + prediction;
              }
          }
      }
  }
  return originalMatrix;
}
}