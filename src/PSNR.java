import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PSNR {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java PSNR <input1.raw> <input2.raw>");
            System.exit(1); // Código de error
        }

        String inputFile1 = args[0];
        String inputFile2 = args[1];

        try {
            byte[] image1 = loadImage(inputFile1);
            byte[] image2 = loadImage(inputFile2);

            if (image1.length != image2.length) {
                System.err.println("Error: Las imágenes deben tener el mismo tamaño.");
                System.exit(1);
            }

            double psnr = calculatePSNR(image1, image2);

            // Salida estándar con el valor del PSNR.
            System.out.println(psnr);
        } catch (IOException e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
            System.exit(1);
        }
    }

    private static byte[] loadImage(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            if (fis.read(data) != data.length) {
                throw new IOException("No se pudo leer completamente el archivo: " + filePath);
            }
        }
        return data;
    }

    private static double calculatePSNR(byte[] image1, byte[] image2) {
        long mse = 0;
        for (int i = 0; i < image1.length; i++) {
            int diff = (image1[i] & 0xFF) - (image2[i] & 0xFF);
            mse += diff * diff;
        }

        double mseValue = (double) mse / image1.length;
        if (mseValue == 0) {
            return Double.POSITIVE_INFINITY; // PSNR es infinito si las imágenes son iguales.
        }

        double maxPixelValue = 255.0;
        return 10 * Math.log10((maxPixelValue * maxPixelValue) / mseValue);
    }
}
