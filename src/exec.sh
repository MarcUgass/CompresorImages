#!/bin/bash


unset _JAVA_OPTIONS

# Emmagatzemar el temps inicial
start_time=$(date +%s)

####################Q1.5 WT 2####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q1_5_wt2_n1.raw -q 1.5 -wt 2
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q1_5_wt2_n2.raw -q 1.5 -wt 2
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q1_5_wt2_n3.raw -q 1.5 -wt 2
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q1_5_wt2_n4.raw -q 1.5 -wt 2
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q1_5_wt2_n5.raw -q 1.5 -wt 2
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q1_5_wt2_n6.raw -q 1.5 -wt 2
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q1_5_wt2_n7.raw -q 1.5 -wt 2

# Descompresión
java decompress -i zip_q1_5_wt2_n1.zip 512 512 1 2 -o imatge_q1_5_wt2_n1.raw -q 1.5 -wt 2
java decompress -i zip_q1_5_wt2_n2.zip 512 512 64 2 -o imatge_q1_5_wt2_n2.raw -q 1.5 -wt 2
java decompress -i zip_q1_5_wt2_n3.zip 677 512 224 3 -o imatge_q1_5_wt2_n3.raw -q 1.5 -wt 2
java decompress -i zip_q1_5_wt2_n4.zip 1024 1024 6 2 -o imatge_q1_5_wt2_n4.raw -q 1.5 -wt 2
java decompress -i zip_q1_5_wt2_n5.zip 2944 3576 1 2 -o imatge_q1_5_wt2_n5.raw -q 1.5 -wt 2
java decompress -i zip_q1_5_wt2_n6.zip 2048 2560 1 1 -o imatge_q1_5_wt2_n6.raw -q 1.5 -wt 2
java decompress -i zip_q1_5_wt2_n7.zip 2048 2560 3 1 -o imatge_q1_5_wt2_n7.raw -q 1.5 -wt 2

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q1_5_wt2_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q1_5_wt2_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q1_5_wt2_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q1_5_wt2_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q1_5_wt2_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q1_5_wt2_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q1_5_wt2_n7.raw)

# Generar gràfica
python3 graphics.py -q 1.5 -wt 2 $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q1_5_wt2.png

# Calcular la media de los valores PSNR
psnr_mean_1_5wt=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_1_5wt



####################Q1.5 P####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q1_5_p_n1.raw -q 1.5 -p
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q1_5_p_n2.raw -q 1.5 -p
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q1_5_p_n3.raw -q 1.5 -p
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q1_5_p_n4.raw -q 1.5 -p
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q1_5_p_n5.raw -q 1.5 -p
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q1_5_p_n6.raw -q 1.5 -p
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q1_5_p_n7.raw -q 1.5 -p

# Descompresión
java decompress -i zip_q1_5_p_n1.zip 512 512 1 2 -o imatge_q1_5_p_n1.raw -q 1.5 -p
java decompress -i zip_q1_5_p_n2.zip 512 512 64 2 -o imatge_q1_5_p_n2.raw -q 1.5 -p
java decompress -i zip_q1_5_p_n3.zip 677 512 224 3 -o imatge_q1_5_p_n3.raw -q 1.5 -p
java decompress -i zip_q1_5_p_n4.zip 1024 1024 6 2 -o imatge_q1_5_p_n4.raw -q 1.5 -p
java decompress -i zip_q1_5_p_n5.zip 2944 3576 1 2 -o imatge_q1_5_p_n5.raw -q 1.5 -p
java decompress -i zip_q1_5_p_n6.zip 2048 2560 1 1 -o imatge_q1_5_p_n6.raw -q 1.5 -p
java decompress -i zip_q1_5_p_n7.zip 2048 2560 3 1 -o imatge_q1_5_p_n7.raw -q 1.5 -p

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q1_5_p_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q1_5_p_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q1_5_p_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q1_5_p_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q1_5_p_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q1_5_p_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q1_5_p_n7.raw)

# Generar gràfica
python3 graphics.py -q 1.5 -p $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q1_5_p.png

# Calcular la media de los valores PSNR
psnr_mean_1_5p=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_1_5p


####################Q2 WT 2####################

#Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q2_wt2_n1.raw -q 2 -wt 2

java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q2_wt2_n2.raw -q 2 -wt 2

