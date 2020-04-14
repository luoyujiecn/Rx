package com.kxj.rx.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kxj.rx.R;
import com.kxj.rx.bean.InformationBean;
import com.kxj.rx.util.ImageLoaderManager;

import java.util.List;

public class InformationAdapter extends BaseAdapter {
    private List<InformationBean> informationBeans;
    private Context context;

    public InformationAdapter(Context context, List<InformationBean> informationBeans) {
        this.context = context;
        this.informationBeans = informationBeans;
    }

    public InformationBean getInformationBean(int position){
        return informationBeans.get(position);
    }

    @Override
    public int getCount() {
        return informationBeans == null ? 0 : informationBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.information_list_item, null);
        TextView title = view.findViewById(R.id.title);
        TextView author_name = view.findViewById(R.id.author_name);
        ImageView thumbnail_pic_s = view.findViewById(R.id.thumbnail_pic_s);
        title.setText(informationBeans.get(i).getTitle());
        author_name.setText(informationBeans.get(i).getAuthor_name());
        ImageLoaderManager.loadImage(context, informationBeans.get(i).getThumbnail_pic_s(), thumbnail_pic_s);
        Log.e("information",informationBeans.get(i).toString());
        return view;
    }
}
