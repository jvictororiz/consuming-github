package br.com.joaov.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.joaov.home.databinding.ItemRepositoryBinding
import br.com.joaov.home.model.RepositoryModel

internal class RepositoryAdapter : ListAdapter<RepositoryModel, RepositoryAdapter.ViewHolder>(RepositoryDiffUtil) {
    
    var onClickUser: ((RepositoryModel) -> Unit)? = null
    var onClickItem: ((RepositoryModel) -> Unit)? = null
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickItem,
            onClickUser
        )
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    
    override fun submitList(list: MutableList<RepositoryModel>?) {
        super.submitList(ArrayList(list?.toMutableList().orEmpty()))
    }
    
    override fun submitList(list: MutableList<RepositoryModel>?, commitCallback: Runnable?) {
        super.submitList(ArrayList(list?.toMutableList().orEmpty()), commitCallback)
    }
    
    class ViewHolder(
        private val binding: ItemRepositoryBinding,
        private val onClickItem: ((RepositoryModel) -> Unit)? = null,
        private val onClickUser: ((RepositoryModel) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(repository: RepositoryModel) {
            binding.repositoryComponent.onClickItem = { repositoryModel -> repositoryModel?.let { onClickItem?.invoke(it) } }
            binding.repositoryComponent.onClickUser = { repositoryModel -> repositoryModel?.let { onClickUser?.invoke(it) } }
            binding.repositoryComponent.repository = repository
        }
    }
    
    object RepositoryDiffUtil : DiffUtil.ItemCallback<RepositoryModel>() {
        
        override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
            return oldItem == newItem
        }
        
        override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
    
}