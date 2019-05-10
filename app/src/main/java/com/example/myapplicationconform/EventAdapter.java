package com.example.myapplicationconform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventAdapter extends BaseAdapter {
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map,map1;
        map = new HashMap<String, Object>();
        map1 = new HashMap<String, Object>();
        map.put("date", "2019/05/16");
        map.put("title", "資訊週");
        map.put("DetailUrl", "http://www.csie.tku.edu.tw/news/news.php?Sn=1700");
        map.put("EnterUrl", "http://www.csie.tku.edu.tw/news/news.php?class=101");

        map1.put("date", "2019/05/06");
        map1.put("title", "demo 繳交");
        map1.put("DetailUrl", "http://www.csie.tku.edu.tw/down/archive.php?class=101");
        map1.put("EnterUrl", "http://www.csie.tku.edu.tw/photo/album.php?CID=1");
        list.add(map1);
        list.add(map);
        return list;
    }

    static class ViewHolder {
        public TextView date;
        public TextView title;
        public Button btnAll;
        public Button btn;
    }

    private List<Map<String, Object>> data;
    private LayoutInflater mInflater = null;

    private Context context1;

    public EventAdapter(Context context) {
        data = getData();
        context1=context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        data = getData();
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.event_listitem, null);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.btnAll = (Button) convertView.findViewById(R.id.btnAll);
            holder.btn = (Button) convertView.findViewById(R.id.btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.date.setText((String) data.get(position).get("date"));
        holder.title.setText((String) data.get(position).get("title"));
        holder.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context1,EventWebView.class);

                Bundle bundle = new Bundle();
                bundle.putString("url",(String) data.get(position).get("DetailUrl"));
                intent.putExtras(bundle);

                context1.startActivity(intent);
            }
        });;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context1,EventWebView.class);

                Bundle bundle = new Bundle();
                bundle.putString("url",(String) data.get(position).get("EnterUrl"));
                intent.putExtras(bundle);

                context1.startActivity(intent);
            }
        });
        return convertView;
    }


}
