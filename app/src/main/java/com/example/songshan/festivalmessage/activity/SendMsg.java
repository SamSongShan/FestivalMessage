package com.example.songshan.festivalmessage.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.songshan.festivalmessage.R;
import com.example.songshan.festivalmessage.Utiles.FlowLayout;
import com.example.songshan.festivalmessage.bean.Festival;
import com.example.songshan.festivalmessage.bean.FestivalLab;
import com.example.songshan.festivalmessage.bean.Msg;
import com.example.songshan.festivalmessage.bean.SendedMsg;
import com.example.songshan.festivalmessage.biz.SmsBiz;

import java.util.HashSet;

public class SendMsg extends AppCompatActivity {
    private int mFestivalId;
    private int msgId;
    private Festival mFestival;
    private Msg mMsg;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mInflater = LayoutInflater.from(this);
        mSmsBiz=new SmsBiz(this);
        initData();
        assignViews();
        initEvents();
        initRecivers();
    }
    private int mMsgSendCount;
    private int mTotalCount;
    private void initRecivers() {
        Intent sentInent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sentInent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);
        registerReceiver(mSendBroadcastReciiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == RESULT_OK) {
                    Log.e("TAG", "短信发送成功");
                } else {
                    Log.e("TAG", "短信发送失败");
                }
                mMsgSendCount ++;
                if (mMsgSendCount==mTotalCount){
                    finish();
                }
            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
        registerReceiver(mDeliverPiBroadcastReciiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("TAG", "联系人已经收到短信");

            }
        }, new IntentFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReciiver);
        unregisterReceiver(mDeliverPiBroadcastReciiver);
    }

    public static final int CODE_REQUEST = 1;

    private void initEvents() {
        idBtLxr.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });

    }

    private void initData() {
        mFestivalId = getIntent().getIntExtra("festivalId", -1);
        msgId = getIntent().getIntExtra("msgId", -1);
        setTitle(FestivalLab.getmInstance().getFestivalById(mFestivalId).getName());
    }

    private HashSet<String> mContactName = new HashSet<>();
    private HashSet<String> mContactnum = new HashSet<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String number = getContactNumber(cursor);
                if (!TextUtils.isEmpty(number)) {
                    mContactnum.add(number);
                    mContactName.add(contactName);

                    addTag(contactName);
                }

            }
            //System.out.println("66666666666666666666666");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addTag(String contactName) {
        TextView inflate = (TextView) mInflater.inflate(R.layout.tag, idFlowlayoutContent, false);
        inflate.setText(contactName);
        idFlowlayoutContent.addView(inflate);
    }

    private String getContactNumber(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;

        if (numberCount > 0) {
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
            return number;

        }
        cursor.close();
        return number;
    }

    private EditText idEtContent;
    private Button idBtLxr;
    private FlowLayout idFlowlayoutContent;
    private FloatingActionButton idBtFab;
    private FrameLayout idLayoutLoading;
    private static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";
    private static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mDeliverPiBroadcastReciiver;
    private BroadcastReceiver mSendBroadcastReciiver;

    private SmsBiz mSmsBiz ;

    private void assignViews() {
        idEtContent = (EditText) findViewById(R.id.id_et_content);
        idBtLxr = (Button) findViewById(R.id.id_bt_lxr);
        idFlowlayoutContent = (FlowLayout) findViewById(R.id.id_flowlayout_content);
        idBtFab = (FloatingActionButton) findViewById(R.id.id_bt_fab);
        idLayoutLoading = (FrameLayout) findViewById(R.id.id_layout_loading);
        idBtFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactnum.size() == 0) {
                    Toast.makeText(SendMsg.this, "请先选择联系人", Toast.LENGTH_LONG).show();


                    return;
                }
                //String msg = idEtContent.getText().toString();
                String msg = mMsg.getContent().toString();
                if (TextUtils.isEmpty(msg)) {

                    Toast.makeText(SendMsg.this, "短信内容不能为空", Toast.LENGTH_LONG).show();
                }
                idLayoutLoading.setVisibility(View.VISIBLE);
                mTotalCount = mSmsBiz.sendMsg(mContactnum, buildSendMsg(msg), mSendPi, mDeliverPi);
                mMsgSendCount=0;
            }
        });
        idLayoutLoading.setVisibility(View.GONE);
        if (msgId != -1) {
            mMsg = FestivalLab.getmInstance().getMsgById(msgId);
            idEtContent.setText(mMsg.getContent());

        }
    }

    private SendedMsg buildSendMsg(String msg) {
       SendedMsg sendedMsg=new SendedMsg();
        sendedMsg.setMsg(msg);
        sendedMsg.setFestivalName(mFestival.getName());
        String names="";
        for (String name:mContactName){
            names+=name+"..";
        }
        sendedMsg.setNames(names.substring(0,names.length()-1));
        String numbers="";
        for (String number:mContactnum){
            numbers+= number+"..";
        }
        sendedMsg.setNames(numbers.substring(0,numbers.length()-1));
        return sendedMsg;
    }



    public static void toActivity(Context context, int festivalId, int msgId) {
        Intent intent = new Intent(context, SendMsg.class);
        intent.putExtra("festivalId", festivalId);
        intent.putExtra("msgId", msgId);
        context.startActivity(intent);
    }
}
