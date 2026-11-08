package com.example.a2024110518

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a2024110518.model.PlayerInfo

/**
 * 信息修改 Activity：修改姓名、学号、电话、邮箱、性别和爱好。
 * 修改结果保存在内存中的 PlayerInfo，返回主界面后即可看到最新信息。
 */
class PersonInfoActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var cbTravel: CheckBox
    private lateinit var cbReading: CheckBox
    private lateinit var cbGame: CheckBox
    private lateinit var cbShopping: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_info)
        PlayerInfo.ensureInitialized(this)

        bindViews()
        fillPlayerInfo()

        findViewById<Button>(R.id.btnSave).setOnClickListener { savePlayerInfo() }
        findViewById<Button>(R.id.btnCancel).setOnClickListener { finish() }
    }

    private fun bindViews() {
        etName = findViewById(R.id.etName)
        etStudentId = findViewById(R.id.etStudentId)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        rgGender = findViewById(R.id.rgGender)
        cbTravel = findViewById(R.id.cbTravel)
        cbReading = findViewById(R.id.cbReading)
        cbGame = findViewById(R.id.cbGame)
        cbShopping = findViewById(R.id.cbShopping)
    }

    private fun fillPlayerInfo() {
        etName.setText(PlayerInfo.name)
        etStudentId.setText(PlayerInfo.studentId)
        etPhone.setText(PlayerInfo.phone)
        etEmail.setText(PlayerInfo.email)

        if (PlayerInfo.gender == getString(R.string.gender_female)) {
            rgGender.check(R.id.rbFemale)
        } else {
            rgGender.check(R.id.rbMale)
        }

        cbTravel.isChecked = PlayerInfo.hobbies.contains(getString(R.string.hobby_travel))
        cbReading.isChecked = PlayerInfo.hobbies.contains(getString(R.string.hobby_reading))
        cbGame.isChecked = PlayerInfo.hobbies.contains(getString(R.string.hobby_game))
        cbShopping.isChecked = PlayerInfo.hobbies.contains(getString(R.string.hobby_shopping))
    }

    private fun savePlayerInfo() {
        val name = etName.text.toString().trim()
        if (name.isEmpty()) {
            etName.error = getString(R.string.error_name_empty)
            return
        }

        PlayerInfo.name = name
        PlayerInfo.studentId = etStudentId.text.toString().trim()
        PlayerInfo.phone = etPhone.text.toString().trim()
        PlayerInfo.email = etEmail.text.toString().trim()
        PlayerInfo.gender = if (rgGender.checkedRadioButtonId == R.id.rbFemale) {
            getString(R.string.gender_female)
        } else {
            getString(R.string.gender_male)
        }

        PlayerInfo.hobbies.clear()
        collectHobby(cbTravel, R.string.hobby_travel)
        collectHobby(cbReading, R.string.hobby_reading)
        collectHobby(cbGame, R.string.hobby_game)
        collectHobby(cbShopping, R.string.hobby_shopping)

        Toast.makeText(this, R.string.toast_saved, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun collectHobby(checkBox: CheckBox, stringRes: Int) {
        if (checkBox.isChecked) {
            PlayerInfo.hobbies.add(getString(stringRes))
        }
    }
}
