import sys
import matplotlib.pyplot as plt

def plot_psnr(data):
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

    # Mostrar la gráfica
    plt.tight_layout()
    plt.show()

if __name__ == "__main__":
    # Leer los datos desde el comando Bash
    data = sys.argv[1:]

    if data:
        plot_psnr(data)
    else:
        print("Por favor, pase los datos de PSNR como argumentos de línea de comando.")
