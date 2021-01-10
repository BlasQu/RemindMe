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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindmev100.room.Creds
import com.example.remindmev100.room.CredsVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ad_setcolor.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        dataStore = createDataStore("settings")

        runBlocking {
            getlang()
        }
        animatestuff()
        setVMAdapter()

        changelang.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                changelang()
                recreate()
            }
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
        vmp.readCreds.observe(this, Observer {
            adapter.submitData(it)
        })

        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vmp.randomString.offer((Pair(newText!!, vmp.randomString.value.second)))
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

    suspend fun changelang(){
        val creds_language = preferencesKey<String>("creds_language")
        lateinit var current_language : String
        dataStore.edit {
            current_language = dataStore.data.first()[creds_language] ?: "english"
            if (current_language == "english") it[creds_language] = "polish"
            else it[creds_language] = "english"
        }
        getlang()
    }

    suspend fun getlang(){

        val creds_language = preferencesKey<String>("creds_language")
        var language : String = dataStore.data.first()[creds_language] ?: "english"


        val flag = if (language == "english"){
            changelang.setImageResource(R.drawable.englando390x260)
            "en"
        } else {
            changelang.setImageResource(R.drawable.polando390x260)
            "pl"
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