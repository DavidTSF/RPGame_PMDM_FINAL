package dev.davidvega.rpgame.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Image utils.
 */
public class ImageUtils {

    /**
     * Save image from url.
     *
     * @param context  the context
     * @param imageUrl the image url
     * @param fileName the file name
     */
// Método para descargar y guardar la imagen en almacenamiento local
    public static void saveImageFromUrl(Context context, Object imageUrl, String fileName) {
        // Crear un Executor para manejar la descarga en un hilo de fondo
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                File directory = context.getDir("images", Context.MODE_PRIVATE);
                File file = new File(directory, fileName);

                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(imageUrl)
                        .submit()
                        .get(); // Obtener el Bitmap de manera síncrona en este hilo de fondo

                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

            } catch (Exception e) {
                Log.e("ImageUtils", "Error guardando la imagen", e);
            } finally {
                executor.shutdown(); // Apagar el Executor una vez terminado
            }
        });
    }


    /**
     * Gets saved image.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the saved image
     */
// Método para obtener la imagen almacenada localmente
    public static Bitmap getSavedImage(Context context, String fileName) {
        File directory = context.getDir("images", Context.MODE_PRIVATE);
        File file = new File(directory, fileName);

        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }

    /**
     * Image exists boolean.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the boolean
     */
// Método para verificar si una imagen ya existe
    public static boolean imageExists(Context context, String fileName) {
        File directory = context.getDir("images", Context.MODE_PRIVATE);
        File file = new File(directory, fileName);
        return file.exists();
    }
}
