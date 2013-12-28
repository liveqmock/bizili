package com.vteba.cache.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.KeyProvider;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.buffer.BufferAllocator;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.ReconnectRequest;
import net.rubyeye.xmemcached.networking.Connector;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.Protocol;

/**
 * MemcachedClient委托代理实例
 * @author yinlei
 * date 2013-4-4 下午4:24:14
 */
@SuppressWarnings("deprecation")
public class MemcacheClientDelegate {
	private MemcachedClient memcachedClient;

	public MemcacheClientDelegate(MemcachedClient memcachedClient) {
		super();
		this.memcachedClient = memcachedClient;
	}

	public boolean add(String arg0, int arg1, Object arg2, long arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.add(arg0, arg1, arg2, arg3);
	}

	public boolean add(String arg0, int arg1, Object arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.add(arg0, arg1, arg2);
	}

	public <T> boolean add(String arg0, int arg1, T arg2, Transcoder<T> arg3,
			long arg4) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.add(arg0, arg1, arg2, arg3, arg4);
	}

	public <T> boolean add(String arg0, int arg1, T arg2, Transcoder<T> arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.add(arg0, arg1, arg2, arg3);
	}

	public void addServer(InetSocketAddress arg0, int arg1) throws IOException {
		memcachedClient.addServer(arg0, arg1);
	}

	public void addServer(InetSocketAddress arg0) throws IOException {
		memcachedClient.addServer(arg0);
	}

	public void addServer(String arg0, int arg1, int arg2) throws IOException {
		memcachedClient.addServer(arg0, arg1, arg2);
	}

	public void addServer(String arg0, int arg1) throws IOException {
		memcachedClient.addServer(arg0, arg1);
	}

	public void addServer(String arg0) throws IOException {
		memcachedClient.addServer(arg0);
	}

	public void addStateListener(MemcachedClientStateListener arg0) {
		memcachedClient.addStateListener(arg0);
	}

	public void addWithNoReply(String arg0, int arg1, Object arg2)
			throws InterruptedException, MemcachedException {
		memcachedClient.addWithNoReply(arg0, arg1, arg2);
	}

	public <T> void addWithNoReply(String arg0, int arg1, T arg2,
			Transcoder<T> arg3) throws InterruptedException, MemcachedException {
		memcachedClient.addWithNoReply(arg0, arg1, arg2, arg3);
	}

	public boolean append(String arg0, Object arg1, long arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.append(arg0, arg1, arg2);
	}

	public boolean append(String arg0, Object arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.append(arg0, arg1);
	}

	public void appendWithNoReply(String arg0, Object arg1)
			throws InterruptedException, MemcachedException {
		memcachedClient.appendWithNoReply(arg0, arg1);
	}

	public <T> boolean cas(String arg0, CASOperation<T> arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1);
	}

	public <T> boolean cas(String arg0, GetsResponse<T> arg1,
			CASOperation<T> arg2) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2);
	}

	public <T> boolean cas(String arg0, int arg1, CASOperation<T> arg2,
			Transcoder<T> arg3) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3);
	}

	public <T> boolean cas(String arg0, int arg1, CASOperation<T> arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2);
	}

	public <T> boolean cas(String arg0, int arg1, GetsResponse<T> arg2,
			CASOperation<T> arg3, Transcoder<T> arg4) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3, arg4);
	}

	public <T> boolean cas(String arg0, int arg1, GetsResponse<T> arg2,
			CASOperation<T> arg3) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3);
	}

	public boolean cas(String arg0, int arg1, Object arg2, long arg3, long arg4)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3, arg4);
	}

	public boolean cas(String arg0, int arg1, Object arg2, long arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3);
	}

	public <T> boolean cas(String arg0, int arg1, T arg2, Transcoder<T> arg3,
			long arg4, long arg5) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public <T> boolean cas(String arg0, int arg1, T arg2, Transcoder<T> arg3,
			long arg4) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.cas(arg0, arg1, arg2, arg3, arg4);
	}

	public <T> void casWithNoReply(String arg0, CASOperation<T> arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		memcachedClient.casWithNoReply(arg0, arg1);
	}

	public <T> void casWithNoReply(String arg0, GetsResponse<T> arg1,
			CASOperation<T> arg2) throws TimeoutException,
			InterruptedException, MemcachedException {
		memcachedClient.casWithNoReply(arg0, arg1, arg2);
	}

	public <T> void casWithNoReply(String arg0, int arg1, CASOperation<T> arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		memcachedClient.casWithNoReply(arg0, arg1, arg2);
	}

	public <T> void casWithNoReply(String arg0, int arg1, GetsResponse<T> arg2,
			CASOperation<T> arg3) throws TimeoutException,
			InterruptedException, MemcachedException {
		memcachedClient.casWithNoReply(arg0, arg1, arg2, arg3);
	}

	public long decr(String arg0, long arg1, long arg2, long arg3, int arg4)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.decr(arg0, arg1, arg2, arg3, arg4);
	}

	public long decr(String arg0, long arg1, long arg2, long arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.decr(arg0, arg1, arg2, arg3);
	}

	public long decr(String arg0, long arg1, long arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.decr(arg0, arg1, arg2);
	}

	public long decr(String arg0, long arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.decr(arg0, arg1);
	}

	public void decrWithNoReply(String arg0, long arg1)
			throws InterruptedException, MemcachedException {
		memcachedClient.decrWithNoReply(arg0, arg1);
	}

	public boolean delete(String arg0, int arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.delete(arg0, arg1);
	}

	public boolean delete(String arg0, long arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.delete(arg0, arg1);
	}

	public boolean delete(String arg0) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.delete(arg0);
	}

	public void deleteWithNoReply(String arg0, int arg1)
			throws InterruptedException, MemcachedException {
		memcachedClient.deleteWithNoReply(arg0, arg1);
	}

	public void deleteWithNoReply(String arg0) throws InterruptedException,
			MemcachedException {
		memcachedClient.deleteWithNoReply(arg0);
	}

	public void flushAll() throws TimeoutException, InterruptedException,
			MemcachedException {
		memcachedClient.flushAll();
	}

	public void flushAll(InetSocketAddress arg0, long arg1, int arg2)
			throws MemcachedException, InterruptedException, TimeoutException {
		memcachedClient.flushAll(arg0, arg1, arg2);
	}

	public void flushAll(InetSocketAddress arg0, long arg1)
			throws MemcachedException, InterruptedException, TimeoutException {
		memcachedClient.flushAll(arg0, arg1);
	}

	public void flushAll(InetSocketAddress arg0) throws MemcachedException,
			InterruptedException, TimeoutException {
		memcachedClient.flushAll(arg0);
	}

	public void flushAll(int arg0, long arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		memcachedClient.flushAll(arg0, arg1);
	}

	public void flushAll(long arg0) throws TimeoutException,
			InterruptedException, MemcachedException {
		memcachedClient.flushAll(arg0);
	}

	public void flushAll(String arg0) throws TimeoutException,
			InterruptedException, MemcachedException {
		memcachedClient.flushAll(arg0);
	}

	public void flushAllWithNoReply() throws InterruptedException,
			MemcachedException {
		memcachedClient.flushAllWithNoReply();
	}

	public void flushAllWithNoReply(InetSocketAddress arg0, int arg1)
			throws MemcachedException, InterruptedException {
		memcachedClient.flushAllWithNoReply(arg0, arg1);
	}

	public void flushAllWithNoReply(InetSocketAddress arg0)
			throws MemcachedException, InterruptedException {
		memcachedClient.flushAllWithNoReply(arg0);
	}

	public void flushAllWithNoReply(int arg0) throws InterruptedException,
			MemcachedException {
		memcachedClient.flushAllWithNoReply(arg0);
	}

	public <T> Map<String, T> get(Collection<String> arg0, long arg1,
			Transcoder<T> arg2) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.get(arg0, arg1, arg2);
	}

	public <T> Map<String, T> get(Collection<String> arg0, long arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.get(arg0, arg1);
	}

	public <T> Map<String, T> get(Collection<String> arg0, Transcoder<T> arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.get(arg0, arg1);
	}

	public <T> Map<String, T> get(Collection<String> arg0)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.get(arg0);
	}

	public <T> T get(String arg0, long arg1, Transcoder<T> arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.get(arg0, arg1, arg2);
	}

	public <T> T get(String arg0, long arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.get(arg0, arg1);
	}

	public <T> T get(String arg0, Transcoder<T> arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.get(arg0, arg1);
	}

	public <T> T get(String arg0) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.get(arg0);
	}

	public <T> T getAndTouch(String arg0, int arg1, long arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.getAndTouch(arg0, arg1, arg2);
	}

	public <T> T getAndTouch(String arg0, int arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.getAndTouch(arg0, arg1);
	}

	public Map<InetSocketAddress, AuthInfo> getAuthInfoMap() {
		return memcachedClient.getAuthInfoMap();
	}

	public Collection<InetSocketAddress> getAvailableServers() {
		return memcachedClient.getAvailableServers();
	}

	public Collection<InetSocketAddress> getAvaliableServers() {
		return memcachedClient.getAvaliableServers();
	}

	public long getConnectTimeout() {
		return memcachedClient.getConnectTimeout();
	}

	public Connector getConnector() {
		return memcachedClient.getConnector();
	}

	public Counter getCounter(String arg0, long arg1) {
		return memcachedClient.getCounter(arg0, arg1);
	}

	public Counter getCounter(String arg0) {
		return memcachedClient.getCounter(arg0);
	}

	public long getHealSessionInterval() {
		return memcachedClient.getHealSessionInterval();
	}

	public KeyIterator getKeyIterator(InetSocketAddress arg0)
			throws MemcachedException, InterruptedException, TimeoutException {
		return memcachedClient.getKeyIterator(arg0);
	}

	public String getName() {
		return memcachedClient.getName();
	}

	public long getOpTimeout() {
		return memcachedClient.getOpTimeout();
	}

	public Protocol getProtocol() {
		return memcachedClient.getProtocol();
	}

	public Queue<ReconnectRequest> getReconnectRequestQueue() {
		return memcachedClient.getReconnectRequestQueue();
	}

	public List<String> getServersDescription() {
		return memcachedClient.getServersDescription();
	}

	public Collection<MemcachedClientStateListener> getStateListeners() {
		return memcachedClient.getStateListeners();
	}

	public Map<InetSocketAddress, Map<String, String>> getStats()
			throws MemcachedException, InterruptedException, TimeoutException {
		return memcachedClient.getStats();
	}

	public Map<InetSocketAddress, Map<String, String>> getStats(long arg0)
			throws MemcachedException, InterruptedException, TimeoutException {
		return memcachedClient.getStats(arg0);
	}

	public Map<InetSocketAddress, Map<String, String>> getStatsByItem(
			String arg0, long arg1) throws MemcachedException,
			InterruptedException, TimeoutException {
		return memcachedClient.getStatsByItem(arg0, arg1);
	}

	public Map<InetSocketAddress, Map<String, String>> getStatsByItem(
			String arg0) throws MemcachedException, InterruptedException,
			TimeoutException {
		return memcachedClient.getStatsByItem(arg0);
	}

	public int getTimeoutExceptionThreshold() {
		return memcachedClient.getTimeoutExceptionThreshold();
	}

	@SuppressWarnings("rawtypes")
	public Transcoder getTranscoder() {
		return memcachedClient.getTranscoder();
	}

	public Map<InetSocketAddress, String> getVersions()
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.getVersions();
	}

	public Map<InetSocketAddress, String> getVersions(long arg0)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.getVersions(arg0);
	}

	public <T> Map<String, GetsResponse<T>> gets(Collection<String> arg0,
			long arg1, Transcoder<T> arg2) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.gets(arg0, arg1, arg2);
	}

	public <T> Map<String, GetsResponse<T>> gets(Collection<String> arg0,
			long arg1) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.gets(arg0, arg1);
	}

	public <T> Map<String, GetsResponse<T>> gets(Collection<String> arg0,
			Transcoder<T> arg1) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.gets(arg0, arg1);
	}

	public <T> Map<String, GetsResponse<T>> gets(Collection<String> arg0)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.gets(arg0);
	}

	public <T> GetsResponse<T> gets(String arg0, long arg1, Transcoder<T> arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.gets(arg0, arg1, arg2);
	}

	public <T> GetsResponse<T> gets(String arg0, long arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.gets(arg0, arg1);
	}

	public <T> GetsResponse<T> gets(String arg0, @SuppressWarnings("rawtypes") Transcoder arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.gets(arg0, arg1);
	}

	public <T> GetsResponse<T> gets(String arg0) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.gets(arg0);
	}

	public long incr(String arg0, long arg1, long arg2, long arg3, int arg4)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.incr(arg0, arg1, arg2, arg3, arg4);
	}

	public long incr(String arg0, long arg1, long arg2, long arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.incr(arg0, arg1, arg2, arg3);
	}

	public long incr(String arg0, long arg1, long arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.incr(arg0, arg1, arg2);
	}

	public long incr(String arg0, long arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.incr(arg0, arg1);
	}

	public void incrWithNoReply(String arg0, long arg1)
			throws InterruptedException, MemcachedException {
		memcachedClient.incrWithNoReply(arg0, arg1);
	}

	public boolean isFailureMode() {
		return memcachedClient.isFailureMode();
	}

	public boolean isSanitizeKeys() {
		return memcachedClient.isSanitizeKeys();
	}

	public boolean isShutdown() {
		return memcachedClient.isShutdown();
	}

	public boolean prepend(String arg0, Object arg1, long arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.prepend(arg0, arg1, arg2);
	}

	public boolean prepend(String arg0, Object arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.prepend(arg0, arg1);
	}

	public void prependWithNoReply(String arg0, Object arg1)
			throws InterruptedException, MemcachedException {
		memcachedClient.prependWithNoReply(arg0, arg1);
	}

	public void removeServer(String arg0) {
		memcachedClient.removeServer(arg0);
	}

	public void removeStateListener(MemcachedClientStateListener arg0) {
		memcachedClient.removeStateListener(arg0);
	}

	public boolean replace(String arg0, int arg1, Object arg2, long arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.replace(arg0, arg1, arg2, arg3);
	}

	public boolean replace(String arg0, int arg1, Object arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.replace(arg0, arg1, arg2);
	}

	public <T> boolean replace(String arg0, int arg1, T arg2,
			Transcoder<T> arg3, long arg4) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.replace(arg0, arg1, arg2, arg3, arg4);
	}

	public <T> boolean replace(String arg0, int arg1, T arg2, Transcoder<T> arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.replace(arg0, arg1, arg2, arg3);
	}

	public void replaceWithNoReply(String arg0, int arg1, Object arg2)
			throws InterruptedException, MemcachedException {
		memcachedClient.replaceWithNoReply(arg0, arg1, arg2);
	}

	public <T> void replaceWithNoReply(String arg0, int arg1, T arg2,
			Transcoder<T> arg3) throws InterruptedException, MemcachedException {
		memcachedClient.replaceWithNoReply(arg0, arg1, arg2, arg3);
	}

	public boolean set(String arg0, int arg1, Object arg2, long arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.set(arg0, arg1, arg2, arg3);
	}

	public boolean set(String arg0, int arg1, Object arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.set(arg0, arg1, arg2);
	}

	public <T> boolean set(String arg0, int arg1, T arg2, Transcoder<T> arg3,
			long arg4) throws TimeoutException, InterruptedException,
			MemcachedException {
		return memcachedClient.set(arg0, arg1, arg2, arg3, arg4);
	}

	public <T> boolean set(String arg0, int arg1, T arg2, Transcoder<T> arg3)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.set(arg0, arg1, arg2, arg3);
	}

	public void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> arg0) {
		memcachedClient.setAuthInfoMap(arg0);
	}

	public void setBufferAllocator(BufferAllocator arg0) {
		memcachedClient.setBufferAllocator(arg0);
	}

	public void setConnectTimeout(long arg0) {
		memcachedClient.setConnectTimeout(arg0);
	}

	public void setConnectionPoolSize(int arg0) {
		memcachedClient.setConnectionPoolSize(arg0);
	}

	public void setEnableHealSession(boolean arg0) {
		memcachedClient.setEnableHealSession(arg0);
	}

	public void setEnableHeartBeat(boolean arg0) {
		memcachedClient.setEnableHeartBeat(arg0);
	}

	public void setFailureMode(boolean arg0) {
		memcachedClient.setFailureMode(arg0);
	}

	public void setHealSessionInterval(long arg0) {
		memcachedClient.setHealSessionInterval(arg0);
	}

	public void setKeyProvider(KeyProvider arg0) {
		memcachedClient.setKeyProvider(arg0);
	}

	public void setLoggingLevelVerbosity(InetSocketAddress arg0, int arg1)
			throws TimeoutException, InterruptedException, MemcachedException {
		memcachedClient.setLoggingLevelVerbosity(arg0, arg1);
	}

	public void setLoggingLevelVerbosityWithNoReply(InetSocketAddress arg0,
			int arg1) throws InterruptedException, MemcachedException {
		memcachedClient.setLoggingLevelVerbosityWithNoReply(arg0, arg1);
	}

	public void setMergeFactor(int arg0) {
		memcachedClient.setMergeFactor(arg0);
	}

	public void setName(String arg0) {
		memcachedClient.setName(arg0);
	}

	public void setOpTimeout(long arg0) {
		memcachedClient.setOpTimeout(arg0);
	}

	public void setOptimizeGet(boolean arg0) {
		memcachedClient.setOptimizeGet(arg0);
	}

	public void setOptimizeMergeBuffer(boolean arg0) {
		memcachedClient.setOptimizeMergeBuffer(arg0);
	}

	public void setPrimitiveAsString(boolean arg0) {
		memcachedClient.setPrimitiveAsString(arg0);
	}

	public void setSanitizeKeys(boolean arg0) {
		memcachedClient.setSanitizeKeys(arg0);
	}

	public void setTimeoutExceptionThreshold(int arg0) {
		memcachedClient.setTimeoutExceptionThreshold(arg0);
	}

	public void setTranscoder(@SuppressWarnings("rawtypes") Transcoder arg0) {
		memcachedClient.setTranscoder(arg0);
	}

	public void setWithNoReply(String arg0, int arg1, Object arg2)
			throws InterruptedException, MemcachedException {
		memcachedClient.setWithNoReply(arg0, arg1, arg2);
	}

	public <T> void setWithNoReply(String arg0, int arg1, T arg2,
			Transcoder<T> arg3) throws InterruptedException, MemcachedException {
		memcachedClient.setWithNoReply(arg0, arg1, arg2, arg3);
	}

	public void shutdown() throws IOException {
		memcachedClient.shutdown();
	}

	public Map<String, String> stats(InetSocketAddress arg0, long arg1)
			throws MemcachedException, InterruptedException, TimeoutException {
		return memcachedClient.stats(arg0, arg1);
	}

	public Map<String, String> stats(InetSocketAddress arg0)
			throws MemcachedException, InterruptedException, TimeoutException {
		return memcachedClient.stats(arg0);
	}

	public boolean touch(String arg0, int arg1, long arg2)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.touch(arg0, arg1, arg2);
	}

	public boolean touch(String arg0, int arg1) throws TimeoutException,
			InterruptedException, MemcachedException {
		return memcachedClient.touch(arg0, arg1);
	}
	
	
}
