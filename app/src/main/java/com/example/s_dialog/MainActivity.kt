package com.example.s_dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.s_dialog.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // [1] 기본 다이얼로그
        binding.btn1Alert.setOnClickListener {
            var builder = AlertDialog.Builder(this) // builder 를 통해서 dialog 설정
            builder.setTitle("기본 다이얼로그 타이틀")
            builder.setMessage("기본 다이얼로그 메세지")
            builder.setIcon(R.mipmap.ic_launcher)

            // 버튼 클릭 시 무슨 작업을 할지 설정
            val listener = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    when (p1) {
                        DialogInterface.BUTTON_POSITIVE ->
                            binding.tvTitle.text = "BUTTON POSITIVE"
                        DialogInterface.BUTTON_NEUTRAL ->
                            binding.tvTitle.text = "BUTTON NEUTRAL"
                        DialogInterface.BUTTON_NEGATIVE ->
                            binding.tvTitle.text = "BUTTON NEGATIVE"
                    }
                }
            }

            builder.setPositiveButton("Positive", listener)
            builder.setNeutralButton("Negative", listener)
            builder.setNegativeButton("Neutral", listener)

            builder.show()
        }

        // [2] 커스텀 다이얼로그 - 다이얼로그 xml 파일이 필요함
        binding.btn2Custom.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("커스텀 다이얼로그")
            builder.setIcon(R.mipmap.ic_launcher)

            // 레이아웃 지정 - 커스텀한 레이아웃을 builder 의 setview 를 통해서 넣음
            val v1 = layoutInflater.inflate(R.layout.dialog, null)
            builder.setView(v1)

            // p0에 해당 AlertDialog가 들어온다. findviewById 를 통해 view 를 가져와서 사용
            val listener = DialogInterface.OnClickListener { p0, p1 ->
                val alert = p0 as AlertDialog
                val edit1: EditText? = alert.findViewById<EditText>(R.id.editText)
                val edit2: EditText? = alert.findViewById<EditText>(R.id.editText2)

                binding.tvTitle.text = "이름 : ${edit1?.text}"
                binding.tvTitle.append(" / 나이 : ${edit2?.text}") // append = 뒤에 붙인다는 뜻 ( 이름 / 나이 )
            }

            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", null)

            builder.show()
        }

        // [3] 날짜 다이얼로그
        binding.btn3Date.setOnClickListener {
            val calendar = Calendar.getInstance() // 오늘 날짜를 가져옴
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i년 i2월 i3일
                binding.tvTitle.text = "${i}년 ${i2 + 1}월 ${i3}일"
            }

            // picker 를 띄우면 날짜를 지정할 수 있는 창이 띄워짐
            var picker = DatePickerDialog(this, listener, year, month, day)
            picker.show()
        }

        // [4] 시간 다이얼로그
        binding.btn4Time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            val listener = TimePickerDialog.OnTimeSetListener{ timePicker, i, i2 ->
                binding.tvTitle.text = "${i}시 ${i2}분"
            }

            // 마지막 인수를 true 로 주면 24시간제로 표시
            val picker = TimePickerDialog(this, listener, hour, minute, false)
            picker.show()
        }

        // [5] 진행 표시 다이얼로그 (커스텀과 비슷)
        binding.btn5Porgress.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("프로그래스바")
            builder.setIcon(R.mipmap.ic_launcher)

            val v1 = layoutInflater.inflate(R.layout.progressbar, null)
            builder.setView(v1)

            builder.show()
            // 프로그래스바 끝내는 건 builder.dismiss() 인가 봄
        }
    }
}