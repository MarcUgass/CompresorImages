import sys
import matplotlib.pyplot as plt
import argparse

def plot_psnr(data, quantization, method, output_file):
    # Prepare los datos para la gráfica
    psnr_values = []
    image_indices = list(range(1, len(data) + 1))  # Eje X: índices de las imágenes (enteros)

    # Parsear los datos (valores PSNR)
    for psnr in data:
        psnr_values.append(float(psnr))

    # Crear la figura para la gráfica
    plt.figure(figsize=(10, 6))

    # Graficar los valores PSNR como puntos dispersos (scatter plot)
    plt.scatter(image_indices, psnr_values, c='b', marker='o', label=f'PSNR (Q={quantization})')

    # Etiquetas
    plt.title(f'PSNR para diferentes imágenes ({method} con Q={quantization})')
    plt.xlabel('Índice de la Imagen')
    plt.ylabel('Valor PSNR')

    # Asegurar que los valores del eje X sean enteros
    plt.xticks(image_indices)

    # Añadir leyenda
    plt.legend()

    # Guardar la gráfica como imagen
    plt.tight_layout()
    plt.savefig(output_file)  # Guarda la imagen en el archivo especificado
    print(f"Gráfico guardado en: {output_file}")

if __name__ == "__main__":
    # Configurar argparse para gestionar los argumentos de la línea de comandos
    parser = argparse.ArgumentParser(description="Generar gráfica PSNR de métodos de compresión")
    parser.add_argument('-q', type=int, default=2, help="Paso de cuantización, por defecto 2 si no se especifica")
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
