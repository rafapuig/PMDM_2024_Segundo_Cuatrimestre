package com.example.booksroomdemo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.booksroomdemo.BooksApplication
import com.example.booksroomdemo.data.database.BookDao
import com.example.booksroomdemo.data.database.BooksProvider
import com.example.booksroomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookDao: BookDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bookDao = (application as BooksApplication).database.bookDao()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            bookDao.getAllObservable().collect {
                binding.message.text = it.toString()
            }
        }
    }

    private fun initListeners() {
        binding.loadButton.setOnClickListener { loadDataBase() }
        binding.removeButton.setOnClickListener { clearDatabase() }
        binding.viewList.setOnClickListener { viewBooks() }
    }

    private fun viewBooks() {
        startActivity(Intent(this, BooksListActivity::class.java))
    }

    private fun clearDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            bookDao.clear()
        }

    }


    private fun loadDataBase() {

        lifecycleScope.launch(Dispatchers.IO) {
            bookDao.insertAll(BooksProvider.books)
            /*val books = dao.getAll()
            withContext(Dispatchers.Main) {
                binding.message.text = books.toString()
            }*/
        }
    }


}