package com.example.songshan.festivalmessage.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.songshan.festivalmessage.R;
import com.example.songshan.festivalmessage.Utiles.FlowLayout;
import com.example.songshan.festivalmessage.bean.SendedMsg;
import com.example.songshan.festivalmessage.db.SmsProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SmsHistoryFragment extends ListFragment {
    private static final int LOADER_ID = 1;
    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        initLoader();
        setupListAdapter();
    }

    private void setupListAdapter() {
        mCursorAdapter = new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View inflate = mInflater.inflate(R.layout.fragment_sms_history, parent, false);

                return inflate;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView idTvMsg;
                FlowLayout idFlContacts;
                TextView idTvFes;
                TextView idTvDate;


                idTvMsg = (TextView) view.findViewById(R.id.id_tv_msg);
                idFlContacts = (FlowLayout) view.findViewById(R.id.id_fl_contacts);
                idTvFes = (TextView) view.findViewById(R.id.id_tv_fes);
                idTvDate = (TextView) view.findViewById(R.id.id_tv_date);
                idTvMsg.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_MSG)));
                idTvFes.setText(cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_FES_NAME)));
                Long dateVal = cursor.getLong(cursor.getColumnIndex(SendedMsg.COLUMN_DATE));
                idTvDate.setText(parseData(dateVal));
                String names = cursor.getString(cursor.getColumnIndex(SendedMsg.COLUMN_NAMES));
                if (TextUtils.isEmpty(names)){
                    return ;
                }idFlContacts.removeAllViews();
                for (String name : names.split(":")) {
                    addTag(name, idFlContacts);
                }

            }
        };
        setListAdapter(mCursorAdapter);
    }
    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String parseData(Long dateVal) {

        return df.format(dateVal);
    }

    private void addTag(String name, FlowLayout idFlContacts) {

        TextView inflate = (TextView) mInflater.inflate(R.layout.tag, idFlContacts, false);
        inflate.setText(name);
        idFlContacts.addView(inflate);

    }


    private void initLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (loader.getId() == LOADER_ID) {
                    mCursorAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mCursorAdapter.swapCursor(null);
            }
        });
    }
}
