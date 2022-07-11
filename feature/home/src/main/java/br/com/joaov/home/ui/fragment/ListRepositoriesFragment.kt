package br.com.joaov.home.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import br.com.joaov.home.databinding.FragmentListRepositoriesBinding
import br.com.joaov.home.ui.adapter.RepositoryAdapter
import br.com.joaov.home.ui.extension.setOnLastItemListener
import br.com.joaov.home.ui.extension.showAnimation
import br.com.joaov.home.ui.viewModel.ListRepositoriesViewModel
import br.com.joaov.home.ui.viewModel.model.ListRepositoriesEvent
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class ListRepositoriesFragment : Fragment() {
    
    private lateinit var binding: FragmentListRepositoriesBinding
    private val viewModel by viewModel<ListRepositoriesViewModel>()
    private val adapter by lazy { RepositoryAdapter() }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        setupObservers()
    }
    
    private fun setupViews() {
        binding.rvRepositories.adapter = adapter
    }
    
    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.rvRepositories.isVisible = state.showList
            binding.loading.root.showAnimation(state.loading)
            binding.errorPagination.root.isVisible = state.showFooterPaginationError
            binding.errorPagination.tvError.setText(state.messageFooter)
            binding.withoutConnectionScren.root.isVisible = state.showScreenWithoutConnection
            binding.tvWithoutConnectionWithCache.isVisible = state.showTextWithoutConnection
            adapter.submitList(state.listRepositories)
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ListRepositoriesEvent.GoToWeb -> {
                    startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(event.url)
                        }
                    )
                }
                is ListRepositoriesEvent.ShowMessage -> {
                    Snackbar.make(binding.root, getString(event.idMessage), Snackbar.LENGTH_SHORT).show()
                }
            }
            
        }
    }
    
    private fun setupListeners() {
        adapter.onClickItem = {
            viewModel.tapOnItem(it)
        }
        
        adapter.onClickUser = {
            viewModel.tapOnUserItem(it)
        }
        
        binding.rvRepositories.setOnLastItemListener {
            viewModel.loadNewPage()
        }
        binding.withoutConnectionScren.btnRetry.setOnClickListener {
            viewModel.loadNewPage()
        }
        
        binding.errorPagination.btnRefresh.setOnClickListener {
            viewModel.loadNewPage()
        }
    }
    
}