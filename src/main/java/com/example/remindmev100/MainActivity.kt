package com.example.remindmev100

import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindmev100.room.Creds
import com.example.remindmev100.room.CredsVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ad_setcolor.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        getlang()
        animatestuff()
        setVMAdapter()

        changelang.setOnClickListener {
            changelang()
            recreate()
        }

        floatingaddbutton.setOnClickListener {
            AlertDialogBuilder(this).addcreds()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.options1 ->{
                Toast.makeText(this, this.resources.getString(R.string.FAQ),Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun setVMAdapter(){
        rvview.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter()
        rvview.adapter = adapter
        val vmp = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CredsVM::class.java)
        vmp.setSearch("")
        vmp.readCreds.observe(this, Observer {
            adapter.credsList = it
            adapter.notifyDataSetChanged()
        })

        search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vmp.setSearch(newText!!)
                return false
            }

        })

    }

    fun animatestuff(){

        langholder.alpha = 0f
        mainact.alpha = 0f

        langholder.animate().apply {
            alpha(1f)
            duration = 400
        }

        mainact.animate().apply {
            alpha(1f)
            duration = 400
        }
    }

    fun changelang(){

        val prefs = getSharedPreferences("creds_settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        if (prefs.getString("creds_language", "english") == "english"){
            editor.putString("creds_language", "polish")
            editor.apply()
            getlang()
        } else {
            editor.putString("creds_language", "english")
            editor.apply()
            getlang()
        }
    }

    fun getlang(){

        lateinit var flag : String
        val prefs = getSharedPreferences("creds_settings", Context.MODE_PRIVATE)

        if (prefs.getString("creds_language", "english") == "english"){
            changelang.setImageResource(R.drawable.englando390x260)
            flag = "en"
        } else {
            changelang.setImageResource(R.drawable.polando390x260)
            flag = "pl"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val res = resources.configuration
            when (flag){
                "en" -> res.setLocale(Locale("en"))
                "pl" -> res.setLocale(Locale("pl"))
            }
            resources.updateConfiguration(res, resources.displayMetrics)
        }
    }
}