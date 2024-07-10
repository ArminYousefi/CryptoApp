package google.yousefi.cryptoapp.ui.previewdata

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import google.yousefi.cryptoapp.model.Percentage

/**
 * ارائه‌دهنده‌ی پیش‌نمایش برای مقادیر درصدی Percentage.
 */
class PercentagePreviewProvider : PreviewParameterProvider<Percentage> {

    /**
     * توالی مقادیر مختلف برای نمایش درصدها.
     */
    override val values = sequenceOf(
        Percentage("0.42"),    // درصد مثبت
        Percentage("-0.57"),   // درصد منفی
        Percentage("0.00")     // صفر درصد
    )
}
