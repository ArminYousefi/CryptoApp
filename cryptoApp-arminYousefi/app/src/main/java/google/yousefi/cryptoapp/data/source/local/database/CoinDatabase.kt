package google.yousefi.cryptoapp.data.source.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import google.yousefi.cryptoapp.data.source.local.database.converters.ImmutableListTypeConverter
import google.yousefi.cryptoapp.data.source.local.database.converters.PercentageTypeConverter
import google.yousefi.cryptoapp.data.source.local.database.converters.PriceTypeConverter
import google.yousefi.cryptoapp.data.source.local.database.dao.CoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinDao
import google.yousefi.cryptoapp.data.source.local.database.dao.FavouriteCoinIdDao
import google.yousefi.cryptoapp.data.source.local.database.model.Coin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoin
import google.yousefi.cryptoapp.data.source.local.database.model.FavouriteCoinId

/**
 * این کلاس CoinDatabase یک پایگاه داده Room برای ذخیره اطلاعات مربوط به ارزهای دیجیتال و موارد مرتبط با آن‌ها استفاده می‌شود.
 *
 * @Database برچسبی است که برای نشان دادن این که این کلاس یک پایگاه داده Room استفاده می‌شود، استفاده می‌شود.
 * @param version نسخه فعلی پایگاه داده Room.
 * @param entities لیستی از Entity‌هایی که در این پایگاه داده ذخیره می‌شوند، اعم از Coin، FavouriteCoin و FavouriteCoinId.
 * @param autoMigrations تعاریفی برای خودکارسازی مهاجرت‌های خودکار از یک نسخه به نسخه دیگر، که شامل مهاجرت‌هایی از نسخه 1 به نسخه 2، 2 به 3 و غیره می‌شود.
 * @param typeConverters تعاریف تبدیل‌های نوع‌های سفارشی برای ذخیره سازی اطلاعاتی مانند قیمت (Price)، درصد (Percentage) و لیست غیرقابل تغییر (ImmutableList).
 */
@Database(
    version = 6,
    entities = [Coin::class, FavouriteCoin::class, FavouriteCoinId::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = CoinDatabase.Migration2to3::class),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5, spec = CoinDatabase.Migration4to5::class),
        AutoMigration(from = 5, to = 6, spec = CoinDatabase.Migration5to6::class)
    ]
)
@TypeConverters(
    PriceTypeConverter::class,
    PercentageTypeConverter::class,
    ImmutableListTypeConverter::class
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun favouriteCoinDao(): FavouriteCoinDao
    abstract fun favouriteCoinIdDao(): FavouriteCoinIdDao

    // تعریف مهاجرت گذرا برای تغییر نام جدول FavouriteCoin به FavouriteCoinId
    @RenameTable(fromTableName = "FavouriteCoin", toTableName = "FavouriteCoinId")
    internal class Migration2to3 : AutoMigrationSpec

    // تعریف مهاجرت گذرا برای تغییر نام جدول CachedCoin به Coin
    @RenameTable(fromTableName = "CachedCoin", toTableName = "Coin")
    internal class Migration4to5 : AutoMigrationSpec

    // تعریف مهاجرت گذرا برای حذف ستون prices24h از جدول Coin
    @DeleteColumn(tableName = "Coin", columnName = "prices24h")
    internal class Migration5to6 : AutoMigrationSpec
}
