package com.example.wangzhicheng.SegmentTabLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangzhicheng on 2017/3/1.
 */

public class TestFragment extends Fragment {
    private static String TITLE="TITLE";
    private String[] mTitle;

    public static TestFragment newInstance(String[] title){
        TestFragment fragment=new TestFragment();
        fragment.setmTitle(title);
        return fragment;
    }
    public void setmTitle(String[] mTitle) {
        this.mTitle = mTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_fragment, null);
        CustomRadioGroup customRadioGroup= (CustomRadioGroup) v.findViewById(R.id.radio_group);
        customRadioGroup.setChilds(mTitle);
        return v;
    }
}
