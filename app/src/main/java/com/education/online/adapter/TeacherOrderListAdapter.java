package com.education.online.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.bean.SystemMessage;
import com.education.online.bean.TeacherOrderBean;
import com.education.online.util.ImageUtil;
import com.education.online.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class TeacherOrderListAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
        private Activity activity;
        private LayoutInflater inflater;
        private int imgLength=0;
        private LinearLayout.LayoutParams llp;
        private String loadingHint = "";
        private List<TeacherOrderBean> datalist=new ArrayList<>();

    public void setLoadingHint(String hint) {
        loadingHint = hint;
        notifyDataSetChanged();
    }

    public TeacherOrderListAdapter(Activity activity, List<TeacherOrderBean> datalist){
        this.datalist=datalist;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        imgLength= (ScreenUtil.getWidth(activity)- ImageUtil.dip2px(activity, 70))/4; //左右边距40， 中间3*10
        llp=new LinearLayout.LayoutParams(imgLength, imgLength);
        llp.rightMargin=ImageUtil.dip2px(activity, 10);
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datalist.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return getItemCount() - 1 > position ? position : -1;
    }

    @Override
    //创建一个视图
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        RecyclerView.ViewHolder vh =null;
        if (pos == -1) {
            View view = inflater.inflate(R.layout.footer_layout, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, ImageUtil.dip2px(activity, 45)));
            vh = new FooterViewHolder(view);
        } else {
            View convertView = inflater.inflate(R.layout.system_message_listitem, null);
            vh = new OrderItem(convertView, pos);
        }
        return vh;
    }

    @Override
    //绑定数据源
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.footerHint.setText(loadingHint);
        }else{
            OrderItem ivh = (OrderItem) holder;
            TeacherOrderBean msg=datalist.get(position);
            ivh.descTxt.setText(msg.getPlan());
            ivh.titleTxt.setText(msg.getCourse_name());

        }


    }

    public class OrderItem extends RecyclerView.ViewHolder{
        ImageView messageIcon;
        TextView descTxt, timeTxt, titleTxt;
        public OrderItem(View v, final int position)
        {
            super(v);
            messageIcon = (ImageView) v.findViewById(R.id.messageIcon);
            descTxt = (TextView) v.findViewById(R.id.descTxt);
            timeTxt = (TextView) v.findViewById(R.id.timeTxt);
            titleTxt = (TextView) v.findViewById(R.id.titleTxt);
        }

    }
}
