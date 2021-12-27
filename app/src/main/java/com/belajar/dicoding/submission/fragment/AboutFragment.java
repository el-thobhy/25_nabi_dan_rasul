package com.belajar.dicoding.submission.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.belajar.dicoding.submission.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout layer_ig;
    LinearLayout layer_github;
    LinearLayout layer_fb;
    LinearLayout layer_linkedin;
    private Context liContext=null;

    String urlFb="https://www.facebook.com/abeng.malalak.7";
    String urlLn="https://www.linkedin.com/in/auriwan-yasper-005628145";
    String appIns="http://instagram.com/el_thobhy";
    String urlGit="https://github.com/auriwan";
    ImageView image;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
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
        View view=inflater.inflate(R.layout.fragment_about, container, false);
        liContext=this.getActivity();

        image=(ImageView) view.findViewById(R.id.image);
        layer_fb=(LinearLayout) view.findViewById(R.id.facebook);
        layer_github=(LinearLayout) view.findViewById(R.id.github);
        layer_ig=(LinearLayout) view.findViewById(R.id.instagram);
        layer_linkedin=(LinearLayout) view.findViewById(R.id.linkedin);
        image.setOnClickListener((View.OnClickListener) this);
        layer_linkedin.setOnClickListener((View.OnClickListener) this);
        layer_github.setOnClickListener((View.OnClickListener) this);
        layer_fb.setOnClickListener((View.OnClickListener) this);
        layer_ig.setOnClickListener((View.OnClickListener) this);
        return view;
    }
    @Override
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.facebook:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlFb)));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.github:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlGit)));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.linkedin:
                try {
                    Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urlLn));
                    intent.setPackage("com.linkedin.android");
                    startActivity(intent);

                }catch (ActivityNotFoundException anfe){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlLn)));
                }break;
            case R.id.instagram:
                try {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(appIns));
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                }catch (ActivityNotFoundException anfe){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(appIns)));
                }break;
            case R.id.image:
                try{
                    Intent fullScreenImage=new Intent(getActivity(),FullScreenActivity.class);
                    startActivity(fullScreenImage);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
}