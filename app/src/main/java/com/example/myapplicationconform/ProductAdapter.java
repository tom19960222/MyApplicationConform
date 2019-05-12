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
    private int num = 0;

    private List<productName> getData() {

        gv = (GlobalVariable)context1.getApplicationContext();

        Call<productNameSchema> call = gv.getApi().getProductName();
        final ProductAdapter PA = this;

        call.enqueue(new Callback<productNameSchema>() {
            @Override
            public void onResponse(Call<productNameSchema> call, Response<productNameSchema> response) {
                result = response.body().getProductName();
                PA.data = result;
                PA.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<productNameSchema> call, Throwable t) {
//                Toast.makeText(context1, t.toString(), Toast.LENGTH_LONG);

            }
        });
        return result;
    }

    static class ViewHolder {
        public ImageView icon;
        public TextView Pid;
        public TextView Pname;
    }

//    private List<Map<String, Object>> data;
    private List<productName> data;
    private LayoutInflater mInflater = null;
    private Context context1;

    public ProductAdapter(Context context) {
        context1 = context;
        this.data = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        getData();
    }

    @Override
    public int getCount() {
        // How many items are in the data set represented by this Adapter.(在此适配器中所代表的数据集中的条目数)
        return this.data.size();

    }

    @Override
    public productName getItem(int position) {
        // Get the data item associated with the specified position in the data set.(获取数据集中与指定索引对应的数据项)
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
//        data = getData();
        // Get the row id associated with the specified position in the list.(取在列表中与指定索引对应的行id)
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
//        Picasso.get().load(gv.getUrl()+data.get(position).getIcon()).into(holder.icon);
        holder.Pid.setText(result.get(position).toString());
        holder.Pname.setText( result.get(position).toString());
        return convertView;
    }
}
