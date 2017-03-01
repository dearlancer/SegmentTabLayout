package com.example.wangzhicheng.SegmentTabLayout;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] mTitles = {"登录", "注册"};
    private String[] mTitle = {"呵呵", "哈哈"};
    ArrayList<Fragment>fragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SegmentTabLayout tabLayout = (SegmentTabLayout) findViewById(R.id.tl_4);
        fragments.add(TestFragment.newInstance(mTitles));
        fragments.add(TestFragment.newInstance(mTitle));
        tabLayout.setTabData(mTitles,this,R.id.container,fragments);
    }
}
