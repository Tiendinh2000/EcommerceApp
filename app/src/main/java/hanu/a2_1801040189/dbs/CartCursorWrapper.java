package hanu.a2_1801040189.dbs;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040189.models.Product;

public class CartCursorWrapper extends CursorWrapper {

    public CartCursorWrapper(Cursor cursor) {
        super(cursor);
        moveToFirst();
    }

    public Product getProduct() {
        if (isAfterLast()) {
            return null;
        }

        int id = getInt(getColumnIndex(DBSchema.CartTable.Cols.ID));
        String name = getString(getColumnIndex(DBSchema.CartTable.Cols.NAME));
        String thumb = getString(getColumnIndex(DBSchema.CartTable.Cols.THUMB));
        int price = getInt(getColumnIndex(DBSchema.CartTable.Cols.PRICE));
        int quan = getInt(getColumnIndex(DBSchema.CartTable.Cols.QUAN));
       Product p = new Product(id,name,thumb,price,quan);

        moveToNext();

        return p;
    }

    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();

        while (!isAfterLast()) {
            Product p = getProduct();
            list.add(p);
        }

        return list;
    }

}
