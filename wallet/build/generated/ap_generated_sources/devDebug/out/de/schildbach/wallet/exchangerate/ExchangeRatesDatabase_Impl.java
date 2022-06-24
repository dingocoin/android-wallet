package de.schildbach.wallet.exchangerate;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExchangeRatesDatabase_Impl extends ExchangeRatesDatabase {
  private volatile ExchangeRateDao _exchangeRateDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `exchange_rates` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `source` TEXT NOT NULL, `currency_code` TEXT NOT NULL, `rate_timestamp` INTEGER NOT NULL, `rate_coin` INTEGER NOT NULL, `rate_fiat` INTEGER NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_exchange_rates_source_currency_code` ON `exchange_rates` (`source`, `currency_code`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c046f1bd5ec96c14c0f639a2dbe274a5')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `exchange_rates`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsExchangeRates = new HashMap<String, TableInfo.Column>(6);
        _columnsExchangeRates.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExchangeRates.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExchangeRates.put("currency_code", new TableInfo.Column("currency_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExchangeRates.put("rate_timestamp", new TableInfo.Column("rate_timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExchangeRates.put("rate_coin", new TableInfo.Column("rate_coin", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExchangeRates.put("rate_fiat", new TableInfo.Column("rate_fiat", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExchangeRates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExchangeRates = new HashSet<TableInfo.Index>(1);
        _indicesExchangeRates.add(new TableInfo.Index("index_exchange_rates_source_currency_code", true, Arrays.asList("source","currency_code")));
        final TableInfo _infoExchangeRates = new TableInfo("exchange_rates", _columnsExchangeRates, _foreignKeysExchangeRates, _indicesExchangeRates);
        final TableInfo _existingExchangeRates = TableInfo.read(_db, "exchange_rates");
        if (! _infoExchangeRates.equals(_existingExchangeRates)) {
          return new RoomOpenHelper.ValidationResult(false, "exchange_rates(de.schildbach.wallet.exchangerate.ExchangeRateEntry).\n"
                  + " Expected:\n" + _infoExchangeRates + "\n"
                  + " Found:\n" + _existingExchangeRates);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c046f1bd5ec96c14c0f639a2dbe274a5", "4a65360b88905a5b4bc541d987d426b7");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "exchange_rates");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `exchange_rates`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ExchangeRateDao exchangeRateDao() {
    if (_exchangeRateDao != null) {
      return _exchangeRateDao;
    } else {
      synchronized(this) {
        if(_exchangeRateDao == null) {
          _exchangeRateDao = new ExchangeRateDao_Impl(this);
        }
        return _exchangeRateDao;
      }
    }
  }
}
