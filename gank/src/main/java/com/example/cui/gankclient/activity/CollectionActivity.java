package com.example.cui.gankclient.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.cui.gankclient.R;
import com.example.cui.gankclient.adapter.CollectAdapter;
import com.example.cui.gankclient.bean.Collect;
import com.example.cui.gankclient.bean.ResultsBean;
import com.wang.avi.AVLoadingIndicatorView;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN;


public class CollectionActivity extends BaseActivity {
    private static final String TAG = CollectionActivity.class.getSimpleName();
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.android_recycler)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);

        avi.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final List<Collect> mData = DataSupport.findAll(Collect.class);

        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag start");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.d(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "drag end");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);
            }
        };
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);

        final CollectAdapter mAdapter = new CollectAdapter(mData);

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "view swiped start: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
//                holder.setTextColor(R.id.tv, Color.BLACK);

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.d(TAG, "View Swiped: " + pos);
                Collect collect = mAdapter.getData().get(pos);

                DataSupport.deleteAll(Collect.class,"url=?",collect.getUrl());
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(CollectionActivity.this, R.color.colorPrimaryDark));
//                canvas.drawText("Just some text", 0, 40, paint);
            }
        };

        //添加打开动画
        mAdapter.openLoadAnimation(ALPHAIN);
        //列表动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.enableDragItem(mItemTouchHelper);
 //       mAdapter.setOnItemDragListener(listener);
//        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));

        recyclerView.setAdapter(mAdapter);
        avi.hide();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(CollectionActivity.this, WebActivity.class);
                Collect bean = (Collect) adapter.getData().get(position);

                ResultsBean resultsBean = new ResultsBean();
                resultsBean.setUrl(bean.getUrl());
                resultsBean.setDesc(bean.getDesc());

                intent.putExtra("bean", resultsBean);
                startActivity(intent);
            }
        });

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
