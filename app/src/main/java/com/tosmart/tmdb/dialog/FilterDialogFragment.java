package com.tosmart.tmdb.dialog;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.DialogFilterAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/26
 */
public class FilterDialogFragment extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private static final String KEY = "filter_index";

    private int mFilterIndex = 0;

    public static FilterDialogFragment getInstance(int filterIndex) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, filterIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFilterIndex = bundle.getInt(KEY);
            Log.d(TAG, "onCreateView: mFilterIndex=" + mFilterIndex);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_dialog_filter_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DialogFilterAdapter(mFilterIndex));
    }

    public void show(FragmentManager supportFragmentManager) {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.add(this, TAG);
        ft.commit();
    }
}
