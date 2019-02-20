package com.example.litechat.presenter

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.litechat.contracts.StatusContract
import com.example.litechat.model.UserDataModel
import com.example.litechat.model.UserProfileData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_status.view.*
import java.io.File

class StatusFragmentPresenter(view : View) : StatusContract.StatusPresenter
{

    var currentView = view  //This variable stores the current instance of the view that is calling it

    /**
     * This function updates the current activity enterd by the user on firebase as well as in [UserProfileData] for it to be used locally
     * It takes the new activity enterd by the user as a parameter and updates it at both the locations
     * This function is called from the view([FragmentStatus]) whenever the user clicks on the tick image button
     */

    override fun updateUserInfo(activity : String) {
        var database = FirebaseFirestore.getInstance()
        UserProfileData.UserCurrentActivity = activity
        var map : HashMap<String , String> = HashMap()
        map.put("currentActivity" , activity)
        database.collection("Users").document(UserProfileData.UserNumber).set(map , SetOptions.merge())
            .addOnSuccessListener {
            /**
             * Do something if the updating of user is successful
             */
        }
            .addOnFailureListener {
                /**
                 * Do something if data updating fails
                 */
            }
    }

    /**
     * This function updates the status image selected by the user on firebase storage as well as the Uri in [UserProfileData] for it to be used locally
     * It takes the new Uri of the image selected by the user as a parameter and updates it at both the locations
     * This function is called from the view([FragmentStatus]) whenever the user clicks on the tick image button
     */

    override fun updateStatusImage(uri: Uri) {
        var mStorageRef = FirebaseStorage.getInstance().reference
        var path = uri
        Log.d("Mobile Number Check" , UserProfileData.UserNumber)
        mStorageRef.child(UserProfileData.UserNumber).child("StatusImage").putFile(path)
            .addOnSuccessListener {
            Toast.makeText(currentView.context , "Upload Successful" , Toast.LENGTH_SHORT).show()
            currentView.statusImageView.setImageURI(path)
        }
            .addOnFailureListener {
            Toast.makeText(currentView.context , "Upload UnSuccessful" , Toast.LENGTH_SHORT).show()
            Log.d("Finding Error" , it.toString())
        }
    }
}