package com.education.online.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.AVUserCacheUtils;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.education.online.R;
import com.education.online.act.CM_MessageChatAct;
import com.education.online.act.SearchAct;
import com.education.online.bean.LoginInfo;
import com.education.online.retrofit.LiveConversationEventHandler;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/8/12.
 */
public class ActUtil {

    public static void closeSoftPan(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
    private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };


    public static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    public static void initData(Context con) {
//        SharedPreferencesUtil.setString(con, Constant.Url_API,
//                "http://182.48.115.38:8088/platform/api");
//        SharedPreferencesUtil.setString(con, Constant.Product_code,
//                "1100");
//        SharedPreferencesUtil.setString(con, Constant.App_Channel,
//                "10002");
//        SharedPreferencesUtil.setString(con, Constant.App_Key,
//                "600025");
        SharedPreferencesUtil.setString(con, Constant.WXAppId,
                Constant.WXAppId);
        SharedPreferencesUtil.setString(con, Constant.WXAppSecret,
                "94a1f4de4f5011e6b0ee525400b263eb");
        SharedPreferencesUtil.setString(con, Constant.App_Secret,
                "9af39ddG3jfld3B5J0mE8jd6hHi3k8al");
        SharedPreferencesUtil.setString(con, Constant.Pic_Savepath,
                Constant.SavePath);

    }

