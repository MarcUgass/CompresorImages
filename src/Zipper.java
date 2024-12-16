import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.BufferedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;

public class Zipper {
  public static void zipFile(String sourceFile, String outputFile) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(outputFile);
         ZipOutputStream zipOut = new ZipOutputStream(fos);
         FileInputStream fis = new FileInputStream(sourceFile)) {

      //ZipEntry zipEntry = new ZipEntry(sourceFile);
      ZipEntry zipEntry = new ZipEntry(new File(sourceFile).getName());
      zipOut.putNextEntry(zipEntry);

      byte[] buffer = new byte[1024];
      int length;
      while ((length = fis.read(buffer)) >= 0) {
        zipOut.write(buffer, 0, length);
      }

      System.out.println("File compressed successfully to: " + outputFile);
    }
  }

  public static void unzipFile(String zipFile, String outputDir) throws IOException {
    try (FileInputStream fis = new FileInputStream(zipFile);
         ZipInputStream zipIn = new ZipInputStream(fis)) {

      ZipEntry zipEntry;
      while ((zipEntry = zipIn.getNextEntry()) != null) {
        String fileName = zipEntry.getName();
        File outputFile = new File(outputDir, fileName);

        // Create parent directories if they don't exist
        outputFile.getParentFile().mkdirs();

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
          byte[] buffer = new byte[1024];
          int length;
          while ((length = zipIn.read(buffer)) >= 0) {
            bos.write(buffer, 0, length);
          }
        }
        zipIn.closeEntry();
      }

      System.out.println("File unzipped successfully to: " + outputDir);
    }
  }

  public static void zipBuffer(byte[] buffer, String outputFile, String outputImage) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(outputFile);
      ZipOutputStream zipOut = new ZipOutputStream(fos)){

        ZipEntry zipEntry = new ZipEntry(outputImage);
        zipOut.putNextEntry(zipEntry);
        
        zipOut.write(buffer);

        System.out.println("File compressed successfully to: " + outputFile);
      }

  }
}