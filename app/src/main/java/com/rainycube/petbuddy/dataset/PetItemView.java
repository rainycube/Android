package com.rainycube.petbuddy.dataset;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rainycube.petbuddy.R;

/**
 * Created by SBKim on 2016-06-15.
 */
public class PetItemView extends RelativeLayout {

//    @BindView(R.id.name)
    TextView name;

//    @BindView(R.id.type)
    TextView type;

//    @BindView(R.id.gender)
    TextView gender;

//    @BindView(R.id.location)
    TextView location;

    public PetItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.pet_item_view, this);
        name = (TextView)findViewById(R.id.name);
        type = (TextView)findViewById(R.id.type);
        gender = (TextView)findViewById(R.id.gender);
        location = (TextView)findViewById(R.id.location);
    }

    public void bind(PetItem item) {
        name.setText(item.getPetName());
        type.setText(item.getPetType());
        gender.setText(item.getPetGender());
        location.setText(item.getTradeLocation());
    }
}