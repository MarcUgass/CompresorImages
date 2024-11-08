public class Compressor {
  /**
   * Applies the predictor to each row in each image and returns a new matrix with the computed values.
   * @param matrix The original 3D matrix representing the pixel values.
   * @return A new 3D matrix where each row has been replaced by the predictor values.
   */
  public int[][][] applyPredictorToMatrix(int[][][] matrix) {
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
}
