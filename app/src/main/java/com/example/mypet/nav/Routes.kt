package com.example.mypet.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mypet.R

const val ROOT = "root"
const val START = "start"
const val MAIN = "main"


sealed class Routes(val route: String, @StringRes val title: Int) {
    object ListProfile: Routes("listProfile", R.string.list_profile_title)
    object BugReport: Routes("bugReport", R.string.bug_report_title)
    object CreateProfile: Routes("createProfile", R.string.create_profile_title)
    object Profile: Routes("profile", R.string.profile_title)
    object UpdateProfile: Routes("updateProfile", R.string.update_profile_title)

    object CreateProcedure: Routes("createProcedure", R.string.create_procedure)
    object Procedure: Routes("procedure", R.string.procedure)
    object UpdateProcedure: Routes("updateProcedure", R.string.update_procedure)

    object CreateTherapy: Routes("createMedicine", R.string.create_therapy)
    object Therapy: Routes("therapy", R.string.therapy)
    object UpdateTherapy: Routes("updateMedicine", R.string.update_therapy)
}

sealed class BottomBarRoutes(val route: String, @DrawableRes var icon: Int, @StringRes val title: Int) {
    object ListHygiene: BottomBarRoutes("listHygiene", R.drawable.hygiene_icon, R.string.list_hygiene_title)
    object ListMedicine: BottomBarRoutes("listMedicine", R.drawable.medicine_icon, R.string.list_therapy_title)
}