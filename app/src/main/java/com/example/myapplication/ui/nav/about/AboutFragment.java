package com.example.myapplication.ui.nav.about;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentAboutBinding;
import com.example.myapplication.entity.GitHubProject;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapter;
import com.example.myapplication.util.GlideUtil;
import com.orhanobut.hawk.Hawk;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AboutFragment extends BaseFragment<FragmentAboutBinding, AboutViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initRefreshLayout();
        initRecyclerView();

//        GlideUtil.loadImageWithGoss(mDataBinding.ivBg, Hawk.get(Code.HawkCode.SPLASH_IMAGE_URL));
    }


    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mDataBinding.refreshLayout.setEnableLoadMore(false);
        mDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDataBinding.refreshLayout.finishRefresh(1000);
            }
        });

        mDataBinding.refreshLayout.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

                if (newState == RefreshState.TwoLevelFinish) {
                    mDataBinding.secondFloorContent.animate().alpha(0).setDuration(1000);
                }
            }
        });

        mDataBinding.header.openTwoLevel(true);
        mDataBinding.header.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getActivity(), "发现了什么神奇的东西", Toast.LENGTH_SHORT).show();
                mDataBinding.secondFloorContent.animate().alpha(1).setDuration(2000);
                return true;
            }
        });
    }

    private void initRecyclerView() {

        CommonAdapter commonAdapter = new CommonAdapter<GitHubProject>(R.layout.item_open_source_project, com.example.myapplication.BR.OpenSource) {
            @Override
            public void addListener(View root, GitHubProject itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetailsActivity.start(getActivity(), itemData.getAddress());
                    }
                });
            }
        };
        mDataBinding.recycler.setAdapter(commonAdapter);
        mDataBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getProjectList().observe(this, new Observer<List<GitHubProject>>() {
            @Override
            public void onChanged(List<GitHubProject> list) {
                commonAdapter.onItemDatasChanged(list);
            }
        });
    }
}