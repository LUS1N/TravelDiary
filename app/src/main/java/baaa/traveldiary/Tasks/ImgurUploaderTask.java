package baaa.traveldiary.Tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import baaa.traveldiary.Utils.BitmapCoder;
import baaa.traveldiary.Constants;

/**
 * Created by L on 4/21/2016.
 */
public class ImgurUploaderTask extends AsyncTask<Bitmap, Void, String>
{
    String IMGUR_POST_URI = "https://api.imgur.com/3/upload";

    @Override
    protected String doInBackground(Bitmap... params)
    {
        String responseJSON = "";

        // encode image to base64
        String encoded = BitmapCoder.encodeToBase64(params[0], Bitmap.CompressFormat.PNG, 100);

        try
        {
            // try to get the response from post
            responseJSON = postImage(encoded);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // return the parsed url from the response
        // TODO deal with error uploading
        return parseURL(responseJSON);
    }


    private String parseURL(String result)
    {
        try
        {
            // check if status is ok
            if(Integer.parseInt(new JSONObject(result).get("status").toString()) == HttpURLConnection.HTTP_OK)
            {
                // parse the URL
                return new JSONObject(result).optJSONObject("data").getString("link");
            }
            else
                return "";
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String postImage(String encoded) throws IOException
    {
        String responseJSON = "";
        // prepare url
        URL url = new URL(IMGUR_POST_URI);


        // prepare post body
        String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(
                encoded, "UTF-8");
        data += "&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(
                Constants.MY_IMGUR_CLIENT_ID,
                "UTF-8");

        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Authorization", "Client-ID " + Constants.MY_IMGUR_CLIENT_ID);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

        wr.write(data);
        wr.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;

        while ((line = in.readLine()) != null)
        {
            responseJSON += line;
            System.out.println(line);
        }
        in.close();
        return responseJSON;
    }

}
