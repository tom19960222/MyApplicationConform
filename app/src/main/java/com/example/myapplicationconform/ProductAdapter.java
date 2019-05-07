package com.example.myapplicationconform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class ProductAdapter extends BaseAdapter {

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

            map = new HashMap<String, Object>();
            map.put("Picon", R.drawable.p);
            map.put("Pid", "1");
            map.put("Pname", "test");
            list.add(map);

        return list;
    }


    static class ViewHolder {
        public ImageView Picon;
        public TextView Pid;
        public TextView Pname;
    }

    private List<Map<String, Object>> data;
    private LayoutInflater mInflater = null;

    private Context context;

    public ProductAdapter(Context context) {
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
            holder.Picon = (ImageView) convertView.findViewById(R.id.UserIcon);
            holder.Pid = (TextView) convertView.findViewById(R.id.UserName);
            holder.Pname = (TextView) convertView.findViewById(R.id.Feedback);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.Picon.setImageResource((Integer) data.get(position).get("Picon"));
        holder.Pid.setText((String) data.get(position).get("Pid"));
        holder.Pname.setText((String) data.get(position).get("Pname"));
        return convertView;
    }
}
