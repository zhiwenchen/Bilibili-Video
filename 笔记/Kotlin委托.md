# 委托
> 参考文章[5分钟速通Kotlin委托](https://juejin.cn/post/7223258679259873317?searchId=2024020614500145A96652D994CB123B37)
## Java实现委托

## Kotlin实现类委托

## Kotlin实现属性委托
定义一个委托类
```kotlin

 class MyDelegate {


    operator fun getValue(thisRef:Any?, peroperty: KProperty<*>):String {
        
    }
    
    operator fun setValue() {
        
    }
}
```
- thisRef:
- property:
- value:

## 原理
