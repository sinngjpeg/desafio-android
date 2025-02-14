package com.picpay.desafio.android.common

import android.widget.ImageButton
import android.widget.TextView

object AccessibilityUtils {

    /**
     * Define uma label de acessibilidade para um TextView representando 'Usuário'.
     */
    fun setLabelForUser(textView: TextView) {
        val label = "Usuário: "
        val content = textView.text.toString()
        textView.contentDescription = "$label$content"
    }

    /**
     * Define uma label de acessibilidade para um TextView representando 'Nome completo'.
     */
    fun setLabelFullName(textView: TextView) {
        val label = "Nome completo: "
        val content = textView.text.toString()
        textView.contentDescription = "$label$content"
    }

    /**
     * Define uma label de acessibilidade para um botão representando 'Botão voltar'.
     */
    fun setLabelForButtonBack(button: ImageButton) {
        val label = "Botão voltar"
        button.contentDescription = label
    }
}
