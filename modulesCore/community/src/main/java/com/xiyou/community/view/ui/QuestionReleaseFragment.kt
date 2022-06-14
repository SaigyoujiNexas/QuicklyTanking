package com.xiyou.community.view.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.common.utils.DialogUtil
import com.example.common.utils.ToastUtil
import com.xiyou.community.databinding.FragmentQuestionReleaseBinding
import com.xiyou.community.viewModel.QuestionInfoViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@AndroidEntryPoint
class QuestionReleaseFragment : Fragment() {

    private var _binding: FragmentQuestionReleaseBinding? = null

    val viewModel: QuestionInfoViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionReleaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etQuestionReleaseTitle.setText(viewModel.title)
        binding.etQuestionReleaseContent.setText(viewModel.content)
        binding.fabQuestionRelease.setOnClickListener {

            val dialog = context?.let { it1 -> DialogUtil.showLoading(it1, "上传中") }
            dialog?.show()
            viewModel.releaseQuestion(binding.etQuestionReleaseTitle.text.toString(),
            binding.etQuestionReleaseContent.text.toString()){
                dialog?.hide()
                ToastUtil.showToast("发布成功")
                findNavController().popBackStack()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.title = ""
        viewModel.content = ""
    }


    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}