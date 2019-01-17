package com.suenara.exampleapp.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Objects;
import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatsAdapter extends RecyclerView.Adapter<CatsAdapter.CatViewHolder> {

    public interface OnItemClickListener {
        void onCatItemClicked(CatModel catModel);
    }

    private List<CatModel> catsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    CatsAdapter(Context context) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        catsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return catsCollection != null ? catsCollection.size() : 0;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.row_cat, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        final CatModel catModel = catsCollection.get(position);
        holder.title.setText(catModel.getTitle());
        holder.iv_cover.setImageUrl(catModel.getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CatsAdapter.this.onItemClickListener != null) {
                    CatsAdapter.this.onItemClickListener.onCatItemClicked(catModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCatsCollection(Collection<CatModel> catsCollection) {
        validateCatsCollection(catsCollection);
        this.catsCollection = (List<CatModel>)catsCollection;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateCatsCollection(Collection<CatModel> catsCollections) {
        Objects.requireNonNull(catsCollections);
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;
        @BindView(R.id.iv_cover) AutoLoadImageView iv_cover;

        CatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setClickable(true);
            itemView.setFocusable(true);
        }
    }
}
