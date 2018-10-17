package com.vdroid.tyc.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vdroid.tyc.R;
import com.vdroid.tyc.database.entities.CourseEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public interface OnCourseClickListener {
        void onClick(CourseEntity courseEntity);
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_course_name)
        TextView tvCourseName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(CourseEntity course, OnCourseClickListener onClickListener) {

            if (onClickListener != null) {
                tvCourseName.setOnClickListener(view -> {
                    onClickListener.onClick(course);
                });
            }
            tvCourseName.setText(course.getName());
        }
    }

    private List<CourseEntity> mCourses;
    private OnCourseClickListener mOnClickListener;

    public CourseAdapter(OnCourseClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_course_item, viewGroup, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int i) {
        if (mCourses == null) {
            return;
        }

        courseViewHolder.bind(mCourses.get(i), mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (mCourses == null) {
            return 0;
        }
        return mCourses.size();
    }

    public void setData(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
}
