import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.awt.image.BufferedImage;


public class RawImage {
    public static void LoadImage() {
        String fitxer_image = "03508649.1_512_512_2_0_12_0_0_0.raw";
        int files = 512;
        int columnes = 512;
        int components = 1;
        int bytes_sample = 1;
        boolean isUnsigned = true;
        String path = "./imatges/" + fitxer_image;

        //byte[] rawData = Files.readAllBytes(Paths.get(path));
        File arxiu = new File(path);
        int[][][] matriuImg = montarMatriu(arxiu, files, columnes, components, bytes_sample, isUnsigned);
    }

    public static int[][][] montarMatriu(File arxiu, int files, int columns, int components, int bytes_sample, boolean isUnsigned) {
        int[][][] matriu = new int[components][files][columns];

        try (FileInputStream fis = new FileInputStream(arxiu)) {
            byte[] buffer = new byte[files * columns * components * bytes_sample];
            int byteRead = fis.read(buffer);

            if (byteRead != buffer.length) {
                throw new IOException("Different file size");
            }

            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matriu;
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