package com.lemon.mapper;

import com.lemon.common.CaseEditVO;
import com.lemon.common.CaseListVO;
import com.lemon.pojo.Cases;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
public interface CasesMapper extends BaseMapper<Cases> {

	/**
	 * 通过suiteId查询所有的案例
	 * @param suiteId
	 * @return
	 */
	@Select("select *  from cases where suite_id=#{suiteId}")
	List<Cases> findCases(Integer suiteId);

	/**
	 * 查询项目下所有案例/拼接接口信息
	 * @param projectId
	 * @return
	 */
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t3.id = #{projectId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="id", property="testReport",one=@One(select="com.lemon.mapper.TestReportMapper.findReportByCase"))

	})
	List<CaseListVO> showCaseUnderProject(Integer projectId);


	/**
	 * 查询suite下的案例/拼接接口信息
	 * @param suiteId
	 * @return
	 */
	@Select("SELECT DISTINCT t1.*,t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t1.suite_id=#{suiteId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="id", property="testReport",one=@One(select="com.lemon.mapper.TestReportMapper.findReportByCase"))
	})
	List<CaseListVO> showCaseUnderSuiteId(Integer suiteId);

	/**
	 * 查询指定case的信息-携带参数CaseEditVO对象
	 * @param caseId
	 * @return
	 */
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.method,t6.url FROM cases t1 JOIN case_param_value t2 ON t2.case_id = t1.id JOIN api_request_param t3 ON t2.api_request_param_id = t3.id JOIN api t6 ON t3.api_id = t6.id WHERE t1.id = #{caseId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="suite_id", property="suiteId"),
	    @Result(column="api_id", property="apiId"),
	    @Result(column="{caseId=id,apiId=api_id}", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findByCase")),
	    @Result(column="id", property="testRules",many=@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))

	})
	CaseEditVO findCaseEditVO(String caseId);

	/**
	 * 查询项目下所有测试案例--用来执行
	 * @param projectId
	 * @return
	 */
	@Select("SELECT t4.NAME projectName,t4.`host`,t2.name suiteName,t3.url,t3.method,t1.*  FROM cases t1 LEFT JOIN suite t2 ON t1.suite_id=t2.id LEFT JOIN api t3 ON t1.api_id=t3.id LEFT JOIN project t4 ON t2.project_id=t4.id WHERE t2.project_id=#{projectId} ORDER BY t1.suite_id,t1.id ASC")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="suite_id", property="suiteId"),
	    @Result(column="api_id", property="apiId"),
	    @Result(column="id", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findCaseByCaseId")),
	    @Result(column="id", property="testRules",many=@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))
	})
	List<CaseEditVO> findCaseByPorject(Integer projectId);

	/**
	 * 查询套件下所有测试案例--用来执行
	 * @param suiteId
	 * @return
	 */
	@Select("SELECT t4.NAME projectName,t4.`host`,t2.name suiteName,t3.url,t3.method,t1.* FROM cases t1 LEFT JOIN suite t2 ON t1.suite_id=t2.id LEFT JOIN api t3 ON t1.api_id=t3.id LEFT JOIN project t4 ON t2.project_id=t4.id WHERE t2.id=#{suiteId} ORDER BY t1.id ASC")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="suite_id", property="suiteId"),
	    @Result(column="api_id", property="apiId"),
	    @Result(column="id", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findCaseByCaseId")),
	    @Result(column="id", property="testRules",many=@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))
	})
	List<CaseEditVO> findCaseBySuite(Integer suiteId);

	@Delete("DELETE t1,t2,t3 FROM cases t1 LEFT JOIN case_param_value t2 ON t1.id=t2.case_id LEFT JOIN test_rule t3 ON t1.id=t3.case_id WHERE t1.id = #{caseId}")
	CaseEditVO delCases(Integer caseId);
}
