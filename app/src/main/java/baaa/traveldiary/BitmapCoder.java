package baaa.traveldiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by L on 4/22/2016.
 */
public class BitmapCoder
{

    /*
        Source http://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android

        Example usage:

        String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
        Bitmap myBitmapAgain = decodeBase64(myBase64Image);
     */

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);

        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
