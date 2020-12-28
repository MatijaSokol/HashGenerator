package hr.ferit.matijasokol.hashgenerator.utils

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snackbar(
        message: String,
        actionTextColor: Int? = null,
        actionMessage: String? = null,
        action: (() -> Unit)? = null
) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).apply {
        if (actionMessage != null && action != null) {
            setAction(actionMessage) { action() }
        }
        if (actionTextColor != null) {
            setActionTextColor(ContextCompat.getColor(requireContext(), actionTextColor))
        }
    }.show()
}