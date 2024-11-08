import java.util.HashMap;

public class MetricsCalculator {
  /**
   * Calculates the entropy of an image, which measures the randomness or information content.
   * @param matrix The image matrix in 3D array form (e.g., [components][rows][columns]).
   * @return The entropy value as a float, representing the average bits per pixel.
   */
  public static float calculateEntropy(int[][][] matrix) {
    HashMap<Integer, Integer> frequencyMap = new HashMap<>();
    int totalPixels = 0;

    // Populate the frequency map with occurrences of each pixel value
    for (int[][] component : matrix) {
      for (int[] row : component) {
        for (int value : row) {
          frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
          totalPixels++;
        }
      }
    }

    // Compute entropy using the probability of each unique value
    float entropy = 0;
    for (int frequency : frequencyMap.values()) {
      float probability = (float) frequency / totalPixels;
      entropy += (float) (probability * (Math.log(probability) / Math.log(2))); // entropy formula
    }
    return -entropy; // Entropy is negative, so we negate the sum to return a positive result
  }

  /**
   * Calculates the Mean Squared Error (MSE) between two image matrices, which represents
   * the average squared difference between corresponding pixels in two images.
   * @param original The original image matrix.
   * @param quantized The quantized (compressed or modified) image matrix.
   * @return The MSE value as a double.
   */
  public static double calculateMSE(int[][][] original, int[][][] quantized) {
    double mse = 0;
    int totalElements = original.length * original[0].length * original[0][0].length;

    // Sum the squared differences between corresponding pixels in both matrices
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[i].length; j++) {
        for (int k = 0; k < original[i][j].length; k++) {
          mse += Math.pow(original[i][j][k] - quantized[i][j][k], 2);
        }
      }
    }
    return mse / totalElements; // Average over total number of elements for final MSE
  }

  /**
   * Calculates the Peak Signal-to-Noise Ratio (PSNR), which measures image quality by comparing
   * the original and quantized image matrices.
   * @param original The original image matrix.
   * @param quantized The quantized (compressed or modified) image matrix.
   * @return The PSNR value in decibels (dB). A higher PSNR indicates better image quality.
   */
  public static double calculatePSNR(int[][][] original, int[][][] quantized) {
    double mse = calculateMSE(original, quantized); // Use MSE as a basis for PSNR
    if (mse == 0) return Double.POSITIVE_INFINITY; // PSNR is infinite if there is no error (perfect match)

    double maxPixelValue = 255.0; // Max pixel value for 8-bit images
    return 10 * Math.log10(Math.pow(maxPixelValue, 2) / mse); // PSNR formula in dB
  }

  /**
   * Calculates the Peak Absolute Error (PAE) between two image matrices, which is the largest
   * absolute difference between corresponding pixels.
   * @param original The original image matrix.
   * @param quantized The quantized (compressed or modified) image matrix.
   * @return The maximum absolute error (PAE) as a double.
   */
  public static double calculatePAE(int[][][] original, int[][][] quantized) {
    double maxError = 0;

    // Find the maximum absolute difference between corresponding pixels in both matrices
    for (int i = 0; i < original.length; i++) {
      for (int j = 0; j < original[i].length; j++) {
        for (int k = 0; k < original[i][j].length; k++) {
          double diff = Math.abs(original[i][j][k] - quantized[i][j][k]);
          if (diff > maxError) {
            maxError = diff; // Update maxError if current difference is greater
          }
        }
      }
    }
    return maxError; // Return the peak absolute error
  }


/*
  public static double metricas(int[][][] mat_org, int[][][] mat_cuant, String metricas) {
    double valor = 0.0;
    int totalElements = mat_org.length * mat_org[0].length * mat_org[0][0].length;

    if (mat_org.length == mat_cuant.length) {
      if ("MSE".equals(metricas)) {
        for (int i = 0; i < mat_org.length; i++) {
          for (int j = 0; j < mat_org[i].length; j++) {
            for (int k = 0; k < mat_org[i][j].length; k++) {
              valor += Math.pow((mat_org[i][j][k] - mat_cuant[i][j][k]), 2);
            }
          }
        }
        valor /= totalElements; // Dividir pel nombre total de píxels per obtenir el valor de MSE
      } else if ("PAE".equals(metricas)) {
        double max = 0;
        for (int i = 0; i < mat_org.length; i++) {
          for (int j = 0; j < mat_org[i].length; j++) {
            for (int k = 0; k < mat_org[i][j].length; k++) {
              double diff = Math.abs(mat_org[i][j][k] - mat_cuant[i][j][k]);
              if (diff > max) {
                max = diff;
              }
            }
          }
        }
        valor = max;
      }
    }
    return valor;
  }

  public static double PSNR(int[][][] original, int[][][] comprimit) {
    double mse = metricas(original, comprimit, "MSE");
    if (mse == 0) return Double.POSITIVE_INFINITY; // PSNR és infinit si el MSE és zero
    double max = 255.0;
    return 10 * Math.log10(Math.pow(max, 2) / mse);
  }
  */
}
