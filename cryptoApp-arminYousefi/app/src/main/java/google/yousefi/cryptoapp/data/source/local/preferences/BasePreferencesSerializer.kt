package google.yousefi.cryptoapp.data.source.local.preferences

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream

/**
 * یک کلاس انتزاعی برای سریال‌سازی و دسریال‌سازی تنظیمات پایه است که از اینترفیس Serializer ارث‌بری می‌کند.
 *
 * @param T نوع داده‌ای که باید سریال‌سازی و دسریال‌سازی شود.
 * @property defaultInstance تابعی که نمونه پیش‌فرض از نوع T را ایجاد می‌کند.
 * @property serializer سریال‌سازی که برای تبدیل T به و از String استفاده می‌شود.
 */
abstract class BasePreferencesSerializer<T>(
    private val defaultInstance: () -> T,
    private val serializer: KSerializer<T>,
) : Serializer<T> {

    /**
     * مقدار پیش‌فرض برای نوع T.
     */
    override val defaultValue: T
        get() = defaultInstance()

    /**
     * متدی که داده‌های سریال‌سازی شده را از یک ورودی InputStream خوانده و به نوع T تبدیل می‌کند.
     *
     * @param input ورودی InputStream که داده‌های سریال‌سازی شده را دریافت می‌کند.
     * @return نمونه‌ای از نوع T که از داده‌های ورودی خوانده شده ساخته شده است یا مقدار پیش‌فرض در صورت بروز خطا.
     * @throws SerializationException در صورت بروز خطا در سریال‌سازی و دسریال‌سازی، این خطا ایجاد می‌شود.
     */
    @Throws(SerializationException::class)
    override suspend fun readFrom(input: InputStream): T {
        return try {
            Json.decodeFromString(
                deserializer = serializer,
                string = input.readBytes().decodeToString()
            )
        } catch (exception: SerializationException) {
            Timber.e("Error serializing preferences with ${serializer.descriptor}", exception)
            defaultValue
        }
    }

    /**
     * متدی که یک نمونه از نوع T را به OutputStream می‌نویسد.
     *
     * @param t نمونه‌ای از نوع T که باید سریال‌سازی شود.
     * @param output خروجی OutputStream که داده‌های سریال‌سازی شده را دریافت می‌کند.
     */
    override suspend fun writeTo(t: T, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = serializer,
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}
