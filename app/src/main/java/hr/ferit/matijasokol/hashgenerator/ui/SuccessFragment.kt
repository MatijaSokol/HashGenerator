package hr.ferit.matijasokol.hashgenerator.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import hr.ferit.matijasokol.hashgenerator.R
import hr.ferit.matijasokol.hashgenerator.databinding.FragmentSuccessBinding
import hr.ferit.matijasokol.hashgenerator.other.Constants.ENCRYPTED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    private val args: SuccessFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuccessBinding.bind(view)

        binding.tvHash.text = args.hash

        binding.btnCopy.setOnClickListener {
            onCopyClicked()
        }
    }

    private fun onCopyClicked() {
        lifecycleScope.launch {
            copyToClipboard(args.hash)
            applyAnimations()
        }
    }

    private fun copyToClipboard(hash: String) {
        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(ENCRYPTED, hash)
        clipboardManager.setPrimaryClip(clipData)
    }

    private suspend fun applyAnimations() {
        binding.include.messageBackground.animate().translationY(80f).duration = 200L
        binding.include.tvMessage.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.include.messageBackground.animate().translationY(-80f).duration = 400L
        binding.include.tvMessage.animate().translationY(-80f).duration = 400L
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}