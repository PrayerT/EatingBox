package com.example.eatingbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.eatingbox.Util;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button manager = root.findViewById(R.id.manager);
        TextView welcome = root.findViewById(R.id.hello);
        TextView loading = root.findViewById(R.id.loading);
        LinearLayout linearLayout = root.findViewById(R.id.step);

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Util.testlog("manager clicked");
            }
        });

        //绑定layout
        return root;

    }
}
