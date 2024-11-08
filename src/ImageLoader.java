import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageLoader {
  /**
   * Loads an image from a file and stores it in a 3D matrix.
   * @param file The image file to load.
   * @param rows Number of rows in the image.
   * @param columns Number of columns in the image.
   * @param components Number of components (e.g., RGB).
   * @param bytesPerSample Size in bytes of each sample (e.g., 1 for byte, 2 for short).
   * @param isUnsigned Specifies if the values are unsigned.
   * @return A 3D matrix of the loaded image.
   * @throws IOException If an error occurs while reading the file.
   */

  private File File = null;
  private int rows = 0;
  private int columns = 0;
  private int components = 0;
  private int bytesPerSample = 0;
  private boolean isUnsigned = true;

  public ImageLoader(String file_image) {
    String[] parameters_raw = file_image.split("\\.");
    String[] parameters = parameters_raw[1].split("_");

    this.rows = Integer.parseInt(parameters[1]);
    this.columns = Integer.parseInt(parameters[2]);
    this.components = Integer.parseInt(parameters[0]);

    this.bytesPerSample = 0;
    this.isUnsigned = switch (Integer.parseInt(parameters[3])) {
      //1-> 8 bits
      case 1 -> {
        this.bytesPerSample = 1;
        yield true;
      }
      //2-> 16 bits unsigned
      case 2 -> {
        this.bytesPerSample = 2; // 2 bytes = 16 bits
        yield true;
      }
      //3-> 16 bits signed
      case 3 -> {
        this.bytesPerSample = 2;
        yield false;
      }
      default -> true;
    };

    //boolean direccion = true; //true = dividir, false = multiplicar
    String path = "./imatges/" + file_image;
    this.File = new File(path); // Crear un objecte File amb el path
  }

  public int[][][] loadImage() throws IOException { //File file, int rows, int columns, int components, int bytesPerSample, boolean isUnsigned
    int[][][] matrix = new int[components][rows][columns];

    // Open the file and read all bytes into the buffer
    try (FileInputStream fis = new FileInputStream(File)) {
      byte[] buffer = new byte[rows * columns * components * bytesPerSample];
      int bytesRead = fis.read(buffer);

      // Verify that all expected bytes have been read
      if (bytesRead != buffer.length) {
        throw new IOException("File size does not match expected size.");
      }

      ByteBuffer byteBuffer = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN);
      int index = 0;

      // Fill the matrix with values from the file
      for (int i = 0; i < components; i++) {
        for (int j = 0; j < rows; j++) {
          for (int k = 0; k < columns; k++) {
            matrix[i][j][k] = readSample(byteBuffer, index, bytesPerSample, isUnsigned);
            index += bytesPerSample;
          }
        }
      }
    }
    return matrix;
  }

  // Reads an individual sample value from the buffer based on sample size
  private static int readSample(ByteBuffer byteBuffer, int index, int bytesPerSample, boolean isUnsigned) {
    int value;
    if (bytesPerSample == 1) {
      byte b = byteBuffer.get(index);
      value = isUnsigned ? Byte.toUnsignedInt(b) : b;
    } else if (bytesPerSample == 2) {
      short s = byteBuffer.getShort(index);
      value = isUnsigned ? Short.toUnsignedInt(s) : s;
    } else {
      throw new IllegalArgumentException("Unsupported bytes per sample: " + bytesPerSample);
    }
    return value;
  }

  public File getFile() {
    return File;
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public int getComponents() {
    return components;
  }

  public int getBytesPerSample() {
    return bytesPerSample;
  }

  public boolean isUnsigned() {
    return isUnsigned;
  }

}

/*
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Supplier;

public class ImageLoader {
  /**
   * Loads an image from a file and stores it in a 3D matrix.
   * @param file The image file to load.
   * @param rows Number of rows in the image.
   * @param columns Number of columns in the image.
   * @param components Number of components (e.g., RGB).
   * @param bytesPerSample Size in bytes of each sample (e.g., 1 for byte, 2 for short).
   * @param isUnsigned Specifies if the values are unsigned.
   * @return A 3D matrix of the loaded image.
   * @throws IOException If an error occurs while reading the file.
   *
public static int[][][] loadImage(File file, int rows, int columns, int components, int bytesPerSample, boolean isUnsigned) throws IOException {
  int[][][] matrix = new int[components][rows][columns];
  byte[] buffer = new byte[rows * columns * components * bytesPerSample];

  // Read the entire file into buffer
  try (FileInputStream fis = new FileInputStream(file)) {
    int bytesRead = fis.read(buffer);
    if (bytesRead != buffer.length) {
      throw new IOException("File size does not match expected size.");
    }
  }

  ByteBuffer byteBuffer = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN);

  // Define the appropriate reader based on bytesPerSample and isUnsigned
  Supplier<Integer> sampleReader;
  if (bytesPerSample == 1) {
    sampleReader = isUnsigned ? () -> Byte.toUnsignedInt(byteBuffer.get()) : byteBuffer::get;
  } else if (bytesPerSample == 2) {
    sampleReader = isUnsigned ? () -> Short.toUnsignedInt(byteBuffer.getShort()) : byteBuffer::getShort;
  } else {
    throw new IllegalArgumentException("Unsupported bytes per sample: " + bytesPerSample);
  }

  // Fill the matrix with values from the buffer
  for (int i = 0; i < components; i++) {
    for (int j = 0; j < rows; j++) {
      for (int k = 0; k < columns; k++) {
        matrix[i][j][k] = sampleReader.get();
      }
    }
  }
  return matrix;
}
}*/