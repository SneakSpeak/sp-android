package fi.wintus.nomnom.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import io.sneakspeak.sneakspeak.fragments.GreetingFragment
import io.sneakspeak.sneakspeak.fragments.RegisterFragment
import io.sneakspeak.sneakspeak.fragments.UserChatFragment
import java.util.*


class MainFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // val fragMan: FragmentManager = fm
    val TAG = "MainFragmentAdapter"

    val titles = listOf("Servers", "Users", "Channels")
    val fragments = ArrayList<Fragment>()

    init {
        Log.d(TAG, "Creating fragments.")

        fragments.add(RegisterFragment())
        fragments.add(UserChatFragment())
        fragments.add(GreetingFragment())
    }


    override fun getPageTitle(position: Int) = titles[position]

    override fun getCount() = titles.size

    override fun getItem(position: Int) = fragments[position]

    override fun getItemPosition(obj: Any) = POSITION_NONE
}
