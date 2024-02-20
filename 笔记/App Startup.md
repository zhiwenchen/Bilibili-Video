# App Stratup
> 提供一个统一的ContentProvider来给所有依赖项初始化，好处不是特别多，优化效果有限，了解即可

## 各个依赖项自己使用ContentProvider进行初始化的缺点
- 多个ContentProvider增加App启动时间
- ContentProvider的onCreate会早于Application的onCreate
- 生命周期是 先Application的attachBaseContext，然后ContentProvider的onCreate，最后是Application的onCreate方法

