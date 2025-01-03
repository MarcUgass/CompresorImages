# Proyecto: Compresor de Imagenes

Este proyecto es una aplicación Java que permite interactuar con archivos mediante diferentes modos de ejecución: interactivo, compresión y descompresión.

## Estructura del Proyecto

La aplicación está diseñada para ejecutarse desde la terminal y ofrece las siguientes opciones principales:
- **Modo interactivo (Main)**: Proporciona un menú para interactuar directamente con las funcionalidades del programa.
- **Compress**: Permite comprimir uno o varios archivos en un archivo ZIP.
- **Decompress**: Permite descomprimir archivos ZIP.

## Requisitos Previos

1. Java Development Kit (JDK) instalado (versión 8 o superior).
2. Una terminal en un sistema operativo compatible con Java (Linux, macOS, Windows).

## Compilación

Primero, asegúrate de que todos los archivos fuente se encuentren en el mismo directorio. Luego, compila los archivos con el siguiente comando:
```bash
javac *.java
```
Esto generará archivos `.class` en el mismo directorio.

## Ejecución

### 1. Modo Interactivo (Main)
El programa inicia en modo interactivo para seleccionar entre las diferentes funcionalidades.
```bash
java Main
```

### 2. Comprimir Archivos
Usa el modo `compress` para comprimir uno o varios archivos.
```bash
java compress -i input.raw Ancho Alto Imagenes Formato -o output.raw [-q factor] [[-wt nivel] / [-p]]
```
#### Ejemplo:
```bash
java compress n1_GRAY.1_2560_2048_1_0_8_0_0_0.raw 2048 2560 1 1 -o output.raw -q 3 -wt 3
```

### 3. Descomprimir Archivos
Usa el modo `decompress` para descomprimir un archivo ZIP.
```bash
java decompress -i input.zip Ancho Alto Imagenes Formato -o output.raw [-q factor] [[-wt nivel] / [-p]]
```
#### Ejemplo:
```bash
java decompress -i output.zip 2048 2560 1 1 -o copia_orignial.raw -q 3 -wt 3
```

### **4. Calcular PSNR**
Calcula el valor de PSNR entre dos imágenes crudas (`.raw`). Para ejecutar este cálculo:
```bash
java PSNR input1.raw input2.raw
```
El resultado es el valor de PSNR mostrado en la salida estándar.

Script sencillo de uso:

```bash
#!/bin/bash

# Ejecutar el cálculo de PSNR
psnr=$(java PSNR input1.raw outputDir/input2.raw)

# Mostrar el valor calculado
echo "El valor del PSNR es: $psnr"
```

### Ejemplo completo de script
Puedes combinar las funciones de compresión, descompresión y cálculo de PSNR en un único flujo:
```bash
#!/bin/bash

# Paso 1: Comprimir archivos
java Compress input1.raw input2.raw compressed.zip

# Paso 2: Descomprimir archivos
java Decompress compressed.zip outputDir

# Paso 3: Calcular PSNR
psnr=$(java PSNR input1.raw outputDir/input2.raw)

echo "El valor del PSNR es: $psnr"
```

Este script ejecuta las tres fases automáticamente y es ideal para integraciones en pipelines automatizados.

---

