import java.io.File;
import java.io.IOException;

public class decompress {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: decompress -i input.zip Ancho Alto Imagenes Formato -o output.raw [-q factor] [[-wt nivel] / [-p]]");
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

        if (inputFile == null || outputFile == null) {
            System.err.println("Error: Debes especificar un archivo de entrada y salida.");
            return;
        }

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

        // Ruta de trabajo
        String zipFilePath = "../imatges/" + inputFile;
        String tempRawFilePath = zipFilePath.replace(".zip", ".raw"); //"_temp.raw");

        // Descomprimir el archivo ZIP
        Zipper.unzipFile(zipFilePath, "../imatges");

        // Cargar imagen
        ImageLoader loader = new ImageLoader();
        File rawFile = new File(tempRawFilePath);
        
        if (!rawFile.exists()) {
            System.err.println("Error: No se pudo descomprimir el archivo RAW del ZIP.");
            return;
        }
        

        loader.constructor(rawFile, width, height, components, bytesPerSample, isUnsigned);
        int[][][] matrix = loader.loadImage();

        // Deshacer predicción o transformada wavelet
        if (usePredictor) {
            Predictor predictor = new Predictor();
            matrix = predictor.InversePredictor2(matrix);
        } else if (waveletLevel > 0) {
            WaveletTransform wavelet = new WaveletTransform();
            matrix = wavelet.RHAAR_INV_LVL(matrix, waveletLevel);
        }

        // Deshacer cuantización
        if (quantizationFactor > 0) {
            matrix = Quantizer.quantize(matrix, quantizationFactor, false);
        }

        // Guardar la imagen descomprimida
        ImageSaver.saveImage(matrix, loader.getBytesPerSample(), loader.isUnsigned(), "../imatges/" + outputFile);

        // Eliminar archivo temporal
        if (rawFile.exists() && rawFile.delete()) {
            System.out.println("Archivo temporal eliminado: " + tempRawFilePath);
        } else {
            System.err.println("No se pudo eliminar el archivo temporal: " + tempRawFilePath);
        }

        System.out.println("Proceso de descompresión finalizado con éxito.");
    }
}
