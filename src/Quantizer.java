public class Quantizer {
  /**
   * Performs quantization on the image matrix.
   * @param matrix The original image matrix.
   * @param qStep The quantization step size.
   * @param divide If true, divides by qStep; if false, multiplies by qStep.
   * @return The quantized matrix.
   */

  public static int[][][] quantize(int[][][] matriu, int qstep, boolean direccion) { //1 dividim, 0 multiplicar
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
}
