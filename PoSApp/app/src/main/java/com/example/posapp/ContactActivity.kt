package com.example.posapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class ContactActivity : AppCompatActivity(){
    private var listView : ListView? = null
    private var listviewAdapterContact : ContactListViewAdapter?=null
    private var listofContacts :ArrayList<Contacts>?=null
    private var xDlist : ArrayList<String> = ArrayList()
    private lateinit var selectionArgs:Array<String>
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        listView = findViewById<ListView>(R.id.viewlist)
        listofContacts = ArrayList()
        //search for a specific
        //contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE ?"  , selectionArgs,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC")
        //search for all contact
        //contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC")
        val phones = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        Log.i("HEHEHEHE", phones.toString())
        while (phones!!.moveToNext()) {
            val name =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phonenumber =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contacts = Contacts()
            contacts.name = name
            contacts.number = phonenumber
            listofContacts!!.add(contacts)
        }
        phones.close()
        listviewAdapterContact = ContactListViewAdapter(this, listofContacts!!)
        listView!!.adapter = listviewAdapterContact


        val searchView=findViewById<SearchView>(R.id.Contactsearchview)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(searchtext: String?): Boolean {
                query(searchtext)
                return false
            }

            override fun onQueryTextChange(searchtext: String?): Boolean {
                query(searchtext)
                return false
            }

        })
    }
    @SuppressLint("Range")
    fun query(searchtext:String?) {
        xDlist.clear()//clear both lists every time a button is clicked, so it removes the old query, meaning it wont be shown in the listview
        listofContacts!!.clear()//this is used to solve the old problem where the old query wasnt removed when the button is clicked again, and new contact query keeps getting added every time a button is clicked
        val searchname = findViewById<TextView>(R.id.searchcontact)
        selectionArgs= arrayOf("%"+searchtext+"%")
        val lmao = arrayOf(listofContacts)
        val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE?"  , selectionArgs,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC")
        while(phones!!.moveToNext()){
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phonenumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contacts = Contacts()
            contacts.name = name
            contacts.number = phonenumber
            listofContacts!!.add(contacts) //this is the code where it will be shown in the listview
        }
        phones.close()
        Log.i("LMAOOOOOOO", Arrays.toString(lmao))
        listviewAdapterContact=ContactListViewAdapter(this@ContactActivity,listofContacts!!)
        listView!!.adapter=listviewAdapterContact

    }

}