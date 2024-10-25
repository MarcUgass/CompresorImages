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
    public static void LoadImage() throws IOException {
        List<String> images = new ArrayList<>();

        images.add("03508649.1_512_512_2_0_12_0_0_0.raw");
        images.add("03508649.64_512_512_2_0_12_0_0_0.raw");
        images.add("aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw");
        images.add("Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw");
        images.add("mamo_1.1_3576_2944_2_0_12_0_0_0.raw");
        images.add("n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw"); //Entropia = 7,42 bps
        images.add("n1_RGB.3_2560_2048_1_0_8_0_0_0.raw");

        String file_image = images.get(5);

        String[] parameters_raw = file_image.split("\\.");
        String[] parameters = parameters_raw[1].split("_");

        int files = Integer.parseInt(parameters[1]);
        int columnes = Integer.parseInt(parameters[2]);
        int components = Integer.parseInt(parameters[0]);
        int qstep = 15;

        int bytes_sample = 0;
        boolean isUnsigned = switch (Integer.parseInt(parameters[3])) {
            //1-> 8 bits
            case 1 -> {
                bytes_sample = 1;
                yield true;
            }
            //2-> 16 bits unsigned
            case 2 -> {
                bytes_sample = 2; // 2 bytes = 16 bits
                yield true;
            }
            //3-> 16 bits signed
            case 3 -> {
                bytes_sample = 2;
                yield false;
            }
            default -> true;
        };

        //boolean direccion = true; //true = dividir, false = multiplicar
        String path = "./imatges/" + file_image;
        File file = new File(path); // Crear un objecte File amb el path
        int[][][] matrixImg = generateMatrix(file, files, columnes, components, bytes_sample, isUnsigned);
        float entropia = calcularEntropia(matrixImg);
        System.out.println("Entropy 1: " + entropia);

        int[][][] matrixCuantizada = Cuantizacion(matrixImg, qstep, true);
        boolean guardado = SaveFile(matrixCuantizada, bytes_sample, isUnsigned);
        float entropia2 = calcularEntropia(matrixCuantizada);
        System.out.println("Entropy 2: " + entropia2);

        int[][][] matrixDescuantizada = Cuantizacion(matrixCuantizada, qstep, false);

        double mse = metricas(matrixImg, matrixDescuantizada, "MSE");
        System.out.println("MSE: " + mse);
        double pae = metricas(matrixImg, matrixDescuantizada, "PAE");
        System.out.println("PAE: " + pae);
        double psnr = PSNR(matrixImg, matrixDescuantizada);
        System.out.println("PSNR: " + psnr);


    }

    public static int[][][] generateMatrix(File arxiu, int files, int columns, int components, int bytes_sample, boolean isUnsigned) {
        int[][][] matriu = new int[components][files][columns]; // Crear una matriu de 3 dimensions

        try (FileInputStream fis = new FileInputStream(arxiu)) { // Crear un objecte FileInputStream amb l'arxiu
            byte[] buffer = new byte[files * columns * components * bytes_sample]; // Crear un buffer de bytes
            int byteRead = fis.read(buffer); // Llegir els bytes de l'arxiu i guardar-los al buffer

            if (byteRead != buffer.length) { // Comprovar que s'han llegit tots els bytes
                throw new IOException("Different file size"); // Llençar una excepció si no s'han llegit tots els bytes
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer); // Crear un objecte ByteBuffer amb el buffer
            byteBuffer.order(ByteOrder.BIG_ENDIAN); // Establir el byte order del ByteBuffer, sempre ha de ser aquest ordre

            int index = 0;
            int value = 0;
            for (int i = 0; i < components; i++) {
                for (int j = 0; j < files; j++) {
                    for (int k = 0; k < columns; k++) {
                        if (bytes_sample == 1) {
                            //Si es un byte s'utilitza el format byte, ja que ocupa menys que int
                            byte b = byteBuffer.get(index);
                            if (isUnsigned) {
                                value = Byte.toUnsignedInt(b); //Si es unsigned, convertim al rang [0,255]
                            } else {
                                value = b; // Si es signed, Java ho interpreta correctament en [-128, 127]
                            }
                        } else if (bytes_sample == 2) {
                            //Si son 2 bytes es un short
                            short s = byteBuffer.getShort(index);
                            if (isUnsigned) {
                                value = Short.toUnsignedInt(s); //Si es unsigned, convertim al rang [0,65535]
                            } else {
                                value = s; //Si es signed, ja està al rang correcte [-32768, 32767]
                            }
                        }
                        matriu[i][j][k] = value;
                        index += bytes_sample;
                    }
                }
            }
        } catch (IOException e) { // Capturar una excepció d'entrada/sortida
            throw new RuntimeException(e); // Llençar una nova excepció
        }
        return matriu;
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



