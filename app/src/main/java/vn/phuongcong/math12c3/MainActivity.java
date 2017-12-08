package vn.phuongcong.math12c3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "aaaaaa.sqlite";
    public static final String SQL = "select * from image where imageID = ";
    private SQLiteDatabase database;
    ImageView imageView;
    byte[] imageByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.product_image);
        readData();
        Bitmap bm = BitmapFactory.decodeByteArray(imageByte, 0 ,imageByte.length);
        imageView.setImageBitmap(bm);
    }
    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery(SQL + 1111 , null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    imageByte = cursor.getBlob(1);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }
}
