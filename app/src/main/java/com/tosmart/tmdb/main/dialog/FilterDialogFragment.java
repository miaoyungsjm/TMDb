package com.tosmart.tmdb.main.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.DialogFilterAdapter;
import com.tosmart.tmdb.main.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ggz
 * @date 2020/10/26
 */
public class FilterDialogFragment extends DialogFragment {
    private final String TAG = getClass().getSimpleName();

    private MainViewModel mMainViewModel;
    private int mFilterIndex = 0;

    public static FilterDialogFragment getInstance() {
        return new FilterDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter, container, false);
        ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity());
        mMainViewModel = viewModelProvider.get(MainViewModel.class);
        mFilterIndex = mMainViewModel.getFilterIndex();
        Log.e(TAG, "onCreateView: mFilterIndex = " + mFilterIndex);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_dialog_filter_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        DialogFilterAdapter adapter = new DialogFilterAdapter(mFilterIndex);
        recyclerView.setAdapter(adapter);

        // 刷新数据源
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mFilterIndex = position;
                mMainViewModel.mFilterFlag.setValue(position);
                dismiss();
            }
        });
    }

    public void show(FragmentManager supportFragmentManager) {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.add(this, TAG);
        ft.commit();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
