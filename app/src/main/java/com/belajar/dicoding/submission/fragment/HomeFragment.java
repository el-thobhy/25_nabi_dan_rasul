package com.belajar.dicoding.submission.fragment;

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
import com.belajar.dicoding.submission.adapter.ListNabiRasulAdapter;
import com.belajar.dicoding.submission.model.NabiRasulItem;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ListNabiRasulAdapter.onItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rvNabiRasul;
    private ArrayList<NabiRasulItem> list= new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        rvNabiRasul=view.findViewById(R.id.rv_prohet);
        rvNabiRasul.setHasFixedSize(true);

        list.addAll(DataNabiRasul.getListData());
        rvNabiRasul.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvNabiRasul.setAdapter(new ListNabiRasulAdapter(view.getContext(), list,this));
        return view;
    }


    @Override
    public void onItemClicked(NabiRasulItem data) {
        Fragment fragment=  DetailFragment.newInstance(data.getName(),data.getDetail(), data.getPhoto(), data.getUmur(), data.getUmat());

        FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();

        Fragment act = getActivity().getSupportFragmentManager().findFragmentByTag("main_fragment");
        //transaction.replace(R.id.body_container,fragment,"detail fragment");

            /*if(act==null){
                Toast.makeText(getContext(),"Tekan Sekali lagi",Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new HomeFragment(),"main_fragment").commit();
            }else {
                transaction.hide(act);
            }*/
        transaction.hide(act);
        transaction.add(R.id.body_container, fragment);
            transaction.addToBackStack(null);

            transaction.commit();



    }
}
