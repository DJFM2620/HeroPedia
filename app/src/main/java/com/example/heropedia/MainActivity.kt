package com.example.heropedia

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(){

    private lateinit var imageProfile : ImageView
    private lateinit var buttonEdit : MaterialButton
    private lateinit var profileOrCoverImage : String
    private var imageUri : Uri? = null

    private var cameraPermission = arrayOf<String>()

    //private val CAMERA_REQUEST : Int = 100
    //private val IMAGE_PICK_CAMERA_REQUEST : Int = 400

    companion object {
        private const val CAMERA_REQUEST = 100
        private const val IMAGE_PICK_CAMERA_REQUEST = 400
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.fragment_overview, null)

        imageProfile = view.findViewById(R.id.profile_image)
        buttonEdit = view.findViewById(R.id.btn_edit_image)

        val progressDialog : ProgressDialog = ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false)

        cameraPermission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        buttonEdit.setOnClickListener(View.OnClickListener {

            fun onClick(view: View?) {

                progressDialog.setMessage("Updating Profile Picture")
                progressDialog.show()
                profileOrCoverImage = "Image"

                showImagePicDialog()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        imageProfile?.let { Glide.with(this).load(imageUri).into(it) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){

            CAMERA_REQUEST -> {

                if(grantResults.isNotEmpty()){

                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if(cameraAccepted && writeStorageAccepted){

                        pickFromCamera()

                    }else{
                        Toast.makeText(this, "Please enable camera and storage permissions", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Something went wrong! try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showImagePicDialog(){

        val options = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Pick Image From")
        builder.setItems(options) { dialog : DialogInterface, which ->

            if (which == 0) {

                if (!checkCameraPermission()) {

                    requestCameraPermission()

                } else {
                    pickFromCamera()
                }
            }
        }
        builder.create().show()
    }
    private fun checkCameraPermission(): Boolean {

        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED)
        val result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED)

        return result && result1
    }

    private fun requestCameraPermission(){

        requestPermissions(cameraPermission, CAMERA_REQUEST)
    }

    private fun pickFromCamera(){

        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_pic")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_description")

        imageUri = this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_REQUEST)
    }
}