package com.neatroots.yoqolganproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.neatroots.yoqolganproject.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        binding.search.setOnClickListener {
            startActivity(Intent(this,SearchActivity::class.java))
        }
        var intent=Intent(this@HomeActivity,CategoryActivity::class.java)
        binding.salad.setOnClickListener {
            intent.putExtra("TITTLE","Salad")
            intent.putExtra("CATEGORY","Salad")
            startActivity(intent)
        }
        binding.mainDish.setOnClickListener {
            intent.putExtra("TITTLE","Main Dish")
            intent.putExtra("CATEGORY","Dish")
            startActivity(intent)
        }
        binding.drinks.setOnClickListener {
            intent.putExtra("TITTLE","Drinks")
            intent.putExtra("CATEGORY","Drinks")
            startActivity(intent)
        }
        binding.more.setOnClickListener {
            var dialog= Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
        binding.desserts.setOnClickListener {
            intent.putExtra("TITTLE","Desserts")
            intent.putExtra("CATEGORY","Desserts")
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        dataList= ArrayList()
        binding.rvPopular.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var db=Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject=db.getDao()
        var recipes=daoObject.getAll()
        for (i in recipes!!.indices){
            if (recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=PopularAdapter(dataList,this)
            binding.rvPopular.adapter=rvAdapter
        }
    }
}