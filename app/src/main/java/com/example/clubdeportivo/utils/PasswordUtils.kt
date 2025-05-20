import android.graphics.Typeface
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import com.example.clubdeportivo.R

object PasswordUtils {

    // Configura el toggle para cualquier EditText e ImageButton
    fun setupPasswordToggle(editText: EditText, toggleButton: ImageButton) {
        var isPasswordVisible = false

        // Listener para el botón
        toggleButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(editText, toggleButton, isPasswordVisible)
        }
        // Estado inicial
        updateToggleIcon(toggleButton, isPasswordVisible)
    }

    private fun togglePasswordVisibility(
        editText: EditText,
        button: ImageButton,
        isVisible: Boolean
    ) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        // Fuerza la fuente predeterminada del sistema
        editText.typeface = Typeface.DEFAULT

        editText.setSelection(editText.text.length) // Mueve el cursor al final
        updateToggleIcon(button, isVisible)
    }

    private fun updateToggleIcon(button: ImageButton, isVisible: Boolean) {
        val iconRes = if (isVisible) {
            R.drawable.ic_open_eye
        } else {
            R.drawable.ic_close_eye
        }
        button.setImageResource(iconRes)
    }
}