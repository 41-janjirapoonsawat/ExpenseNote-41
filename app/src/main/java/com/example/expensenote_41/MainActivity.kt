package com.example.expensenote_41

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // สร้างตัวแปรสำหรับหน้า UI Element
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    // สร้างตัวแปรสำหรับเก็บค่ายอดรวมสะสม
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // เชื่อมโยงตัวแปร XML
        etAmount = findViewById(R.id.etAmount)
        etNote = findViewById(R.id.etNote)
        btnAdd = findViewById(R.id.btnAdd)
        btnClear = findViewById(R.id.btnClear)
        tvLastItem = findViewById(R.id.tvLastItem)
        tvTotal = findViewById(R.id.tvTotal)

        btnAdd.setOnClickListener {
            // ดึงข้อมูลจากช่องกรอก
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            // ตรวจสอบเงื่อนไขหากยังไม่ได้กรอก 
            if (amountText.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "กรุณากรอกจำนวนเงินเป็นตัวเลขที่มากกว่า 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ทำการบวกจำนวนเงินเข้าไปในยอดเงินรวมสะสม
            total += amount

            // ตรวจสอบคำอธิบาย
            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"

            // แสดงผลลัพธ์
            tvLastItem.text = "รายการล่าสุด: %s (%.2f บาท)".format(showNote, amount)
            tvTotal.text = "ยอดรวม: %.2f บาท".format(total)

            // เคลียร์ช่องป้อนข้อมูล
            etAmount.text.clear()
            etNote.text.clear()
        }

        btnClear.setOnClickListener {
            total = 0.0

            etAmount.text.clear()
            etNote.text.clear()
            tvLastItem.text = "รายการล่าสุด: -"
            tvTotal.text = "ยอดรวม: 0.00 บาท"


        }
    }
}
