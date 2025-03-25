package com.acme.data.fault.tolerance.mybatis;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * Executor静态拦截实现 （包装器）
 * @see Executor
 *
 * @author: wuhao
 * @since 1.0.0
 */
public class ExecutorDecorators implements Executor {

//    private  final Executor delegate;

    private  final List<ExecutorDecorator> decorators;



    private  final int lastIndex;

    public ExecutorDecorators( Executor delegate, List<ExecutorDecorator> decorators) {
        this.decorators = decorators;
        decorators.forEach(decorator->decorator.setDelegate(delegate));
        this.lastIndex = decorators.size() - 1;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        for (int i = 0; i < lastIndex; i++) {
            ExecutorDecorator decorator = decorators.get(i);
            return decorator.update(ms,parameter);
        }
        ExecutorDecorator lastDecorator = decorators.get(lastIndex+1);
        return lastDecorator.update(ms,parameter);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        return null;
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException {
        return null;
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return null;
    }

    @Override
    public void commit(boolean required) throws SQLException {

    }

    @Override
    public void rollback(boolean required) throws SQLException {

    }

    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return null;
    }

    @Override
    public boolean isCached(MappedStatement ms, CacheKey key) {
        return false;
    }

    @Override
    public void clearLocalCache() {

    }

    @Override
    public void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType) {

    }

    @Override
    public Transaction getTransaction() {
        return null;
    }

    @Override
    public void close(boolean forceRollback) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void setExecutorWrapper(Executor executor) {

    }
}
