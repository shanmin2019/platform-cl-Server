package com.lemon.mapper;

import com.lemon.pojo.TestReport;
import com.lemon.pojo.TestRule;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
public interface TestRuleMapper extends BaseMapper<TestRule> {


	/**
	 * 通过caseId断言参数
	 * @param caseId
	 * @return
	 */
	@Select("select * from test_rule where case_id = #{caseId}")
	public List<TestRule> findByCase(String caseId);
}
