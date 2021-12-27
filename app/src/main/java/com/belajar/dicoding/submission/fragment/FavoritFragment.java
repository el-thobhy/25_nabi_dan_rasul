package com.belajar.dicoding.submission.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.belajar.dicoding.submission.DataNabiRasul;
import com.belajar.dicoding.submission.R;
import com.belajar.dicoding.submission.adapter.FavAdapter;
import com.belajar.dicoding.submission.adapter.ListNabiRasulAdapter;
import com.belajar.dicoding.submission.db.FavDB;
import com.belajar.dicoding.submission.model.NabiRasulItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritFragment extends Fragment implements FavAdapter.onItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FavDB favDB;
    private ArrayList<NabiRasulItem> favItemList=new ArrayList<>();
    private FavAdapter favAdapter;
    private RecyclerView rvNabiRasul;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritFragment newInstance(String param1, String param2) {
        FavoritFragment fragment = new FavoritFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favorit, container, false);
        favDB=new FavDB(getActivity());
        rvNabiRasul=view.findViewById(R.id.rv_prohet);
        rvNabiRasul.setHasFixedSize(true);

        //favItemList.addAll(DataNabiRasul.getListData());
        rvNabiRasul.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvNabiRasul.setAdapter(new FavAdapter(view.getContext(), favItemList,this));

        loadData();


        return view;
    }

    private void loadData() {
        if(favItemList!=null){
            favItemList.clear();
        }else {
            Toast.makeText(getContext(),"Belum Ada Favorite",Toast.LENGTH_LONG).show();
        }
        SQLiteDatabase db=favDB.getReadableDatabase();
        Cursor cursor=favDB.select_all_favorite_list();
        try{
            while (cursor.moveToNext()){
                @SuppressLint("Range") String title=cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                @SuppressLint("Range") String detail=cursor.getString(cursor.getColumnIndex(FavDB.ITEM_DETAIL));
                @SuppressLint("Range") int image=Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));
                @SuppressLint("Range") String umur=cursor.getString(cursor.getColumnIndex(FavDB.ITEM_UMUR));
                @SuppressLint("Range") String umat=cursor.getString(cursor.getColumnIndex(FavDB.ITEM_UMAT));
                @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                NabiRasulItem favItem=new NabiRasulItem(title,detail,image,umur,umat,id,null);
                favItemList.add(favItem);
            }
        }finally {
            if (cursor!=null&&cursor.isClosed()){
                cursor.close();
                db.close();
            }
        }
        favAdapter=new FavAdapter(getActivity(),favItemList,this);
        rvNabiRasul.setAdapter(favAdapter);
    }

    @Override
    public void onItemClicked(NabiRasulItem data) {
        Fragment fragment=  DetailFragment.newInstance(data.getName(),data.getDetail(), data.getPhoto(), data.getUmur(), data.getUmat());

        FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}