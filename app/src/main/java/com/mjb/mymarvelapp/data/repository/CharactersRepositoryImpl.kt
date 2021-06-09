package com.mjb.mymarvelapp.data.repository

import com.mjb.mymarvelapp.data.datasource.api.service.CharactersApi
import com.mjb.mymarvelapp.infrastructure.exception.ErrorHandler
import com.mjb.mymarvelapp.infrastructure.exception.ErrorHandler.NETWORK_ERROR_MESSAGE
import com.mjb.mymarvelapp.infrastructure.network.NetworkHandler
import com.mjb.mymarvelapp.infrastructure.utils.BadRequest
import com.mjb.mymarvelapp.infrastructure.utils.Error
import com.mjb.mymarvelapp.infrastructure.utils.ErrorNoConnection
import com.mjb.mymarvelapp.infrastructure.utils.Success
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apiService: CharactersApi,
    private val networkHandler: NetworkHandler
) : CharactersRepository {
    override fun getCharactersList(offset: Int) = flow {
        emit(
            if (networkHandler.isConnected == true) {
                apiService.getCharactersList(offset).run {
                    if (isSuccessful && body() != null) {
                        Success(body()!!.apiDataResponse?.results?.map { it.toCharacterListDomain() })
                    } else {
                        BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }

    override fun getCharacterDetail(id: Int) = flow {
        emit(
            if (networkHandler.isConnected == true) {
                apiService.getCharacterDetail(id).run {
                    if (isSuccessful && body() != null) {
                        Success(body()!!.apiDataResponse?.results?.map { it.toCharacterDetailDomain() })
                    } else {
                        BadRequest(Throwable(ErrorHandler.BAD_REQUEST))
                    }
                }
            } else {
                ErrorNoConnection(Throwable(NETWORK_ERROR_MESSAGE))
            }
        )
    }.catch {
        it.printStackTrace()
        emit(Error(Throwable(ErrorHandler.UNKNOWN_ERROR)))
    }
}
