package com.zhiwen.bilibilivideo.ext

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


// 创建Fragment的委托
// 返回值
//fun <T: ViewBinding>


// 定义委托类
// 第一个泛型，表示这个属性是哪个类的，这里是Fragment类的
// 第二个泛型，表示
class ViewBindingDelegate: ReadOnlyProperty<Fragment, ViewBinding> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): ViewBinding {
        TODO("Not yet implemented")
    }

}