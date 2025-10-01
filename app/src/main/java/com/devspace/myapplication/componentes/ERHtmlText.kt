package com.devspace.myapplication.componentes

import android.text.TextUtils
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.textview.MaterialTextView

@Composable
fun ERHtmlText(
    text: String,
    maxLine:Int? = null,
) {

    val spannedText = HtmlCompat.fromHtml(text, 0)

    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

    AndroidView(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        factory = {
            MaterialTextView(it).apply {
                setTextColor(textColor)
                maxLine?.let {
                    maxLines = it
                    this.ellipsize = TextUtils.TruncateAt.END
                }
            }
        },
        update = { it.text = spannedText }
    )

}