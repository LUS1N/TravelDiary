package baaa.traveldiary.Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.Storage;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap>
{
    private final WeakReference<ImageView> imageViewReference;
    private String stringUrl = "";

    public ImageDownloaderTask(ImageView imageView)
    {
        this.imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        return downloadBitmap(params[0]);
    }

    private Bitmap downloadBitmap(String url)
    {
        if (url == null)
        {
            return null;
        }
        stringUrl = url;
        HttpURLConnection urlConnection = null;
        try
        {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null)
            {
                return BitmapFactory.decodeStream(inputStream);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap image)
    {
        if (isCancelled())
            return;

        ImageView imgView = imageViewReference.get();
        if (imgView == null || image == null)
            return;

        addImageToViewAndStorage(image, imgView);

    }

    private void addImageToViewAndStorage(Bitmap image, ImageView imgView)
    {
        // save the instance in the hashmap
        Storage.putImage(stringUrl, image);

        // if thumbnail is required, return resized version from storage
        imgView.setImageBitmap(image);
    }
}