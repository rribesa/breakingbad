package com.rribesa.breakingbad.character.presentation.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rribesa.breakingbad.R
import com.rribesa.breakingbad.character.domain.model.Character
import com.rribesa.breakingbad.databinding.CharacterListItemBinding

class CharacterListAdapter(
    private val characters: List<Character>,
    private val adapterOnClick: (Character) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CharacterViewHolder).bind(characters[position], adapterOnClick)
    }
}

class CharacterViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    private val binding = CharacterListItemBinding.bind(itemView)
    fun bind(character: Character, click: (Character) -> Unit) {
        binding.characterListName.text = character.name
        binding.characterListNicknameText.text = character.nickname
        Glide
            .with(itemView.context)
            .load(character.image)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.CharacterListAvatarImage)

        itemView.setOnClickListener { click(character) }
    }
}
