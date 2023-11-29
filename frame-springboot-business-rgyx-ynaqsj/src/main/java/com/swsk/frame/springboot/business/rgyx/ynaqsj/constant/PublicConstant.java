package com.swsk.frame.springboot.business.rgyx.ynaqsj.constant;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * 常量保存
 *
 * @author huangyongqi
 * @date 2023/7/12
 */

public class PublicConstant {

    /**
     * 云南海拔范围
     * <p>
     * 最高点海拔6740米，位于滇藏交界处德钦县境内怒山山脉的梅里雪山主峰卡瓦格博峰；
     * 最低点海拔76.4米，位于河口县境内南溪河与红河交汇的中越界河处
     * <p>
     * 在海拔 0 m的理论最大射程数据参见
     * 理论最大射程数据参见 表 B.1B.1 。
     * 对于海拔大于等于500 m的作业点，理论最大射程应乘以相应的最大射程增量系数，增量系数参见表B.2。
     */
    public static final Double PEAK = 6740d;
    public static final Double NADIR = 76.4d;

    /**
     * 高炮类型分为37mm和57mm
     * 火箭类型 rocket
     */
    public static final String ANTI_37_MM = "37mm";
    public static final String ANTI_57_MM = "57mm";
    public static final String ROCKET = "rocket";

    public static final String NORTH = "N";
    public static final String EAST = "E";

    public static final String DEGREE = "°";


    /**
     * 云南的省级区域码
     */
    public static final String YUNNAN_CODE = "530000";
    public static final String YUNNAN = "云南省";
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "3dspace.123";


    /**
     * 0-5的字符串
     */
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";

    /**
     * 用户角色 制图员1 审核员2 管理员3
     */

    public static final String OPERATOR = ONE;
    public static final String EXAMINER = TWO;
    public static final String ADMIN = THREE;

    /**
     * 射界图复核状态 未复核0 已复核1
     */
    public static final String NOT_RECHECK = ZERO;

    public static final String HAS_RECHECKS = ONE;
    /**
     * 安全射界-标注点
     */
    public static final List<String> LABEL_TYPES = ListUtil.of("1km等间距点", "45度整数倍点", "射击仰角标注");
    public static final List<String> LABEL_FLAGS = ListUtil.of(ZERO, ONE);

}
