package vn.phuongcong.math12c3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.phuongcong.math12c3.R;

public class PhanActivity extends AppCompatActivity {

    @BindView(R.id.civ_g11)
    CircleImageView civG11;
    @BindView(R.id.civ_g10)
    CircleImageView civG10;
    @BindView(R.id.civ_g12)
    CircleImageView civG12;
    @BindView(R.id.civ_h10)
    CircleImageView civH10;
    @BindView(R.id.civ_h11)
    CircleImageView civH11;
    @BindView(R.id.civ_h12)
    CircleImageView civH12;
    @BindView(R.id.av_detail)
    AdView avDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.civ_g11)
    public void onViewClicked() {



    }

    @OnClick({R.id.civ_g10,R.id.civ_g11, R.id.civ_g12, R.id.civ_h10, R.id.civ_h11, R.id.civ_h12})
    public void onViewClicked(View view) {
        Intent i = new Intent(this, ChapterActivity.class);
        switch (view.getId()) {
            case R.id.civ_g10:
                i.putExtra("phan", 110);
                i.putExtra("title", "ĐSố && GTích 10");
                startActivity(i);
                break;
            case R.id.civ_g11:
                i.putExtra("phan", 111);
                i.putExtra("title", "ĐSố && GTích 11");
                startActivity(i);
                break;
            case R.id.civ_g12:
                i.putExtra("phan", 112);
                i.putExtra("title", "ĐSố && GTích 12");
                startActivity(i);
                break;
            case R.id.civ_h10:
                i.putExtra("phan", 210);
                i.putExtra("title", "Hình học 10");
                startActivity(i);
                break;
            case R.id.civ_h11:
                i.putExtra("phan", 211);
                i.putExtra("title", "Hình học 11");
                startActivity(i);
                break;
            case R.id.civ_h12:
                i.putExtra("phan", 212);
                i.putExtra("title", "Hình học 12");
                startActivity(i);
                break;
        }
    }
}
