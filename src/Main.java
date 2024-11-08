import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class Main {
  private static final Scanner scanner = new Scanner(System.in); // Initialize the scanner
  private static final String[] IMAGE_FILES = {
      "03508649.1_512_512_2_0_12_0_0_0.raw",
      "03508649.64_512_512_2_0_12_0_0_0.raw",
      "aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw",
      "Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw",
      "mamo_1.1_3576_2944_2_0_12_0_0_0.raw",
      "n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw", // Entropia = 7,42 bps
      "n1_RGB.3_2560_2048_1_0_8_0_0_0.raw"
  };

  public static void main(String[] args) throws IOException {
      // Prompt for file path
      System.out.print("Choose the image file: ");
      for (int i = 0; i < IMAGE_FILES.length; i++) {
        System.out.println((i + 1) + "- " + IMAGE_FILES[i]);
      }

      String numFile = scanner.nextLine();
      ImageLoader Original_Image = new ImageLoader(IMAGE_FILES[Integer.parseInt(numFile) - 1]);
      // Load image
      int[][][] Original_Matrix = Original_Image.loadImage();
      System.out.println("Image loaded successfully!");

      float Original_Entropy = MetricsCalculator.calculateEntropy(Original_Matrix);
      System.out.println(Original_Entropy);

      int[][][] Actual_Matrix = Original_Matrix;

      // Display of the Menu
      // Display the options menu
      while (true) {
        System.out.println("\nSelect an option:");
        System.out.println("1. Calculate Entropy");
        System.out.println("2. Quantize/Dequantize");
        System.out.println("3. Apply Predictor");
        System.out.println("4. Apply Wavelet Transform");
        System.out.println("5. Show metrics");
        System.out.println("6. Save Image");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");

        String choice = scanner.nextLine();

        switch (choice) {
          case "1":
            // Calculate entropy
            float entropy = MetricsCalculator.calculateEntropy(Actual_Matrix);
            System.out.println("Original Image entropy: " +  Original_Entropy);
            System.out.println("New entropy: " + entropy);
            break;

          case "2":
            // Quantize the image
            System.out.print("Enter quantization step: ");
            int qStep = scanner.nextInt();
            System.out.print("Quantitzation/Desquantitzation (true/false): ");
            boolean divide = scanner.nextBoolean();
            Actual_Matrix = Quantizer.quantize(Actual_Matrix, qStep, divide);
            System.out.println("Image quantized successfully!");
            break;

          case "3":
            // Apply predictor to the image
            Compressor compressor = new Compressor();
            Actual_Matrix = compressor.applyPredictorToMatrix(Actual_Matrix);
            System.out.println("Predictor applied successfully!");
            break;
          case "4":
            //WaveletTransform waveletTransform = new WaveletTransform();
            break;
          case "5":
            MetricsCalculator metrics = new MetricsCalculator();

            double mse = metrics.calculateMSE(Original_Matrix, Actual_Matrix);
            System.out.println("MSE: " + mse);
            double pae = metrics.calculatePAE(Original_Matrix, Actual_Matrix);
            System.out.println("PAE: " + pae);
            double psnr = metrics.calculatePSNR(Original_Matrix, Actual_Matrix);
            System.out.println("PSNR: " + psnr);
            break;
          case "6":
            System.out.println("Enter the name of the file to save the image: ");
            String file_name = scanner.nextLine();
            file_name = "./imatges/" + file_name + ".raw";
            ImageSaver saver = new ImageSaver();
            boolean saved = saver.saveImage(Actual_Matrix, Original_Image.getBytesPerSample(), Original_Image.isUnsigned(), file_name);
          case "7":
            // Exit the program
            System.out.println("Exiting...");
            scanner.close();
            return;

          default:
            System.out.println("Invalid choice, please try again.");

        }


/*
      // Prompt for quantization parameters
      System.out.print("Enter the quantization step size (e.g., 5): ");
      int qStep = scanner.nextInt();
      System.out.print("Quantization direction (true for division, false for multiplication): ");
      boolean divide = scanner.nextBoolean();

      // Perform quantization
      int[][][] quantizedMatrix = Quantizer.quantize(imageMatrix, qStep, divide);
      System.out.println("Quantization completed!");

      // Calculate MSE, PSNR, and PAE metrics
      double mse = MetricsCalculator.calculateMSE(imageMatrix, quantizedMatrix);
      double psnr = MetricsCalculator.calculatePSNR(imageMatrix, quantizedMatrix);
      double pae = MetricsCalculator.calculatePAE(imageMatrix, quantizedMatrix);

      System.out.println("MSE: " + mse);
      System.out.println("PSNR: " + psnr + " dB");
      System.out.println("PAE: " + pae);

      // Prompt for output file path
      System.out.print("Enter the path to save the quantized image file: ");
      scanner.nextLine(); // Consume newline left from previous input
      String outputPath = scanner.nextLine();

      // Save quantized image
      boolean saved = ImageSaver.saveImage(quantizedMatrix, bytesPerSample, isUnsigned, outputPath);
      System.out.println("Image saved successfully: " + saved);
      */
         // Close the scanner to free resources
      }

    }
  }
