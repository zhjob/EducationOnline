package com.education.online.act.Mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.education.online.R;
import com.education.online.act.BaseFrameAct;
import com.education.online.adapter.WalletHistoryAdapter;

/**
 * Created by 可爱的蘑菇 on 2016/9/16.
 */
public class MyWalletHistory extends BaseFrameAct {

    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        _setHeaderTitle("明细");
        initView();
    }

    private void initView() {
        recyclerList=(RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        recyclerList.setAdapter(new WalletHistoryAdapter(this));
    }
}
