package hanu.a2_1801040189.Adapters;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hanu.a2_1801040189.R;
import hanu.a2_1801040189.dbs.CartCursorWrapper;
import hanu.a2_1801040189.dbs.CartManager;
import hanu.a2_1801040189.dbs.DBHelper;
import hanu.a2_1801040189.models.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductHolder> implements View.OnClickListener {

    private List<Product> list;

    public ProductListAdapter(List list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context c = parent.getContext();
        View view = LayoutInflater.from(c).inflate(R.layout.a_product_list, parent, false);

        ProductHolder vh = new ProductHolder(view, c);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product p = (Product) list.get(position);
        holder.bind(p);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class ProductHolder extends RecyclerView.ViewHolder {
        private CartManager DB;
        CartCursorWrapper cursor;

        ImageButton add;
        TextView name, category, price;
        ImageView thumb;
        Context context;

        public ProductHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            price = itemView.findViewById(R.id.product_price);
            name = itemView.findViewById(R.id.product_name);
            thumb = itemView.findViewById(R.id.product_thumb);
            add = itemView.findViewById(R.id.btn_add_product);
DB= new CartManager(context);
        }


        public void bind(Product p) {

            int pr = (int) p.getPrice();
            name.setText(p.getName());
            price.setText(" $ " + String.valueOf(pr));

            Picasso.get().load(p.getImage()).into(thumb);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkExisted(p.getId(), DB.getProductsListInCart()) != null) {

                        Product productInCart = checkExisted(p.getId(), DB.getProductsListInCart());
                        int currenQuantity = productInCart.getQuantity();
                        boolean edit = DB.AddProductQuantity(productInCart, currenQuantity);

                        new AlertDialog.Builder(context)
                                .setTitle("Add Successfully!")
                                .setMessage("Add 1 "+p.getName().substring(0,15)+"... more into your cart!")
                                .setNeutralButton("OK", null)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .show();
                    } else {

                        new AlertDialog.Builder(context)
                                .setTitle("Add to your Cart")
                                .setMessage(p.getName().substring(0,10)+"... is not existed in your Cart! \r\nAre you sure you want to add this product?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean edit = DB.addProduct(p);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }

                }
            });
        }

        private Product checkExisted(int id, List<Product> list) {
            for (Product p : list) {
                if (p.getId() == id)
                    return p;
            }
            return null;
        }

    }

}



