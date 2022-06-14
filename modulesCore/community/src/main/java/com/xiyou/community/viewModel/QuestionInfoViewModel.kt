package com.xiyou.community.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.example.modulespublic.common.net.BaseResponse
import com.xiyou.community.data.*
import com.xiyou.community.repository.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionInfoViewModel
@Inject
    constructor(val repository: CommunityRepository)
    : ViewModel() {

    val list = MutableLiveData(listOf<QuestionCard>())
    val questions: LiveData<List<QuestionCard>>
        get()=list
    var title: String? = ""
    var content: String? = ""
    val mutableContent = MutableLiveData<List<CommunityData>>()
    val contents: LiveData<List<CommunityData>>
    get() = mutableContent
    fun getAllQuestion(onSuccess:() -> Unit = {}, onFailure:() -> Unit = {}) = viewModelScope.launch {
        val res: List<QuestionCard>
        try{
            res = repository.getAllQuestionList()
            list.postValue(res)
            onSuccess.invoke()
        }catch (e: Throwable)
        {
            ToastUtil.showToast(e.localizedMessage?:e.message?:"")
            onFailure.invoke()
        }

    }
    fun releaseQuestion(title: String, content: String, onSuccess: () -> Unit = {}) = viewModelScope.launch {
        val question = QuestionRelease(title = title, content = content)
        var res: BaseResponse<String?>?
        try{
            res = repository.releaseQuestion(question)
        }catch(e: Throwable)
        {
            res = BaseResponse(e.localizedMessage,200, e.localizedMessage)
        }
        when(res!!.isSuccess)
        {
            true ->  onSuccess.invoke()
            false -> ToastUtil.showToast(res.msg)
        }
    }
    fun releaseAnswer(answer: String, onSuccess: () -> Unit) = viewModelScope.launch {
        val ans = Answer(answer)
        var res: BaseResponse<String?>?
        try{
            res = repository.releaseAnswer(ans)
        }catch (e: Throwable)
        {
            res = BaseResponse(e.localizedMessage, 200, e.localizedMessage)
        }
        when(res!!.isSuccess)
        {
            true -> onSuccess.invoke()
            false -> ToastUtil.showToast(res.msg)
        }
    }
    fun getCommunity() = viewModelScope.launch {
        var res: List<CommunityData>?
        try{
            res = repository.getCommunity()
        }catch(e: Throwable)
        {
            res = null
            ToastUtil.showToast(e.localizedMessage?:e.message?:"")
        }
        res?.let{
            mutableContent.postValue(it)
        }
    }
}