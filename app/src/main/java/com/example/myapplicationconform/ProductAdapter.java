package com.example.myapplicationconform;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ProductAdapter extends BaseAdapter {

    private List<productName> result;
//    private List<String> test = new ArrayList<String>();;
    private Context context;
    private GlobalVariable gv;

    private void getData() {
         gv = (GlobalVariable)context1.getApplicationContext();

        Call<productNameSchema> call = gv.getApi().getProductName();

        final ProductAdapter PA = this;

        call.enqueue(new Callback<productNameSchema>() {
            @Override
            public void onResponse(Call<productNameSchema> call, Response<productNameSchema> response) {
                result = response.body().getProductName();
                PA.data = result;
//                Toast.makeText(context1, result.toString(), Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<productNameSchema> call, Throwable t) {
//                Toast.makeText(context1, t.toString(), Toast.LENGTH_LONG);

            }
        });
    }


    static class ViewHolder {
        public ImageView icon;
        public TextView Pid;
        public TextView Pname;
    }

//    private List<Map<String, Object>> data;
    private List<productName> data = new ArrayList<productName>();;
    private LayoutInflater mInflater = null;
    private Context context1;

    public ProductAdapter(Context context) {
        context1 = context;
        getData();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return data.size();

    }

    @Override
    public productName getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
//        data = getData();
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        data = getData();
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
        Picasso.get().load(gv.getUrl()+data.get(position).getIcon()).into(holder.icon);
        holder.Pid.setText(data.get(position).getPid().toString());
        holder.Pname.setText( data.get(position).getPname().toString());
//        holder.Pname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println( "你選的是"+ data.get(position).getPid());
//                Toast.makeText(context1, "你選的是" +  data.get(position).getPid() , Toast.LENGTH_SHORT).show();
//            }
//        });
        return convertView;
    }
}
