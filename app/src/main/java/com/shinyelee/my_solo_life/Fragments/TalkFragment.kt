package com.shinyelee.my_solo_life.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shinyelee.my_solo_life.R
import com.shinyelee.my_solo_life.board.BoardInsideActivity
import com.shinyelee.my_solo_life.board.BoardListLVAdapter
import com.shinyelee.my_solo_life.board.BoardModel
import com.shinyelee.my_solo_life.board.BoardWriteActivity
import com.shinyelee.my_solo_life.databinding.FragmentTalkBinding
import com.shinyelee.my_solo_life.utils.FBRef

class TalkFragment : Fragment() {

    // viewBinding
    private var vBinding : FragmentTalkBinding? = null
    private val binding get() = vBinding!!

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()
    private val TAG = TalkFragment::class.java.simpleName

    private lateinit var boardRVAdapter: BoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // viewBinding
        vBinding = FragmentTalkBinding.inflate(inflater, container, false)

        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter

        binding.boardListView.setOnItemClickListener { parent, view, position, id ->

            // 첫 번째 방법 //
            // listView에 있는 데이터 title, contents, time 다 다른 액티비티로 전달해서 만드는 방법
//
//            val intent = Intent(context, BoardInsideActivity::class.java)
//
//            intent.putExtra("title", boardDataList[position].title)
//            intent.putExtra("contents", boardDataList[position].contents)
//            intent.putExtra("time", boardDataList[position].time)
//
//            startActivity(intent)

            // 두 번째 방법 //
            // Firebase 자체 데이터 id(uid 아님)를 기반으로 다시 데이터를 받아오는 방법
            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)

        }

        // talk -> write
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        // talk -> home
        binding.homeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }
        // talk -> tip
        binding.tipT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }
        // talk -> bookmark
        binding.bookmarkT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }
        // talk -> store
        binding.storeT.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        getFBBoardData()

        return binding.root

    }

    private fun getFBBoardData() {

        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for(dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()
                Log.d(TAG, boardDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }

        }
        FBRef.boardRef.addValueEventListener(postListener)

    }


}