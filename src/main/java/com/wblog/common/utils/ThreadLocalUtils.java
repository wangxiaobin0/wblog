package com.wblog.common.utils;

import com.wblog.model.entity.SystemLogEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.UserTo;

/**
 * ThreadLocal工具类
 */
public class ThreadLocalUtils {
    private static ThreadLocal<SystemLogEntity> userLog = new ThreadLocal<>();

    private static ThreadLocal<AdminTo> adminTo = new ThreadLocal<>();

    private static ThreadLocal<UserTo> user = new ThreadLocal<>();

    /**
     * 设置操作执行结果
     *
     * @param systemLogEntity
     */
    public static void setUserLog(SystemLogEntity systemLogEntity) {
        userLog.set(systemLogEntity);
    }

    /**
     * 设置操作执行结果
     */
    public static SystemLogEntity getUserLog() {
        return userLog.get();
    }

    /**
     * 设置管理员信息
     *
     * @param admin
     */
    public static void setAdminTo(AdminTo admin) {
        adminTo.set(admin);
    }

    /**
     * 获取管理员信息
     *
     * @return
     */
    public static AdminTo getAdminTo() {
        return adminTo.get();
    }

    /**
     * 设置用户信息
     *
     * @param admin
     */
    public static void setUserTo(UserTo admin) {
        user.set(admin);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserTo getUserTo() {
        return user.get();
    }

}
