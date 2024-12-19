#!/bin/bash

#Compresión
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o proba1.raw -q 3 -wt 3

#Descompresión
java decompress -i proba1.zip 2048 2560 1 1 -o imatge1.raw -q 3 -wt 3

#Cálculo métricas
psnr1=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge1.raw)

echo "Valor de PSNR: $psnr1"
