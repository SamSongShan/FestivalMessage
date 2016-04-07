package com.example.songshan.festivalmessage.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.songshan.festivalmessage.R;
import com.example.songshan.festivalmessage.bean.FestivalLab;
import com.example.songshan.festivalmessage.bean.Msg;
import com.example.songshan.festivalmessage.fragment.FestivalCategoryFragment;

public class ChooseMsgActivity extends AppCompatActivity {
    private ListView mListView;
    private FloatingActionButton mFaSend;
    private ArrayAdapter<Msg> msgArrayAdapter;
    private int mFestivalId;
    private LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);
        mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL, -1);
        mInflater = LayoutInflater.from(this);
        initviews();
        initEvent();
    }

    private void initEvent() {
        mFaSend = (FloatingActionButton) findViewById(R.id.id_toSend);
        mFaSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/3/29
                SendMsg.toActivity(ChooseMsgActivity.this,mFestivalId,-1);
            }
        });
    }

    private void initviews() {
        mListView = (ListView) findViewById(R.id.listView_msg);
        mFaSend = (FloatingActionButton) findViewById(R.id.tabLayout);
        mListView.setAdapter(msgArrayAdapter = new ArrayAdapter<Msg>(this, -1, FestivalLab.getmInstance().getMsgByFesivalId(mFestivalId)) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_msg, parent, false);
                }
                TextView conten = (TextView) convertView.findViewById(R.id.tv_listview_msg);
                Button tosent = (Button) convertView.findViewById(R.id.bt_tosend);
                conten.setText(getItem(position).getContent());
                tosent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //// TODO: 2016/3/29
                        SendMsg.toActivity(ChooseMsgActivity.this,mFestivalId,getItem(position).getId());

                    }
                });
                return convertView;
            }
        });
    }
}
