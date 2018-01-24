package vn.phuongcong.math12c3.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.artifex.mupdf.viewer.DocumentActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rw.keyboardlistener.KeyboardUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.phuongcong.math12c3.KeyboardUtil;
import vn.phuongcong.math12c3.R;
import vn.phuongcong.math12c3.adapter.ChapterAdapter;
import vn.phuongcong.math12c3.adapter.LessonAdapter;
import vn.phuongcong.math12c3.data.Chapter;
import vn.phuongcong.math12c3.data.Database;
import vn.phuongcong.math12c3.data.Lesson;
import vn.phuongcong.math12c3.data.MuPdfData;

public class ChapterActivity extends AppCompatActivity implements ChapterAdapter.OnItemChapterClick, LessonAdapter.OnEventClick {
    public static final String DATABASE_NAME = "giatoan.sqlite";
    public static final String SQL = "select * from chapter where class = ";
    public static final String SQLLESSON = "select * from lesson where chapterID = ";
    @BindView(R.id.btn_back_chapter)
    Button btnBackChapter;
    @BindView(R.id.btn_close_search)
    ImageButton btnCloseSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.btn_seach)
    ImageButton btnSeach;
    @BindView(R.id.civ_title_chapter)
    CircleImageView civTitleChapter;
    @BindView(R.id.tv_chapter_title)
    TextView tvChapterTitle;
    private SQLiteDatabase database;
    @BindView(R.id.rcv_chapter)
    RecyclerView rcvChapter;
    @BindView(R.id.tv_chapter_name)
    TextView tvChapterName;
    @BindView(R.id.rcv_lesson)
    RecyclerView rcvLesson;
    private int hocPhan;
    ArrayList<Chapter> chapters;
    private ChapterAdapter chapterAdapter;
    private LessonAdapter lessonAdapter;

    private Integer flag;
    private ArrayList<Lesson> lessons;
    private String title;
    private AdView adViewa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        getSupportActionBar().hide();

        ButterKnife.bind(this);
        chapters = new ArrayList<>();
        lessons = new ArrayList<>();
        hocPhan = getIntent().getIntExtra("phan", 0);
        title = getIntent().getStringExtra("title");
        tvChapterTitle.setText(title);
        readChapter();
        setRcvLesson();
        setRcvChapter();
        readLesson(hocPhan + 1);
        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (isVisible) {
                    tvChapterTitle.setVisibility(View.GONE);
                    btnCloseSearch.setVisibility(View.VISIBLE);
                    edtSearch.setBackground(getResources().getDrawable(R.drawable.edtb));
                } else {
                    edtSearch.setBackground(getResources().getDrawable(R.drawable.edt));
                    btnCloseSearch.setVisibility(View.GONE);
                    tvChapterTitle.setVisibility(View.VISIBLE);
                    edtSearch.clearFocus();
                    edtSearch.setText("");

                }
//                Toast.makeText(ChapterActivity.this, "keyboard visible: "+isVisible,Toast.LENGTH_LONG).show();
            }
        });
        adViewa = findViewById(R.id.adViewa);
        MobileAds.initialize(this, getResources().getString(R.string.appId));
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewa.loadAd(adRequest);

    }

    private void setRcvLesson() {
        rcvLesson.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        lessonAdapter = new LessonAdapter(lessons);
        lessonAdapter.setEvents(this);
        rcvLesson.setAdapter(lessonAdapter);
    }

    private void setRcvChapter() {
        rcvChapter.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        flag = chapters.get(0).getChapterID();
        chapterAdapter = new ChapterAdapter(chapters);
        chapterAdapter.setChapterChossed(flag);
        chapterAdapter.setEvents(this);
        rcvChapter.setAdapter(chapterAdapter);
    }

    private void readChapter() {
        chapters.clear();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery(SQL + hocPhan, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String index = cursor.getString(3);
                    chapters.add(new Chapter(id, name, index));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }


    @Override
    public void onClick(int id) {
        flag = id;
        chapterAdapter.setChapterChossed(flag);
        chapterAdapter.notifyDataSetChanged();
    }

    @Override
    public void setChapterName(Chapter c) {
        lessons.clear();
        tvChapterName.setText(c.getChapterName());
        readLesson(c.getChapterID());
    }

    private void readLesson(Integer chapterID) {

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery(SQLLESSON + chapterID, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    byte[] giai = cursor.getBlob(3);
                    byte[] noidung = cursor.getBlob(4);
                    lessons.add(new Lesson(id, name, giai, noidung));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        lessonAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLessonClick(Lesson l) {
        Uri uri = MuPdfData.getUri(l.getId() + "");
        if (uri != null) {
            Intent intent = new Intent(this, DocumentActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.putExtra("lessonName", l.getLesonName());
            startActivity(intent);
        } else {
            Toast.makeText(this, "file chưa được cập nhật", Toast.LENGTH_LONG).show();
        }

    }

    @OnClick({R.id.btn_back_chapter, R.id.edt_search, R.id.btn_close_search, R.id.btn_seach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_chapter:
                finish();
                KeyboardUtil.hideKeyboard(this, edtSearch);
                break;
            case R.id.edt_search:
                break;
            case R.id.btn_close_search:
                KeyboardUtil.hideKeyboard(this, edtSearch);
                break;
            case R.id.btn_seach:
                search();
                KeyboardUtil.hideKeyboard(this, edtSearch);
                break;
        }
    }

    private void search() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyboardUtil.hideKeyboard(this, edtSearch);
    }
}
