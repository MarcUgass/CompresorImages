import sys
import matplotlib.pyplot as plt

def plot_psnr(data, output_file):
    # Prepare los datos para la gráfica
    methods = []
    psnr_values = []
    quantization_steps = []

    # Parsear los datos
    for entry in data:
        method, quantization, psnr = entry.split(':')
        methods.append(method)
        quantization_steps.append(quantization)
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
    # Leer los datos desde el comando Bash
    data = sys.argv[1:]
    output_file = sys.argv[-1]  # Tomar el último argumento como el nombre del archivo de salida

    if data and output_file:
        plot_psnr(data, output_file)
    else:
        print("Por favor, pase los datos de PSNR y el nombre de archivo de salida.")
