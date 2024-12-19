#!/bin/bash

#Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zipn1.raw -q 2 -wt 2

java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zipn2.raw -q 2 -wt 2

java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zipn3.raw -q 2 -wt 2

java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zipn4.raw -q 2 -wt 2

java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zipn5.raw -q 2 -wt 2

java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zipn6.raw -q 2 -wt 2

java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zipn7.raw -q 2 -wt 2



#Descompresión
java decompress -i zipn1.zip 512 512 1 2 -o imatge1.raw -q 2 -wt 2

java decompress -i zipn2.zip 512 512 64 2 -o imatge2.raw -q 2 -wt 2

java decompress -i zipn3.zip 677 512 224 3 -o imatge3.raw -q 2 -wt 2

java decompress -i zipn4.zip 1024 1024 6 2 -o imatge4.raw -q 2 -wt 2

java decompress -i zipn5.zip 2944 3576 1 2 -o imatge5.raw -q 2 -wt 2

java decompress -i zipn6.zip 2048 2560 1 1 -o imatge6.raw -q 2 -wt 2

java decompress -i zipn7.zip 2048 2560 3 1 -o imatge7.raw -q 2 -wt 2

#Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge1.raw)

psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge2.raw)

psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge3.raw)

psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge4.raw)

psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge5.raw)

psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge6.raw)

psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge7.raw)

#echo "Valor de PSNR: $psnr1 , $psnr2 , $psnr3 , $psnr4 , $psnr5 , $psnr6 , $psnr7"

python3 graphics.py -q 2 -wt $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 output.png
