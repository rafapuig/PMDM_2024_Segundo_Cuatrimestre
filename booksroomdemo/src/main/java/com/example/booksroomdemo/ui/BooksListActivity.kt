package com.example.booksroomdemo.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booksroomdemo.BooksApplication
import com.example.booksroomdemo.data.database.dao.BookDao
import com.example.booksroomdemo.databinding.ActivityBooksListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksListActivity : AppCompatActivity() {


    lateinit var binding: ActivityBooksListBinding
    lateinit var adapter: BookAdapter
    lateinit var bookDao: BookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBooksListBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bookDao = (application as BooksApplication).database.bookDao()

        initRecycler()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch(Dispatchers.IO) {
            adapter.books = bookDao.getAll()
        }
    }

    private fun initRecycler() {
        adapter = BookAdapter()
        binding.booksRecycler.adapter = adapter
        binding.booksRecycler.layoutManager = LinearLayoutManager(this)
    }
}