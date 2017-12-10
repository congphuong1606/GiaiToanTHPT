package vn.phuongcong.math12c3.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import vn.phuongcong.math12c3.R;
import vn.phuongcong.math12c3.data.Lesson;

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
        getSupportActionBar().hide();
        imageView=findViewById(R.id.product_image);
        readData();
        Bitmap bm = BitmapFactory.decodeByteArray(imageByte, 0 ,imageByte.length);
        imageView.setImageBitmap(bm);
    }
    private void readData() {
        Bundle bundle=getIntent().getExtras();
        Lesson lesson = (Lesson) bundle.getSerializable("lesson");
        imageByte=lesson.getLesonGiai();


    }
}
