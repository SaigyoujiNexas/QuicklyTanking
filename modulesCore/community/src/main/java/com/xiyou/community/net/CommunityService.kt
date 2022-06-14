package com.xiyou.community.net

import com.example.modulespublic.common.net.BaseResponse
import com.xiyou.community.data.Answer
import com.xiyou.community.data.CommunityData
import com.xiyou.community.data.QuestionCard
import com.xiyou.community.data.QuestionRelease
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val GET_COMMUNITY = "getCommunity"
interface CommunityService {
    @GET("getQuestion")
    suspend fun getAllQuestionList() : Array<QuestionCard>

    @POST("")
    suspend fun releaseQuestion(@Body request: QuestionRelease): BaseResponse<String?>

    @POST("")
    suspend fun releaseAnswer(@Body request: Answer) : BaseResponse<String?>

    @GET(GET_COMMUNITY)
    suspend fun getCommunity(): List<CommunityData>
}