java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q2_wt2_n3.raw -q 2 -wt 2

java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q2_wt2_n4.raw -q 2 -wt 2

java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q2_wt2_n5.raw -q 2 -wt 2

java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q2_wt2_n6.raw -q 2 -wt 2

java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q2_wt2_n7.raw -q 2 -wt 2

#Descompresión
java decompress -i zip_q2_wt2_n1.zip 512 512 1 2 -o imatge_q2_wt2_n1.raw -q 2 -wt 2

java decompress -i zip_q2_wt2_n2.zip 512 512 64 2 -o imatge_q2_wt2_n2.raw -q 2 -wt 2

java decompress -i zip_q2_wt2_n3.zip 677 512 224 3 -o imatge_q2_wt2_n3.raw -q 2 -wt 2

java decompress -i zip_q2_wt2_n4.zip 1024 1024 6 2 -o imatge_q2_wt2_n4.raw -q 2 -wt 2

java decompress -i zip_q2_wt2_n5.zip 2944 3576 1 2 -o imatge_q2_wt2_n5.raw -q 2 -wt 2

java decompress -i zip_q2_wt2_n6.zip 2048 2560 1 1 -o imatge_q2_wt2_n6.raw -q 2 -wt 2

java decompress -i zip_q2_wt2_n7.zip 2048 2560 3 1 -o imatge_q2_wt2_n7.raw -q 2 -wt 2

#Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q2_wt2_n1.raw)

psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q2_wt2_n2.raw)

psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q2_wt2_n3.raw)

psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q2_wt2_n4.raw)

psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q2_wt2_n5.raw)

psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q2_wt2_n6.raw)

psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q2_wt2_n7.raw)

#echo "Valor de PSNR: $psnr1 , $psnr2 , $psnr3 , $psnr4 , $psnr5 , $psnr6 , $psnr7"

python3 graphics.py -q 2 -wt $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q2_wt2.png


# Calcular la media de los valores PSNR
psnr_mean_2wt=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_2wt

####################Q2 P####################


# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q2_p_n1.raw -q 2 -p

java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q2_p_n2.raw -q 2 -p

java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q2_p_n3.raw -q 2 -p

java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q2_p_n4.raw -q 2 -p

java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q2_p_n5.raw -q 2 -p

java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q2_p_n6.raw -q 2 -p

java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q2_p_n7.raw -q 2 -p

# Descompresión
java decompress -i zip_q2_p_n1.zip 512 512 1 2 -o imatge_q2_p_n1.raw -q 2 -p

java decompress -i zip_q2_p_n2.zip 512 512 64 2 -o imatge_q2_p_n2.raw -q 2 -p

java decompress -i zip_q2_p_n3.zip 677 512 224 3 -o imatge_q2_p_n3.raw -q 2 -p

java decompress -i zip_q2_p_n4.zip 1024 1024 6 2 -o imatge_q2_p_n4.raw -q 2 -p

java decompress -i zip_q2_p_n5.zip 2944 3576 1 2 -o imatge_q2_p_n5.raw -q 2 -p

java decompress -i zip_q2_p_n6.zip 2048 2560 1 1 -o imatge_q2_p_n6.raw -q 2 -p

java decompress -i zip_q2_p_n7.zip 2048 2560 3 1 -o imatge_q2_p_n7.raw -q 2 -p

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q2_p_n1.raw)

psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q2_p_n2.raw)

psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q2_p_n3.raw)

psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q2_p_n4.raw)

psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q2_p_n5.raw)

psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q2_p_n6.raw)

psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q2_p_n7.raw)

#echo "Valor de PSNR: $psnr1 , $psnr2 , $psnr3 , $psnr4 , $psnr5 , $psnr6 , $psnr7"

python3 graphics.py -q 2 -p $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q2_p.png


# Calcular la media de los valores PSNR
psnr_mean_2p=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_2p

####################Q2.5 WT 2####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q2_5_wt2_n1.raw -q 2.5 -wt 2
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q2_5_wt2_n2.raw -q 2.5 -wt 2
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q2_5_wt2_n3.raw -q 2.5 -wt 2
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q2_5_wt2_n4.raw -q 2.5 -wt 2
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q2_5_wt2_n5.raw -q 2.5 -wt 2
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q2_5_wt2_n6.raw -q 2.5 -wt 2
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q2_5_wt2_n7.raw -q 2.5 -wt 2

