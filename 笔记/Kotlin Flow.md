
// 不阻塞主线程（主线程还可以开启其他协程完成任务）
```kotlin
fun simple(): Flow<Int> = flow { // 流构建器
    for (i in 1..3) {
        delay(100) // 假装我们在这里做了一些有用的事情
        emit(i) // 发送下一个值
    }
}

fun main() = runBlocking<Unit> {
    // 启动并发的协程以验证主线程并未阻塞
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // 收集这个流
    simple().collect { value -> println(value) } 
    // 流的取消和协程一样
}
```
- Flow 类型的构建器函数名为 flow。
- flow { ... } 构建块中的代码可以挂起。
- 函数 simple 不再标有 `suspend` 修饰符。
- 流使用 `emit` 函数 发射 值。
- 流使用 `collect` 函数 收集 值。

Flow 是一种类似于序列的冷流 — flow 构建器中的代码直到流被收集的时候才运行。

simple函数没有suspend修饰符的原因：因为simple函数被调用的时候，并没有等待任何事情，只有调用collect的时候才会执行代码
