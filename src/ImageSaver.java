import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;

public class ImageSaver {
  /**
   * Saves a 3D matrix of an image to a file.
   * @param matrix The image matrix to save.
   * @param bytesPerSample Size in bytes of each sample.
   * @param isSigned Indicates if the values are signed.
   * @param path Output file path.
   * @return True if the file was saved successfully.
   * @throws IOException If an error occurs while writing the file.
   */
  public static boolean saveImage(int[][][] matrix, int bytesPerSample, boolean isSigned, String path) throws IOException {
    ByteBuffer byteBuffer = ByteBuffer.allocate(matrix.length * matrix[0].length * matrix[0][0].length * bytesPerSample);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);

    // Iterate over the matrix and store values in the buffer
    for (int[][] component : matrix) {
      for (int[] row : component) {
        for (int value : row) {
          if (bytesPerSample == 1) {
            byteBuffer.put((byte) value);
          } else if (bytesPerSample == 2) {
            byteBuffer.putShort((short) value);
          }
        }
      }
    }

    // Write the buffer to the specified file
    byte[] buffer = byteBuffer.array();
    Files.write(new File(path).toPath(), buffer);

    return new File(path).exists();
  }

  public static ByteBuffer getImageBuffer(int[][][] matrix, int bytesPerSample){
    ByteBuffer byteBuffer = ByteBuffer.allocate(matrix.length * matrix[0].length * matrix[0][0].length * bytesPerSample);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);

    // Iterate over the matrix and store values in the buffer
    for (int[][] component : matrix) {
      for (int[] row : component) {
        for (int value : row) {
          if (bytesPerSample == 1) {
            byteBuffer.put((byte) value);
          } else if (bytesPerSample == 2) {
            byteBuffer.putShort((short) value);
          }
        }
      }
    }

    return byteBuffer;    
  }

}
