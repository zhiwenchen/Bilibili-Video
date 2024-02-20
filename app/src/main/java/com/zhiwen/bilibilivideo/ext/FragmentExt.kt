package com.zhiwen.bilibilivideo.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zhiwen.bilibilivideo.logd
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


// 创建Fragment的委托
// 返回值
//fun <T: ViewBinding>


// 定义委托类
// 第一个泛型，表示这个属性是哪个类的，这里是Fragment类的
// 第二个泛型，表示这个属性的类型，这里是ViewBinding的子类（父类不能赋值给子类对象，所以需要定义成泛型）
// <>定义的是泛型
class ViewBindingDelegate<VB:ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
): ReadOnlyProperty<Fragment, VB> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        logd("getValue")
        return bindingInflater(thisRef.layoutInflater,null, false)
    }

}