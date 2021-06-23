package hanu.a2_1801040189;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hanu.a2_1801040189.data.dbs.CartManager;
import hanu.a2_1801040189.models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tv_name, tv_price, tv_description, tv_category, tv_quantity;
    ImageButton btn_addToCart, btn_return;
    private CartManager DB;
    ImageView productImg;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mapping();

        Product p = (Product) getIntent().getSerializableExtra("product");
//        Log.d("product in detil", "" + p.toString());
        Log.d("check", "" + String.valueOf(DB.checkQuantity(p.getId())));


        Picasso.get().load(p.getImage()).into(productImg);
        tv_name.setText(p.getName());
        tv_price.setText(String.valueOf(p.getPrice()));
        tv_description.setText(p.getDescription());
        tv_category.setText(p.getCategory());
        //   tv_quantity.setText(String.valueOf(DB.checkQuantity(p.getId())));


        tv_quantity.setText(String.valueOf(DB.checkQuantity(p.getId())));


        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quan = DB.checkQuantity(p.getId());
                if (quan > 0)
                    DB.addOneMoreProduct(p.getId());
                else
                    DB.addProduct(p);
                tv_quantity.setText(String.valueOf(DB.checkQuantity(p.getId())));
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.super.onBackPressed();
            }
        });
    }

    public void mapping() {
        tv_name = findViewById(R.id.tv_detail_name);
        tv_category = findViewById(R.id.tv_detail_category);
        tv_description = findViewById(R.id.tv_detail_description);
        tv_price = findViewById(R.id.tv_detail_price);
        tv_quantity = findViewById(R.id.tv_detail_quantity);
        DB = new CartManager(getApplicationContext());
        btn_addToCart = findViewById(R.id.btn_detail_add_product);
        productImg = findViewById(R.id.iv_detail_image);
        btn_return = findViewById(R.id.btn_detail_return);
    }
}