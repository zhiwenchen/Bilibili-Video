# 依赖注入
对比Spring

## 实例化ViewModel
在Android开发中，使用ViewModel可以有效地管理UI相关的数据。ViewModel的一个挑战是实例化时如何传递参数，因为ViewModel的构造通常是由系统通过ViewModelProvider处理的，而不是直接由开发者创建。幸运的是，Android Architecture Components 提供了一种解决方案：ViewModelProvider.Factory接口和ViewModel的新实例化方式。

使用ViewModelProvider.Factory初始化带参数的ViewModel
1. 定义ViewModel

首先，定义一个接受参数的ViewModel。例如：

```kotlin
class MyViewModel(private val repository: MyRepository) : ViewModel() {
    // 使用repository进行操作
}
```
2. 创建ViewModel的Factory

接着，创建一个ViewModelProvider.Factory的实现，用于实例化你的ViewModel：

```kotlin
class MyViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```
3. 在Fragment或Activity中使用Factory实例化ViewModel

最后，在Fragment或Activity中，使用你的Factory来获取ViewModel的实例。

```kotlin
class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity)

        // 假设你有一个Repository的实例
        val repository = MyRepository()

        // 使用Factory获取ViewModel实例
        val viewModel = ViewModelProvider(this, MyViewModelFactory(repository))
                            .get(MyViewModel::class.java)

        // 现在你的ViewModel已经准备好了，并且已经用传入的repository初始化
    }
}
```
使用Hilt依赖注入初始化带参数的ViewModel（推荐）
如果你的项目使用了Hilt依赖注入框架，那么初始化带参数的ViewModel会更简单。首先，确保你的项目已经设置并启用了 Hilt。

1. 定义ViewModel

使用@HiltViewModel注解你的ViewModel，并且确保构造函数有需要的依赖项，并且这些依赖项已经通过Hilt模块提供。

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    // 使用repository进行操作
}
```
2. 在Activity或Fragment中获取ViewModel实例

在Hilt集成的Activity或Fragment中，可以直接使用by viewModels()或by activityViewModels()委托来获取ViewModel实例，而无需显式使用Factory。

```kotlin
@AndroidEntryPoint
class MyActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity)

        // 现在你的ViewModel已经准备好了，并且已经用构造函数传入的repository初始化
    }
}
```
使用Hilt进行依赖注入可以极大简化ViewModel的初始化，特别是对于带参数的ViewModel，不仅减少了样板代码，还加强了代码的可测试性。

## by关键字的原理
在Kotlin中，by关键字引入了委托模式（Delegation）。by viewModels()使用这种模式对ViewModel的创建和生命周期进行管理。下面详细介绍by关键字的作用和原理，以及viewModels()的用途和工作原理。

by 关键字（委托模式）
在Kotlin中，by关键字用于属性委托。当一个属性被委托时，所有对该属性的访问（读取或写入）都将转发给另一个对象，称为委托对象。这种模式允许将属性的读取和写入逻辑委托给指定的逻辑，从而实现代码的复用和分离关注点。

Kotlin标准库提供了一些内置的委托，如lazy()、observable()、notNull等，这些都是利用了属性委托的功能。用户也可以定义自己的委托。

属性委托需要实现两个方法：

- getValue()函数，用于属性读取操作。
- setValue()函数，用于属性写入操作。（只对var属性需要）

示例：

```kotlin
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
}

class Example {
    val myProperty: String by Delegate()
}
```
在这个示例中，访问Example对象的myProperty属性时，实际上会调用Delegate的getValue()方法来提供值。

viewModels() 委托
viewModels() 是一个扩展函数，用于在Activity和Fragment中方便地获取ViewModel的实例。这个函数返回一个ViewModelLazy对象，它是一个由by关键字支持的委托，负责获取或创建ViewModel的实例。

viewModels()有助于实现ViewModel的按需创建和生命周期感知。具体来说，当首次访问相关属性时，ViewModel会被创建并与Activity或Fragment的生命周期关联起来，保证了ViewModel的生命周期管理和数据保持。

viewModels()使用了ViewModelProvider，这是一个工厂类，用于创建和获取ViewModel实例。ViewModelProvider会根据ViewModel的类型来获取现有的ViewModel或创建一个新的实例，并确保ViewModel与组件（Activity或Fragment）的生命周期绑定，从而在配置更改（如屏幕旋转）时保持数据状态。

原理
当你写下以下代码时：

```kotlin
class MyActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
}
```
- by viewModels()表达式根据Activity或Fragment的情况调用相应的viewModels()扩展函数。
- 这个调用返回一个ViewModelLazy实例，它是Kotlin的Lazy委托的一个特化版本，专为ViewModel设计。
- 在首次访问viewModel属性时，ViewModelLazy委托的getValue()方法被调用，它通过ViewModelProvider来获取或创建MyViewModel的实例。
- 由于ViewModelProvider与Activity或Fragment的生命周期相关联，所以返回的ViewModel实例也会自动与其生命周期绑定。

这种委托方式简化了ViewModel的初始化，同时确保了其生命周期的正确管理，使开发者能够专注于业务逻辑而不是生命周期的复杂性。