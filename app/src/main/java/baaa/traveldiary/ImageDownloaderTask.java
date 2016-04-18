package baaa.traveldiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap>
{
    private final WeakReference<ImageView> imageViewReference;


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
        HttpURLConnection urlConnection = null;
        Log.w("BB", " downloading image from " + url);
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
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        }
        catch (Exception e)
        {
            urlConnection.disconnect();
            Log.w("BB", "Error downloading image from " + url);
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
            image = null;
        if (imageViewReference != null)
        {
            ImageView imgView = imageViewReference.get();
            if (imgView != null)
            {
                if (image != null)
                {
                    imgView.setImageBitmap(image);
                }
                else
                {
                    Drawable placeholder = imgView.getContext().getResources().getDrawable(
                            R.drawable.ic_action_name);
                    imgView.setImageDrawable(placeholder);
                }
            }
        }
    }
}