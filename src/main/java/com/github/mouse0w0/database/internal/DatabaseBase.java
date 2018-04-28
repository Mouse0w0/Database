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
	private final int maxConnectionPoolSize;

	private ExecutorService threadPool;
	private volatile boolean disconnected = false;

	public DatabaseBase() {
		this(-1);
	}

	public DatabaseBase(int maxConnectionPoolSize) {
		this.maxConnectionPoolSize = maxConnectionPoolSize;
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
	public Connection getConnection() throws SQLException {
		checkDisconnected();
		Object lock = getLockObject();
		synchronized (lock) {
			while (true) {
				for (Entry<Connection, Boolean> entry : connections.entrySet()) {
					if (entry.getValue()) {
						Connection connection = entry.getKey();
						connections.put(connection, Boolean.FALSE);
						return connection;
					}
				}

				if (maxConnectionPoolSize < 0 || connections.size() < maxConnectionPoolSize) {
					Connection connection = createConnection();
					connections.put(connection, Boolean.FALSE);
					return connection;
				}
				
				try {
					lock.wait();
				} catch (InterruptedException ignored) {
				}
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
	public void sync(Consumer<Connection> consumer) throws SQLException {
		checkDisconnected();
		try (Connection connection = getConnection()) {
			consumer.accept(connection);
		}
	}

	@Override
	public void async(Consumer<Connection> consumer) throws SQLException {
		checkDisconnected();
		asyncTask(() -> {
			try (Connection connection = getConnection()) {
				consumer.accept(connection);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}

	protected abstract Connection createConnection() throws SQLException;

	protected Object getLockObject() {
		return connections;
	}

	protected void asyncTask(Runnable runnable) {
		if (threadPool == null)
			threadPool = maxConnectionPoolSize < 0 ? Executors.newCachedThreadPool()
					: Executors.newFixedThreadPool(maxConnectionPoolSize);
		threadPool.execute(runnable);
	}

	protected void checkDisconnected() {
		if (!isConnected())
			throw new IllegalStateException("Database has been disconnected.");
	}
}
