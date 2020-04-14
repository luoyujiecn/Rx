package com.kxj.rx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kxj.rx.R;
import com.kxj.rx.mvp.MvpFragment;
import com.kxj.rx.ui.activity.InformationInfoActivity;
import com.kxj.rx.ui.adapter.InformationAdapter;
import com.kxj.rx.util.ToastUtil;

public class InformationTypeFragment extends MvpFragment<InformationCantract.Persenter> implements InformationCantract.View {
    private ListView listView;

    private String typeStr;

    public InformationTypeFragment(String typeStr) {
        this.typeStr = typeStr;
    }

    @NonNull
    @Override
    public InformationCantract.Persenter createPresenter() {
        return new InformationPresenter();
    }

    @Override
    protected int setLayout() {
        return R.layout.information_type_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.information_listView);
    }

    @Override
    protected void initData() {
        getPresenter().loadData(typeStr);
    }


    @Override
    public void setAdapter(final InformationAdapter adapter) {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InformationInfoActivity.class);
                intent.putExtra("url",adapter.getInformationBean(i).getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtil.showToast(errorMsg);
    }
}
