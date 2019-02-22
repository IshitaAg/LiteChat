package com.example.litechat.model

import android.content.Context

import android.util.Log
import android.widget.Toast
import com.example.litechat.contracts.HomeActivityContract
import com.example.litechat.model.contactsRoom.User
import com.example.litechat.presenter.HomeActivityPresenter
import com.example.litechat.presenter.StatusFragmentPresenter
import com.google.firebase.firestore.FirebaseFirestore

class DataRetriveClass : HomeActivityContract.Model {
    override fun roomSetData(applicationContext: Context, userList: List<User>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun roomGetData(applicationContext: Context): List<User> {
        TODO(reason = "not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    /**
     * This class should not be used currently as the structure of firestore is not yet finalized.
     */

    override fun getUserDataFromFirestore(number: String){
        FirebaseFirestore.getInstance().collection("Users").document(number).get()
            .addOnSuccessListener {
                UserProfileData.UserName = it.getString("name")
                UserProfileData.UserNumber = it.getString("number")
                UserProfileData.UserCurrentActivity = it.get("currentActivity").toString()
                UserProfileData.UserAbout = it.getString("about").toString()
                UserProfileData.UserImage = it.getString("image").toString()
                UserProfileData.UserProfileImage = it.getString("profileImage").toString()
                Log.d("UserData" , "Data Retrieved class successfully called")
            }
    }


    override fun getCurrentActivitiesOfOtherUsers(presenter: StatusFragmentPresenter) {
        var maps: HashMap<String, String> = HashMap<String, String>()
        FirebaseFirestore.getInstance().collection("Users").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.id == UserProfileData.UserNumber)
                        continue
                    maps[document.getString("name").toString()] = document.getString("image").toString()
                }
                presenter.onStatusDataRecived(maps)
            }
    }
}