package com.moviesdb.moviesdbmvvm.activity.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class ViewModelAdapter extends RecyclerView.Adapter<ViewModelAdapter.MyViewHolder> {

    private final Map<Integer,Object> mGlobalObjects = new Hashtable<>();

    private final Map<Class, CellInfo> mCellInfoMap = new Hashtable<>();
    protected final List<Object> mItems = new LinkedList<>();


    private int mBeginUpdateItemsSize  = 0;

    protected void addGlobalItem(int bindingId, Object object) {
        mGlobalObjects.put(bindingId, object);
    }
    protected void registerCell(Class objectClass, @LayoutRes int layoutId,int bindingId){
        CellInfo cellInfo = new CellInfo();
        cellInfo.mBindingId = bindingId;
        cellInfo.mLayoutId = layoutId;
        mCellInfoMap.put(objectClass, cellInfo);
    }

    public void reload(){
        reload(null);
    }

    public void addItems(List<Object> list){
        mItems.addAll(list);
    }

    public void insertList(List<?> list){
        mItems.clear();
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    public abstract void reload(@Nullable SwipeRefreshLayout refreshLayout);

    protected void loadMore(){

    }

    protected void beginUpdate(){
        mBeginUpdateItemsSize = getItemCount();
    }

    protected void endUpdates() {
        int changed = Math.min(mBeginUpdateItemsSize, getItemCount());
        int diff = Math.max(mBeginUpdateItemsSize, getItemCount()) - changed;

        if (diff == 0 && changed > 1) {
            notifyDataSetChanged();
            return;
        }

        if (changed != 0) notifyItemRangeChanged(0, changed);

        if (diff > 0) {
            if (mBeginUpdateItemsSize > getItemCount()) {
                notifyItemRangeRemoved(changed, diff);
            } else {
                notifyItemRangeInserted(changed, diff);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        Object item = getItemAt(position);
        CellInfo cellInfo = getCellInfo(item);

        if (cellInfo.mBindingId != 0) {
            ViewDataBinding binding = viewHolder.getBinding();
            binding.setVariable(cellInfo.mBindingId, item);
        }

        if (position == getItemCount() - 2) loadMore();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected Object getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        CellInfo cellInfo = getCellInfo(getItemAt(position));
        return cellInfo.mLayoutId;
    }

    protected CellInfo getCellInfo(Object object) {
        for (Map.Entry<Class, CellInfo> entry : mCellInfoMap.entrySet()) {
            if (entry.getKey().isInstance(object))
                return entry.getValue();
        }
        return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        ViewDataBinding binding = holder.getBinding();
        for (Map.Entry<Integer, Object> entry : mGlobalObjects.entrySet()) {
            binding.setVariable(entry.getKey(), entry.getValue());
        }
        onBind(binding);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding mBinding;

        MyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        ViewDataBinding getBinding() {
            return mBinding;
        }

    }


    public static class CellInfo {
        @LayoutRes
        private int mLayoutId;
        private int mBindingId;


    }

    protected void onBind(ViewDataBinding viewGroup){

    }


}
