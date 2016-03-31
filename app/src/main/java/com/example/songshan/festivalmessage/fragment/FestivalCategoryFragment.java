package com.example.songshan.festivalmessage.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.example.songshan.festivalmessage.R;
import com.example.songshan.festivalmessage.activity.ChooseMsgActivity;
import com.example.songshan.festivalmessage.bean.Festival;
import com.example.songshan.festivalmessage.bean.FestivalLab;

import java.util.ArrayList;

/**
 * Created by songshan on 2016/3/28.
 */
public class FestivalCategoryFragment extends Fragment {
    public static final String ID_FESTIVAL="festival_id";
    private GridView mGrideView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_festival,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       mInflater=LayoutInflater.from(getActivity());
        mGrideView= (GridView) view.findViewById(R.id.gridView);
        mGrideView.setAdapter(mAdapter=new ArrayAdapter<Festival>(getActivity(),-1,FestivalLab.getmInstance().getFestival()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=mInflater.inflate(R.layout.item_festival,parent,false);
                }
               TextView tv= (TextView) convertView.findViewById(R.id.item_festival);
                tv.setText(getItem(position).getName());
                return convertView;
            }
        });
        mGrideView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //// TODO: 2016/3/28
                Intent intent=new Intent(getActivity(), ChooseMsgActivity.class);
                intent.putExtra(ID_FESTIVAL,mAdapter.getItem(position).getId());
                startActivity(intent);

            }
        });
    }
}
