import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import torch
from torchvision import transforms
from compressai.models import Cheng2020Anchor
from compressai.zoo import cheng2020_anchor
from compressai.zoo import bmshj2018_factorized

def compress_and_decompress_show_image(image_path):
    # Load and preprocess image
    # Read RAW file and convert to numpy array
    with open(image_path, 'rb') as f:
        raw_data = f.read()
    
    # Assuming 8-bit per pixel RAW image in grayscale
    img_array = np.frombuffer(raw_data[:2048*2560], dtype=np.uint8)
    img_array = img_array.reshape((2560, 2048))  # RAW image dimensions
    
    # Convert to PIL Image and resize
    img = Image.fromarray(img_array)
    img = img.resize((256, 256))
    
    # Convert to tensor and add batch dimension
    x = transforms.ToTensor()(img).unsqueeze(0)
    # Replicate channel to have 3 RGB channels
    x = x.repeat(1, 3, 1, 1)

    # Create compression model
    net = cheng2020_anchor(quality=6, pretrained=True)
    net.eval()
    
    with torch.no_grad():
        out_net = net.compress(x)

    # HERE OUTNET IS THE COMPRESSED IMAGE
    print(out_net['strings'])
    
    # HERE BEGINS THE DECOMPRESSION
    out_net = net.decompress(out_net['strings'],out_net['shape'])

    out_net['x_hat']
    print(out_net.keys())

    rec_net = transforms.ToPILImage()(out_net['x_hat'].squeeze().cpu().detach())

    diff = torch.mean((out_net['x_hat'] - x).abs(), axis=1).squeeze().cpu().detach()

    fix, axes = plt.subplots(1, 3, figsize=(16, 12))
    for ax in axes:
        ax.axis('off')
        
    axes[0].imshow(img)
    axes[0].title.set_text('Original')

    axes[1].imshow(rec_net)
    axes[1].title.set_text('Reconstructed')

    axes[2].imshow(diff, cmap='viridis')
    axes[2].title.set_text('Difference')

    plt.show()

    
    plt.show()

def compress_image(image_path):
    with open(image_path, 'rb') as f:
        raw_data = f.read()
    
    # Assuming 8-bit per pixel RAW image in grayscale
    img_array = np.frombuffer(raw_data[:2048*2560], dtype=np.uint8) # 2048*2560 is the size of the image, change it if the image is not 2048x2560
    img_array = img_array.reshape((2560, 2048))  # RAW image dimensions
    
    # Convert to PIL Image and resize
    img = Image.fromarray(img_array)
    img = img.resize((256, 256))
    
    # Convert to tensor and add batch dimension
    x = transforms.ToTensor()(img).unsqueeze(0)
    # Replicate channel to have 3 RGB channels
    x = x.repeat(1, 3, 1, 1)

    # Create compression model
    net = cheng2020_anchor(quality=6, pretrained=True)
    net.eval()
    
    with torch.no_grad():
        out_net = net.compress(x)

    return out_net, net


def decompress_image(out_net, net):
    # Descomprimimos la imagen usando el modelo
    out_net = net.decompress(out_net['strings'], out_net['shape'])
    
    # Convertimos el tensor a numpy array y lo movemos a CPU
    img_array = out_net['x_hat'].squeeze().cpu().detach().numpy()
    
    # Reorganizamos las dimensiones para tener formato [componentes, filas, columnas]
    img_array = np.transpose(img_array, (0, 1, 2))
    
    # Convertimos los valores a enteros
    img_array = (img_array * 255).astype(np.uint8)
    
    # Convertimos a imagen PIL para poder redimensionar
    img = Image.fromarray(img_array[0])
    
    # Redimensionamos a 2560x2048
    img = img.resize((2048, 2560))
    
    # Convertimos de vuelta a numpy array
    img_array = np.array(img)
    
    return img_array

if __name__ == "__main__":
    # Example usage
    
    # Build relative path from script directory
    image_path ="imatges\\n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw"

    compressed_image, net = compress_image(image_path)
    decompressed_image = decompress_image(compressed_image, net)

    compress_and_decompress_show_image(image_path)