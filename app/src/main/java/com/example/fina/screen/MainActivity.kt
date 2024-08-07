package com.example.fina.screen

import com.example.fina.R
import com.example.fina.screen.favourite.FavouriteFragment
import com.example.fina.utils.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        supportFragmentManager
            .beginTransaction()
//            .addToBackStack(MoviesFragment::javaClass.name)
            .replace(R.id.layoutContainer, FavouriteFragment())
            .commit()
    }
}
