package com.kxj.rx.ui.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kxj.rx.R;
import com.kxj.rx.basemvp.BaseActivity;
import com.kxj.rx.bean.TabEntity;
import com.kxj.rx.ui.fragment.IndexFragment;
import com.kxj.rx.ui.fragment.InformationFragment;
import com.kxj.rx.ui.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private CommonTabLayout commonTabLayout;
    private FrameLayout frameLayout;
    private String[] mTitles = {"首页", "资讯", "我的"};
    private int[] mSelectP = {R.drawable.tab_home_select, R.drawable.select_information, R.drawable.tab_contact_select};
    private int[] mUnSelectP = {R.drawable.tab_home_unselect, R.drawable.un_select_information, R.drawable.tab_contact_unselect};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private Fragment indexF, inforF, myF;

    @Override
    protected void initLayout(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mSelectP[i], mUnSelectP[i]));
        }
    }

    @Override
    protected void initViews() {
        commonTabLayout = $(R.id.tl_1);
        frameLayout = $(R.id.fragment);
        commonTabLayout.setTabData(mTabEntities);
        indexF = new IndexFragment();
        inforF = new InformationFragment();
        myF = new MyFragment();
        fragmentList.add(indexF);
        fragmentList.add(inforF);
        fragmentList.add(myF);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment, indexF);
        transaction.add(R.id.fragment, inforF);
        transaction.add(R.id.fragment, myF);
        transaction.commit();
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showFragment(fragmentList.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        commonTabLayout.setCurrentTab(0);
        showFragment(fragmentList.get(0));
    }

    /**
     * 显示Fragment的方法
     */
    public void showFragment(Fragment fragment) {
        transaction = manager.beginTransaction();
        for (Fragment f : fragmentList) {
            transaction.hide(f);
            if (f == fragment) {
                transaction.show(f);
            }
        }
        transaction.commit();
    }

    /*
     * activity重建时不恢复保存的信息
     * */
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("android:support:fragments", null);
    }
}
