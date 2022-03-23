package com.shinyelee.my_solo_life.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shinyelee.my_solo_life.R

class ContentsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        val rv : RecyclerView = findViewById(R.id.rv)

        val items = ArrayList<ContentsModel>()
        items.add(ContentsModel("imageurl1", "title1"))
        items.add(ContentsModel("imageurl2", "title2"))
        items.add(ContentsModel("imageurl3", "title3"))

        val rvAdapter = ContentsRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 2)

    }

}