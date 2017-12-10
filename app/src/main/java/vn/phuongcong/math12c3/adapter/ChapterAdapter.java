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
import de.hdodenhof.circleimageview.CircleImageView;
import vn.phuongcong.math12c3.R;
import vn.phuongcong.math12c3.data.Chapter;

/**
 * Created by congp on 12/9/2017.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    ArrayList<Chapter> chapters;
    int flag;



    private Context context;
    OnItemChapterClick listener;

    public ChapterAdapter(ArrayList<Chapter> chapters) {
        this.chapters = chapters;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.
                getContext()).inflate(R.layout.item_chapter, parent, false);
        context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Chapter c = chapters.get(position);
        holder.tvChapter.setText(c.getChapterIndex());
        if(flag==c.getChapterID()){

//            holder.cimvLast.setVisibility(View.GONE);
            holder.civChapter.setVisibility(View.VISIBLE);
            listener.setChapterName(c);
        }else {
            holder.cimvLast.setVisibility(View.VISIBLE);
            holder.civChapter.setVisibility(View.GONE);
        }
        holder.tvChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(c.getChapterID());
            }
        });



    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public void setEvents(OnItemChapterClick events) {
        this.listener = events;
    }

    public void setChapterChossed(Integer flag) {
        this.flag=flag;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_chapter)
        CircleImageView civChapter;
        @BindView(R.id.cimv_last)
        CircleImageView cimvLast;
        @BindView(R.id.tv_chapter)
        TextView tvChapter;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemChapterClick {
        void onClick(int id);
        void setChapterName(Chapter c);
    }
}
