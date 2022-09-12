package com.example.dsa1

import android.util.SparseArray
import androidx.collection.ArraySet
import androidx.core.util.forEach
import com.example.dsa1.ext.all
import com.example.dsa1.ext.getSelectedData

internal object CombinationAttrsUtils {

    /**
     * 重新计算attr的是否可有效的状态
     * 优化版穷举：遍历所有sku根据失败数来决定每个属性是否有库存
     */
    fun refreshAttrsClickState(
        combinationList: List<List<String>>,
        attrsList: List<AttrSelectedEntity>
    ) {
        var forEachCount = 0


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

            forEachCount++
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

                    forEachCount++
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

                forEachCount++
            }
        }

        println("优化版穷举方式：本次遍历次数：$forEachCount")
    }

    /**
     * 重新计算attr的是否可有效的状态
     * 直接穷举：直接遍历每个属性判断是否有库存
     */
    fun refreshAttrsClickState2(
        combinationList: List<List<String>>,
        attrsList: List<AttrSelectedEntity>
    ) {
        var forEachCount = 0

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

            forEachCount++
        }

        attrsList.forEachIndexed { index, entity ->
            entity.childList.forEach { childEntity ->
                val entityMap = selectedIdMap.clone()
                entityMap.put(index, childEntity.id)
                run {
                    combinationList.forEach {
                        val allContains =
                            entityMap.all { attrIndex, id ->
                                forEachCount++
                                it.getOrNull(attrIndex) == id
                            }
                        if (allContains) {
                            canClickIdSet.add(childEntity.id)
                            return@run
                        }
                    }
                }
            }
        }

        //设置是否有库存
        attrsList.forEach { entity ->
            entity.childList.forEach {
                it.isValidButton = canClickIdSet.contains(it.id)

                forEachCount++
            }
        }

        println("直接穷举方式：本次遍历次数：$forEachCount")
    }
}