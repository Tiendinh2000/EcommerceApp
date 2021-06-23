package hanu.a2_1801040189.data.dbs;

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

    public List<Product> getProductsListInCart() {
        String sql = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(sql, null);
        CartCursorWrapper cursorWrapper = new CartCursorWrapper(cursor);
        return cursorWrapper.getProducts();
    }

    public Product getProduct(int id) {
        String sql = "SELECT * FROM cart WHERE id=" + id;
        Cursor cursor = db.rawQuery(sql, null);
        CartCursorWrapper wrapper = new CartCursorWrapper(cursor);
        return wrapper.getProduct();
    }

    public Boolean addProduct(Product p) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSchema.CartTable.Cols.ID, p.getId());
        contentValues.put(DBSchema.CartTable.Cols.NAME, p.getName());
        contentValues.put(DBSchema.CartTable.Cols.THUMB, p.getImage());
        contentValues.put(DBSchema.CartTable.Cols.PRICE, p.getPrice());
        contentValues.put(DBSchema.CartTable.Cols.QUAN, 1);
        long result = db.insert(DBSchema.CartTable.TABLENAME, null, contentValues);
        if (result == 1)
            return false;
        else
            return true;
    }

//    public Boolean AddProductQuantity(Product p, int quantity) {
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DBSchema.CartTable.Cols.NAME, p.getName());
//        contentValues.put(DBSchema.CartTable.Cols.THUMB, p.getImage());
//        contentValues.put(DBSchema.CartTable.Cols.PRICE, p.getPrice());
//        contentValues.put(DBSchema.CartTable.Cols.QUAN, quantity + 1);
//        long result = db.update(DBSchema.CartTable.TABLENAME, contentValues, "id=?", new String[]{String.valueOf(p.getId())});
//        if (result == 1)
//            return false;
//        else
//            return true;
//    }
//
//    public Boolean RemoveProductQuantity(Product p, int quantity) {
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DBSchema.CartTable.Cols.NAME, p.getName());
//        contentValues.put(DBSchema.CartTable.Cols.THUMB, p.getImage());
//        contentValues.put(DBSchema.CartTable.Cols.PRICE, p.getPrice());
//        contentValues.put(DBSchema.CartTable.Cols.QUAN, quantity - 1);
//        long result = db.update(DBSchema.CartTable.TABLENAME, contentValues, "id=?", new String[]{String.valueOf(p.getId())});
//        //delete when quantity=0
//
//        ResetDeleteProduct();
//        if (result == 1)
//            return false;
//        else
//            return true;
//    }

    public boolean ResetDeleteProduct() {
        try{
            db.delete(DBSchema.CartTable.TABLENAME, "quantity=?", new String[]{String.valueOf(0)});
            return  true;
        }catch (Exception e){
            return false;
        }

    }

    public Cursor getData() {
        Cursor cursor = db.rawQuery("Select * from cart", null);
        return cursor;
    }

    public int checkQuantity(int id) {
        String sql = "Select * FROM cart WHERE id=" + id;

        try {
            Product p = getProduct(id);
            return p.getQuantity();
//            Cursor cursor = db.rawQuery(sql, null);
//            CartCursorWrapper wrapper = new CartCursorWrapper(cursor);
//            Product p = wrapper.getProduct();
//           return p.getQuantity();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public boolean addOneMoreProduct(int id){
        int addedOne= checkQuantity(id)+1;
        try{
            db.execSQL("UPDATE cart SET "+DBSchema.CartTable.Cols.QUAN+"="+addedOne+ " WHERE id="+id);
            return true;
        }catch (Exception e){
            return false;
        }


    }

public boolean removeOneProduct(int id){
        ResetDeleteProduct();
        int removedOne = checkQuantity(id)-1;
        try{
            db.execSQL("UPDATE cart SET "+DBSchema.CartTable.Cols.QUAN+"="+removedOne+ " WHERE id="+id);
            return true;
        }catch (Exception e){
            return false;
        }

}

}
