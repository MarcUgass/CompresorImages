import sys
import matplotlib.pyplot as plt
import argparse

def plot_psnr(data, quantization, method, output_file):
    # Prepare los datos para la gráfica
    methods = []
    psnr_values = []
    quantization_steps = []

    # Parsear los datos
    for psnr in data:
        methods.append(method)
        quantization_steps.append(str(quantization))  # convertir a string para el color
        psnr_values.append(float(psnr))

    # Crear la figura para la gráfica
    plt.figure(figsize=(10, 6))

    # Crear gráficos por cada valor de PSNR basado en los métodos y pasos de cuantización
    scatter = plt.scatter(methods, psnr_values, c=quantization_steps, cmap='viridis', label='PSNR values')

    # Etiquetas
    plt.title('PSNR para diferentes métodos de compresión')
    plt.xlabel('Métodos')
    plt.ylabel('Valor PSNR')
    
    # Añadir una barra de colores
    plt.colorbar(scatter, label='Paso de cuantización')

    # Guardar la gráfica como imagen
    plt.tight_layout()
    plt.savefig(output_file)  # Guarda la imagen en el archivo especificado
    print(f"Gráfico guardado en: {output_file}")

if __name__ == "__main__":
    # Configurar argparse para gestionar los argumentos de la línea de comandos
    parser = argparse.ArgumentParser(description="Generar gráfica PSNR de métodos de compresión")
    parser.add_argument('-q', type=int, required=True, help="Paso de cuantización")
    parser.add_argument('-wt', action='store_true', help="Usar wavelet (si no, se usará predictor)")
    parser.add_argument('-p', action='store_true', help="Usar predictor")
    parser.add_argument('psnr_values', nargs='+', help="Lista de valores PSNR")
    parser.add_argument('output_file', help="Archivo de salida para guardar la gráfica")

    args = parser.parse_args()

    # Determinar el método basado en los flags -wt y -p
    method = 'wavelet' if args.wt else 'predictor' if args.p else 'desconocido'

    # Comprobar que haya un método seleccionado
    if method == 'desconocido':
        print("Error: Debe especificar un método usando -wt para wavelet o -p para predictor.")
        sys.exit(1)

    # Llamar a la función para generar la gráfica
    plot_psnr(args.psnr_values, args.q, method, args.output_file)
