package com.dreamlive.copyjokes.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dreamlive.copyjokes.R
import com.dreamlive.jokes.entity.ClassifyData
import com.dreamlive.jokes.utils.CommonUtils.Companion.dateToString

/**
 * Created by win10 on 2017/7/21.
 */

class ClassifyDataAdapter(data: List<ClassifyData>) : BaseQuickAdapter<ClassifyData, BaseViewHolder>(R.layout.item_classify_data_card, data) {
    override fun convert(helper: BaseViewHolder, item: ClassifyData) {
        var imageIv: ImageView = helper.getView(R.id.item_welfare_img)
        if (item.type == "福利") {
            helper.setVisible(R.id.item_type_welfare_rl, true)
            helper.setVisible(R.id.item_type_not_welfare_rl, false)
            Glide.with(mContext).load(item.url).into(imageIv)
        } else {
            helper.setVisible(R.id.item_type_welfare_rl, false)
            helper.setVisible(R.id.item_type_not_welfare_rl, true)
            var imageIv: ImageView = helper.getView(R.id.item_img)
            if (item != null && item.images != null && item.images!!.size > 0) {
                imageIv!!.visibility = View.VISIBLE
                Glide.with(mContext).load(item.images!!.get(0)).into(imageIv)
            } else {
                imageIv!!.visibility = View.GONE
            }
            helper.setText(R.id.item_content, item.desc)
            helper.setText(R.id.item_type, item.type)

            helper.setText(R.id.item_date, dateToString(item.createdAt, "yyyy-MM-dd HH:mm"))
        }
    }
}

