import java.io.File;
import java.io.IOException;

public class decompress {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: decompress -i input.zip -o output.raw [-q factor] [[-wt nivel] / [-p]]");
            return;
        }

        String inputFile = null;
        String outputFile = null;

        int quantizationFactor = 0;
        int waveletLevel = 0;
        boolean usePredictor = false;

        // Procesar argumentos
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    inputFile = args[++i];
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

        // Ruta de trabajo
        String zipFilePath = "../imatges/" + inputFile;
        String tempRawFilePath = zipFilePath.replace(".zip", "_temp.raw");

        // Descomprimir el archivo ZIP
        Zipper.unzipFile(zipFilePath, "../imatges");

        // Cargar imagen
        ImageLoader loader = new ImageLoader();
        File rawFile = new File(tempRawFilePath);
        if (!rawFile.exists()) {
            System.err.println("Error: No se pudo descomprimir el archivo RAW del ZIP.");
            return;
        }

        // Leer metadatos (ajustar parámetros según sea necesario)
        int width = 512; // TODO: Obtener desde los parámetros si es posible
        int height = 512; // TODO: Obtener desde los parámetros si es posible
        int components = 3; // TODO: Obtener desde los parámetros si es posible
        int bytesPerSample = 1; // TODO: Ajustar según formato del archivo
        boolean isUnsigned = true; // TODO: Ajustar según formato del archivo

        loader.constructor(rawFile, width, height, components, bytesPerSample, isUnsigned);
        int[][][] matrix = loader.loadImage();

        // Deshacer predicción o transformada wavelet
        if (usePredictor) {
            Predictor predictor = new Predictor();
            matrix = predictor.inversePredictor(matrix);
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
