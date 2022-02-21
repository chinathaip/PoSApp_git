package com.example.posapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.posapp.databinding.ActivityPosSettingsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.URI

class PosSettings : AppCompatActivity() {
    private var imageUri : Uri?=null
    private val notificationID = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewmodel :PosSettingViewModel by viewModels()
        val binding=ActivityPosSettingsBinding.inflate(layoutInflater) //view biding
        setContentView(binding.root) //.root returns the directory of the
        var imageName:String = "";
        val takePic = registerForActivityResult(ActivityResultContracts.TakePicture()){ isSuccess ->
            /*
                ActivityResultContracts.TakePicture() takes a picture and save to the provided Uri
                if the image is saved successfully, the function will return "true"
                "isSuccess" is the returned value from .TakePicture()
                 */
            if(isSuccess){// if the picture was taken and saved successfully.. (this is basically "if (true)"
                //create an alert box. after clicking "yes", the image will be put into the layout
                val alertdialog = AlertDialog.Builder(this)
                alertdialog.setTitle("Upload picture status")
                alertdialog.setMessage("Successfully uploaded")
                alertdialog.setPositiveButton("Yes"){
                        dialog,which -> Toast.makeText(this,"Yes",Toast.LENGTH_SHORT).show()
                    val conStraintLayout = findViewById<ConstraintLayout>(R.id.lol)
                    val factor:Float = conStraintLayout.context.resources.displayMetrics.density
                    Log.i("SettingActivity", factor.toString())
                    val width = conStraintLayout.width * 0.5 *factor
                    val height = conStraintLayout.height*0.3 * factor

                    val imageView = ImageView(this)
                    imageView.layoutParams=ConstraintLayout.LayoutParams(width.toInt(),height.toInt())
                    imageView.setImageURI(imageUri) //use the value saved in the code below
                    conStraintLayout.addView(imageView)
                }
                alertdialog.show()


            }else{// if the picture wasn't taken or saved successfully.....
                //create an alert box. after clicking ok, nothing happens lol
                val alertdialog = AlertDialog.Builder(this)
                alertdialog.setTitle("Upload picture status")
                alertdialog.setMessage("Upload Failed")
                alertdialog.setNegativeButton("OK"){
                        dialong ,which->Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
                }
                alertdialog.show()
            }
        }


        binding.uploadDailyBTN.setOnClickListener{
            val imagePath = File(getExternalFilesDir(null),"my_images") //create a new File instance from parent pathname and child pathname
            imagePath.mkdirs() //create the directory from the given parameter above^^^^
            val newFile=File(imagePath,"img_${System.currentTimeMillis()}.jpg") // create a new jpg file using the path from imagePath, each file is named according to the time of creation
            imageName=newFile.name //store the image name for future use
            val imgUri :Uri= FileProvider.getUriForFile(this,"com.example.posapp.fileprovider",newFile) //generate a new URI
            this.imageUri=imgUri //not related to the code, just save the value so we can use it in the callback
            takePic.launch(imgUri)  //call ActivityResultLauncher and pass the generated URI to it
        }


