package com.kxj.rx.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.kxj.rx.R;
import com.kxj.rx.basemvp.BaseFragment;
import com.kxj.rx.mvp.MvpFragment;

import java.util.ArrayList;

public class InformationFragment extends BaseFragment {
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ViewPager viewPager;
    //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    private String[] mTitles = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    private String[] mTitleNames = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private MyPagerAdapter mAdapter;
    @Override
    protected int setLayout() {
        return R.layout.infomation_fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slidingTabLayout = view.findViewById(R.id.tl_5);
        viewPager = view.findViewById(R.id.information_viewpager);
        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    protected void initData() {
        for (String title : mTitles) {
            mFragments.add(new InformationTypeFragment(title));
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleNames[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
