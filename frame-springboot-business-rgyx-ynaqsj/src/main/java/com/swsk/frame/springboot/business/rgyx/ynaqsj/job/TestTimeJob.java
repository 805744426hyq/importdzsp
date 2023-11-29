package com.swsk.frame.springboot.business.rgyx.ynaqsj.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 定时任务步骤
 * 1.在方法底下实现业务逻辑
 * 2.在页面配置 com.swsk.frame.springboot.business.rgyx.ynaqsj.job.TimeJob
 *
 * @author huangyongqi
 * @date 2023/8/11
 */
@DisallowConcurrentExecution
public class TestTimeJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        AutoSaveService autoSaveService = (AutoSaveService) SpringContextUtil.getBean("autoSaveService");
//
//        MResult result = autoSaveService.workAutoSave(null);
        System.out.println(123456);
    }
}
