package com.hamahang.app

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val managerNumbers = setOf("09156170125", "+989156170125", "989156170125")
    private val testOtp = "1234"
    private val managerPin = "2580"

    private var currentPhone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stepPhone = findViewById<LinearLayout>(R.id.stepPhone)
        val stepOtp = findViewById<LinearLayout>(R.id.stepOtp)
        val stepPin = findViewById<LinearLayout>(R.id.stepPin)
        val stepHome = findViewById<LinearLayout>(R.id.stepHome)

        val inputPhone = findViewById<EditText>(R.id.inputPhone)
        val inputOtp = findViewById<EditText>(R.id.inputOtp)
        val inputPin = findViewById<EditText>(R.id.inputPin)
        val textWelcome = findViewById<TextView>(R.id.textWelcome)

        // اگر قبلاً لاگین کرده، مستقیم برو خانه
        val prefs = getSharedPreferences("hamahang_prefs", Context.MODE_PRIVATE)
        val savedUser = prefs.getString("last_user", null)
        if (savedUser != null) {
            val isManager = prefs.getBoolean("is_manager", false)
            showHome(stepPhone, stepOtp, stepPin, stepHome, textWelcome, savedUser, isManager)
        }

        findViewById<Button>(R.id.btnSendOtp).setOnClickListener {
            val phone = inputPhone.text.toString().trim().replace(" ", "")
            if (!isValidPhone(phone)) {
                Toast.makeText(this, "شماره معتبر نیست. مثال: 09123456789", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            currentPhone = normalize(phone)
            stepOtp.visibility = View.VISIBLE
            Toast.makeText(this, "کد آزمایشی: 1234", Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.btnVerifyOtp).setOnClickListener {
            val otp = inputOtp.text.toString().trim()
            if (otp != testOtp) {
                Toast.makeText(this, "کد اشتباه است. در نسخه آزمایشی 1234 بزنید.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isManager(currentPhone)) {
                stepPin.visibility = View.VISIBLE
                Toast.makeText(this, "شماره مدیر شناسایی شد. PIN را وارد کنید.", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(currentPhone, false)
                showHome(stepPhone, stepOtp, stepPin, stepHome, textWelcome, currentPhone, false)
            }
        }

        findViewById<Button>(R.id.btnVerifyPin).setOnClickListener {
            val pin = inputPin.text.toString().trim()
            if (pin != managerPin) {
                Toast.makeText(this, "PIN اشتباه است.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(currentPhone, true)
            showHome(stepPhone, stepOtp, stepPin, stepHome, textWelcome, currentPhone, true)
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            prefs.edit().clear().apply()
            stepPhone.visibility = View.VISIBLE
            stepOtp.visibility = View.GONE
            stepPin.visibility = View.GONE
            stepHome.visibility = View.GONE
            inputPhone.text.clear()
            inputOtp.text.clear()
            inputPin.text.clear()
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        val p = phone.replace(" ", "")
        return p.matches(Regex("^09\\d{9}$")) ||
                p.matches(Regex("^\\+989\\d{9}$")) ||
                p.matches(Regex("^989\\d{9}$"))
    }

    private fun normalize(phone: String): String {
        return when {
            phone.startsWith("+98") -> "0" + phone.substring(3)
            phone.startsWith("98") -> "0" + phone.substring(2)
            else -> phone
        }
    }

    private fun isManager(phone: String): Boolean {
        val n = normalize(phone)
        return n == "09156170125" || managerNumbers.contains(phone) || managerNumbers.contains(n)
    }

    private fun loginUser(phone: String, manager: Boolean) {
        getSharedPreferences("hamahang_prefs", Context.MODE_PRIVATE).edit()
            .putString("last_user", normalize(phone))
            .putBoolean("is_manager", manager)
            .apply()
        val msg = if (manager) "خوش آمدید مدیر!" else "خوش آمدید!"
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showHome(
        stepPhone: View, stepOtp: View, stepPin: View, stepHome: View,
        textWelcome: TextView, phone: String, isManager: Boolean
    ) {
        stepPhone.visibility = View.GONE
        stepOtp.visibility = View.GONE
        stepPin.visibility = View.GONE
        stepHome.visibility = View.VISIBLE
        textWelcome.text = if (isManager) "مدیر عزیز ($phone)\nوارد شدید." else "کاربر ($phone)\nوارد شدید."
    }
}
