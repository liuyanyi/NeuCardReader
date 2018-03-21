package com.wolfaonliu.cardreader;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mogician on 2018/3/21.
 */

public class InfoFragment extends Fragment {

    String name = "";
    String body = "";
    View view;
    TextView info_name;
    TextView info_body;

    @Override
    public void setArguments(Bundle args) {
        this.name = args.getString("name");
        this.body = args.getString("body", "");
        super.setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.info_fragment, container, false);
        info_name = view.findViewById(R.id.info_name);
        info_body = view.findViewById(R.id.info_body);
        setText();
        return view;
    }

    private void setText() {
        info_name.setText(name);
        info_body.setText(body);
    }

}
