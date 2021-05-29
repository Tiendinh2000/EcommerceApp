package hanu.a2_1801040189;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040189.Adapters.CartAdapter;
import hanu.a2_1801040189.dbs.CartCursorWrapper;
import hanu.a2_1801040189.dbs.CartManager;
import hanu.a2_1801040189.dbs.DBHelper;
import hanu.a2_1801040189.models.Product;

public class CartActivity extends AppCompatActivity {

    private List list = new ArrayList();
    int totalAllprice;

    CartAdapter adapter;
    RecyclerView recyclerView;
    public CartManager DB;
    CartCursorWrapper cursor;
    TextView tv_allPrice, tv_emptyCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tv_allPrice = findViewById(R.id.allPrice);
        tv_emptyCart = findViewById(R.id.tv_emptyCart);


        setList();
        setAdapter(list);
        totalAllprice = getTotalAllPrice(list);
        //check if there is nothing in cart, display TextView
        if (totalAllprice == 0) {
            tv_emptyCart.setAlpha(1);
        }

        tv_allPrice.setText("$ " + String.valueOf(totalAllprice));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    /**
     * BroadcastReceiver : to receive message(increase or decrease the price) from CartAdapter
     */
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // receive message value:  add or minus a product's cost
            String minusPrice = intent.getStringExtra("MinusPrice");
            String addPrice = intent.getStringExtra("addPrice");

            // minus cost of product has been removed quantity
            if (minusPrice != null) {
                totalAllprice -= Integer.parseInt(minusPrice);
                tv_allPrice.setText("$ " + String.valueOf(totalAllprice));
            }
            // add cost of product has been add quantity
            if (addPrice != null) {
                totalAllprice += Integer.parseInt(addPrice);
                tv_allPrice.setText("$ " + String.valueOf(totalAllprice));
                ;
            }

        }
    };

    /**
     * Set list from SQLite
     */
    private void setList() {
        DB = new CartManager(this.getApplicationContext());
        cursor = new CartCursorWrapper(DB.getData());
        this.list = cursor.getProducts();
    }

    /**
     * @param l : set adapter for recycleView with a List of Product
     */
    private void setAdapter(List l) {
        adapter = new CartAdapter(l);
        recyclerView = findViewById(R.id.rv_cart);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

    }

    /**
     * @param l : A list of products
     * @return total price of All Product contained in this list
     */
    private int getTotalAllPrice(List<Product> l) {
        int result = 0;
        for (Product p : l) {
            int totalPriceOfAPrice = (int) (p.getQuantity() * p.getPrice());
            result += totalPriceOfAPrice;
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_productList:
                Intent i = new Intent(this, MainActivity.class);
                this.startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}