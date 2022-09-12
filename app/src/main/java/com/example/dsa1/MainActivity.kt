package com.example.dsa1

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.dsa1.databinding.ActivityMainBinding
import com.example.dsa1.ext.getSelectedData

class MainActivity : AppCompatActivity() {
    val adapter = AttrTitleAdapter(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = ActivityMainBinding.inflate(layoutInflater, FrameLayout(this), false)
        setContentView(vb.root)

//        val (attrList, combinationList) = getABCDInfoData()

//        val (attrList, combinationList) = getColorInfoData()

        val (attrList, combinationList) = getABCInfoData()

        adapter.onChildClickedListener = { position, childPosition ->
            val selectedList = adapter.data.mapNotNull { it.childList.getSelectedData() }
            if (selectedList.size == adapter.data.size) {
                val ids = selectedList.map { it.id }
                if (combinationList.contains(ids)) {
                    //选择了有效的sku
                    "你选择的sku：$ids".toast()
                } else {
                    //选择了无效的sku
                    "无效sku".toast()
                }
            } else {
                //没有全选
            }
        }
        adapter.setNewData(attrList, combinationList)
        vb.rvList.adapter = adapter
    }

    private fun getABCInfoData(): Pair<ArrayList<AttrSelectedEntity>, ArrayList<List<String>>> {
        //        ABC示例
        val attrList = arrayListOf<AttrSelectedEntity>()
        (0..2).forEach {
            val title = ('A'.code + it).toChar().toString()
            val childList = arrayListOf<AttrSelectedEntity.ChildEntity>()
            val c1 = title + 1.toString()
            childList.add(AttrSelectedEntity.ChildEntity(c1, c1))
            val c2 = title + 2.toString()
            childList.add(AttrSelectedEntity.ChildEntity(c2, c2))
            attrList.add(AttrSelectedEntity(title, childList))
        }
        val combinationList = arrayListOf<List<String>>()
        combinationList.add(listOf("A1", "B1", "C2"))
        combinationList.add(listOf("A2", "B1", "C1"))
        combinationList.add(listOf("A1", "B2", "C1"))
        return Pair(attrList, combinationList)
    }

    private fun getColorInfoData(): Pair<ArrayList<AttrSelectedEntity>, ArrayList<List<String>>> {
        //        颜色套餐大小示例
        val attrList = arrayListOf<AttrSelectedEntity>()
        attrList.add(
            AttrSelectedEntity(
                "颜色",
                arrayListOf(
                    AttrSelectedEntity.ChildEntity("紫色", "A1"),
                    AttrSelectedEntity.ChildEntity("红色", "A2"),
                )
            )
        )
        attrList.add(
            AttrSelectedEntity(
                "大小",
                arrayListOf(
                    AttrSelectedEntity.ChildEntity("64G", "B1"),
                    AttrSelectedEntity.ChildEntity("128G", "B2"),
                )
            )
        )
        attrList.add(
            AttrSelectedEntity(
                "套餐",
                arrayListOf(
                    AttrSelectedEntity.ChildEntity("套餐一", "C1"),
                    AttrSelectedEntity.ChildEntity("套餐二", "C2"),
                )
            )
        )
        val combinationList = arrayListOf<List<String>>()
        combinationList.add(arrayListOf("A1", "B1", "C2"))
        combinationList.add(arrayListOf("A2", "B1", "C1"))
        combinationList.add(arrayListOf("A1", "B2", "C1"))
        return Pair(attrList, combinationList)
    }

    private fun getABCDInfoData(): Pair<ArrayList<AttrSelectedEntity>, ArrayList<List<String>>> {
        //        ABCD示例
        val attrList = arrayListOf<AttrSelectedEntity>()
        (0..3).forEach {
            val title = ('A'.code + it).toChar().toString()
            val childList = arrayListOf<AttrSelectedEntity.ChildEntity>()
            val c1 = title + 1.toString()
            childList.add(AttrSelectedEntity.ChildEntity(c1, c1))
            val c2 = title + 2.toString()
            childList.add(AttrSelectedEntity.ChildEntity(c2, c2))
            attrList.add(AttrSelectedEntity(title, childList))
        }
        val combinationList = arrayListOf<List<String>>()
        combinationList.add(listOf("A1", "B2", "C1", "D1"))
        combinationList.add(listOf("A2", "B2", "C2", "D2"))
        combinationList.add(listOf("A2", "B1", "C1", "D1"))
        return Pair(attrList, combinationList)
    }
}