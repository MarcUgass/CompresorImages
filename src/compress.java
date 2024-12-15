import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class compress {
    public static void main(String args[]) throws IOException {
        if (args.length < 3) {
            System.out.println("Uso: compress -i input.raw Ancho Alto Imagenes Formato -o output.raw [-q factor] [[-wt nivel] / [-p]] ");
            return;
        }

        String inputFile = null;
        String outputFile = null;

        int width = 0, height = 0, components = 0, format = 0;

        int bytesPerSample = 0;
        boolean isUnsigned = true;

        int quantizationFactor = 0;
        int waveletLevel = 0;
        boolean usePredictor = false;
        File file = null;
        //boolean useZip = false;

        // Procesar argumentos
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    inputFile = args[++i];
                    width = Integer.parseInt(args[++i]);
                    height = Integer.parseInt(args[++i]);
                    components = Integer.parseInt(args[++i]);
                    format = Integer.parseInt(args[++i]);
                    break;
                case "-o":
                    outputFile = args[++i];
                    break;
                case "-q":
                    quantizationFactor = Integer.parseInt(args[++i]);
                    break;
                case "-wt":
                    waveletLevel = Integer.parseInt(args[++i]);
                    break;
                case "-p":
                    usePredictor = true;
                    break;
            }
        }
        
        // Cargar imagen
        ImageLoader loader = new ImageLoader();
        String path = "../imatges/" + inputFile;
        file = new File(path); 

        //1-> 8 bits
        if (format == 1) {
            bytesPerSample = 1;
            isUnsigned = true;
        }
        //2-> 16 bits unsigned
        else if (format == 2) {
            bytesPerSample = 2;
            isUnsigned = true;
        }
        //3-> 16 bits signed
        else if (format == 3) {
            bytesPerSample = 2;
            isUnsigned = false;
        }
        else {
            isUnsigned = true;
        }
        loader.constructor(file, width, height, components, bytesPerSample, isUnsigned);
        int[][][] matrix = loader.loadImage();

        //Si hay quantización se aplica
        if (quantizationFactor > 0) {
            matrix = Quantizer.quantize(matrix, quantizationFactor, true);
        }

        // Aplicar transformaciones en orden
        if (usePredictor) {
            Predictor predictor = new Predictor();
            matrix = predictor.Predcitor2(matrix);
        }else if (waveletLevel > 0) {
            WaveletTransform wavelet = new WaveletTransform();
            matrix = wavelet.RHAAR_FWD_LVL(matrix, waveletLevel);
        }

        String file_name = "../imatges/" + outputFile;

        // Guardar resultado
        ImageSaver.saveImage(matrix, loader.getBytesPerSample(), loader.isUnsigned(), outputFile);

        Zipper.zipFile(outputFile, outputFile + ".zip");
        
    }
}
