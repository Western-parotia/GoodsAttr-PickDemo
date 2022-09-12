package com.example.dsa1.ext

import android.util.SparseArray
import androidx.annotation.IntRange
import androidx.core.util.forEach
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

/**
 * 参考map的all拓展
 * @param emptyBoolean 当为空时默认返回值
 * @return 当全部遍历为true时返回true，否则返回false
 */
inline fun <T> SparseArray<T>?.all(
    emptyBoolean: Boolean = true,
    predicate: (key: Int, value: T) -> Boolean
): Boolean {
    this?.forEach { key, value ->
        if (!predicate(key, value)) return false
    }
    return emptyBoolean
}