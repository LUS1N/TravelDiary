package baaa.traveldiary.Utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

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

}
