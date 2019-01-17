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
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> {

    public interface OnItemClickListener {
        void onDogItemClicked(DogModel dogModel);
    }

    private List<DogModel> dogsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    DogsAdapter(Context context) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dogsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return dogsCollection != null ? dogsCollection.size() : 0;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.row_dog, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        final DogModel dogModel = dogsCollection.get(position);
        holder.textViewTitle.setText(dogModel.getTitle());
        holder.iv_cover.setImageUrl(dogModel.getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DogsAdapter.this.onItemClickListener != null) {
                    DogsAdapter.this.onItemClickListener.onDogItemClicked(dogModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDogsCollection(Collection<DogModel> dogsCollection) {
        validateDogsCollection(dogsCollection);
        this.dogsCollection = (List<DogModel>)dogsCollection;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateDogsCollection(Collection<DogModel> dogsCollections) {
        Objects.requireNonNull(dogsCollections);
    }

    static class DogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView textViewTitle;
        @BindView(R.id.iv_cover) AutoLoadImageView iv_cover;

        DogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
