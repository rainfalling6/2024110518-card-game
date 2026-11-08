package com.example.a2024110518.model

import android.content.Context
import com.example.a2024110518.R

/**
 * 玩家信息的内存数据源，供三个 Activity 共享。
 * 按实验要求，修改后的信息无需保存到文件中，因此使用单例对象保存于内存。
 */
object PlayerInfo {
    var name: String = ""
    var gender: String = ""
    var studentId: String = ""
    var phone: String = ""
    var email: String = ""
    var highestScore: Int = 0

    val hobbies: MutableSet<String> = linkedSetOf()

    private var initialized = false

    fun ensureInitialized(context: Context) {
        if (initialized) {
            return
        }
        name = context.getString(R.string.default_name)
        studentId = context.getString(R.string.default_student_id)
        phone = context.getString(R.string.default_phone)
        email = context.getString(R.string.default_email)
        gender = context.getString(R.string.gender_male)
        hobbies.add(context.getString(R.string.default_hobby_1))
        hobbies.add(context.getString(R.string.default_hobby_2))
        initialized = true
    }

    fun hobbiesText(separator: String): String = hobbies.joinToString(separator)
}
