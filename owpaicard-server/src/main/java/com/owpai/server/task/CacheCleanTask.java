package com.owpai.server.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 缓存清理定时任务
 */
@Component
@Slf4j
public class CacheCleanTask {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每天凌晨2点执行缓存清理
     * cron表达式：秒 分 时 日 月 周
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanCache() {
        log.info("开始执行缓存清理任务...");
        try {
            // 获取所有缓存key
            redisTemplate.keys("*").forEach(key -> {
                // 这里可以根据具体业务需求设置清理策略
                // 例如：根据key的前缀、过期时间等条件进行清理
                redisTemplate.delete(key);
                log.info("清理缓存key: {}", key);
            });
            log.info("缓存清理任务执行完成");
        } catch (Exception e) {
            log.error("缓存清理任务执行失败: {}", e.getMessage());
        }
    }
}