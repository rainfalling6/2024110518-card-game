# 翻牌游戏应用（Android 实验四）

- **姓名**：李逸凡
- **学号**：2024110518
- **课程**：Android 移动应用开发
- **实验**：实验四 翻牌游戏应用

## 功能

1. **主界面**：显示玩家姓名、性别、学号、电话、邮箱、爱好和游戏最高分。
2. **信息修改**：修改玩家信息（保存在内存中，不写入文件），返回主界面后自动刷新。
3. **翻牌游戏**：16 张牌，点击翻牌、按点数/花色配对并计分，支持重置游戏和退出；最高分回写主界面。
4. **测试**：模型单元测试（JUnit）与界面 UI 测试（Espresso + ActivityScenario）。

## 结构（MVC）

```
app/src/main/java/com/example/a2024110518/
├── MainActivity.kt            主界面
├── PersonInfoActivity.kt      信息修改
├── CardGameActivity.kt        翻牌游戏（Controller）
└── model/
    ├── Card.kt                牌
    ├── Deck.kt                牌堆
    ├── CardMatchingGame.kt    游戏规则
    └── PlayerInfo.kt          玩家信息内存数据源
```

## 构建与运行

用 Android Studio 打开项目并运行 `app` 模块，或在项目根目录执行：

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew connectedDebugAndroidTest
```

## 实验报告

`实验四 翻牌游戏应用 实验报告.docx`

运行截图见 `screenshots/` 目录。
