package hr.ferit.matijasokol.hashgenerator.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import hr.ferit.matijasokol.hashgenerator.R
import hr.ferit.matijasokol.hashgenerator.databinding.FragmentHomeBinding
import hr.ferit.matijasokol.hashgenerator.utils.snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setHasOptionsMenu(true)

        setListeners()
    }

    override fun onResume() {
        super.onResume()
        setAutoCompleteTextView()
    }

    private fun setListeners() {
        binding.btnGenerate.setOnClickListener {
            onGenerateClicked()
        }
    }

    private fun onGenerateClicked() {
        if (binding.plainText.text.isNotBlank()) {
            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess(getHashData())
            }
        } else {
            snackbar(getString(R.string.field_empty), R.color.blue, getString(R.string.ok)) {}
        }
    }

    private fun getHashData(): String {
        val algorithm = binding.autoCompleteTv.text.toString()
        val plainText = binding.plainText.text.toString()
        return viewModel.getHash(plainText, algorithm)
    }

    private fun setAutoCompleteTextView() {
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithms)
        binding.autoCompleteTv.setAdapter(arrayAdapter)
    }

    private suspend fun applyAnimations() {
        with(binding) {
            btnGenerate.isClickable = false
            tvTitle.animate().alpha(0f).duration = 400L
            btnGenerate.animate().alpha(0f).duration = 400L
            textInputLayout.animate().alpha(0f).translationXBy(1200f).duration = 400L
            plainText.animate().alpha(0f).translationXBy(-1200f).duration = 400L

            delay(300L)

            with(successBackground) {
                animate().alpha(1f).duration = 600L
                animate().rotationBy(720f).duration = 600L
                animate().scaleXBy(900f).duration = 800L
                animate().scaleYBy(900f).duration = 800L
            }

            delay(400L)

            ivSuccess.animate().alpha(1f).duration = 500L

            delay(800L)
        }
    }

    private fun navigateToSuccess(hash: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_menu -> {
                binding.plainText.text.clear()
                snackbar(getString(R.string.cleared))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}