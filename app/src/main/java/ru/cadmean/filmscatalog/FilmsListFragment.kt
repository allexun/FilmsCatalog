package ru.cadmean.filmscatalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class FilmsListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var filmsAdapter: FilmsAdapter

    private val tag1 = "FilmsListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_films_list, container, false)

        val viewModel: FilmsViewModel by activityViewModels()

        filmsAdapter = FilmsAdapter(viewModel.images)

        recyclerView = rootView.findViewById(R.id.films_recycler)
        recyclerView.adapter = filmsAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        refreshLayout = rootView as SwipeRefreshLayout
        refreshLayout.setOnRefreshListener(this)

        viewModel.films.observe(this) { films ->
            for (film in films) {
                Log.d(tag1, film.toString())
            }

            refreshLayout.isRefreshing = false
            filmsAdapter.films = films
            filmsAdapter.notifyDataSetChanged()
        }

        viewModel.downloadedImage.observe(this) { film ->
            filmsAdapter.notifyItemChanged(filmsAdapter.films.indexOf(film))
        }

        return rootView
    }

    override fun onRefresh() {
        val viewModel: FilmsViewModel by activityViewModels()
        viewModel.loadFilms()
    }
}