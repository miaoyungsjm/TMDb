package com.tosmart.tmdb.main.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.tosmart.tmdb.BR;
import com.tosmart.tmdb.R;
import com.tosmart.tmdb.adapter.ListFavAdapter;
import com.tosmart.tmdb.adapter.OnItemClickListener;
import com.tosmart.tmdb.db.entity.Favorite;
import com.tosmart.tmdb.detail.DetailActivity;
import com.tosmart.tmdb.main.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tosmart.tmdb.detail.DetailActivity.KEY_ID;
import static com.tosmart.tmdb.detail.DetailActivity.KEY_TYPE;

/**
 * @author ggz
 * @date 2020/10/30
 */
public class SearchDialogFragment extends DialogFragment {
    private final String TAG = getClass().getSimpleName();

    private SearchViewModel mSearchViewModel;

    private ImageView mLogoIv;
    private ImageView mBackIv;
    private EditText mEditText;
    private TextView mSearchResultTv;

    private ListFavAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_search, container, false);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.vm, mSearchViewModel);
        binding.setVariable(BR.click, new ClickProxy());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int spacingLeft = SizeUtils.dp2px(24);
        int spacingTop = SizeUtils.dp2px(43);
        SpacingItemDecoration decoration =
                new SpacingItemDecoration(7, spacingLeft, spacingTop);

        mLogoIv = view.findViewById(R.id.iv_search_title_logo);
        mBackIv = view.findViewById(R.id.iv_search_title_back_btn);
        mEditText = view.findViewById(R.id.et_search_title_search_edit);
        mSearchResultTv = view.findViewById(R.id.tv_search_content_result);

        RecyclerView recyclerView = view.findViewById(R.id.rv_search_content);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.addItemDecoration(decoration);

        initAdapter();

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onResume();
    }

    private void initAdapter() {
        mAdapter = new ListFavAdapter();
        mAdapter.setOnItemClickListener(mListener);

        mSearchViewModel.mSearchResult.observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> list) {
                Log.d(TAG, "onChanged: search: " + list.size());
                mAdapter.setFavList(list);
                String show = String.format(
                        getResources().getString(R.string.str_search_result_show),
                        list.size());
                mSearchResultTv.setText(show);
            }
        });
    }

    public static SearchDialogFragment getInstance() {
        return new SearchDialogFragment();
    }

    public void show(FragmentManager supportFragmentManager) {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.add(this, TAG);
        ft.commit();
    }

    private OnItemClickListener mListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int id, int type) {
            Log.d(TAG, "onClick: id=" + id + ", type=" + type);
            jumpToDetail(id, type);
        }
    };

    private void jumpToDetail(int id, int type) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(KEY_ID, id);
        intent.putExtra(KEY_TYPE, type);
        startActivity(intent);
    }

    public class ClickProxy {
        public void itemClick(View view) {
            switch (view.getId()) {
                case R.id.ll_search_title_search_btn:
                    String keyword = mEditText.getText().toString();
                    if (!keyword.isEmpty()) {
                        mSearchViewModel.requestSearch(keyword);
                        mLogoIv.setVisibility(View.INVISIBLE);
                        mBackIv.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.iv_search_title_back_btn:
                    mLogoIv.setVisibility(View.VISIBLE);
                    mBackIv.setVisibility(View.INVISIBLE);
                    mEditText.setText("");
                    mSearchResultTv.setText("");
                    mAdapter.setFavList(new ArrayList<Favorite>());
                    break;
                default:
            }
        }
    }
}
