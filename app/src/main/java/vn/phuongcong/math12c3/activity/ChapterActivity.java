package vn.phuongcong.math12c3.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vn.phuongcong.math12c3.KeyboardUtils;
import vn.phuongcong.math12c3.R;
import vn.phuongcong.math12c3.adapter.ChapterAdapter;
import vn.phuongcong.math12c3.adapter.LessonAdapter;
import vn.phuongcong.math12c3.data.Chapter;
import vn.phuongcong.math12c3.data.Database;
import vn.phuongcong.math12c3.data.Lesson;

public class ChapterActivity extends AppCompatActivity implements ChapterAdapter.OnItemChapterClick, LessonAdapter.OnEventClick {
    public static final String DATABASE_NAME = "aaaaaa.sqlite";
    public static final String SQL = "select * from chapter where class = ";
    public static final String SQLLESSON = "select * from lesson where chapterID = ";
    @BindView(R.id.btn_back_chapter)
    Button btnBackChapter;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.btn_seach)
    Button btnSeach;
    @BindView(R.id.civ_title_chapter)
    CircleImageView civTitleChapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        chapters = new ArrayList<>();
        lessons = new ArrayList<>();
        hocPhan = getHocPhan();
        readChapter();
        setRcvLesson();
        setRcvChapter();


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
                    chapters.add(new Chapter(id, name,index));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    private int getHocPhan() {
        int i = 0;
        switch (getIntent().getStringExtra("gt11")) {
            case "gt11":
                i = 11;
                break;
            case "hh11":
                i = 111;
                break;
        }
        return i;
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
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", l);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @OnClick({R.id.btn_back_chapter, R.id.edt_search, R.id.btn_seach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_chapter:
                finish();
                KeyboardUtils.hideKeyboard(this,edtSearch);
                break;
            case R.id.edt_search:
                break;
            case R.id.btn_seach:
                edtSearch.clearFocus();
                edtSearch.setText("");
                KeyboardUtils.hideKeyboard(this,edtSearch);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        KeyboardUtils.hideKeyboard(this,edtSearch);
    }
}
