package com.example.remindmev100

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.remindmev100.room.Creds
import com.example.remindmev100.room.CredsVM
import kotlinx.android.synthetic.main.ad_additem.*
import kotlinx.android.synthetic.main.ad_deleteitem.*
import kotlinx.android.synthetic.main.ad_pinnote.*
import kotlinx.android.synthetic.main.ad_setcolor.*
import kotlinx.android.synthetic.main.ad_setnote.*
import kotlinx.android.synthetic.main.ad_setpin.*
import kotlinx.android.synthetic.main.spinner_item.view.*

class AlertDialogBuilder(val MainActivity: Context) {
	private val error : String = "Te pole nie może być puste!"
	val vm = ViewModelProvider.AndroidViewModelFactory.getInstance(MainActivity.applicationContext as Application).create(CredsVM::class.java)
    fun addcreds() {
        val dialog = AlertDialog.Builder(MainActivity)

        dialog.apply {
            setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_additem, null))
        }

        val builder = dialog.create()
        builder.show()
		builder.setCanceledOnTouchOutside(false)

		val inputtitle = builder.inputtitle
		val inputlogin = builder.inputlogin
		val inputpass = builder.inputpass
		val pin = builder.pin
		val note = builder.note

        builder.addbutton.setOnClickListener {
			if (inputcheck(inputtitle.text.toString(), inputlogin.text.toString(), inputpass.text.toString())){
				builder.dismiss()
				vm.addCreds(Creds(0, inputtitle.text.toString(), inputlogin.text.toString(), inputpass.text.toString(), pin.isChecked, note.isChecked))
			}
			else {
				if (inputtitle.text.isEmpty()){
					inputtitle.error = error
				}
				if (inputlogin.text.isEmpty()){
					inputlogin.error = error
				}
				if (inputpass.text.isEmpty()){
					inputpass.error = error
				}
			}
        }

		builder.cancelbutton.setOnClickListener {
			builder.dismiss()
		}
    }

    fun inputcheck(title: String, login: String, pass: String) : Boolean{
		if (title != "" && login != "" && pass != "")
		{
			return true
		}
		return false
    }

	fun deleteData(id: Int){
		val dialog = AlertDialog.Builder(MainActivity)

		dialog.apply {
			setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_deleteitem, null))
			setCancelable(false)
		}
		val builder = dialog.create()
		builder.show()

		builder.canceldeletebutton.setOnClickListener {
			addcolor(id)
			builder.dismiss()
		}

		builder.deletebutton.setOnClickListener {
			vm.deleteCreds(id)
			builder.dismiss()
		}
	}

	fun editCreds(id: Int, title: String, login: String, password: String, pin2: Boolean, note2: Boolean, color: Int, pincontent: String, notecontent: String){
		val dialog = AlertDialog.Builder(MainActivity)

		dialog.apply {
			setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_additem, null))
		}

		val builder = dialog.create()
		builder.show()
		builder.setCanceledOnTouchOutside(false)

		val inputtitle = builder.inputtitle
		val inputlogin = builder.inputlogin
		val inputpass = builder.inputpass
		val pin = builder.pin
		val note = builder.note

		builder.titlead.text = MainActivity.resources.getString(R.string.edcreds)
		inputtitle.setText(title)
		inputlogin.setText(login)
		inputpass.setText(password)
		pin.isChecked = pin2
		note.isChecked = note2

		builder.addbutton.setOnClickListener {
			if (inputcheck(inputtitle.text.toString(), inputlogin.text.toString(), inputpass.text.toString())){
				builder.dismiss()
				vm.updateCreds(Creds(id, inputtitle.text.toString(), inputlogin.text.toString(), inputpass.text.toString(), pin.isChecked, note.isChecked, color, pincontent, notecontent))
			}
			else {
				if (inputtitle.text.isEmpty()){
					inputtitle.error = error
				}
				if (inputlogin.text.isEmpty()){
					inputlogin.error = error
				}
				if (inputpass.text.isEmpty()){
					inputpass.error = error
				}
			}
		}

		builder.cancelbutton.setOnClickListener {
			builder.dismiss()
		}
	}

	fun pinnote(id: Int, pin: Boolean, note: Boolean,  pincontent: String, notecontent: String){
		val dialog = AlertDialog.Builder(MainActivity)

		dialog.apply {
			setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_pinnote, null))
		}

		val builder = dialog.create()
		builder.show()


		builder.cancelpinbutton.setOnClickListener{
			builder.dismiss()
		}

		if (pin){
			builder.herepin.text = pincontent
			builder.editpin.setColorFilter(ContextCompat.getColor(MainActivity, R.color.lime))
			builder.editpin.setOnClickListener {
			setpin(id, pin, note, pincontent, notecontent)
			builder.dismiss()
			}

		} else {
			builder.editpin.setColorFilter(ContextCompat.getColor(MainActivity, R.color.lired))
			builder.editpin.setOnClickListener {
				Toast.makeText(MainActivity, R.string.Unable, Toast.LENGTH_SHORT).show()
			}
		}

		if (note){
			builder.herenote.text = notecontent
			builder.editnote2.setColorFilter(ContextCompat.getColor(MainActivity, R.color.lime))
			builder.editnote2.setOnClickListener {
				setnote(id, pin, note, pincontent, notecontent)
				builder.dismiss()
			}
		} else {
			builder.editnote2.setColorFilter(ContextCompat.getColor(MainActivity, R.color.lired))
			builder.editnote2.setOnClickListener {
				Toast.makeText(MainActivity, R.string.Unable, Toast.LENGTH_SHORT).show()
			}
		}
	}

	fun setpin(id: Int, pin: Boolean, note: Boolean, pincontent: String, notecontent: String){
		val dialog = AlertDialog.Builder(MainActivity)

		dialog.apply {
			setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_setpin, null))
			setCancelable(false)
		}

		val builder = dialog.create()
		builder.show()

		builder.insertPIN.setText(pincontent)


		builder.backpinbutton.setOnClickListener{
			builder.dismiss()
			pinnote(id, pin, note, pincontent, notecontent)
		}

		builder.confirmpinbutton.setOnClickListener {
			builder.dismiss()
			vm.updatePIN(id, builder.insertPIN.text.toString())
		}
	}

	fun setnote(id: Int, pin: Boolean, note: Boolean, pincontent: String, notecontent: String){
		val dialog = AlertDialog.Builder(MainActivity)

		dialog.apply {
			setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_setnote, null))
			setCancelable(false)
		}

		val builder = dialog.create()
		builder.show()
		builder.insertnote.setText(notecontent)

		builder.backnotebutton.setOnClickListener{
			builder.dismiss()
			pinnote(id, pin, note, pincontent, notecontent)
		}

		builder.confirmnotebutton.setOnClickListener {
			builder.dismiss()
			vm.updateNote(id, builder.insertnote.text.toString())
		}
	}

	fun addcolor(id: Int){
		val dialog = AlertDialog.Builder(MainActivity)

		dialog.apply {
			setView(LayoutInflater.from(MainActivity).inflate(R.layout.ad_setcolor, null))
		}

		val builder = dialog.create()
		builder.show()

		builder.delete.setOnClickListener {
			deleteData(id)
			builder.dismiss()
		}

		ArrayAdapter.createFromResource(MainActivity, R.array.colors, R.layout.spinner_item).also {
			it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
			builder.spinner.adapter = it
		}

		builder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onNothingSelected(p0: AdapterView<*>?) {
			}

			override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
				when (p2){
					1 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.black))
					}
					2 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.blue))
					}
					3 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.green))
					}
					4 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.red))
					}
					5 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.violet))
					}
					6 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.pink))
					}
					7 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.orange))
					}
					8 ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.sky))
					}
					else ->{
						p1!!.spinnertext.setTextColor(ContextCompat.getColor(MainActivity,R.color.grey))
					}
				}
			}
		}

		builder.confirmbutton.setOnClickListener {
			when (builder.spinner.selectedItemPosition){
				1->{
					vm.updateColor(id, 1)
					builder.dismiss()
				}
				2->{
					vm.updateColor(id, 2)
					builder.dismiss()
				}
				3->{
					vm.updateColor(id, 3)
					builder.dismiss()
				}
				4->{
					vm.updateColor(id, 4)
					builder.dismiss()
				}
				5->{
					vm.updateColor(id, 5)
					builder.dismiss()
				}
				6->{
					vm.updateColor(id, 6)
					builder.dismiss()
				}
				7->{
					vm.updateColor(id, 7)
					builder.dismiss()
				}
				8->{
					vm.updateColor(id, 8)
					builder.dismiss()
				}
				else->{
					vm.updateColor(id, 0)
					builder.dismiss()
				}
			}
		}

	}
}