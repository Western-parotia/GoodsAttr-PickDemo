package com.example.dsa1

import android.util.SparseArray
import androidx.collection.ArraySet
import androidx.core.util.forEach
import com.example.dsa1.ext.getSelectedData

internal object CombinationAttrsUtils {

    /**
     * 重新计算attr的是否可有效的状态
     */
    fun refreshAttrsClickState(
        combinationList: List<List<String>>,
        attrsList: List<AttrSelectedEntity>
    ) {
        //用户已选择的item id
        //key：所在position，value：选中的id
        val selectedIdMap = SparseArray<String>()
        //所有可有效的item id
        //如果你的属性id不同行可能会重复，则再套个SparseArray就行了
        val canClickIdSet = ArraySet<String>()

        //获取用户已选择数据
        attrsList.forEachIndexed { index, entity ->
            val selectedData = entity.childList.getSelectedData()
            if (selectedData != null) {
                selectedIdMap.append(index, selectedData.id)
            }
        }

        //获取用户所有有效的数据
        combinationList.forEach { combinationItemList ->
            var invalidCount = 0 //组合匹配失败个数
            var invalidPosition = 0 //组合匹配失败的位置
            run {
                selectedIdMap.forEach { position, selectedId ->
                    if (combinationItemList.getOrNull(position) != selectedId) {
                        invalidCount++
                        invalidPosition = position
                        if (invalidCount >= 2) {
                            //2个及以上就没必要再遍历了
                            return@run
                        }
                    }
                }
            }
            when (invalidCount) {
                0 -> { //该组合完全匹配,当前组合所有的attr都有效
                    combinationItemList.forEach { id ->
                        canClickIdSet.add(id)
                    }
                }
                1 -> { //该组合有一个不匹配,不匹配的attr有效
                    combinationItemList.getOrNull(invalidPosition)?.let {
                        canClickIdSet.add(it)
                    }
                }
                else -> { //两个以上不匹配均默认无效
                }
            }
        }

        //设置是否有库存
        attrsList.forEach { entity ->
            entity.childList.forEach {
                it.isValidButton = canClickIdSet.contains(it.id)
            }
        }
    }
}