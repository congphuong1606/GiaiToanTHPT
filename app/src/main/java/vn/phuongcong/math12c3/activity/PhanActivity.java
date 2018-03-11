package vn.phuongcong.math12c3.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.kobakei.ratethisapp.RateThisApp;

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
    private AdView adViewa;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan);
        ButterKnife.bind(this);
        adViewa = findViewById(R.id.av_ff);
        MobileAds.initialize(this, getResources().getString(R.string.appId));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewa.loadAd(adRequest);
        pref = getSharedPreferences("RATE", Context.MODE_PRIVATE);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(pref.getInt("rate",0)!=1) {
            getMenuInflater().inflate(R.menu.menu, menu);

            final MenuItem btnRate = menu.findItem(R.id.action_rate);

            if (btnRate != null) {
                btnRate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
//
                        if(pref.getInt("rate",0)!=1) {
                            showDialogRate(R.style.MyAlertDialogStyle);

                        }
                        return true;
                    }
                });
            }
        }
        return true;
    }

    private void showDialogRate(int myAlertDialogStyle) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, myAlertDialogStyle);
        builder.setTitle("Đánh giá ");
        builder.setMessage("Nếu bạn thích ứng dụng này, thì hãy cho tôi 5 sao lấy động lực. Chỉ mất một vài phút thôi nhé. Cám ơn bạn rất nhiều !");
        builder.setCancelable(true);
        Dialog dialog= builder.create();
        builder.setPositiveButton("Cho 5 *",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String appPackage = PhanActivity.this.getPackageName();
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse("https://play.google.com/store/apps/details?id=" + appPackage));
                PhanActivity.this.startActivity(intent);
                pref.edit().putInt("rate",1).apply();
            }
        });
        builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pref.edit().clear();
                dialog.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        builder.show();
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
