package com.example.dsa1

import android.annotation.SuppressLint
import android.graphics.Paint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.dsa1.databinding.AdapterMainItemBinding
import com.example.dsa1.databinding.AdapterMainItemChildBinding
import com.example.dsa1.ext.adapterLayoutPosition
import com.example.dsa1.ext.selectedPosition

@SuppressLint("NotifyDataSetChanged")
class AttrTitleAdapter :
    BaseQuickAdapter<AttrSelectedEntity, BaseViewHolder>(R.layout.adapter_main_item) {
    private val combinationList = arrayListOf<List<String>>()

    /**
     * 每次点击后都回调
     */
    var onChildClickedListener: ((position: Int, childPosition: Int) -> Unit)? =
        null

    override fun convert(holder: BaseViewHolder, item: AttrSelectedEntity) {
        val vb = AdapterMainItemBinding.bind(holder.itemView)

        vb.tvTitle.text = item.title

        val adapter = ChildAdapter()
        adapter.setNewInstance(item.childList)
        adapter.setOnItemClickListener { _, _, childPosition ->
            val lastPosition = item.childList.selectedPosition
            if (lastPosition == childPosition) {
                item.childList[childPosition].inverterSelected()
                adapter.notifyItemChanged(childPosition)
            } else {
                item.childList.selectedPosition = childPosition
                adapter.notifyItemChanged(lastPosition)
                adapter.notifyItemChanged(childPosition)
            }

            //刷新属性信息
            CombinationAttrsUtils.refreshAttrsClickState(combinationList, data)
            notifyDataSetChanged()

            onChildClickedListener?.invoke(holder.adapterLayoutPosition, childPosition)
        }
        vb.rvItemList.adapter = adapter
    }

    /**
     * @param combinationList 组合的id集合，如：[[红色,mL],[红色,XL],[蓝色-XL]]
     */
    fun setNewData(
        list: ArrayList<AttrSelectedEntity>,
        combinationList: List<List<String>>
    ) {
        this.combinationList.clear()
        this.combinationList.addAll(combinationList)
        CombinationAttrsUtils.refreshAttrsClickState(combinationList, list)
        super.setNewData(list)
    }

    @Deprecated("见上方重载方法", ReplaceWith("setNewData(data,list)"), level = DeprecationLevel.ERROR)
    override fun setNewData(data: MutableList<AttrSelectedEntity>?) {
        super.setNewData(data)
    }

    class ChildAdapter :
        BaseQuickAdapter<AttrSelectedEntity.ChildEntity, BaseViewHolder>(R.layout.adapter_main_item_child) {
        override fun convert(holder: BaseViewHolder, item: AttrSelectedEntity.ChildEntity) {
            val vb = AdapterMainItemChildBinding.bind(holder.itemView)
            vb.tvChild.text = item.text
            when {
                !item.isValidButton -> {
                    vb.tvChild.paintFlags = vb.tvChild.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    if (holder.adapterLayoutPosition == data.selectedPosition) {
                        vb.tvChild.setBackgroundColor(0xffffaaaa.toInt())
                    } else {
                        vb.tvChild.setBackgroundColor(0xffffffff.toInt())
                    }
                    vb.tvChild.setTextColor(0xff999999.toInt())
                }
                holder.adapterLayoutPosition == data.selectedPosition -> {
                    vb.tvChild.paintFlags =
                        vb.tvChild.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    vb.tvChild.setBackgroundColor(0xffff6666.toInt())
                    vb.tvChild.setTextColor(0xffffffff.toInt())
                }
                else -> {
                    vb.tvChild.paintFlags =
                        vb.tvChild.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    vb.tvChild.setBackgroundColor(0xffffffff.toInt())
                    vb.tvChild.setTextColor(0xff333333.toInt())
                }
            }
        }
    }
}