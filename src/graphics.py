import sys
import matplotlib.pyplot as plt
import argparse
import os

def plot_psnr(data, quantization, method, output_file):
    # Preparar els valors per la gràfica
    psnr_values = []
    image_indices = list(range(1, len(data) + 1))  # Eix X: índex de les imatges (enteros)

    # Parsejar els valors PSNR
    for psnr in data:
        psnr_values.append(float(psnr))

    # Crear la figura per la gràfica
    plt.figure(figsize=(10, 6))

    # Graficar els valors PSNR com a punts dispersos
    plt.scatter(image_indices, psnr_values, c='b', marker='o', label=f'PSNR (Q={quantization})')

    # Etiquetes
    plt.title(f'PSNR per diferents imatges ({method} amb Q={quantization})')
    plt.xlabel('Índex de la Imatge')
    plt.ylabel('Valor PSNR')

    # Assegurar que els valors de l'eix X siguin enters
    plt.xticks(image_indices)

    # Afegir llegenda
    plt.legend()

    # Crear el directori 'reports' si no existeix
    output_dir = os.path.join(os.path.dirname(__file__), '..', 'reports')
    os.makedirs(output_dir, exist_ok=True)

    # Guardar la gràfica com a imatge a 'reports'
    output_path = os.path.join(output_dir, output_file)
    plt.tight_layout()
    plt.savefig(output_path)  # Guarda el fitxer a la ruta especificada
    print(f"Gràfic guardat a: {output_path}")

if __name__ == "__main__":
    # Configurar argparse per gestionar els arguments de la línia de comandes
    parser = argparse.ArgumentParser(description="Generar gràfica PSNR de mètodes de compressió")
    parser.add_argument('-q', type=float, default=2, help="Pas de quantització, per defecte 2 si no es especifica")
    parser.add_argument('-wt', action='store_true', help="Usar wavelet (si no, es farà servir predictor)")
    parser.add_argument('-p', action='store_true', help="Usar predictor")
    parser.add_argument('psnr_values', nargs='+', help="Llista de valors PSNR")
    parser.add_argument('output_file', help="Nom del fitxer de sortida per guardar la gràfica")

    args = parser.parse_args()

    # Determinar el mètode basat en els flags -wt i -p
    method = 'wavelet' if args.wt else 'predictor' if args.p else 'desconegut'

    # Comprovar que hi hagi un mètode seleccionat
    if method == 'desconegut':
        print("Error: Cal especificar un mètode usant -wt per wavelet o -p per predictor.")
        sys.exit(1)

    # Cridar la funció per generar la gràfica
    plot_psnr(args.psnr_values, args.q, method, args.output_file)
