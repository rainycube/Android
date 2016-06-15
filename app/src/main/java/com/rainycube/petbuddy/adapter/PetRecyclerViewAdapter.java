package com.rainycube.petbuddy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rainycube.petbuddy.R;
import com.rainycube.petbuddy.dataset.PetItem;
import com.rainycube.petbuddy.dataset.PetItemView;

import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.Realm;

/**
 * Created by SBKim on 2016-06-15.
 */
public class PetRecyclerViewAdapter extends RealmSearchAdapter<PetItem, PetRecyclerViewAdapter.ViewHolder> {

    public PetRecyclerViewAdapter(
            Context context,
            Realm realm,
            String filterColumnName) {
        super(context, realm, filterColumnName);
    }

    public class ViewHolder extends RealmSearchViewHolder {

        private PetItemView petItemView;

        public ViewHolder(FrameLayout container, TextView footerTextView) {
            super(container, footerTextView);
        }

        public ViewHolder(PetItemView petItemView) {
            super(petItemView);
            this.petItemView = petItemView;
        }
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        ViewHolder vh = new ViewHolder(new PetItemView(viewGroup.getContext()));
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        PetItem pet = realmResults.get(position);
        viewHolder.petItemView.bind(pet);
    }

    @Override
    public ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.footer_view, viewGroup, false);
        return new ViewHolder((FrameLayout) v, (TextView) v.findViewById(R.id.footer_text_view));
    }

    @Override
    public void onBindFooterViewHolder(ViewHolder holder, int position) {
        super.onBindFooterViewHolder(holder, position);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }
        );
    }

}