import sys
import matplotlib.pyplot as plt
import argparse

def plot_psnr_means(data, quantization_steps, output_file):
    # Parsear las entradas de PSNR y pasos de cuantización
    psnr_means = [float(value) for value in data]
    quantization_values = [float(step) for step in quantization_steps]

    if len(psnr_means) != len(quantization_values):
        print("Error: El número de valores PSNR y pasos de cuantización debe ser el mismo.")
        sys.exit(1)

    # Crear la figura para la gráfica
    plt.figure(figsize=(10, 6))

    # Graficar los valores PSNR en función de los pasos de cuantización
    plt.plot(quantization_values, psnr_means, marker='o', linestyle='-', color='g', label='Media PSNR')

    # Etiquetas
    plt.title('Media de PSNR por Paso de Cuantización')
    plt.xlabel('Paso de Cuantización')
    plt.ylabel('Valor Medio de PSNR')

    # Añadir leyenda
    plt.legend()

    # Guardar la gráfica como imagen
    plt.tight_layout()
    plt.savefig(output_file)  # Guarda la imagen en el archivo especificado
    print(f"Gráfico guardado en: {output_file}")

if __name__ == "__main__":
    # Configurar argparse para gestionar los argumentos de la línea de comandos
    parser = argparse.ArgumentParser(description="Generar gráfica de medias de PSNR por pasos de cuantización")
    parser.add_argument('psnr_means', nargs='+', help="Lista de valores medios de PSNR")
    parser.add_argument('output_file', help="Archivo de salida para guardar la gráfica")
    parser.add_argument('--quantization_steps', nargs='+', required=True, help="Lista de pasos de cuantización en orden correspondiente a los valores de PSNR")

    args = parser.parse_args()

    # Llamar a la función para generar la gráfica
    plot_psnr_means(args.psnr_means, args.quantization_steps, args.output_file)
