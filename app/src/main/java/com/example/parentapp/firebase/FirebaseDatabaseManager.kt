package com.example.parentapp.firebase

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

object FirebaseDatabaseManager {
    private val database = FirebaseDatabase.getInstance().reference

    /**
     * ✅ Register a child with a unique code and link to a parent.
     */
    suspend fun registerChild(uniqueCode: String, parentId: String, childName: String): Boolean {
        return try {
            val childData = mapOf(
                "parentId" to parentId,
                "childName" to childName,
                "screenTime" to 0, // Default screen time tracking
                "location" to "",  // Default empty location
                "sosAlert" to false // Default no SOS alert
            )
            database.child("children").child(uniqueCode).setValue(childData).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * ✅ Check if a child exists with a unique code.
     * Returns the child ID if found, otherwise returns `null`.
     */
    suspend fun checkChildCode(uniqueCode: String): String? {
        return try {
            val snapshot = database.child("children").child(uniqueCode).get().await()
            if (snapshot.exists()) {
                snapshot.key // Returns child ID
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * ✅ Get child's live location.
     * Returns a pair of (latitude, longitude) if found, otherwise returns `null`.
     */
    suspend fun getChildLocation(childId: String): Pair<Double, Double>? {
        return try {
            val snapshot = database.child("children").child(childId).child("location").get().await()
            if (snapshot.exists()) {
                val latitude = snapshot.child("latitude").getValue(Double::class.java) ?: 0.0
                val longitude = snapshot.child("longitude").getValue(Double::class.java) ?: 0.0
                Pair(latitude, longitude)
            } else {
                null // Location not found
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * ✅ Update child's live location.
     * Returns `true` if successful, otherwise `false`.
     */
    suspend fun updateChildLocation(childId: String, latitude: Double, longitude: Double): Boolean {
        return try {
            val locationData = mapOf("latitude" to latitude, "longitude" to longitude)
            database.child("children").child(childId).child("location").setValue(locationData).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * ✅ Get child's screen time.
     * Returns the screen time in minutes if found, otherwise `null`.
     */
    suspend fun getChildScreenTime(childId: String): Int? {
        return try {
            val snapshot = database.child("children").child(childId).child("screenTime").get().await()
            (snapshot.value as? Long)?.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * ✅ Update child's screen time.
     * Returns `true` if successful, otherwise `false`.
     */
    suspend fun updateChildScreenTime(childId: String, screenTime: Int): Boolean {
        return try {
            database.child("children").child(childId).child("screenTime").setValue(screenTime).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * ✅ Send SOS alert from child to parent.
     * Returns `true` if successful, otherwise `false`.
     */
    suspend fun sendSOSAlert(childId: String): Boolean {
        return try {
            val snapshot = database.child("children").child(childId).child("parentId").get().await()
            val parentId = snapshot.value as? String

            if (parentId != null) {
                val alertData = mapOf(
                    "childId" to childId,
                    "timestamp" to System.currentTimeMillis()
                )
                database.child("parents").child(parentId).child("sosAlerts").push().setValue(alertData).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * ✅ Fetch all SOS alerts for a parent.
     * Returns a list of child IDs that triggered the SOS.
     */
    suspend fun getSOSAlerts(parentId: String): List<String> {
        return try {
            val snapshot = database.child("parents").child(parentId).child("sosAlerts").get().await()
            if (snapshot.exists()) {
                snapshot.children.mapNotNull { it.child("childId").getValue(String::class.java) }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
