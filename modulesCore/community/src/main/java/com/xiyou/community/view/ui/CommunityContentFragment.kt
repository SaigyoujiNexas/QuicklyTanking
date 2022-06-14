package com.xiyou.community.view.ui

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xiyou.community.data.CommunityData
import com.xiyou.community.data.QuestionCard
import com.xiyou.community.databinding.FragmentCommunityContentBinding
import com.xiyou.community.view.adapter.ImageAdapter
import com.xiyou.community.view.adapter.ImageDiffCallback
import com.xiyou.community.view.adapter.questionAnswer.QuestionAnswerAdapter
import com.xiyou.community.view.adapter.questionAnswer.QuestionAnswerDiffCallback
import com.xiyou.community.viewModel.QuestionInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class QuestionInfoFragment : Fragment() {

    private var _binding: FragmentCommunityContentBinding ? = null

    private lateinit var content: CommunityData
    private lateinit var question: QuestionCard
    private val binding: FragmentCommunityContentBinding
    get() = _binding!!

    private val viewModel: QuestionInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.
        let {
            content = it.getParcelable<CommunityData>("content") as CommunityData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityContentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivCommunityReleaserHead.load(content.avatar)
        binding.tvCommunityReleaserName.text = content.author
        binding.tvCommunityReleaserContent.text = content.content
        val manager = LinearLayoutManager(context)
        manager.isSmoothScrollbarEnabled = false
        manager.orientation = RecyclerView.VERTICAL
        binding.rvCommunityContentImage.layoutManager = manager
        val adapter = ImageAdapter(ImageDiffCallback())
        binding.rvCommunityContentImage.adapter = adapter
        adapter.submitList(content.picture)
        binding.includeItemCommunityReply.tvCommunityReplyName.text = content.author
        binding.includeItemCommunityReply.tvCommunityReplyContent.text = content.reContent
        val reAdapter = ImageAdapter(ImageDiffCallback())
        binding.includeItemCommunityReply.rvCommunityContentImage.apply{
            layoutManager = LinearLayoutManager(context)
            this.adapter = reAdapter
        }
        reAdapter.submitList(content.rePicture)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}