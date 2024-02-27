package com.zhiwen.bilibilivideo.ext

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun<reified VM: ViewModel> invokeViewModel() = ViewModelDelegate<VM>(VM::class.java)

class ViewModelDelegate<VM:ViewModel>(private val clazz: Class<VM>) : ReadOnlyProperty<Fragment, VM>{

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VM {
        return ViewModelProvider(thisRef, ViewModelProvider.NewInstanceFactory())[clazz]
    }

}


// 创建Fragment的委托
// <>定义的是泛型
inline fun <reified T: ViewBinding> Fragment.invokeViewBinding() = ViewBindingDelegate<T>(T::class.java)

// 定义委托类
// 第一个泛型，表示这个属性是哪个类的，这里是Fragment类的
// 第二个泛型，表示这个属性的类型，这里是ViewBinding的子类（父类不能赋值给子类对象，所以需要定义成泛型）
// <>定义的是泛型
class ViewBindingDelegate<VB:ViewBinding>(
    private val clazz: Class<VB>
): ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        // 改造成自动获取
        if (binding == null) {
            try {
                // 调用ViewBinding的inflate方法
                binding = (clazz.getMethod("inflate", LayoutInflater::class.java)
                    .invoke(null, thisRef.layoutInflater) as VB)
            } catch (e: java.lang.IllegalStateException) {
                e.printStackTrace()
                throw e
            }
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    binding = null
                }
            })
        }
        return binding!!
    }

}