package com.atejeda.masterdetail.ui.fragments.pokemon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atejeda.masterdetail.core.datetype.ResultType
import com.atejeda.masterdetail.data.model.Pokemon
import com.atejeda.masterdetail.domain.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel  @Inject constructor(
    private val useCase: PokemonUseCase
) : ViewModel() {

    val allPokemons: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var isApiProgress: MutableLiveData<Boolean> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    var favouriteUpdated: MutableLiveData<Boolean> = MutableLiveData()

    fun getAll(limit:Int,offset:Int) {
        isLoadingLiveData(true)
        viewModelScope.launch {
            var response = useCase.getAll(limit,offset)

            if (response.resultType == ResultType.SUCCESS) {
                allPokemons.postValue(response.data!!)
                isLoadingLiveData(false)
            } else {
                error.postValue(response.error!!)
                isLoadingLiveData(false)
            }
        }
    }

    fun setFavourite(id: Int,checked:Boolean) {
        viewModelScope.launch {
            favouriteUpdated.value = useCase.setfavourite(id,checked)
        }
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isApiProgress.value = isLoading
    }
}