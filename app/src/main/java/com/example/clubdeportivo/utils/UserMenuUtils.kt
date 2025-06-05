package com.example.clubdeportivo.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.clubdeportivo.R
import com.example.clubdeportivo.activities.BuscarClienteActivity
import com.example.clubdeportivo.activities.ListaDiariaNoSociosHabilitadosActivity
import com.example.clubdeportivo.activities.MiUsuarioActivity
import com.example.clubdeportivo.activities.NuevaActividadActivity
import com.google.android.material.navigation.NavigationView

object UserMenuUtils {

    fun setupDrawer(
        activity: Activity
    ) {
        val drawerLayout: DrawerLayout = activity.findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = activity.findViewById(R.id.nav_view)
        val btnUserMenu: ImageButton = activity.findViewById(R.id.userMenu)

        // Configurar el header con el rol del usuario
        setupHeaderWithRole(activity, navigationView)

        // Abrir drawer al tocar ícono de usuario
        btnUserMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Navegación desde el drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val handled: Boolean = when (menuItem.itemId) {
                R.id.nav_search -> {
                    val intent = Intent(activity, BuscarClienteActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
                    activity.startActivity(intent, options.toBundle())
                    true
                }
                R.id.nav_new_actividad -> {
                    val intent = Intent(activity, NuevaActividadActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
                    activity.startActivity(intent, options.toBundle())
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(activity, MiUsuarioActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
                    activity.startActivity(intent, options.toBundle())
                    true
                }
                R.id.nav_day_enabled ->{
                    val intent = Intent(activity, ListaDiariaNoSociosHabilitadosActivity::class.java)
                    val options = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_right, R.anim.slide_out_left)
                    activity.startActivity(intent, options.toBundle())
                    true
                }
                else -> false
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            handled
        }
    }

    private fun setupHeaderWithRole(context: Context, navigationView: NavigationView) {
        // Obtener la vista del header (index 0 es el primer header)
        val headerView = navigationView.getHeaderView(0)

        // Obtener el TextView del rol
        val txtUserRole = headerView.findViewById<TextView>(R.id.txtUserRole)

        // Establecer el texto del rol usando UserSessionUtil
        val userRole = UserSessionUtil.getUserRole(context)
        txtUserRole.text = "Rol: $userRole"
    }
}