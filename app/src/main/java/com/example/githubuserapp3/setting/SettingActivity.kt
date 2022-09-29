package com.example.githubuserapp3.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuserapp3.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}