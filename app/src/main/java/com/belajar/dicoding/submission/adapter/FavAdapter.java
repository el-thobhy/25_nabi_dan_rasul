package com.belajar.dicoding.submission.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.belajar.dicoding.submission.R;
import com.belajar.dicoding.submission.db.FavDB;
import com.belajar.dicoding.submission.model.NabiRasulItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NabiRasulItem> favItemList;
    private FavAdapter.onItemClickListener clickListener;
    private FavDB favDB;

    public FavAdapter(Context context, ArrayList<NabiRasulItem> favItemList, onItemClickListener clickListener) {
        this.context = context;
        this.favItemList = favItemList;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_fav,parent,false);
        favDB=new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NabiRasulItem prophet= favItemList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(prophet.getPhoto())
                .apply(new RequestOptions().override(55,55))
                .into(holder.image);
        holder.tvName.setText(prophet.getName());
        holder.tvDetail.setText(prophet.getDetail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(favItemList.get(position));
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, prophet.getDetail());
                intent.setType("text/palin");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tvName,tvDetail,likeCountTextView;
        Button btnShare;
        Button favBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnShare=itemView.findViewById(R.id.button_share);
            favBtn=itemView.findViewById(R.id.button_favorit);
            image=itemView.findViewById(R.id.image);
            tvName=itemView.findViewById(R.id.tv_item_name);
            tvDetail=itemView.findViewById(R.id.item_detail);
            //remove from list
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final NabiRasulItem favItem=favItemList.get(position);
                    favDB.remove_fav(favItem.getKey_id());
                    removeItem(position);
                }
            });
        }
    }

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,favItemList.size());

    }

    public interface onItemClickListener {
        void onItemClicked(NabiRasulItem data);
    }
}
