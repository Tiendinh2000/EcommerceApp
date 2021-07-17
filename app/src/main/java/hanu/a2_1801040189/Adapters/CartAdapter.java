package hanu.a2_1801040189.Adapters;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import hanu.a2_1801040189.Activity.CheckOutActivity;
import hanu.a2_1801040189.Activity.ProductListActivity;
import hanu.a2_1801040189.R;
import hanu.a2_1801040189.data.dbs.CartManager;
import hanu.a2_1801040189.models.Product;
import hanu.a2_1801040189.models.User;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private List<Product> list;
    FirebaseAuth auth;

    public CartAdapter(List list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.a_product_in_cart, parent, false);

        return new CartHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Product p = list.get(position);
        holder.bind(p);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private CartManager DB;
        Context context;
        TextView name, price, totalPrice, quantity;
        ImageView image;
        ImageButton addQuantity, removeQuantity;
        Button btn_buyNow;


        public CartHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            DB = new CartManager(context);
            addQuantity = itemView.findViewById(R.id.btn_addQuantity);
            removeQuantity = itemView.findViewById(R.id.btn_removeQuantity);
            name = itemView.findViewById(R.id.name_productInCart);
            price = itemView.findViewById(R.id.price_productInCart);
            totalPrice = itemView.findViewById(R.id.totalPrice_productInCart);
            quantity = itemView.findViewById(R.id.tv_quantity);
            image = itemView.findViewById(R.id.image_productInCart);
            btn_buyNow = itemView.findViewById(R.id.btn_buyNow);
            auth = FirebaseAuth.getInstance();
        }


        public void bind(Product p) {
            //price of a specific product
            int pr = (int) p.getPrice();
            //total cost for a product
            int totalPr = pr * p.getQuantity();

            name.setText(p.getName());
            price.setText(" $ " + String.valueOf(pr));
            totalPrice.setText(" $ " + String.valueOf(totalPr));
            quantity.setText(String.valueOf(p.getQuantity()));
            Picasso.get().load(p.getImage()).into(image);


            addQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuan = DB.checkQuantity(p.getId());
                    int currenTotalPrice = getCurrentTotalPrice();
                    boolean add = DB.addOneMoreProduct(p.getId());
                    quantity.setText(String.valueOf(currentQuan + 1));
                    int newTotal = currenTotalPrice + pr;
                    totalPrice.setText(" $ " + String.valueOf(newTotal));
                    String inf = String.valueOf(pr);
                    sendLocalData("addPrice", inf);
                }


            });
            removeQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentQuan = DB.checkQuantity(p.getId());
                    int currenTotalPrice = getCurrentTotalPrice();

                    if (currentQuan == 0) {
                        alertDialog();
                    } else {
                        boolean minus = DB.removeOneProduct(p.getId());
                        quantity.setText(String.valueOf(currentQuan - 1));
                        totalPrice.setText(" $ " + String.valueOf(currenTotalPrice - pr));
                        String information = String.valueOf(pr);
                        sendLocalData("MinusPrice", information);

                    }
                }
            });

            btn_buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userName = auth.getCurrentUser().getEmail();
                    String userId = auth.getCurrentUser().getUid();
                    Log.d("", "user: " + userName + " buy :" + p.getName() + "," + p.getId() + "" + p.getQuantity() + "," + p.getImage() + "," + p.getPrice() + "," + p.getDescription());
                    Intent i = new Intent(context, CheckOutActivity.class);
                    i.putExtra("userName", new User(userId, userName));
                    i.putExtra("product", new Product(p.getId(), p.getName(), p.getImage(), pr, p.getQuantity()));
                    context.startActivity(i);
                }
            });

        }

        /**
         * @return total current quantity of A product has been added
         */
        private int getCurrentQuantity() {
            int currentQuantity = Integer.parseInt(quantity.getText().toString());
            return currentQuantity;
        }

        /**
         * @return total current price of A product
         */
        private int getCurrentTotalPrice() {
            String get = totalPrice.getText().toString();
            int currentQuantity = Integer.parseInt(get.substring(3, get.length()));
            return currentQuantity;
        }

        private void alertDialog() {
            new AlertDialog.Builder(context)
                    .setTitle("Alert!")
                    .setMessage("Can be smaller than 0")
                    .setNeutralButton("OK", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        /**
         * BroadcastReceiver : used to transit message from Adapter. The message is increase or decrease totalPrice
         *
         * @param name  : name of message
         * @param value : value need to be transited
         */

        private void sendLocalData(String name, String value) {
            Intent intent = new Intent("custom-message");
            intent.putExtra(name, value);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

    }


}