# Descompresión
java decompress -i zip_q2_5_wt2_n1.zip 512 512 1 2 -o imatge_q2_5_wt2_n1.raw -q 2.5 -wt 2
java decompress -i zip_q2_5_wt2_n2.zip 512 512 64 2 -o imatge_q2_5_wt2_n2.raw -q 2.5 -wt 2
java decompress -i zip_q2_5_wt2_n3.zip 677 512 224 3 -o imatge_q2_5_wt2_n3.raw -q 2.5 -wt 2
java decompress -i zip_q2_5_wt2_n4.zip 1024 1024 6 2 -o imatge_q2_5_wt2_n4.raw -q 2.5 -wt 2
java decompress -i zip_q2_5_wt2_n5.zip 2944 3576 1 2 -o imatge_q2_5_wt2_n5.raw -q 2.5 -wt 2
java decompress -i zip_q2_5_wt2_n6.zip 2048 2560 1 1 -o imatge_q2_5_wt2_n6.raw -q 2.5 -wt 2
java decompress -i zip_q2_5_wt2_n7.zip 2048 2560 3 1 -o imatge_q2_5_wt2_n7.raw -q 2.5 -wt 2

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q2_5_wt2_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q2_5_wt2_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q2_5_wt2_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q2_5_wt2_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q2_5_wt2_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q2_5_wt2_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q2_5_wt2_n7.raw)

# Generar gràfica
python3 graphics.py -q 2.5 -wt $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q2_5_wt2.png


# Calcular la media de los valores PSNR
psnr_mean_2_5wt=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_2_5wt



####################Q2.5 P####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q2_5_p_n1.raw -q 2.5 -p
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q2_5_p_n2.raw -q 2.5 -p
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q2_5_p_n3.raw -q 2.5 -p
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q2_5_p_n4.raw -q 2.5 -p
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q2_5_p_n5.raw -q 2.5 -p
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q2_5_p_n6.raw -q 2.5 -p
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q2_5_p_n7.raw -q 2.5 -p

# Descompresión
java decompress -i zip_q2_5_p_n1.zip 512 512 1 2 -o imatge_q2_5_p_n1.raw -q 2.5 -p
java decompress -i zip_q2_5_p_n2.zip 512 512 64 2 -o imatge_q2_5_p_n2.raw -q 2.5 -p
java decompress -i zip_q2_5_p_n3.zip 677 512 224 3 -o imatge_q2_5_p_n3.raw -q 2.5 -p
java decompress -i zip_q2_5_p_n4.zip 1024 1024 6 2 -o imatge_q2_5_p_n4.raw -q 2.5 -p
java decompress -i zip_q2_5_p_n5.zip 2944 3576 1 2 -o imatge_q2_5_p_n5.raw -q 2.5 -p
java decompress -i zip_q2_5_p_n6.zip 2048 2560 1 1 -o imatge_q2_5_p_n6.raw -q 2.5 -p
java decompress -i zip_q2_5_p_n7.zip 2048 2560 3 1 -o imatge_q2_5_p_n7.raw -q 2.5 -p

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q2_5_p_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q2_5_p_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q2_5_p_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q2_5_p_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q2_5_p_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q2_5_p_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q2_5_p_n7.raw)

# Generar gràfica
python3 graphics.py -q 2.5 -p $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q2_5_p.png


# Calcular la media de los valores PSNR
psnr_mean_2_5p=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_2_5p

####################Q3 WT 2####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q3_wt2_n1.raw -q 3 -wt 2
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q3_wt2_n2.raw -q 3 -wt 2
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q3_wt2_n3.raw -q 3 -wt 2
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q3_wt2_n4.raw -q 3 -wt 2
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q3_wt2_n5.raw -q 3 -wt 2
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q3_wt2_n6.raw -q 3 -wt 2
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q3_wt2_n7.raw -q 3 -wt 2

