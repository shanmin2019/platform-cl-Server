package com.lemon.service;

import com.lemon.common.ReportVO;
import com.lemon.pojo.TestReport;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
public interface TestReportService extends IService<TestReport> {

	List<TestReport> runTest(Integer projectId, Integer suiteId);

	/**
	 * 获取单个测试案例最新结果详情
	 * @param caseId
	 * @return
	 */
	TestReport findCaseRunResult(Integer caseId);

	public ReportVO getReport(Integer suiteId);


}
