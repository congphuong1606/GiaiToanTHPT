package vn.phuongcong.math12c3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.phuongcong.math12c3.R;

public class PhanActivity extends AppCompatActivity {

    @BindView(R.id.civ_g11)
    CircleImageView civG11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.civ_g11)
    public void onViewClicked() {
        Intent i =new Intent(this,ChapterActivity.class);
        i.putExtra("gt11","gt11");
        startActivity(i);
    }
}
