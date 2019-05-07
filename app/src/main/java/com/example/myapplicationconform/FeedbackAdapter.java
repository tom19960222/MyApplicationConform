package com.example.myapplicationconform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class FeedbackAdapter extends BaseAdapter {

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map,map1;

        map = new HashMap<String, Object>();
        map.put("UserIcon", R.drawable.ic_person_add_black_24dp);
        map.put("UserName", "i");
        map.put("Feedback", "test");
        list.add(map);

        map1 = new HashMap<String, Object>();
        map1.put("UserIcon", R.drawable.ic_group_black_24dp);
        map1.put("UserName", "i1");
        map1.put("Feedback", "test2");
        list.add(map1);
        return list;
    }


    static class ViewHolder {
        public ImageView Usericon;
        public TextView UserName;
        public TextView Feedback;
    }

    private List<Map<String, Object>> data;
    private LayoutInflater mInflater = null;

    private Context context;

    public FeedbackAdapter(Context context) {
        data = getData();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        data = getData();
        // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
        return data.size();

    }

    @Override
    public Object getItem(int position) {
        data = getData();
        // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        data = getData();
        // Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        data = getData();
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.feedback_listitem, null);
            holder.Usericon = (ImageView) convertView.findViewById(R.id.UserIcon);
            holder.UserName = (TextView) convertView.findViewById(R.id.UserName);
            holder.Feedback = (TextView) convertView.findViewById(R.id.Feedback);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.Usericon.setImageResource((Integer) data.get(position).get("UserIcon"));
        holder.UserName.setText((String) data.get(position).get("UserName"));
        holder.Feedback.setText((String) data.get(position).get("Feedback"));
        return convertView;
    }
}
