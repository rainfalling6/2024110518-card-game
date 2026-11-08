package com.example.a2024110518

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a2024110518.model.PlayerInfo

/**
 * 主 Activity：显示当前玩家的基本信息，并提供进入信息修改和翻牌游戏的入口。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var tvNameValue: TextView
    private lateinit var tvGenderValue: TextView
    private lateinit var tvStudentIdValue: TextView
    private lateinit var tvPhoneValue: TextView
    private lateinit var tvEmailValue: TextView
    private lateinit var tvHobbyValue: TextView
    private lateinit var tvHighScoreValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PlayerInfo.ensureInitialized(this)

        bindViews()

        findViewById<Button>(R.id.btnEditInfo).setOnClickListener {
            startActivity(Intent(this, PersonInfoActivity::class.java))
        }
        findViewById<Button>(R.id.btnCardGame).setOnClickListener {
            startActivity(Intent(this, CardGameActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshPlayerInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                showAboutDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAboutDialog() {
        val message = getString(
            R.string.about_message,
            getString(R.string.app_name),
            getString(R.string.default_name),
            getString(R.string.default_student_id),
            getString(R.string.about_version)
        )
        AlertDialog.Builder(this)
            .setTitle(R.string.about_title)
            .setMessage(message)
            .setPositiveButton(R.string.about_ok, null)
            .show()
    }

    private fun bindViews() {
        tvNameValue = findViewById(R.id.tvNameValue)
        tvGenderValue = findViewById(R.id.tvGenderValue)
        tvStudentIdValue = findViewById(R.id.tvStudentIdValue)
        tvPhoneValue = findViewById(R.id.tvPhoneValue)
        tvEmailValue = findViewById(R.id.tvEmailValue)
        tvHobbyValue = findViewById(R.id.tvHobbyValue)
        tvHighScoreValue = findViewById(R.id.tvHighScoreValue)
    }

    private fun refreshPlayerInfo() {
        tvNameValue.text = getString(R.string.info_name, PlayerInfo.name)
        tvGenderValue.text = getString(R.string.info_gender, PlayerInfo.gender)
        tvStudentIdValue.text = getString(R.string.info_student_id, PlayerInfo.studentId)
        tvPhoneValue.text = getString(R.string.info_phone, PlayerInfo.phone)
        tvEmailValue.text = getString(R.string.info_email, PlayerInfo.email)
        tvHobbyValue.text = getString(
            R.string.info_hobby,
            PlayerInfo.hobbiesText(getString(R.string.hobby_separator))
        )
        tvHighScoreValue.text = getString(R.string.info_high_score, PlayerInfo.highestScore)
    }
}