# Descompresión
java decompress -i zip_q3_wt2_n1.zip 512 512 1 2 -o imatge_q3_wt2_n1.raw -q 3 -wt 2
java decompress -i zip_q3_wt2_n2.zip 512 512 64 2 -o imatge_q3_wt2_n2.raw -q 3 -wt 2
java decompress -i zip_q3_wt2_n3.zip 677 512 224 3 -o imatge_q3_wt2_n3.raw -q 3 -wt 2
java decompress -i zip_q3_wt2_n4.zip 1024 1024 6 2 -o imatge_q3_wt2_n4.raw -q 3 -wt 2
java decompress -i zip_q3_wt2_n5.zip 2944 3576 1 2 -o imatge_q3_wt2_n5.raw -q 3 -wt 2
java decompress -i zip_q3_wt2_n6.zip 2048 2560 1 1 -o imatge_q3_wt2_n6.raw -q 3 -wt 2
java decompress -i zip_q3_wt2_n7.zip 2048 2560 3 1 -o imatge_q3_wt2_n7.raw -q 3 -wt 2

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q3_wt2_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q3_wt2_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q3_wt2_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q3_wt2_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q3_wt2_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q3_wt2_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q3_wt2_n7.raw)

# Generar gràfica
python3 graphics.py -q 3 -wt 2 $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q3_wt2.png

# Calcular la media de los valores PSNR
psnr_mean_3wt=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_3wt



####################Q3 P####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q3_p_n1.raw -q 3 -p
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q3_p_n2.raw -q 3 -p
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q3_p_n3.raw -q 3 -p
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q3_p_n4.raw -q 3 -p
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q3_p_n5.raw -q 3 -p
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q3_p_n6.raw -q 3 -p
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q3_p_n7.raw -q 3 -p

# Descompresión
java decompress -i zip_q3_p_n1.zip 512 512 1 2 -o imatge_q3_p_n1.raw -q 3 -p
java decompress -i zip_q3_p_n2.zip 512 512 64 2 -o imatge_q3_p_n2.raw -q 3 -p
java decompress -i zip_q3_p_n3.zip 677 512 224 3 -o imatge_q3_p_n3.raw -q 3 -p
java decompress -i zip_q3_p_n4.zip 1024 1024 6 2 -o imatge_q3_p_n4.raw -q 3 -p
java decompress -i zip_q3_p_n5.zip 2944 3576 1 2 -o imatge_q3_p_n5.raw -q 3 -p
java decompress -i zip_q3_p_n6.zip 2048 2560 1 1 -o imatge_q3_p_n6.raw -q 3 -p
java decompress -i zip_q3_p_n7.zip 2048 2560 3 1 -o imatge_q3_p_n7.raw -q 3 -p

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q3_p_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q3_p_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q3_p_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q3_p_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q3_p_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q3_p_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q3_p_n7.raw)

# Generar gràfica
python3 graphics.py -q 3 -p $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q3_p.png

# Calcular la media de los valores PSNR
psnr_mean_3p=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_3p


####################Q3.5 WT 2####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q3_5_wt2_n1.raw -q 3.5 -wt 2
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q3_5_wt2_n2.raw -q 3.5 -wt 2
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q3_5_wt2_n3.raw -q 3.5 -wt 2
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q3_5_wt2_n4.raw -q 3.5 -wt 2
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q3_5_wt2_n5.raw -q 3.5 -wt 2
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q3_5_wt2_n6.raw -q 3.5 -wt 2
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q3_5_wt2_n7.raw -q 3.5 -wt 2

# Descompresión
java decompress -i zip_q3_5_wt2_n1.zip 512 512 1 2 -o imatge_q3_5_wt2_n1.raw -q 3.5 -wt 2
java decompress -i zip_q3_5_wt2_n2.zip 512 512 64 2 -o imatge_q3_5_wt2_n2.raw -q 3.5 -wt 2
java decompress -i zip_q3_5_wt2_n3.zip 677 512 224 3 -o imatge_q3_5_wt2_n3.raw -q 3.5 -wt 2
java decompress -i zip_q3_5_wt2_n4.zip 1024 1024 6 2 -o imatge_q3_5_wt2_n4.raw -q 3.5 -wt 2
java decompress -i zip_q3_5_wt2_n5.zip 2944 3576 1 2 -o imatge_q3_5_wt2_n5.raw -q 3.5 -wt 2
java decompress -i zip_q3_5_wt2_n6.zip 2048 2560 1 1 -o imatge_q3_5_wt2_n6.raw -q 3.5 -wt 2
java decompress -i zip_q3_5_wt2_n7.zip 2048 2560 3 1 -o imatge_q3_5_wt2_n7.raw -q 3.5 -wt 2

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q3_5_wt2_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q3_5_wt2_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q3_5_wt2_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q3_5_wt2_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q3_5_wt2_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q3_5_wt2_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q3_5_wt2_n7.raw)

