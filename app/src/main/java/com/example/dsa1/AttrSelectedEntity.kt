package com.example.dsa1

class AttrSelectedEntity(val title: String, val childList: ArrayList<ChildEntity>) {

    /**
     * @param id 唯一标识
     */
    class ChildEntity(
        val text: String,
        val id: String
    ) {
        /**
         * 是否是有效的按钮
         */
        var isValidButton = false

        /**
         * 是否被选中了
         */
        var isSelected = false

        /**
         * 取反
         */
        fun inverterSelected() {
            isSelected = !isSelected
        }
    }
}