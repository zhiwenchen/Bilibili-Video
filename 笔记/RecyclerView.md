# `RecyclerView`全解
参考文章[深入理解RecyclerView：布局管理器实现原理和使用方法](https://juejin.cn/post/7317575725699366964)
[浅谈RecyclerView的性能优化](https://juejin.cn/post/7164032795310817294?searchId=20240206110245819936B72B0E020130D5)
## 基础使用

## 缓存原理
#### 四级缓存
- Scrap缓存：屏内缓存，保存mAttachedScrap和mChangedCrap，不参与滑动时的回收服用，只用作临时保存的变量
  - mAttachedScrap：
  - mChangedScrap：
- CacheView缓存：离屏缓存，用户保存最新移出屏幕的ViewHolder，默认最大容量是2
- ViewCacheExtension：扩展缓存，一般不会用到
- RecycledViewPool：回收缓存池，存放的ViewHolder使用时需要重新绑定数据，根据itemType来缓存不同类型的ArrayList，每个最大容量为5

#### 回收复用原理

1. 滑动时缓存
   - 先存放在mCachedViews中
   - 由于mCachedViews最大容量为2，当mCachedViews满了以后，会利用先进先出原则，把旧的ViewHolder存放到RecycledViewPool中后移除掉，腾出空间，再将新的ViewHolder添加到mCachedViews中。

#### 缓存机制总结
- 缓存数量
- RecyclerView实际上使用的是两层缓存，Scrap不参与滑动时的回收复用。所以CacheView被称为一级缓存，RecycledViewPool被称为二级缓存

## 性能优化
首先要发现性能瓶颈在哪里, 使用Android Profiler查看一下各个步骤的耗时
- 提高ViewHolder的复用
- 优化onBindViewHolder方法
- 优化onCreateViewHolder方法

### 提升ViewHolder的复用
1. 使用局部刷新的方法，notifyItm的一系列方法
2. 使用DiffUtil进行刷新，这个比notifyItem略微损耗性能
3. 使用setItemViewCacheSize增大CacheView缓存数目，不过也要综合考虑
4. 增加RecyclerViewPool的大小

### 优化onBindViewHolder方法
该方法会多次调用，所以仅做数据绑定的工作会优化很多

### 优化onCreateViewHolder方法
减少界面渲染时间

## LayoutManager
### 自带的布局管理器
### 自定义LayoutManager



## `ListAdapter`
全部刷新：使用`notifyDataSetChanged()`，部分刷新时可以用其他一些API
`notifyItemRemoved()`、`notifyItemInserted()`、`notifyItemRangeChanged()`等等
无法处理的场景：后端返回了列表的全部数据，如何比较新旧列表的不同，仅更新变化的部分

### `DiffUtil`
`ListAdapter`基于`DiffUtil`工具进行了封装，继承`ListAdapter`即可实现`DiffUtil`的效果，步骤如下
原理：差分算法：最长公共子序列-二维动态规划
速度：

#### 1 实现`DiffUtil.ItemCallback`
```kotlin
class ItemTestCall: DiffUtil.ItemCallback<ItemTestBean>() {
    // 判断主键是否相同（是否是同一个元素）
    override fun areItemsTheSame(oldItem: ItemTestBean, newItem: ItemTestBean):Boolean {
        return oldItem.id == newItem.id
    }
    
    // 判断内容是否相同
    override fun areContentsTheSame(oldItem: ItemTestBean, newItem: ItemTestBean):Boolean {
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.age == newItem.age
    }
}
```
#### 2 实现ViewHolder
```kotlin
class ItemTestViewHolder():RecyclerView.ViewHolder() {
    
    fun bindData(bean:ItemTestBean) {
        
    }
    
}
```
#### 实现ListAdapter
```kotlin
class ItemTestListAdapter : ListAdapter<ItemTestBean, ItemTestListAdapter.ItemTestViewHolder>(itemTestCallback) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTestViewHolder {
        
    }
    
    override fun onBindViewHolder(holder: ItemTestViewHolder, position: Int) {
        //通过ListAdapter内部实现的getItem方法找到对应的Bean
        holder.bind(getItem(position))
    }
}
```

#### 使用ListAdapter完成列表的增删改查
- 插入新的列表
  - adapter.submitList(testList)
- 局部插入元素
```kotlin
// 要传入新的List
val newList = testList.toMutableList().apply {
    add(3,ItemTestBean())
}
adapter.submitList(newList)
```
- 删除和修改元素
```kotlin
val newList=testList.toMutableList().apply {
    //删除
    removeAt(2)
    //修改
    this[3]=this[3].copy(name = "改名后的小帅哥")
}
adapter.submitList(newList)

```

## 结合Paging分页