    public static void startAnimActivity(Activity act, Intent intent) {
        if(Build.VERSION.SDK_INT>=21) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(act).toBundle();
            act.startActivity(intent, bundle);
        }else{
            act.startActivity(intent);
        }
    }

    public static void startAnimActivity(Activity act, Intent intent, View view, String transitionName) {
        if(Build.VERSION.SDK_INT>=21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, Pair.create(view, transitionName));
            Bundle bundle = options.toBundle();
            act.startActivity(intent, bundle);
        }else{
            act.startActivity(intent);
        }
    }

    public static String getVersionCode(Context context) {
        String version = "0";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pi.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    public static boolean canStartLive(String datestring) {
        boolean startCourse=false;
        try {
            Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
            long nowTime=mCalendar.getTimeInMillis();
            SimpleDateFormat dateData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateStart = dateData.parse(datestring);
            long startTime=dateStart.getTime();
            if(startTime-nowTime<1800000)
                startCourse=true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startCourse;
    }

    public  static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static String getDate() {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        long todayL=mCalendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(todayL);//获取当
        String dateTxt = formatter.format(curDate);
        LogUtil.i("Date", "today date: "+dateTxt);
        return dateTxt;
    }

    public static String getChangedDate(int type, int num) {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.add(type, num);
        long todayL=mCalendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(todayL);//获取当
        String dateTxt = formatter.format(curDate);
        LogUtil.i("Date", "change date: "+dateTxt);
        return dateTxt;
    }

    public static String getChangedDateByMonthDay(int type, int num) {
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar.add(type, num);
        long todayL=mCalendar.getTimeInMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        Date curDate = new Date(todayL);//获取当
        String dateTxt = formatter.format(curDate);
        LogUtil.i("Date", "change date: "+dateTxt);
        return dateTxt;
    }

    public static String getDataTime(String dateStr) {
        if(dateStr.length()>0) {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            try {
                Date date = format1.parse(dateStr);
                return formatter.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getWeekDay(int i) {
        String weekDay="";
        switch (i){
            case 1:
                weekDay="周日";
                break;
            case 2:
                weekDay="周一";
                break;
            case 3:
                weekDay="周二";
                break;
            case 4:
                weekDay="周三";
                break;
            case 5:
                weekDay="周四";
                break;
            case 6:
                weekDay="周五";
                break;
            case 7:
                weekDay="周六";
                break;
        }
        return weekDay;
    }

    public static String getPrice(String price) {
        if(price.length()>0) {
            if (Double.valueOf(price) == 0)
                return "免费";
            else
                return "￥" + price;
        }else{
            return "未知";
        }
    }

    public static boolean isLogin(Context context) {
        if(SharedPreferencesUtil.getSessionid(context).equals(SharedPreferencesUtil.FAILURE_STRING))
            return false;
        else
            return true;
    }

    public static boolean isCash(String moneyStr, Activity con) {
        String hinttxt=null;
        if (moneyStr.length() == 0) {
            hinttxt="请输入金额";
        } else if (moneyStr.startsWith(".") || moneyStr.endsWith(".") || moneyStr.startsWith("00")) {
            hinttxt="请输入有效金额";
        } else if (moneyStr.contains(".")) {
            String[] str = moneyStr.split("\\.");
            if (str[0].length() > 9) {
                hinttxt="你输入金额的整数不能多于9位";
            } else if (str[1].length() >= 3) {
                hinttxt="你输入金额的小数不能多于2位";
            }else{
                double money = Double.parseDouble(moneyStr);
                if (money == 0) {
                    hinttxt="请输入有效金额";
                }
            }
        } else if (moneyStr.length() > 9) {
            hinttxt="你输入金额的整数不能多于9位";
        } else if (moneyStr.length() > 0) {
            double money = Double.parseDouble(moneyStr);
            if (money == 0) {
                hinttxt="请输入有效金额";
            }
        }
        if(hinttxt!=null){
            ToastUtils.displayTextShort(con, hinttxt);
            return false;
        }else
            return true;
    }

    public static String getCashs(String money) {
        Double cashInt = (Double.parseDouble(money) * 100);
        BigDecimal original = new BigDecimal(cashInt);
        BigDecimal result = original.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return result.toBigInteger().toString();
    }

    /** 保留两位小数点 */
    public static String twoDecimal(String money) {
        if (money != null && money.length() > 0) {
            Double decimal = Double.valueOf(money);
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String twoDecimal = fnum.format(decimal);
            return twoDecimal;
        } else
            return "暂未获取";
    }
    public static String twoDecimal(Double money) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String twoDecimal = fnum.format(money);
        return twoDecimal;
    }


    public static String getCourseStatusTxt(String status) {
        String courseStatusTxt="";
        if(status!=null&&status.length()>0){
            switch (Integer.valueOf(status)){
                case 0:
                    courseStatusTxt="待审核";
                    break;
                case 1:
                    courseStatusTxt="通过";
                    break;
                case 2:
                    courseStatusTxt="拒绝";
                    break;
            }
        }
        return courseStatusTxt;
    }

    public static String getRefundState(String state) {
        String txt="";
        if(state!=null&&state.length()>0) {
            switch (Integer.valueOf(state)) {
                case 1:
                    txt = "随时退";
                    break;
                case 2:
                    txt = "不可退";
                    break;
                case 3:
                    txt = "开课前一小时可退 ";
                    break;
                case 4:
                    txt = "开课十分钟后不可退 ";
                    break;
            }
        }
        return txt;
    }

    public static String getInsertState(String state) {
        String txt="";
        if(state!=null&&state.length()>0) {
            switch (Integer.valueOf(state)) {
                case 1:
                    txt = "随时插班";
                    break;
                case 2:
                    txt = "不可插班";
                    break;
            }
        }
        return txt;
    }

    public static String getCourseStateTxt(String state) {
        String txt="";
        if(state!=null&&state.length()>0) {
            switch (Integer.valueOf(state)) {
                case 1:
                    txt = "未开始";
                    break;
                case 2:
                    txt = "直播中";
                    break;
                case 3:
                    txt = "已完成";
                    break;
            }
        }
        return txt;
    }

    public static String getCourseTypeTxt(String course_type, TextView courseType) {
        String txt="";
        if(course_type!=null&&course_type.length()>0){
            switch (Integer.valueOf(course_type)) {
                case 1:
                    txt = "课件";
                    break;
                case 2:
                    txt = "视频";
                    break;
                case 3:
                    txt = "直播课";
                    break;
            }
            courseType.setText(txt);
        }else
            courseType.setVisibility(View.GONE);
        return txt;
    }

    public static String getCourseTypeTxt(String course_type) {
        String txt="";
        if(course_type!=null&&course_type.length()>0) {
            switch (Integer.valueOf(course_type)) {
                case 1:
                    txt = "课件";
                    break;
                case 2:
                    txt = "视频";
                    break;
                case 3:
                    txt = "直播课";
                    break;
            }
        }
        return txt;
    }

    public static String getRefund(String course_type) {
        String txt="";
        if(course_type!=null&&course_type.length()>0) {
            switch (Integer.valueOf(course_type)) {
                case 1:
                    txt = "申请";
                    break;
                case 2:
                    txt = "拒绝";
                    break;
                case 3:
                    txt = "退款中";
                    break;
                case 4:
                    txt = "已退款";
                    break;
                case 5:
                    txt = "退款失败";
                    break;
            }
        }
        return txt;
    }

    public static <T> T updateInfo(Class<T> clazz, T oldbean, T newbean) {
        try {
            Method[] methods = clazz.getMethods();
            for (Method method:methods){
                String methodName=method.getName();
                if(methodName.startsWith("get")){
                    if(method.getReturnType()==String.class||method.getReturnType()==Boolean.class||method.getReturnType()==Integer.class) {
                        Method method2 = clazz.getMethod(methodName.replace("get", "set"), method.getReturnType());
                        method2.invoke(oldbean, method.invoke(newbean));
                    }else{
//                        String methodname=method.getReturnType().getName();
//                        ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
//                        Type[] types=type.getActualTypeArguments();
//                        Class genericClazz = (Class)types[0];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldbean;
    }

    public static int getOrderStatsImgRes(String state) {
        int res=0;
        if(state!=null&&state.length()>0){
            switch (Integer.valueOf(state)){
                case 1:
                    res= R.mipmap.icon_status_topay;
                    break;
                case 2:
                    res= R.mipmap.icon_status_finished;
                    break;
                case 3:
                    res= R.mipmap.icon_status_complain;
                    break;
                case 4:
                    res= R.mipmap.icon_status_payback;
                    break;
                case 5:
                    res= R.mipmap.icon_status_to_be_ev;
                    break;
                case 6:
                    res= R.mipmap.icon_status_finished;
                    break;
            }
        }
        return res;
    }

    public static String getOrderStatsTxts(String state) {
        String res="";
        if(state!=null&&state.length()>0){
            switch (Integer.valueOf(state)){
                case 0:
                    res= "已取消";
                    break;
                case 1:
                    res= "待支付";
                    break;
                case 2:
                    res= "已完成";
                    break;
                case 3:
                    res= "退款中";
                    break;
                case 4:
                    res= "已退款";
                    break;
                case 5:
                    res= "待评价";
                    break;
                case 6:
                    res= "已完成";
                    break;
            }
        }
        return res;
    }

    public static  void initChatUser(Context con){
        LogUtil.i("Chat", "initChat");
        ChatManager chatManager=ChatManager.getInstance();

        if(!chatManager.isConnect()) {
            String loginJson = SharedPreferencesUtil.getString(con, Constant.UserInfo);
            LoginInfo loginInfo = JSON.parseObject(loginJson, LoginInfo.class);
            LeanchatUser user = AVUser.newAVUser(LeanchatUser.class, null);

            if (loginInfo.getAvatar().length() > 0)
                user.put("avatar", ImageUtil.getImageUrl(loginInfo.getAvatar()));
            String name = loginInfo.getNickname();
            if (name.length() == 0)
                name = loginInfo.getUsername();
            user.put("username", name);
            user.setObjectId(loginInfo.getUsercode());
            AVUser.changeCurrentUser(user, true);
            AVUserCacheUtils.cacheUser(user.getObjectId(), user);

            String usercode = AVUser.getCurrentUser().getObjectId();
            if (usercode != null && usercode.length() > 0) {
                chatManager.setupManagerWithUserId(usercode);
                chatManager.openClient(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if (e != null) {
                            LogUtils.logException(e);
                        }
//                    AVIMMessageManager.setConversationEventHandler(new LiveConversationEventHandler());
                    }
                });
            }
        }
    }

    public static void goChat(String otherid, final Context con, final String name) {
        LogUtil.i("Chat", "openChat");
        final ChatManager chatManager = ChatManager.getInstance();
        chatManager.fetchConversationWithUserId(otherid, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation conversation, AVIMException e) {
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(con, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
//                    chatManager.getRoomsTable().insertRoom(conversation.getConversationId());
                    Intent intent = new Intent(con, CM_MessageChatAct.class);
                    intent.putExtra("Name", name);
                    intent.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID, conversation.getConversationId());
                    con.startActivity(intent);
                }
            }
        });
    }

    public static String getTimeFormat(long totaltime) {
        int hour = (int) (totaltime / 3600);
        int leftH = (int) (totaltime % 3600);
        int min = leftH / 60;
        int sec = leftH % 60;
        String hourTxt=hour>9?String.valueOf(hour):("0"+hour);
        String minTxt=min>9?String.valueOf(min):("0"+min);
        String secTxt=sec>9?String.valueOf(sec):("0"+sec);
        return hourTxt + ":" +minTxt + ":" +  secTxt;
    }

    public static void KeyBoardCancle(Activity act) {

        View view = act.getWindow().peekDecorView();
        if (view != null) {

            InputMethodManager inputmanger = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void exitChat() {
        AVIMClient imClient = ChatManager.getInstance().getImClient();
        if(imClient!=null) {
            imClient.close(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient client, AVIMException e) {
                    if (e == null) {
                        //登出成功
                        LogUtil.i("Chat", "Login out successfull");
                    }else
                        e.printStackTrace();
                }
            });
        }
    }
}
