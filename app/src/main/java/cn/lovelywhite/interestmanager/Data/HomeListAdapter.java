package cn.lovelywhite.interestmanager.Data;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Vector;

public class HomeListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public View getView(int position, View convertView, ViewGroup parent, Vector<String> data) {
        getView(position,convertView,parent);
        return null;
    }
}
