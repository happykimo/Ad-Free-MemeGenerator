package org.tritonhacks.memegenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(MemeTemplate t);
    }

    private List<MemeTemplate> templates;
    private Context context;
    private OnItemClickListener listener;

    DataAdapter(Context context, List<MemeTemplate> templates, OnItemClickListener listener) {
        this.context = context;
        this.templates = templates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Glide.with(context).load(templates.get(i).getUrl()).into(viewHolder.img);
        viewHolder.bind(templates.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
        }

        void bind(MemeTemplate t, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(t));
        }
    }

}
