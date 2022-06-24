package de.schildbach.wallet.exchangerate;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExchangeRateDao_Impl implements ExchangeRateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ExchangeRateEntry> __insertionAdapterOfExchangeRateEntry;

  public ExchangeRateDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExchangeRateEntry = new EntityInsertionAdapter<ExchangeRateEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `exchange_rates` (`id`,`source`,`currency_code`,`rate_timestamp`,`rate_coin`,`rate_fiat`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExchangeRateEntry value) {
        stmt.bindLong(1, value.getId());
        if (value.getSource() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSource());
        }
        if (value.getCurrencyCode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCurrencyCode());
        }
        final long _tmp;
        _tmp = ExchangeRateEntry.DateConverters.dateToMillis(value.getRateTimeStamp());
        stmt.bindLong(4, _tmp);
        stmt.bindLong(5, value.getRateCoin());
        stmt.bindLong(6, value.getRateFiat());
      }
    };
  }

  @Override
  public void insertOrUpdate(final ExchangeRateEntry exchangeRateEntry) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExchangeRateEntry.insert(exchangeRateEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<ExchangeRateEntry>> findAll() {
    final String _sql = "SELECT * FROM exchange_rates ORDER BY currency_code COLLATE LOCALIZED ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"exchange_rates"}, false, new Callable<List<ExchangeRateEntry>>() {
      @Override
      public List<ExchangeRateEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfRateTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_timestamp");
          final int _cursorIndexOfRateCoin = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_coin");
          final int _cursorIndexOfRateFiat = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_fiat");
          final List<ExchangeRateEntry> _result = new ArrayList<ExchangeRateEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ExchangeRateEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final Date _tmpRateTimeStamp;
            final long _tmp;
            _tmp = _cursor.getLong(_cursorIndexOfRateTimeStamp);
            _tmpRateTimeStamp = ExchangeRateEntry.DateConverters.millisToDate(_tmp);
            final long _tmpRateCoin;
            _tmpRateCoin = _cursor.getLong(_cursorIndexOfRateCoin);
            final long _tmpRateFiat;
            _tmpRateFiat = _cursor.getLong(_cursorIndexOfRateFiat);
            _item = new ExchangeRateEntry(_tmpId,_tmpSource,_tmpCurrencyCode,_tmpRateTimeStamp,_tmpRateCoin,_tmpRateFiat);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<ExchangeRateEntry>> findByConstraint(final String constraint) {
    final String _sql = "SELECT * FROM exchange_rates WHERE currency_code LIKE '%' || ? || '%' ORDER BY currency_code COLLATE LOCALIZED ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (constraint == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, constraint);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"exchange_rates"}, false, new Callable<List<ExchangeRateEntry>>() {
      @Override
      public List<ExchangeRateEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfRateTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_timestamp");
          final int _cursorIndexOfRateCoin = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_coin");
          final int _cursorIndexOfRateFiat = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_fiat");
          final List<ExchangeRateEntry> _result = new ArrayList<ExchangeRateEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ExchangeRateEntry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final Date _tmpRateTimeStamp;
            final long _tmp;
            _tmp = _cursor.getLong(_cursorIndexOfRateTimeStamp);
            _tmpRateTimeStamp = ExchangeRateEntry.DateConverters.millisToDate(_tmp);
            final long _tmpRateCoin;
            _tmpRateCoin = _cursor.getLong(_cursorIndexOfRateCoin);
            final long _tmpRateFiat;
            _tmpRateFiat = _cursor.getLong(_cursorIndexOfRateFiat);
            _item = new ExchangeRateEntry(_tmpId,_tmpSource,_tmpCurrencyCode,_tmpRateTimeStamp,_tmpRateCoin,_tmpRateFiat);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public ExchangeRateEntry findByCurrencyCode(final String currencyCode) {
    final String _sql = "SELECT * FROM exchange_rates WHERE currency_code = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (currencyCode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, currencyCode);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
      final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
      final int _cursorIndexOfRateTimeStamp = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_timestamp");
      final int _cursorIndexOfRateCoin = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_coin");
      final int _cursorIndexOfRateFiat = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_fiat");
      final ExchangeRateEntry _result;
      if(_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpSource;
        _tmpSource = _cursor.getString(_cursorIndexOfSource);
        final String _tmpCurrencyCode;
        _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
        final Date _tmpRateTimeStamp;
        final long _tmp;
        _tmp = _cursor.getLong(_cursorIndexOfRateTimeStamp);
        _tmpRateTimeStamp = ExchangeRateEntry.DateConverters.millisToDate(_tmp);
        final long _tmpRateCoin;
        _tmpRateCoin = _cursor.getLong(_cursorIndexOfRateCoin);
        final long _tmpRateFiat;
        _tmpRateFiat = _cursor.getLong(_cursorIndexOfRateFiat);
        _result = new ExchangeRateEntry(_tmpId,_tmpSource,_tmpCurrencyCode,_tmpRateTimeStamp,_tmpRateCoin,_tmpRateFiat);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
