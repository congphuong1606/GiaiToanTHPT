package vn.phuongcong.math12c3.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rw.keyboardlistener.KeyboardUtils;

import java.util.ArrayList;
import java.util.Locale;

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

    @BindView(R.id.tv_chapter_title)
    TextView tvChapterTitle;
    @BindView(R.id.btn_left_chapter)
    ImageView btnLeftChapter;
    @BindView(R.id.btn_right_chapter)
    ImageView btnRightChapter;
    @BindView(R.id.adViewa)
    AdView adViewa;
    @BindView(R.id.rcv_lesson_result)
    RecyclerView rcvLessonResult;
    @BindView(R.id.layoutResult)
    LinearLayout layoutResult;
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
    private ArrayList<Lesson> lessonResults;
    private String title;
    private LessonAdapter lessonAdapter2;
    private ArrayList<Lesson> allLessons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        ButterKnife.bind(this);
        chapters = new ArrayList<>();
        lessons = new ArrayList<>();
        lessonResults = new ArrayList<>();
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
//        adViewa.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                adViewa.setVisibility(View.VISIBLE);// Set tag true if adView is loaded
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                super.onAdFailedToLoad(i);
//                adViewa.setVisibility(View.GONE); // Set tag false if loading failed
//            }
//        });
        reaAllLesson();
        resetFlag();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text1, int start, int before, int count) {
                String text = text1.toString().trim();
                text = text.toLowerCase(Locale.getDefault());
                lessonResults.clear();
                if (text.length() == 0) {
                    layoutResult.setVisibility(View.GONE);
                } else {
                    for (Lesson l : allLessons) {
                        if (l.getLesonName().toLowerCase(Locale.getDefault()).contains(text)) {
                            lessonResults.add(l);
                        }
                    }
                }
                if (lessonResults.isEmpty()) {
                    layoutResult.setVisibility(View.GONE);
                } else {
                    layoutResult.setVisibility(View.VISIBLE);
                }
                lessonAdapter2.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


    }

    private void setRcvLesson() {
        rcvLesson.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rcvLessonResult.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        lessonAdapter = new LessonAdapter(lessons);
        lessonAdapter2 = new LessonAdapter(lessonResults);
        lessonAdapter.setEvents(this);
        lessonAdapter2.setEvents(this);
        rcvLesson.setAdapter(lessonAdapter);
        rcvLessonResult.setAdapter(lessonAdapter2);
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
        resetFlag();

        chapterAdapter.setChapterChossed(flag);
        chapterAdapter.notifyDataSetChanged();
    }

    private void resetFlag() {
        if (flag.equals(chapters.get(0).getChapterID())) {
            btnLeftChapter.setVisibility(View.GONE);
        } else {
            btnLeftChapter.setVisibility(View.VISIBLE);
        }
        if (flag.equals(chapters.get(chapters.size() - 1).getChapterID())) {
            btnRightChapter.setVisibility(View.GONE);
        } else {
            btnRightChapter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setChapterName(Chapter c) {
        lessons.clear();
        tvChapterName.setText(c.getChapterName());
        readLesson(c.getChapterID());
    }

    private void reaAllLesson() {

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from lesson", null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    byte[] giai = cursor.getBlob(3);
                    byte[] noidung = cursor.getBlob(4);
                    allLessons.add(new Lesson(id, name, giai, noidung));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }


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
            Intent intent = new Intent(this, Document2Activity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.putExtra("lessonName", l.getLesonName());
            startActivity(intent);
        } else {
            Toast.makeText(this, "file chưa được cập nhật", Toast.LENGTH_LONG).show();
        }

    }

    @OnClick({R.id.btn_back_chapter, R.id.edt_search, R.id.btn_left_chapter, R.id.btn_right_chapter, R.id.btn_close_search, R.id.btn_seach})
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
            case R.id.btn_left_chapter:
                onClick(flag - 1);
                break;
            case R.id.btn_right_chapter:
                onClick(flag + 1);
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
