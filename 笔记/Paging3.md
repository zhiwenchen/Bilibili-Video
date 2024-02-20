# Paging组件
> 分页库
Paging库的优势

## 用法


### 第一步 定义数据源，实现PagingSource
PagingSource包含load()方法，指示如何从数据源检索分页数据
PagingSource<Key, Value>有两个参数：Key和Value，key可以当做页码，value当做要加载的对象

```kotlin
class ExamplePagingSource(): PagingSource<Int, User>() {
    
    override suspend fun load(params: LoadParams<Int>):LoadResult<Int, User> {
        
        return LoadResult.Page(data=response.userList, pervKey=null,nextKey=response.nextPageNumber)
    }
    
    // 返回要传递给load()方法的键
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        
    }
    
}

```

## 第二步 设置PagingData流
```kotlin
val flow = Pager(PagingConfig(pageSize=20)) {
    ExamplePagingSource()
}.flow.cacheInd(viewModelScope)
```
## 第三步 定义RecyclerView适配器
Paging库提供了PagingDataAdapter

```kotlin
class UserAdapter(diffCallback: DiffUtil.ItemCallback<User>) :
  PagingDataAdapter<User, UserViewHolder>(diffCallback) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): UserViewHolder {
    return UserViewHolder(parent)
  }

  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val item = getItem(position)
    // Note that item can be null. ViewHolder must support binding a
    // null item as a placeholder.
    holder.bind(item)
  }
}
```

## 第四步 在界面中显示分页数据
```kotlin
lifecycleScope.launch {
    viewModel.flow.collectLatest { pagingdata ->
        pagingAdapter.submitData(pagingData)
    }
}
```