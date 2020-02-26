package com.example.myapplication.base;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cjj.MaterialRefreshLayout;
import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.base.adapter.BasePagerAdapter;
import com.example.myapplication.enums.RefreshState;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.HomeBanner;
import com.example.myapplication.http.bean.home.BannerData;
import com.example.myapplication.ui.adapter.BannerViewHolder;
import com.example.myapplication.util.CommonUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author : devel
 * @date : 2020/2/19 10:18
 * @desc :
 */
public class BindingAdapterUtil {


    /**
     * 设置ViewPager的数据列表
     *
     * @param viewPager ViewPager
     * @param dataList  数据列表
     * @param <T>       数据类型
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter("dataList")
    public static <T> void setDataList(ViewPager viewPager, List<T> dataList) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter instanceof BasePagerAdapter) {
            BasePagerAdapter basePagerAdapter = (BasePagerAdapter) adapter;
            basePagerAdapter.setDataList(dataList);
        }
    }

    /**
     * 设置RefreshLayout的刷新状态
     *
     * @param refreshLayout RefreshLayout
     * @param refreshState  刷新状态
     */
    @BindingAdapter("refreshState")
    public static void setRefreshState(MaterialRefreshLayout refreshLayout, RefreshState refreshState) {
        if (refreshState == null) {
            return;
        }
        switch (refreshState) {
            case REFRESH_END:
                refreshLayout.finishRefresh();
                break;

            case LOAD_MORE_END:
                refreshLayout.finishRefreshLoadMore();
                break;

            default:
                break;
        }
    }


    /**
     * 设置RefreshLayout的加载更多
     *
     * @param refreshLayout RefreshLayout
     * @param hasMore       true表示还有更多，false表示没有更多了
     */
    @BindingAdapter("hasMore")
    public static void setHasMore(MaterialRefreshLayout refreshLayout, Boolean hasMore) {
        if (hasMore != null) {
            refreshLayout.setLoadMore(hasMore);
        }
    }


    @BindingAdapter("loadImage")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }


    @BindingAdapter("loadImageGoss")
    public static void setImageUrlWithGoss(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(imageView);
    }


    @BindingAdapter("visibility")
    public static void setViewVisibility(View view, Boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("bannerData")
    public static void setbBannerData(MZBannerView banner, BannerData bannerData) {
        if (bannerData == null || bannerData.getBannerData() == null) {
            return;
        }
        List<HomeBanner> movies = bannerData.getBannerData();
        banner.setPages(movies, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        banner.start();
    }

    @BindingAdapter("articleTag")
    public static void setArticleTag(TextView view, ArticleBean articleBean) {
        if (articleBean != null && articleBean.getTags() != null) {
            if (articleBean.getTags().size() == 1) {
                view.setText(articleBean.getTags().get(0).getName());
            } else if (articleBean.getTags().size() >= 2) {
                String str = articleBean.getTags().get(0).getName() + articleBean.getTags().get(1).getName();
                view.setText(str);
            } else {
                view.setText("其他");
            }
        }
    }


    @BindingAdapter("todoGrade")
    public static void setTodoGrade(ImageView view, int priority) {
        if (priority == 0) {
            view.setImageResource(R.mipmap.ic_star);
        } else {
            view.setImageResource(R.mipmap.ic_star_outline);
        }
    }


    @BindingAdapter("todoCheck")
    public static void setToDoDone(CheckBox view, int status) {
        if (status == 0) {
            view.setChecked(false);
        } else {
            view.setChecked(true);
        }
    }


    @BindingAdapter("todoStatus")
    public static void setTodoStatus(TextView view, int status) {
        if (status == 0) {
            view.setText("未完成");
            view.setTextColor(App.getContext().getResources().getColor(R.color.app_color_theme_1));
        } else {
            view.setText("已完成");
            view.setTextColor(App.getContext().getResources().getColor(R.color.color_blue));
        }
    }

    @BindingAdapter("coinCount")
    public static void setCoinCount(TextView view, int count) {
        view.setText(count + "分");
    }

    @BindingAdapter("coinLeave")
    public static void setCoinLeave(TextView view, int count) {
        view.setText("LV" + count);
    }

    @BindingAdapter("coinRank")
    public static void setCoinRank(TextView view, int count) {
        if (count == 1) {
            view.setText("");
            view.setBackground(App.getContext().getDrawable(R.mipmap.ic_rank_first));
        } else if (count == 2) {
            view.setText("");
            view.setBackground(App.getContext().getDrawable(R.mipmap.ic_rank_second));

        } else if (count == 3) {
            view.setText("");
            view.setBackground(App.getContext().getDrawable(R.mipmap.ic_rank_thred));
        } else {
            view.setText("" + count);
            view.setBackground(null);
        }
    }

}
