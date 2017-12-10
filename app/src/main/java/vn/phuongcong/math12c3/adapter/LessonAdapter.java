package vn.phuongcong.math12c3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.phuongcong.math12c3.R;
import vn.phuongcong.math12c3.data.Lesson;

/**
 * Created by congp on 12/9/2017.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.Holder> {
    ArrayList<Lesson> lessons;
    int flag;



    private Context context;
    LessonAdapter.OnEventClick listener;

    public LessonAdapter(ArrayList<Lesson> lessons) {
        this.lessons = lessons;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.
                getContext()).inflate(R.layout.item_lesson, parent, false);
        context = v.getContext();
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Lesson l = lessons.get(position);
       holder.tvNameLesson.setText(l.getLesonName());
       holder.tvSttLesson.setText(String.valueOf(position+1));
       holder.tvNameLesson.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.onLessonClick(l);
           }
       });

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void setEvents(LessonAdapter.OnEventClick events) {
        this.listener = events;
    }



    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_stt_lesson)
        TextView tvSttLesson;
        @BindView(R.id.tv_name_lesson)
        TextView tvNameLesson;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnEventClick {
        void onLessonClick(Lesson id);
    }
}
