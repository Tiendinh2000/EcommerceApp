package hanu.a2_1801040189;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hanu.a2_1801040189.dbs.CartManager;
import hanu.a2_1801040189.models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tv_name, tv_price, tv_description, tv_category, tv_quantity;
    ImageButton addToCart;
    private CartManager DB;
    ImageView productImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tv_name = findViewById(R.id.tv_detail_name);
        tv_category = findViewById(R.id.tv_detail_category);
        tv_description = findViewById(R.id.tv_detail_description);
        tv_price = findViewById(R.id.tv_detail_price);
        tv_quantity = findViewById(R.id.tv_detail_quantity);
        DB = new CartManager(getApplicationContext());
        addToCart = findViewById(R.id.btn_detail_add_product);
        productImg = findViewById(R.id.iv_detail_image);

        Product p = (Product) getIntent().getSerializableExtra("product");
        Log.d("product in detil", "" + p.toString());
        Log.d("check", "" + String.valueOf(DB.checkQuantity(p.getId())));

        Picasso.get().load(p.getImage()).into(productImg);
        tv_name.setText(p.getName());
        tv_price.setText(String.valueOf(p.getPrice()));
        tv_description.setText(p.getDescription());
        tv_category.setText(p.getCategory());
        tv_quantity.setText(String.valueOf(DB.checkQuantity(p.getId())));


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}