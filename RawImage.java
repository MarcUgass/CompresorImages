import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class RawImage {

    public RawImage(){}

    public static int[][][] LoadImage(File file, int rows, int columns, int components, int bytesPerSample, boolean isUnsigned) {
        int[][][] matrix = new int[components][rows][columns]; // Create a 3-dimensional matrix

        try (FileInputStream fis = new FileInputStream(file)) { // Create a FileInputStream object with the file
            byte[] buffer = new byte[rows * columns * components * bytesPerSample]; // Create a byte buffer
            int byteRead = fis.read(buffer); // Read bytes from the file and store them in the buffer

            if (byteRead != buffer.length) { // Check that all bytes were read
                throw new IOException("Different file size"); // Throw an exception if not all bytes were read
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer); // Create a ByteBuffer object with the buffer
            byteBuffer.order(ByteOrder.BIG_ENDIAN); // Set the byte order of ByteBuffer, always should be this order

            int index = 0;
            int value = 0;
            for (int i = 0; i < components; i++) {
                for (int j = 0; j < rows; j++) {
                    for (int k = 0; k < columns; k++) {
                        if (bytesPerSample == 1) {
                            // If it's one byte, use the byte format as it takes less space than int
                            byte b = byteBuffer.get(index);
                            if (isUnsigned) {
                                value = Byte.toUnsignedInt(b); // If unsigned, convert to range [0,255]
                            } else {
                                value = b; // If signed, Java interprets it correctly in [-128, 127]
                            }
                        } else if (bytesPerSample == 2) {
                            // If two bytes, it's a short
                            short s = byteBuffer.getShort(index);
                            if (isUnsigned) {
                                value = Short.toUnsignedInt(s); // If unsigned, convert to range [0,65535]
                            } else {
                                value = s; // If signed, it's already in the correct range [-32768, 32767]
                            }
                        }
                        matrix[i][j][k] = value;
                        index += bytesPerSample;
                    }
                }
            }
        } catch (IOException e) { // Catch an input/output exception
            throw new RuntimeException(e); // Throw a new exception
        }
        return matrix;
    }

    public static float calcularEntropia(int[][][] matriu_imatge) {
        float entropia = 0;
        int value = 0;
        //Crear un HashMap per guardar la freqüència de cada valor
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        int totalPixels = 0;
        for (int i = 0; i < matriu_imatge.length; i++) {
            for (int j = 0; j < matriu_imatge[i].length; j++) {
                for (int k = 0; k < matriu_imatge[i][j].length; k++) {
                    value = matriu_imatge[i][j][k];
                    //Si el valor no existeix crea una posicio amb
                    frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1); //crear un histograma automàticament
                    totalPixels++;
                }
            }
        }

        //Calcular la entropia
        for (int key : frequencyMap.keySet()) {
            float probability = (float) frequencyMap.get(key) / totalPixels;
            entropia += (float) (probability * (Math.log(probability)) / Math.log(2));;
        }

        entropia = entropia * -1;
        return entropia;
    }

    public static boolean SaveFile(int[][][] matriu, int bytes_per_sample, boolean signe) throws IOException {
        String path = "./imatges/output.raw";
        File file = new File(path);
        ByteBuffer byteBuffer = ByteBuffer.allocate(matriu.length * matriu[0].length * matriu[0][0].length * bytes_per_sample);
        byteBuffer.order(ByteOrder.BIG_ENDIAN);

        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu[i].length; j++) {
                for (int k = 0; k < matriu[i][j].length; k++) {
                    if (bytes_per_sample == 1) {
                            byteBuffer.put((byte) matriu[i][j][k]);
                    } else if (bytes_per_sample == 2) {
                            byteBuffer.putShort((short) matriu[i][j][k]);
                    }
                }
            }
        }

        byte[] buffer = byteBuffer.array();
        try {
            java.nio.file.Files.write(file.toPath(), buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

      return file.exists();
    }

    public static int[][][] Cuantizacion(int[][][] matriu, int qstep, boolean direccion) { //1 dividim, 0 multiplicar
        int[][][] matriz_cuantizada = new int[matriu.length][matriu[0].length][matriu[0][0].length];
        int signo =  0;
        if (direccion){
            for (int i = 0; i < matriu.length; i++) {
                for (int j = 0; j < matriu[i].length; j++) {
                    for (int k = 0; k < matriu[i][j].length; k++){
                        signo = matriu[i][j][k] < 0 ? -1 : 1;
                        matriz_cuantizada[i][j][k] = (int) (signo * Math.abs(matriu[i][j][k]) / qstep);
                    }
                }
            }
        } else {
            for (int i = 0; i < matriu.length; i++) {
                for (int j = 0; j < matriu[i].length; j++) {
                    for (int k = 0; k < matriu[i][j].length; k++) {
                        signo = matriu[i][j][k] < 0 ? -1 : 1;
                        matriz_cuantizada[i][j][k] = (int) (signo * Math.abs(matriu[i][j][k]) * qstep);
                    }
                }
            }
        }
        return matriz_cuantizada;
    }

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



}



