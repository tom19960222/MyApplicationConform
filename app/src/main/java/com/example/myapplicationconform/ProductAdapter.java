package com.example.myapplicationconform;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ProductAdapter extends BaseAdapter {

    private List<productName> result;
    private Context context;
    private GlobalVariable gv;
    private int num;

    private List<productName> getData() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://b750ac3d.ngrok.io/app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPIService api = retrofit.create(MyAPIService.class);

        Call<productNameSchema> call = api.getProductName();

        call.enqueue(new Callback<productNameSchema>() {
            @Override
            public void onResponse(Call<productNameSchema> call, Response<productNameSchema> response) {
                result = response.body().getProductName();
                num = result.size();
            }

            @Override
            public void onFailure(Call<productNameSchema> call, Throwable t) {

            }
        });
        return result;
    }

//    private List<Map<String, Object>> getData() {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map;
//
//
//            map = new HashMap<String, Object>();
//            map.put("Picon", R.drawable.p);
//            map.put("Pid", "1");
//            map.put("Pname", "test");
//            list.add(map);
//
//        return list;
//    }


    static class ViewHolder {
        public ImageView icon;
        public TextView Pid;
        public TextView Pname;
    }

//    private List<Map<String, Object>> data;
    private List<productName> data;
    private LayoutInflater mInflater = null;

    public ProductAdapter(Context context) {
        data = getData();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        data = getData();
        // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
        return num;

    }

    @Override
    public productName getItem(int position) {
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
            holder.icon = (ImageView) convertView.findViewById(R.id.UserIcon);
            holder.Pid = (TextView) convertView.findViewById(R.id.UserName);
            holder.Pname = (TextView) convertView.findViewById(R.id.Feedback);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.get().load("http://b750ac3d.ngrok.io/"+data.get(position).getIcon()).into(holder.icon);
        holder.Pid.setText(data.get(position).getPid().toString());
        holder.Pname.setText((String) data.get(position).getPname());
        return convertView;
    }
}
