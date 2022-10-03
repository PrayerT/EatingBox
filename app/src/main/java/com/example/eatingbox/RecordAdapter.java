package com.example.eatingbox;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TreeSet;

public class RecordAdapter extends ListActivity {

    private MyCustomAdapter mAdapter;
    MainActivity mainActivity = new MainActivity();
    private String name = mainActivity.acc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyCustomAdapter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("123.56.231.80", 4444);

                    OutputStream sendData = socket.getOutputStream();
                    sendData.write((name + "\r\n").getBytes(StandardCharsets.UTF_8));
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    String recentRecord = bufferedReader.readLine();

                    sendData.close();
                    bufferedReader.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        for (int i = 1; i < 50; i++) {
            mAdapter.addItem("item " + i);
        }
        setListAdapter(mAdapter);
    }

    private class MyCustomAdapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;
        private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
        private int itemCount = 0;

        private ArrayList mData = new ArrayList();
        private LayoutInflater mInflater;

        private TreeSet mSeparatorsSet = new TreeSet();

        public MyCustomAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position).toString();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            System.out.println("getView " + position + " " + convertView + " type = " + type);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.record_item, null);
                holder.textView = (TextView) convertView.findViewById(R.id.recordname);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mData.get(position).toString());
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView textView;
    }
}