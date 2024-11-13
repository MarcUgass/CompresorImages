import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyArray;
import org.python.core.PyInteger;


public int[][][] Predictor3(String path) {
    try {
        // Importar la clase Python
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("src/Proba.py");

        // Llamar a las funciones de compresión y descompresión
        PyObject compressFunc = interpreter.get("compress_image");
        PyObject decompressFunc = interpreter.get("decompress_image");
        
        // Comprimir la imagen
        PyObject[] args = {new PyString(path)};
        PyObject compressedResult = compressFunc.__call__(args);
        
        // Obtener los resultados de la compresión
        PyObject compressedImage = compressedResult.__getitem__(0);
        PyObject net = compressedResult.__getitem__(1);
        
        // Descomprimir la imagen
        PyObject decompressedArray = decompressFunc.__call__(new PyObject[]{compressedImage, net});
        
        // Convertir el resultado de Python a un array de Java
        int[][][] result = convertPyArrayToJava(decompressedArray);
        
        return result;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

private int[][][] convertPyArrayToJava(PyObject pyArray) {
    // Asumiendo que la imagen es de 2560x2048
    int[][][] result = new int[1][2560][2048];
    
    // Convertir el array numpy a array Java
    PyArray numpyArray = (PyArray) pyArray;
    for (int i = 0; i < 2560; i++) {
        for (int j = 0; j < 2048; j++) {
            //result[0][i][j] = ((Number) numpyArray.__getitem__(new PyInteger(i)).__getitem__(new PyInteger(j))).intValue();
            result[0][i][j] = ((PyInteger) numpyArray.__getitem__(new PyInteger(i)).__getitem__(new PyInteger(j))).getValue();
        }
    }
    
    return result;
}

