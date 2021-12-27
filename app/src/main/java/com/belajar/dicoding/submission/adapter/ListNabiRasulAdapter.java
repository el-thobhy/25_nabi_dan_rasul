package com.belajar.dicoding.submission.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class ListNabiRasulAdapter extends RecyclerView.Adapter<ListNabiRasulAdapter.ListViewHolder> {

    private Context context;
    private ArrayList<NabiRasulItem> listNabiRasulItem;
    private onItemClickListener clickListener;
    private FavDB favDB;


    public ListNabiRasulAdapter(Context context, ArrayList<NabiRasulItem> list, onItemClickListener clickListener){
        this.context = context;
        this.listNabiRasulItem =list;
        this.clickListener=clickListener;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_prophet,parent,false);
        return new ListViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NabiRasulItem prophet= listNabiRasulItem.get(position);
        Glide.with(holder.itemView.getContext())
                .load(prophet.getPhoto())
                .apply(new RequestOptions().override(55,55))
                .into(holder.image);
        holder.tvName.setText(prophet.getName());
        holder.tvDetail.setText(prophet.getDetail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(listNabiRasulItem.get(position));
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
        readCursorData(prophet, holder);

    }



    @Override
    public int getItemCount() {
        return listNabiRasulItem.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tvName,tvDetail,likeCountTextView;
        Button btnShare;
        Button favBtn;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            btnShare=itemView.findViewById(R.id.button_share);
            favBtn=itemView.findViewById(R.id.button_favorit);
            image=itemView.findViewById(R.id.image);
            tvName=itemView.findViewById(R.id.tv_item_name);
            tvDetail=itemView.findViewById(R.id.item_detail);
            likeCountTextView = itemView.findViewById(R.id.likeCountTextView);
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    NabiRasulItem nabiItem = listNabiRasulItem.get(position);
                    if(nabiItem.getFavStatus().equals("0")){
                        nabiItem.setFavStatus("1");
                        favDB.insertDataToDatabase(nabiItem.getName(),nabiItem.getDetail(),nabiItem.getPhoto(),nabiItem.getUmur(),nabiItem.getUmat(),nabiItem.getKey_id(),nabiItem.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    }else {
                        nabiItem.setFavStatus("0");
                        favDB.remove_fav(nabiItem.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                    }
                }
            });

        }
    }
    public interface onItemClickListener{
        void onItemClicked(NabiRasulItem data);
    }
    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(NabiRasulItem NabiItem, ListViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(NabiItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                NabiItem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

}
