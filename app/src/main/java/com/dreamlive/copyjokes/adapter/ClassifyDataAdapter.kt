package com.dreamlive.copyjokes.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dreamlive.copyjokes.R
import com.dreamlive.jokes.entity.ClassifyData

/**
 * Created by win10 on 2017/7/21.
 */

class ClassifyDataAdapter(data: List<ClassifyData>) : BaseQuickAdapter<ClassifyData, BaseViewHolder>(R.layout.item_classify_data_card, data) {
    override fun convert(helper: BaseViewHolder, item: ClassifyData) {
        var imageIv: ImageView = helper.getView(R.id.item_welfare_img)
    }

}

