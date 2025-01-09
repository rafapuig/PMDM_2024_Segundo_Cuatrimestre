package com.example.booksroomdemo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.booksroomdemo.BooksApplication
import com.example.booksroomdemo.data.database.BooksDatabase
import com.example.booksroomdemo.data.database.dao.BookDao
import com.example.booksroomdemo.data.database.BooksProvider
import com.example.booksroomdemo.data.database.dao.PublisherDao
import com.example.booksroomdemo.data.database.PublishersProvider
import com.example.booksroomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookDao: BookDao
    private lateinit var publisherDao: PublisherDao

    private lateinit var database: BooksDatabase


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

        database = (application as BooksApplication).database

        bookDao = database.bookDao()
        publisherDao = database.publisherDao()
        //bookDao = BooksApplication.Companion.database.bookDao()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            //bookDao.getAllObservable().collect {
            /*bookDao.getBooksWithPublisher().collect {
                binding.message.text = it.toString()
            }*/
            /*bookDao.getBooksWithTags().collect {
                binding.message.text = it.toString()
            }*/
            bookDao.getBooksAndPublisher().collect {
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
            publisherDao.insert(PublishersProvider.publishers)
            bookDao.insertAll(BooksProvider.books)


            /*val books = dao.getAll()
            withContext(Dispatchers.Main) {
                binding.message.text = books.toString()
            }*/
        }
    }


}