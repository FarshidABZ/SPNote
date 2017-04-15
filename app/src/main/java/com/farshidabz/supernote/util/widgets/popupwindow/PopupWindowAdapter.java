package com.farshidabz.supernote.util.widgets.popupwindow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.farshidabz.supernote.R;
import com.farshidabz.supernote.model.PopupModel;

import java.util.ArrayList;

/**
 * Created by FarshidAbz.
 * Since 4/15/2017.
 */

public class PopupWindowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PopupModel> inputList;
    private OnPopupItemClickListener onPopupItemClickListener;

    public PopupWindowAdapter() {
    }

    public void setInputAdapter(ArrayList<PopupModel> inputList) {
        this.inputList = inputList;
    }

    public void setOnClickListener(OnPopupItemClickListener onPopupItemClickListener) {
        this.onPopupItemClickListener = onPopupItemClickListener;
    }

    public void removeAll() {
        if (inputList == null)
            return;

        inputList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_popupwindow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvPopupTitle.setText(inputList.get(position).getTitle());

        if (inputList.get(position).getIconResId() != 0) {
            viewHolder.imgPopupIcon.setImageResource(inputList.get(position).getIconResId());
        } else {
            viewHolder.imgPopupIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (inputList == null) {
            return 0;
        }
        return inputList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPopupTitle;
        ImageView imgPopupIcon;

        ViewHolder(View itemView) {
            super(itemView);
            tvPopupTitle = (TextView) itemView.findViewById(R.id.tvPopupTitle);
            imgPopupIcon = (ImageView) itemView.findViewById(R.id.imgPopupIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onPopupItemClickListener != null) {
                onPopupItemClickListener.onItemClickListener(getLayoutPosition(), inputList.get(getLayoutPosition()));
            }
        }
    }
}
