package com.inksy.UI.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.inksy.R
import com.inksy.Remote.Status
import com.inksy.UI.ViewModel.JournalView
import com.inksy.Utils.AppUtils
import com.inksy.Utils.TinyDB

class ReportDialog(
    var vm: ViewModelStoreOwner,
    var lco: LifecycleOwner,
    context: Context, var _activity: Activity, var journal_id: String
) : Dialog(context), View.OnClickListener {

    lateinit var tvCancel: TextView
    lateinit var tvOk: TextView
    var _event_id: String? = null
    lateinit var Okprogress: ProgressBar
    private lateinit var edtdesc: EditText
    private lateinit var edtTitle: EditText
    lateinit var journalView: JournalView
    lateinit var tiny: TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawableResource(R.drawable.transparent)
        setContentView(R.layout.dialoge_report_event)
        setCanceledOnTouchOutside(false)
        val window = window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        initUI()
    }

    private fun initUI() {
        journalView = ViewModelProvider(vm)[JournalView::class.java]
        journalView.init()
        tiny = TinyDB(context)
        Okprogress = findViewById(R.id.Okprogress)
        tvCancel = findViewById(R.id.tvCancel)
        tvOk = findViewById(R.id.tvOk)
        edtTitle = findViewById(R.id.edtTitle)
        edtdesc = findViewById(R.id.edtdesc)
        tvCancel.setOnClickListener(this)
        tvOk.setOnClickListener(this)
        edtdesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (edtdesc.getText().toString().length > 0) {
                    val letter: String = edtdesc.getText().toString().get(0).toString()
                    val letterUpperCaee = letter.toUpperCase()
                    if (letter != letterUpperCaee) {
                        edtdesc.setText(capitaliseOnlyFirstLetter(edtdesc.getText().toString()))
                        edtdesc.setSelection(edtdesc.getText().toString().length)
                    }
                }
            }
        })
        edtTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (edtTitle.getText().toString().length > 0) {
                    val letter: String = edtTitle.getText().toString().get(0).toString()
                    val letterUpperCaee = letter.toUpperCase()
                    if (letter != letterUpperCaee) {
                        edtTitle.setText(capitaliseOnlyFirstLetter(edtTitle.getText().toString()))
                        edtTitle.setSelection(edtTitle.getText().toString().length)
                    }
                }
            }
        })
    }

    fun capitaliseOnlyFirstLetter(data: String): String? {
        return data.substring(0, 1).toUpperCase() + data.substring(1)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvCancel -> dismiss()
            R.id.tvOk -> {
                if (edtTitle.text.toString().equals("", ignoreCase = true)) {
                    AppUtils.setErrorOnEditText(edtTitle, "Please enter title...")
                    return
                }
                if (edtdesc.text.toString().equals("", ignoreCase = true)) {
                    AppUtils.setErrorOnEditText(edtdesc, "Please enter description...")
                    return
                }
                edtTitle.isEnabled = false
                edtdesc.isEnabled = false
                Okprogress.visibility = View.VISIBLE
                tvOk.visibility = View.GONE
                reportEvent(journal_id, edtTitle.text.toString(), edtdesc.text.toString())
            }
        }
    }

    private fun reportEvent(journal_id: String, title: String, desc: String) {
        var token = tiny.getString("token")
        journalView.journalReport(journal_id.toInt(), token!!, title, desc)?.observe(lco) {
            when (it.status) {
                Status.ERROR -> {}
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    _activity.finish()
                    Okprogress.visibility = View.GONE

                    Toast.makeText(context, it?.data?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}