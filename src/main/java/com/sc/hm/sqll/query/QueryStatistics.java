package com.sc.hm.sqll.query;

public class QueryStatistics {
	
	private long executionTime = 0L;
	private long fetchTime = 0L;
	private long renderTime = 0L;

	public QueryStatistics() {}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public long getFetchTime() {
		return fetchTime;
	}

	public void setFetchTime(long fetchTime) {
		this.fetchTime = fetchTime;
	}

	public long getRenderTime() {
		return renderTime;
	}

	public void setRenderTime(long renderTime) {
		this.renderTime = renderTime;
	}

}
