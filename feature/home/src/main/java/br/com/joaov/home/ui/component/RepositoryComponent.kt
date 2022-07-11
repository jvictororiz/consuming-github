package br.com.joaov.home.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.com.joaov.home.R
import br.com.joaov.home.databinding.ComponentRepositoryBinding
import br.com.joaov.home.model.RepositoryModel
import br.com.joaov.home.ui.extension.loadByUrl
import com.bumptech.glide.Glide

internal class RepositoryComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyle, defStyleRes) {
    
    private val binding by lazy {
        ComponentRepositoryBinding.inflate(LayoutInflater.from(context), this, true)
    }
    
    var onClickUser: ((RepositoryModel?) -> Unit)? = null
        set(value) {
            field = value
            binding.imgProfile.setOnClickListener { value?.invoke(repository) }
        }
    var onClickItem: ((RepositoryModel?) -> Unit)? = null
        set(value) {
            field = value
            binding.root.setOnClickListener {
                value?.invoke(repository)
            }
        }
    
    var repository: RepositoryModel? = null
        set(value) {
            field = value
            with(binding) {
                tvNameRepository.text = value?.fullName.orEmpty()
                tvNameOwner.text = value?.owner?.login.orEmpty()
                tvCountStar.text = value?.stargazersCount.toString()
                tvCountFork.text = value?.forksCount.toString()
                imgProfile.loadByUrl(value?.owner?.avatarUrl)
            }
        }
}
