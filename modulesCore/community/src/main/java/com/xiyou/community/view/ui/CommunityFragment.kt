package com.xiyou.community.view.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiyou.community.R
import com.xiyou.community.databinding.FragmentCommunityBinding
import com.xiyou.community.view.adapter.communityCard.CommunityCardAdapter
import com.xiyou.community.view.adapter.communityCard.CommunityDiffCallback
import com.xiyou.community.viewModel.QuestionInfoViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CommunityFragment : Fragment() {

    private val viewModel: QuestionInfoViewModel by viewModels()

    private lateinit var binding: FragmentCommunityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCommunity()
        binding.fabCommunityEdit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_community_to_question_release))
        val rv = binding.rvCommunityQuestionCards
        rv.layoutManager = LinearLayoutManager(context)
        val adapter = CommunityCardAdapter(CommunityDiffCallback())
        rv.adapter = adapter

        viewModel.contents.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_community, menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            (menu.findItem(R.id.search_community).actionView as SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
                setIconifiedByDefault(true)
            }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = CommunityFragment()
    }

}