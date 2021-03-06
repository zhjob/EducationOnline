package com.education.online.weex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.education.online.act.CourseMainPage;
import com.education.online.act.MainPage;
import com.education.online.act.SearchResultAct;
import com.education.online.act.VideoMainPage;
import com.education.online.act.login.LoginActivity;
import com.education.online.act.teacher.TeacherInformationPage;
import com.education.online.act.video.LiveTelecast;
import com.education.online.http.Method;
import com.education.online.retrofit.RetrofitAPIManager;
import com.education.online.util.Constant;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/15.
 */

public class WeexUtilModule extends WXModule {

    private static final String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String scheme = Uri.parse(url).getScheme();
        StringBuilder builder = new StringBuilder();
        if (TextUtils.equals("http",scheme) || TextUtils.equals("https",scheme) || TextUtils.equals("file",scheme)) {
            builder.append(url);
        } else {
            builder.append("http:");
            builder.append(url);
        }
        Uri uri = Uri.parse(builder.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(WEEX_CATEGORY);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @WXModuleAnno(runOnUIThread = true)
    public void goCoursePage(String msg) {
        if(mWXSDKInstance.getContext() instanceof Activity) {
            Intent intent = new Intent(mWXSDKInstance.getContext(), CourseMainPage.class);
            intent.putExtra("url","weex/modules/send.js");
            mWXSDKInstance.getContext().startActivity(intent);
        }
    }

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void closePage() {
        if(mWXSDKInstance.getContext() instanceof Activity) {
            ((Activity) mWXSDKInstance.getContext()).finish();
        }
    }

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void toCourseDetailPage(String jsonData) {
        String id=null;
        int type=-1;
        try {
            JSONObject obj=new JSONObject(jsonData);
            if(!obj.isNull("advert_id"))
                id=obj.getString("advert_id");
            if(!obj.isNull("type"))
                type=obj.getInt("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Class clasz=null;
        switch (type){
            case 3:
                clasz=CourseMainPage.class;
                break;
            case 1:
            case 2:
                clasz=VideoMainPage.class;
                break;
        }
        Intent intent = new Intent(mWXSDKInstance.getContext(), clasz);
        intent.putExtra("course_id", id);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @WXModuleAnno(moduleMethod = true,runOnUIThread = true)
    public void toTeacherDetailPage(String jsonData) {
        String id="";
        try {
            JSONObject obj=new JSONObject(jsonData);
            if(!obj.isNull("usercode"))
                id=obj.getString("usercode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(mWXSDKInstance.getContext(), TeacherInformationPage.class);
        intent.putExtra(Constant.UserCode,id);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @WXModuleAnno
    public void toWeexPage(String url, Map<String, Object> map) {
        Intent intent = new Intent(mWXSDKInstance.getContext(), AdvPageWeex.class);
        intent.putExtra("WeexData", map.toString());
        intent.putExtra("WeexUrl", url);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @WXModuleAnno
    public void setScrollY(Map<String, Float> map) {
        LogUtil.i("Scroller", String.valueOf(map.get("scrollY")));
        EventBus.getDefault().post(map.get("scrollY"));
    }

    @WXModuleAnno
    public void searchSubject(Map<String, String> map) {
        LogUtil.i("Scroller", String.valueOf(map.get("scrollY")));
        Intent i = new Intent(mWXSDKInstance.getContext(), SearchResultAct.class);
        String ids=map.get("child_subject_ids");
        if(ids.length()==0)
            ids="-1";
        i.putExtra(Constant.SearchSubject, ids);
        i.putExtra(Constant.SearchCate, map.get("subject_id"));
        i.putExtra(Constant.SearchWords, map.get("subject_name"));
        if(ids.equals("-1")&&map.get("subject_id").equals("-1")){
            ((MainPage)mWXSDKInstance.getContext()).toSubjectList();
        }else
            mWXSDKInstance.getContext().startActivity(i);
    }

    @WXModuleAnno
    public void moreCourse(Map<String, Integer> map) {
        Context act=mWXSDKInstance.getContext();
        switch (map.get("pos")){
            case 1:
                Intent i = new Intent(act, SearchResultAct.class);
                i.putExtra(Constant.TypeCourse, "courseware");
                act.startActivity(i);
                break;
            case 2:
                Intent i2 = new Intent(act, SearchResultAct.class);
                i2.putExtra(Constant.TypeCourse, "coursevideo");
                act.startActivity(i2);
                break;
            case 3:
                act.startActivity(new Intent(act, LiveTelecast.class));
                break;
        }
    }

}
