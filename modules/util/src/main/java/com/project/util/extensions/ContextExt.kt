package com.project.util.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.gms.common.GoogleApiAvailability

const val GOOGLE_MAPS_APP = "com.google.android.apps.maps"
const val HUAWEI_MAPS_APP = "com.huawei.maps.app"

fun Context.openMap(lat: Double, lon: Double) {
    val packName = if (isHmsOnly(this)) {
        HUAWEI_MAPS_APP
    } else {
        GOOGLE_MAPS_APP
    }

    val uri = Uri.parse("geo:$lat,$lon?q=$lat,$lon")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage(packName);
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        openMapOptions(lat, lon)
    }
}

private fun Context.openMapOptions(lat: Double, lon: Double) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:$lat,$lon?q=$lat,$lon")
    )
    startActivity(intent)
}

private fun Context.isGmsAvailable(context: Context?): Boolean {
    var isAvailable = false
    if (null != context) {
        val result: Int = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        isAvailable = com.google.android.gms.common.ConnectionResult.SUCCESS == result
    }
    return isAvailable
}

fun Context.isHmsOnly(context: Context?) = isGmsAvailable(context)