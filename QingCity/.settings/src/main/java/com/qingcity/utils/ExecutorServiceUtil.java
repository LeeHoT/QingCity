package com.qingcity.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingcity.data.manager.PlayerManager;
import com.qingcity.task.CheckChannelStatusTask;

/**
 * 全局定时任务工具类 使用ExecutorService而不使用是因为Timer 无法捕获异常，
 * 在Timer抛出异常时，会导致Timer上发生不可预料的错误，未执行的任务无法继续执行。 同时还可以避免因为任务上一任务时间的超时导致下一任务的延迟执行
 *
 * @author leehotin
 */
public class ExecutorServiceUtil {

	private static Logger logger = LoggerFactory.getLogger(ExecutorServiceUtil.class);

	private static ExecutorServiceUtil instance = new ExecutorServiceUtil();

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static ExecutorServiceUtil getInstance() {
		return instance;
	}

	private static final int THREAD_POOL_SIZE = 2; // 计时器线程池大小

	/**
	 * 定时器设置
	 */
	private final static ScheduledExecutorService timer = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);

	/**
	 * 运行定时器
	 * 
	 * @param task
	 *            需要执行的额任务对象
	 * @param delay
	 *            任务延迟时间
	 * @param period
	 *            两次任务之间执行的时间
	 * @param unit
	 *            时间单位 MILLISECONDS或者SECONDS
	 */

	public static void run(final Runnable task, long delay, long period, TimeUnit unit) {
		try {
			timer.scheduleAtFixedRate(task, delay, period, unit);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 关闭定时器
	 */
	public static void stop() {
		timer.shutdown();
	}

	
}