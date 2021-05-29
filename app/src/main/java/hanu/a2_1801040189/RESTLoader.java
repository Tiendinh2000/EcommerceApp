package hanu.a2_1801040189;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hanu.a2_1801040189.models.Product;

public class RESTLoader extends AsyncTask<String, Void, String> {

    private static final String URL = "https://mpr-cart-api.herokuapp.com/products";
    private static final String URL1 = "https://60b1bd1e62ab150017ae12e1.mockapi.io/api/mycart";
    ProgressDialog pd;
    Context context;

    interface AsyncResponse {
        void processFinish(List<Product> output);
    }

    public AsyncResponse delegate = null;

    public RESTLoader(AsyncResponse delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(URL1);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();

            Scanner sc = new Scanner(is);
            StringBuilder result = new StringBuilder();
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                result.append(line);
            }

            return result.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    // where you may interact with the UI
    protected void onPostExecute(String result) {
        pd.dismiss();
        List<Product> list = new ArrayList<>();
        super.onPostExecute(result);

        if (result == null) {

            return;
        }
        try {
            String OP = "";
            JSONArray root = new JSONArray(result);
            int count = 0;
            while (root.length() != count) {

                JSONObject o1 = root.getJSONObject(count);
                int id = o1.getInt("id");
                String name = o1.getString("name");
                String thumb = o1.getString("thumbnail");
                String cate = o1.getString("category");
                int price = o1.getInt("unitPrice");
                String description = o1.getString("description");

                Product f = new Product(id,name,thumb,cate,price,description);
                count++;
                list.add(f);


            }

            // after Async Process, Output is a List of Product
            delegate.processFinish(list);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setMessage("Loading please wait...");
        pd.setCancelable(false);
        pd.show();
    }


}
