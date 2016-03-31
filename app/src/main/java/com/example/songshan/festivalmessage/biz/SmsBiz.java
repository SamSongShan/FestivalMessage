package com.example.songshan.festivalmessage.biz;

import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import com.example.songshan.festivalmessage.activity.SendMsg;
import com.example.songshan.festivalmessage.bean.SendedMsg;
import com.example.songshan.festivalmessage.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by songshan on 2016/3/30.
 */

public class SmsBiz {
    public SmsBiz(Context context) {
        this.context = context;
    }

    public int sendMsg(String number, String msg, PendingIntent sentPi, PendingIntent delivee) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> message = smsManager.divideMessage(msg);
        for (String content : message) {
            smsManager.sendTextMessage(number, null, content, sentPi, delivee);
        }
        return message.size();
    }
    public int sendMsg(Set<String> number, SendedMsg msg, PendingIntent sentPi, PendingIntent delivee) {
        save(msg);
        int result =0;
        for (String numbers : number) {
          int count=  sendMsg(numbers,msg.getMsg(),sentPi,delivee);
       result += count;
        }
        return result;
    }
    private Context context;
    private void save(SendedMsg sendedMsg){
        sendedMsg.setDate(new Date());
        ContentValues values=new ContentValues();
        values.put(SendedMsg.COLUMN_DATE,sendedMsg.getDate().getTime());
        values.put(SendedMsg.COLUMN_FES_NAME,sendedMsg.getFestivalName());
        values.put(SendedMsg.COLUMN_MSG,sendedMsg.getMsg());
        values.put(SendedMsg.COLUMN_NAMES,sendedMsg.getNames());
        values.put(SendedMsg.COLUMN_NUMBERS,sendedMsg.getNumbers());
        context.getContentResolver().insert(SmsProvider.URI_SMS_ALL,values);


    }
}
