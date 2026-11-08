# 翻牌游戏应用（Android 实验四、五）

- **姓名**：李逸凡
- **学号**：2024110518
- **课程**：Android 移动应用开发
- **实验四**：翻牌游戏应用
- **实验五**：RecyclerView 翻牌游戏应用

## 功能

1. **主界面**：显示玩家姓名、性别、学号、电话、邮箱、爱好和游戏最高分；ActionBar 提供“关于”菜单，点击后弹出关于对话框。
2. **信息修改**：修改玩家信息（保存在内存中，不写入文件），返回主界面后自动刷新。
3. **翻牌游戏（实验五）**：RecyclerView + GridLayoutManager 显示 24 张牌；每局翻三张牌，三张点数相同即配对成功；支持重置游戏、退出和横竖屏自适应（竖屏每行 4 张、横屏每行 6 张）。
4. **测试**：模型单元测试（JUnit）与界面 UI 测试（Espresso + ActivityScenario）。

## 结构（MVC）

```
app/src/main/java/com/example/a2024110518/
├── MainActivity.kt            主界面（含菜单和关于对话框）
├── PersonInfoActivity.kt      信息修改
├── CardGameActivity.kt        翻牌游戏（Controller）
├── CardAdapter.kt             RecyclerView 适配器（含点击回调）
└── model/
    ├── Card.kt                牌（三张牌匹配规则）
    ├── Deck.kt                牌堆
    ├── CardMatchingGame.kt    游戏规则（含 reset 与三张牌配对）
    └── PlayerInfo.kt          玩家信息内存数据源
```

## 分支与提交

- `main`：主分支，实验四成果。
- `Dev`：实验五开发分支，通过 Pull Request 合并到 `main`。

## 构建与运行

用 Android Studio 打开项目并运行 `app` 模块，或在项目根目录执行：

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew connectedDebugAndroidTest
```

## 实验报告

- `实验四 翻牌游戏应用 实验报告.docx`
- `实验五 RecyclerView翻牌游戏应用 实验报告.docx`

运行截图见 `screenshots/` 目录。
