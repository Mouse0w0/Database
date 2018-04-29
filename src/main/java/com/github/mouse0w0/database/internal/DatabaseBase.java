package com.github.mouse0w0.database.internal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.github.mouse0w0.database.Database;

public abstract class DatabaseBase implements Database {

	private final Map<Connection, Boolean> connections = new HashMap<>();;
	private final int maxPoolSize;

	private ExecutorService threadPool;
	private volatile boolean disconnected = false;

	public DatabaseBase() {
		this(-1);
	}

	public DatabaseBase(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	@Override
	public boolean isConnected() {
		return !disconnected;
	}

	@Override
	public void disconnect() throws SQLException {
		disconnected = true;
		threadPool.shutdownNow();
		closeConnections();
	}

	@Override
	public void disconnectOnComplete() {
		disconnected = true;
		new Thread(() -> {
			try {
				threadPool.shutdown();
				while (!threadPool.isTerminated())
					threadPool.awaitTermination(1L, TimeUnit.SECONDS);
				closeConnections();
			} catch (InterruptedException ignored) {
			}
		}).start();
	}

	private void closeConnections() {
		for (Connection connection : connections.keySet())
			try {
				connection.close();
			} catch (SQLException ignored) {
			}
	}

	@Override
	public Connection getConnection() throws SQLException, InterruptedException {
		checkDisconnected();
		Object lock = getLockObject();
		synchronized (lock) {
			while (true) {
				for (Entry<Connection, Boolean> entry : connections.entrySet()) {
					if (entry.getValue()) {
						Connection connection = entry.getKey();
						if(connection.isClosed()) {
							connection = createConnection();
						}
						connections.put(connection, Boolean.FALSE);
						return connection;
					}
				}

				if (maxPoolSize < 0 || connections.size() < maxPoolSize) {
					Connection connection = createConnection();
					connections.put(connection, Boolean.FALSE);
					return connection;
				}
				
				lock.wait();
			}
		}
	}

	@Override
	public void freeConnection(Connection connection) {
		Object lock = getLockObject();
		synchronized (lock) {
			connections.put(connection, Boolean.TRUE);
			lock.notify();
		}
	}

	@Override
	public void sync(Consumer<Connection> consumer) throws SQLException, InterruptedException {
		checkDisconnected();
		Connection connection = getConnection();
		consumer.accept(connection);
		freeConnection(connection);
	}

	@Override
	public void async(Consumer<Connection> consumer) throws SQLException {
		checkDisconnected();
		asyncTask(() -> {
			try {
				Connection connection = getConnection();
				consumer.accept(connection);
				freeConnection(connection);
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	protected abstract Connection createConnection() throws SQLException;

	protected Object getLockObject() {
		return connections;
	}

	protected void asyncTask(Runnable runnable) {
		if (threadPool == null)
			threadPool = maxPoolSize < 0 ? Executors.newCachedThreadPool()
					: Executors.newFixedThreadPool(maxPoolSize);
		threadPool.execute(runnable);
	}

	protected void checkDisconnected() {
		if (!isConnected())
			throw new IllegalStateException("Database has been disconnected.");
	}
}
