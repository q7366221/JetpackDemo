package com.example.myapplication.ui.nav.mine.coin;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.CoinBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class CoinViewModel extends BaseViewModel {

    private MutableLiveData<CoinBean> mCoinList;
    private List<CoinBean.CoinDataBean> mList;

    private int mPage;

    public CoinViewModel() {
        mPage = 0;
        mList = new ArrayList<>();
        mCoinList = new MutableLiveData<>();
    }

    public LiveData<CoinBean> getCoinList() {
        return mCoinList;
    }

    /**
     * 刷新
     */
    public void refreshData(Boolean refresh) {
        if (refresh) {
            mPage = 0;
        } else {
            mPage++;
        }
        mRefresh = true;
        queryCoinList();
    }

    /**
     * 重新加载
     */
    @Override
    public void reloadData() {
        loadData();
    }

    /**
     * 第一次加载数据
     */
    public void loadData() {
        loadState.postValue(LoadState.LOADING);
        mPage = 0;
        mRefresh = false;
        queryCoinList();

    }

    /**
     * 获取积分列表详情
     */
    private void queryCoinList() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        HttpRequest.getInstance()
                .queryCoinList(mPage)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<CoinBean>>() {
                    @Override
                    public void success(HttpBaseResponse<CoinBean> bean) {

                        if (bean != null && bean.errorCode == 0) {
                            loadState.postValue(LoadState.SUCCESS);
                            if (mPage == 0) {
                                //第一次加载或刷新成功 清空列表，重新载入数据，设置刷新成功状态
                                mList.clear();
                                mList.addAll(bean.data.getDatas());
                                mCoinList.postValue(bean.data);
                            } else {
                                //添加数据，设置下拉加载成功状态
                                mList.addAll(bean.data.getDatas());
                                bean.data.setDatas(mList);
                                mCoinList.postValue(bean.data);
                            }
                        } else {
                            loadState.postValue(LoadState.NO_DATA);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }
}
