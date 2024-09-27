import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.nio.ByteOrder;
import java.nio.file.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RawImage {
    public static void LoadImage() {
        List<String> images = new ArrayList<>();

        images.add("03508649.1_512_512_2_0_12_0_0_0.raw");
        images.add("03508649.64_512_512_2_0_12_0_0_0");
        images.add("aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0");
        images.add("Landsat_agriculture.6_1024_1024_2_0_16_0_0_0");
        images.add("mamo_1.1_3576_2944_2_0_12_0_0_0");
        images.add("n1_GRAY.1_2560_2048_1_0_8_0_0_0");
        images.add("n1_RGB.3_2560_2048_1_0_8_0_0_0");

        String file_image = images.getFirst();


        String[] parameters_raw = file_image.split("\\.");
        String[] parameters = parameters_raw[1].split("_");

        int files = Integer.parseInt(parameters[1]);
        int columnes = Integer.parseInt(parameters[2]);
        int components = Integer.parseInt(parameters[0]);

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


        String path = "./imatges/" + file_image;
        File file = new File(path); // Crear un objecte File amb el path
        int[][][] matrixImg = generateMatrix(file, files, columnes, components, bytes_sample, isUnsigned);
    }

    //Format int es invalid ja que estem utilitzant valors d'entre 1-2 bytes, perdent molta cpacitat, --> preguntat Joan
    public static int[][][] generateMatrix(File arxiu, int files, int columns, int components, int bytes_sample, boolean isUnsigned) {
        int[][][] matriu = new int[components][files][columns]; // Crear una matriu de 3 dimensions

        try (FileInputStream fis = new FileInputStream(arxiu)) { // Crear un objecte FileInputStream amb l'arxiu
            byte[] buffer = new byte[files * columns * components * bytes_sample]; // Crear un buffer de bytes
            int byteRead = fis.read(buffer); // Llegir els bytes de l'arxiu i guardar-los al buffer

            if (byteRead != buffer.length) { // Comprovar que s'han llegit tots els bytes
                throw new IOException("Different file size"); // Llençar una excepció si no s'han llegit tots els bytes
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer); // Crear un objecte ByteBuffer amb el buffer
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN); // Establir el byte order del ByteBuffer, sempre ha de ser aquest ordre

            int index = 0;
            for (int i = 0; i < components; i++) {
                for (int j = 0; j < files; j++) {
                    for (int k = 0; k < columns; k++) {
                        int value;
                        if (bytes_sample == 1) {
                            //Si es un byte s'utilitza el format byte, ja que ocupa menys que int
                            byte b = byteBuffer.get(index);
                            if (isUnsigned) {
                                value = Byte.toUnsignedInt(b); //Si es unsigned, convertim al rang [0,255]
                            } else {
                                value = b; // Si es signed, Java ho interpreta correctament en [-128, 127] -->perque?? pregunta joan
                            }
                        } else if (bytes_sample == 2) {
                            //Si son 2 bytes es un short
                            short s = byteBuffer.getShort(index);
                            if (isUnsigned) {
                                value = Short.toUnsignedInt(s); //Si es unsigned, convertim al rang [0,65535]
                            } else {
                                value = s; //Si es signed, ja està al rang correcte [-32768, 32767]
                            }

                            matriu[i][j][k] = value;
                            index += bytes_sample;
                        }
                    }
                }
            }

        } catch (IOException e) { // Capturar una excepció d'entrada/sortida
            throw new RuntimeException(e); // Llençar una nova excepció
        }
        return matriu;
    }


    public static double calcularEntropia(int[][][] matriu_imatge){

    }

}






/*'''int img [][][] = LoadImatge(fitxer_imatge, files, columnes, components, bytes_sample, signed/unsigned)
    --> test print 10 primers i últims samples --> comparar FIJI

    float Entropy(Image)

    raw image--> |------------------
    parametres imatge
    operacio


    nom_app imatge.raw files columnes components bytes_sample unsigned 1
    2 3
    3

    '''
    print("Compressor d'imatges")

*/