        binding.searchcontactBtn.setOnClickListener{
            Toast.makeText(this,"Going to the search contact page",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ContactActivity::class.java)
            startActivity(intent)
        }
        binding.uploadImagetoServerBtn?.setOnClickListener {
            Log.i("KEK",imageUri.toString())
            Log.i("imageName",imageName)
            GlobalScope.launch {
                val url = "http://10.0.2.2/pos/pos_api/public/createimage"
                val post_request_body = JSONObject()
                post_request_body.put("imageName",imageName)
                post_request_body.put("imagePath",imageUri.toString())
                val jsonObj = JsonObjectRequest(Request.Method.POST,url,post_request_body,
                    {response->
                        Log.i("UploadImageToServer","Successful $response")
                    }, {
                            error->Log.i("KKKKK","$error")
                    })
                jsonObj.retryPolicy=DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1f)
                VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonObj)
            }

        }
        binding.btnSubmitToRemoteServer?.setOnClickListener {
            GlobalScope.launch {
                val datafromlocalDB = getdatafromlocalDB()
                for(order in datafromlocalDB.await()){
                    val post_request_body = JSONObject()
                    post_request_body.put("branch_id",order.branchID)
                    post_request_body.put("staff_id",order.staffID)
                    post_request_body.put("order_local_id",order.uid)

                    val url = "http://10.0.2.2/pos/pos_api/public/order"
                    val jsonObjReq = JsonObjectRequest(Request.Method.POST,url,post_request_body, {
                            response ->  Log.i("VolleyPostRequest","Successful: $response") },
                        { error->Log.i("VollyPostError",error.toString()) })

                    jsonObjReq.retryPolicy=DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1f)
                    VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonObjReq)
                }
            }
        }
        binding.btnRetrieveFromRemoteServer?.setOnClickListener {
            GlobalScope.launch {

                //10.0.2.2 is our emulator IP address
                val url = "http://10.0.2.2/pos/pos_api/public/orders"

                val jsonArrayReq = JsonArrayRequest(Request.Method.GET,url,null,
                    //listener (if there's a success response coming back from the server
                    { response: JSONArray ->
                        for(i in 0 until response.length()){
                            val order = response.getJSONObject(i)
                            Log.i("VolleyGetRequest","Successful $response")
                        }
                    },//error listener (if something goes wrong)
                    { error ->  Log.d("JSONArrayRequestError",it.toString()) }
                )
                //set no retry --> prevent duplicate transaction
                jsonArrayReq.retryPolicy=DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,0,1f)

                //add to the request queue
                VolleySingleton.getInstance(applicationContext).addToRequestQueue(jsonArrayReq)
            }
        }



        binding.btnOrderManager?.setOnClickListener {
            val intent = Intent(this,OrderManager::class.java)
            startActivity(intent)
        }

        binding.btnPlaymusicBg?.setOnClickListener {
            var buildnoti = NotificationCompat.Builder(this,"90200")
            val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //start a service
            Intent(this,PlayMusicInBGService::class.java).also {
                intent->startService(intent)
            }
            val intent = Intent(this,PosSettings::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent:PendingIntent=PendingIntent.getActivity(this,0,intent,0)
            createNotificationChannel()

            buildnoti.setSmallIcon(R.drawable.ic_bg_music_stat)
            buildnoti.setContentTitle("Music is playing in background")
            buildnoti.setContentIntent(pendingIntent)
            buildnoti.setPriority(NotificationCompat.PRIORITY_DEFAULT)
            buildnoti.setAutoCancel(false)
            buildnoti.setOnlyAlertOnce(true)
            val playIntent = Intent(this,PlayMusicInBGBroadCastReceiver::class.java).apply {
                action= MEDIAPLAYER_PLAY
            }
            val playPendingIntent:PendingIntent=PendingIntent.getBroadcast(this,0,playIntent,0)
            val stopIntent = Intent(this,PlayMusicInBGBroadCastReceiver::class.java).apply {
                action= MEDIAPLAYER_STOP
            }
            val stopPendingIntent:PendingIntent=PendingIntent.getBroadcast(this,0,stopIntent,0)
            buildnoti.addAction(R.drawable.ic_action_play,"Play",playPendingIntent)
            buildnoti.addAction(R.drawable.ic_action_stop,"Stop",stopPendingIntent)
            notificationManager.notify(notificationID,buildnoti.build())




        }
        binding.btnStopmusicBg?.setOnClickListener {
            Intent(this,PlayMusicInBGService::class.java).also{
                intent->stopService(intent)
            }
            viewmodel.MusicStatusLive.value="PppP"
        }
    }


    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){//make sure to only create a notification on API 26+
            val name = "Play BG Status Channel"
            val descriptionText = "A notification to show background music status"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("90200",name,importance).apply {
                description=descriptionText
            }
            val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun getdatafromlocalDB()=GlobalScope.async{
        val db = POSAppDatabase.getInstance(applicationContext)

        //return the deferred List<Order> from .getAll()
        db.orderDAO().getAll()
    }
    companion object{
        var statusText:String?=null
    }
}