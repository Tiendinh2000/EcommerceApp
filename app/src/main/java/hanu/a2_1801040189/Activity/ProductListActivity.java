package hanu.a2_1801040189.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040189.Adapters.ProductListAdapter;
import hanu.a2_1801040189.R;
import hanu.a2_1801040189.models.Product;

public class ProductListActivity extends AppCompatActivity {


    private ProductListAdapter adapter;

    private FirebaseAuth auth;
    ImageButton btn_search, btn_return;
    EditText input_search;
    RecyclerView recyclerView;
TextView tv_accountName;
    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        btn_search = findViewById(R.id.btn_Search);
        input_search = findViewById(R.id.input_Search);
        tv_accountName=findViewById(R.id.tv_accountName);

        if (auth.getCurrentUser() != null){
            Log.d("auth in avcitvity", "" + auth.getCurrentUser().getEmail().toString());
        tv_accountName.setText("Hello "+ auth.getCurrentUser().getEmail().toString());}
        else{
            Log.d("auth in activity", "null");}



        RESTLoader task = (RESTLoader) new RESTLoader(new RESTLoader.AsyncResponse() {
            @Override
            public void processFinish(List<Product> output) {
                Log.d("input", "" + output.toString());
                // output after the process is a List contains Products
                recyclerView = findViewById(R.id.rv_productList);
                adapter = new ProductListAdapter(output);
                btn_return = findViewById(R.id.btn_return);
                setAdapter(adapter, recyclerView);

                // Find keyword and search
                SearchOnKeyBoard(output);

            }
        }, ProductListActivity.this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productlist, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_Cart:
                Intent i = new Intent(this, CartActivity.class);
                this.startActivity(i);
                break;
            case R.id.btn_Logout:
                auth.signOut();
                startActivity(new Intent(this, AuthenticationActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param a : Adapter
     * @param r : RecyleView
     *          setDapter: set adapter and GridLayout with spanCount = 2 for a RecyclerView
     */
    private void setAdapter(ProductListAdapter a, RecyclerView r) {
        r.setAdapter(a);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        r.setLayoutManager(gridLayoutManager);
    }

    /**
     * @param key  : keywords are searched
     * @param list : list in which keyword appear
     * @return result
     */
    public List<Product> getListKeyWord(String key, List<Product> list) {
        List result = new ArrayList();

        for (Product p : list) {

            if (p.getName().contains(key) || String.valueOf(p.getPrice()).contains(key)) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * \
     *
     * @param listOfProduct: a list contains products
     */
    public void SearchOnKeyBoard(List<Product> listOfProduct) {
        input_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {

                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            String keywords = input_search.getText().toString();
                            List<Product> l = getListKeyWord(keywords, listOfProduct);

                            ProductListAdapter a = new ProductListAdapter(l);
                            btn_return.setVisibility(View.VISIBLE);
                            btn_return.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    input_search.setText("");
                                    ProductListAdapter reset = new ProductListAdapter(listOfProduct);
                                    setAdapter(reset, recyclerView);
                                    btn_return.setVisibility(View.GONE);
                                }
                            });

                            setAdapter(a, recyclerView);
                            return true;
                        default:
                            break;
                    }
                }
                return false;

            }
        });
    }

    private void Autheticated() {
        if (auth.getCurrentUser() == null)
            this.finish();
    }
}