package com.example.songshan.festivalmessage.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songshan on 2016/3/28.
 */
public class FestivalLab {
    public static FestivalLab mInstance;
    private List<Msg> mMsg=new ArrayList<Msg>();
   private List<Festival> mFestival=new ArrayList<Festival>();
    private FestivalLab() {
        mFestival.add(new Festival(1,"七夕节"));
        mFestival.add(new Festival(2,"圣诞节"));
        mFestival.add(new Festival(3,"除夕"));
        mFestival.add(new Festival(4,"元宵节"));
        mFestival.add(new Festival(5,"端午节"));
        mFestival.add(new Festival(6,"儿童节"));
        mFestival.add(new Festival(7,"重阳节"));
        mFestival.add(new Festival(8,"国庆节"));
        mMsg.add(new Msg(1, 1, "悠悠银河人尽望，牛郎织女情满膛。千里鹊桥来相会，葡萄架下诉忠肠。我劝天下有情人，忙碌莫把祝福忘。祝七夕情人节快乐！", "七夕节"));
        mMsg.add(new Msg(2, 1, "老天给我最大的恩赐就是让我拥有了你，拥有了你的爱！在这七夕之夜，我祝福我们永远在一起，永不分离！！！", "七夕节"));

        mMsg.add(new Msg(3, 2, "深祝福，丝丝情谊，串串思念，化作一份礼物，留在你的心田，祝你圣诞快乐，新年幸福！", "圣诞节"));
        mMsg.add(new Msg(4, 2, "音乐卡是我的挂念，钟声是我的问候,歌声是我的祝福，雪花是我的贺卡，美酒是我的飞吻，快乐是我的礼物！圣诞快乐！", "圣诞节"));

        mMsg.add(new Msg(5, 3, "今日大寒，满天雪花飞舞，临近年关仅六天，老叟童依皆大欢；瑞雪兆丰年，龙年伊始话慨感；悦心不减当年；炮声处处映门庭；欢畅笑语迎新年！", "除夕"));
        mMsg.add(new Msg(6, 3, "生活奔忙一天天，身心放松抽根烟；适当进补多锻炼，防寒保暖多睡眠；大雪小雪降瑞雪，大寒小寒心不寒；坐票站票买到票，欢欢喜喜过大年！", "除夕"));

        mMsg.add(new Msg(7, 4, "元宵送你一份汤圆，里面包的是我的心愿:愿你在生活中十分热情九分优雅八分聪慧七分敏锐六分风趣五分温柔四个密友三分豪放二分含蓄一分浪漫!", "元宵节"));
        mMsg.add(new Msg(8, 4 , "天上的月儿圆，锅里的元宵圆，吃饭的桌儿圆，你我的情更圆，就像元宵一样黏黏呼呼团团圆圆。", "元宵节"));
    }
    public List<Msg> getMsgByFesivalId(int id){
        List<Msg> mmsg=new ArrayList<>();
        for (Msg msg:mMsg){
            if (msg.getFestivalId()==id){
                mmsg.add(msg);
            }
        }
        return mMsg;
    }
    public Msg getMsgById(int id){
        for (Msg msg:mMsg){
            if (msg.getId()==id)
                return msg;
        }
        return null;
    }
    public List<Festival> getFestival(){
        return new ArrayList<Festival>(mFestival);
    }
    public Festival getFestivalById(int id){
        for (Festival festival:mFestival){
            if (festival.getId()==id)
                return festival;
        }
        return null;
    }
    public static FestivalLab getmInstance(){
        if (mInstance==null){
            synchronized (FestivalLab.class){
                if (mInstance==null)
                    mInstance=new FestivalLab();
            }
        }
        return mInstance;
    }
}
