package hanu.a2_1801040189.dbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import hanu.a2_1801040189.models.Product;

public class CartManager {

    // singleton
    private static CartManager instance;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public CartManager(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public static CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }

        return instance;
    }

public List<Product> getProductsListInCart(){
        String sql ="SELECT * FROM cart";
    Cursor cursor = db.rawQuery(sql, null);
    CartCursorWrapper cursorWrapper = new CartCursorWrapper(cursor);
    return cursorWrapper.getProducts();
}
    public Boolean addProduct(Product p) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSchema.CartTable.Cols.ID, p.getId());
        contentValues.put(DBSchema.CartTable.Cols.NAME , p.getName());
        contentValues.put(DBSchema.CartTable.Cols.THUMB, p.getImage());
        contentValues.put(DBSchema.CartTable.Cols.PRICE, p.getPrice());
        contentValues.put(DBSchema.CartTable.Cols.QUAN , 1);
        long result = db.insert(DBSchema.CartTable.TABLENAME, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;
    }

    public Boolean AddProductQuantity(Product p, int quantity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSchema.CartTable.Cols.NAME , p.getName());
        contentValues.put(DBSchema.CartTable.Cols.THUMB, p.getImage());
        contentValues.put(DBSchema.CartTable.Cols.PRICE, p.getPrice());
        contentValues.put(DBSchema.CartTable.Cols.QUAN , quantity+1);
        long result = db.update(DBSchema.CartTable.TABLENAME, contentValues, "id=?", new String[]{String.valueOf(p.getId())});
        if (result == 1)
            return false;
        else
            return true;
    }

    public Boolean RemoveProductQuantity(Product p, int quantity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSchema.CartTable.Cols.NAME , p.getName());
        contentValues.put(DBSchema.CartTable.Cols.THUMB, p.getImage());
        contentValues.put(DBSchema.CartTable.Cols.PRICE, p.getPrice());
        contentValues.put(DBSchema.CartTable.Cols.QUAN , quantity-1);
        long result = db.update(DBSchema.CartTable.TABLENAME, contentValues, "id=?", new String[]{String.valueOf(p.getId())});
        //delete when quantity=1
        ResetDeleteProduct();
        if (result == 1)
            return false;
        else
            return true;
    }

    public void ResetDeleteProduct(){
        db.delete(DBSchema.CartTable.TABLENAME,"quantity=?",new String[]{String.valueOf(0)});
    }

    public Cursor getData() {
        Cursor cursor = db.rawQuery("Select * from cart", null);
        return cursor;
    }
}