# Generar gràfica
python3 graphics.py -q 3.5 -wt $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q3_5_wt2.png

# Calcular la media de los valores PSNR
psnr_mean_3_5wt=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)


####################Q3.5 P####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q3_5_p_n1.raw -q 3.5 -p
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q3_5_p_n2.raw -q 3.5 -p
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q3_5_p_n3.raw -q 3.5 -p
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q3_5_p_n4.raw -q 3.5 -p
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q3_5_p_n5.raw -q 3.5 -p
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q3_5_p_n6.raw -q 3.5 -p
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q3_5_p_n7.raw -q 3.5 -p

# Descompresión
java decompress -i zip_q3_5_p_n1.zip 512 512 1 2 -o imatge_q3_5_p_n1.raw -q 3.5 -p
java decompress -i zip_q3_5_p_n2.zip 512 512 64 2 -o imatge_q3_5_p_n2.raw -q 3.5 -p
java decompress -i zip_q3_5_p_n3.zip 677 512 224 3 -o imatge_q3_5_p_n3.raw -q 3.5 -p
java decompress -i zip_q3_5_p_n4.zip 1024 1024 6 2 -o imatge_q3_5_p_n4.raw -q 3.5 -p
java decompress -i zip_q3_5_p_n5.zip 2944 3576 1 2 -o imatge_q3_5_p_n5.raw -q 3.5 -p
java decompress -i zip_q3_5_p_n6.zip 2048 2560 1 1 -o imatge_q3_5_p_n6.raw -q 3.5 -p
java decompress -i zip_q3_5_p_n7.zip 2048 2560 3 1 -o imatge_q3_5_p_n7.raw -q 3.5 -p

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q3_5_p_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q3_5_p_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q3_5_p_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q3_5_p_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q3_5_p_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q3_5_p_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q3_5_p_n7.raw)

# Generar gràfica
python3 graphics.py -q 3.5 -p $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q3_5_p.png

# Calcular la media de los valores PSNR
psnr_mean_3_5p=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_3_5p


####################Q4 WT 2####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q4_wt2_n1.raw -q 4 -wt 2
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q4_wt2_n2.raw -q 4 -wt 2
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q4_wt2_n3.raw -q 4 -wt 2
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q4_wt2_n4.raw -q 4 -wt 2
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q4_wt2_n5.raw -q 4 -wt 2
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q4_wt2_n6.raw -q 4 -wt 2
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q4_wt2_n7.raw -q 4 -wt 2

# Descompresión
java decompress -i zip_q4_wt2_n1.zip 512 512 1 2 -o imatge_q4_wt2_n1.raw -q 4 -wt 2
java decompress -i zip_q4_wt2_n2.zip 512 512 64 2 -o imatge_q4_wt2_n2.raw -q 4 -wt 2
java decompress -i zip_q4_wt2_n3.zip 677 512 224 3 -o imatge_q4_wt2_n3.raw -q 4 -wt 2
java decompress -i zip_q4_wt2_n4.zip 1024 1024 6 2 -o imatge_q4_wt2_n4.raw -q 4 -wt 2
java decompress -i zip_q4_wt2_n5.zip 2944 3576 1 2 -o imatge_q4_wt2_n5.raw -q 4 -wt 2
java decompress -i zip_q4_wt2_n6.zip 2048 2560 1 1 -o imatge_q4_wt2_n6.raw -q 4 -wt 2
java decompress -i zip_q4_wt2_n7.zip 2048 2560 3 1 -o imatge_q4_wt2_n7.raw -q 4 -wt 2

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q4_wt2_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q4_wt2_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q4_wt2_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q4_wt2_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q4_wt2_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q4_wt2_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q4_wt2_n7.raw)

# Generar gràfica
python3 graphics.py -q 4 -wt $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q4_wt2.png

# Calcular la media de los valores PSNR
psnr_mean_4wt=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

