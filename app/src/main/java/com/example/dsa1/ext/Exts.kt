package com.example.dsa1.ext

import androidx.annotation.IntRange
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.dsa1.AttrSelectedEntity

val BaseViewHolder.adapterLayoutPosition: Int get() = if (layoutPosition < 0) bindingAdapterPosition else layoutPosition

/**
 * 当前选择的position（单选逻辑）
 */
var <T : AttrSelectedEntity.ChildEntity> List<T>?.selectedPosition: Int
    set(value) {
        this?.forEachIndexed { index, bean ->
            bean.isSelected = index == value
        }
    }
    @IntRange(from = -1L)
    get() {
        this?.forEachIndexed { index, bean ->
            if (bean.isSelected) {
                return index
            }
        }
        return -1
    }

/**
 * 获取选择的那条数据（单选逻辑）
 */
fun <T : AttrSelectedEntity.ChildEntity> List<T>?.getSelectedData(): T? {
    return this?.getOrNull(selectedPosition)
}