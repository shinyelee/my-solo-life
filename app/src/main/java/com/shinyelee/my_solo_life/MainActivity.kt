package com.shinyelee.my_solo_life

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shinyelee.my_solo_life.auth.IntroActivity
import com.shinyelee.my_solo_life.databinding.ActivityMainBinding
import com.shinyelee.my_solo_life.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    // firebase
    private lateinit var auth: FirebaseAuth

    // viewBinding
    private var vBinding : ActivityMainBinding? = null
    private val binding get() = vBinding!!

    // fragments(view) list
    var viewList = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {

        // firebase
        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // viewBinding
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // fragments(view) list
        viewList.add(layoutInflater.inflate(R.layout.fragment_home, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_tip, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_talk, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_bookmark, null))
        viewList.add(layoutInflater.inflate(R.layout.fragment_store, null))

        // viewPager
        binding.viewPager.adapter = pagerAdapter()
        binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.home
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.tip
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.talk
                    3 -> binding.bottomNavigationView.selectedItemId = R.id.bookmark
                    4 -> binding.bottomNavigationView.selectedItemId = R.id.store
                }
            }
        })

        // viewPager
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> binding.viewPager.currentItem = 0
                R.id.tip -> binding.viewPager.currentItem = 1
                R.id.talk -> binding.viewPager.currentItem = 2
                R.id.bookmark -> binding.viewPager.currentItem = 3
                R.id.store -> binding.viewPager.currentItem = 4
            }
            return@setOnNavigationItemSelectedListener true
        }

        // 로그아웃 버튼 클릭하면
        binding.logoutBtn.setOnClickListener {

            // 로그아웃 실행
            auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // 스택에 쌓인 액티비티 종료하고 인트로 액티비티로 이동
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

    }

    inner class pagerAdapter : PagerAdapter() {

        override fun getCount() = viewList.size

        override fun isViewFromObject(view: View, `object`: Any) = view == `object`

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var currentView = viewList[position]
            binding.viewPager.addView(currentView)
            return currentView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            binding.viewPager.removeView(`object` as View)
        }
    }

    override fun onDestroy() {
        vBinding = null
        super.onDestroy()
    }

}