python3 graphics_means.py $psnr_mean_1_5wt $psnr_mean_2wt $psnr_mean_2_5wt $psnr_mean_3wt $psnr_mean_3_5wt $psnr_mean_4wt quantization_wt.png --quantization_steps 1.5 2 2.5 3 3.5 4


####################Q4 P####################

# Compresión
java compress -i 03508649.1_512_512_2_0_12_0_0_0.raw 512 512 1 2 -o zip_q4_p_n1.raw -q 4 -p
java compress -i 03508649.64_512_512_2_0_12_0_0_0.raw 512 512 64 2 -o zip_q4_p_n2.raw -q 4 -p
java compress -i aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw 677 512 224 3 -o zip_q4_p_n3.raw -q 4 -p
java compress -i Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw 1024 1024 6 2 -o zip_q4_p_n4.raw -q 4 -p
java compress -i mamo_1.1_3576_2944_2_0_12_0_0_0.raw 2944 3576 1 2 -o zip_q4_p_n5.raw -q 4 -p
java compress -i n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o zip_q4_p_n6.raw -q 4 -p
java compress -i n1_RGB.3_2560_2048_1_0_8_0_0_0.raw 2048 2560 3 1 -o zip_q4_p_n7.raw -q 4 -p

# Descompresión
java decompress -i zip_q4_p_n1.zip 512 512 1 2 -o imatge_q4_p_n1.raw -q 4 -p
java decompress -i zip_q4_p_n2.zip 512 512 64 2 -o imatge_q4_p_n2.raw -q 4 -p
java decompress -i zip_q4_p_n3.zip 677 512 224 3 -o imatge_q4_p_n3.raw -q 4 -p
java decompress -i zip_q4_p_n4.zip 1024 1024 6 2 -o imatge_q4_p_n4.raw -q 4 -p
java decompress -i zip_q4_p_n5.zip 2944 3576 1 2 -o imatge_q4_p_n5.raw -q 4 -p
java decompress -i zip_q4_p_n6.zip 2048 2560 1 1 -o imatge_q4_p_n6.raw -q 4 -p
java decompress -i zip_q4_p_n7.zip 2048 2560 3 1 -o imatge_q4_p_n7.raw -q 4 -p

# Cálculo métricas
psnr1=$(java PSNR 03508649.1_512_512_2_0_12_0_0_0.raw imatge_q4_p_n1.raw)
psnr2=$(java PSNR 03508649.64_512_512_2_0_12_0_0_0.raw imatge_q4_p_n2.raw)
psnr3=$(java PSNR aviris_yellowstone_f060925t01p00r12_sc00_cal.224_512_677_3_0_16_0_0_0.raw imatge_q4_p_n3.raw)
psnr4=$(java PSNR Landsat_agriculture.6_1024_1024_2_0_16_0_0_0.raw imatge_q4_p_n4.raw)
psnr5=$(java PSNR mamo_1.1_3576_2944_2_0_12_0_0_0.raw imatge_q4_p_n5.raw)
psnr6=$(java PSNR n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw imatge_q4_p_n6.raw)
psnr7=$(java PSNR n1_RGB.3_2560_2048_1_0_8_0_0_0.raw imatge_q4_p_n7.raw)

# Calcular la media de los valores PSNR
psnr_mean_4p=$(echo "scale=2; ($psnr1 + $psnr2 + $psnr3 + $psnr4 + $psnr5 + $psnr6 + $psnr7) / 7" | bc)

# Mostrar el resultado
echo "Mitjana:" $psnr_mean_4p

# Generar gràfica
python3 graphics.py -q 4 -p $psnr1 $psnr2 $psnr3 $psnr4 $psnr5 $psnr6 $psnr7 report_q4_p.png

python3 graphics_means.py $psnr_mean_2p $psnr_mean_2p $psnr_mean_2_5p $psnr_mean_3p $psnr_mean_3_5p $psnr_mean_4p quantization_p.png --quantization_steps 1.5 2 2.5 3 3.5 4



# Emmagatzemar el temps final
end_time=$(date +%s)

# Calcular la durada total en segons
duration=$((end_time - start_time))

# Convertir els segons en format hh:mm:ss
formatted_duration=$(printf "%02d:%02d:%02d\n" $(($duration / 3600)) $(($duration % 3600 / 60)) $(($duration % 60)))

# Imprimir el temps total
echo "Temps d'execució: $formatted_duration"

