package com.lemon.controller;


import java.util.List;

import com.lemon.mapper.CasesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CaseListVO;
import com.lemon.common.Result;
import com.lemon.pojo.Cases;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/cases")
@Api("测试案例模块")
@CrossOrigin
public class CasesController {

    @Autowired
    CasesService casesService;
    @Autowired
    CaseParamValueService caseParamValueService;
    @Resource
    CasesMapper casesMapper;

    /**
     * 添加测试案例到集合中
     *
     * @param cases
     * @param apiVO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加案例", httpMethod = "POST")
    public Result add(Cases cases, ApiVO apiVO) {
        return casesService.add(cases, apiVO);
    }

    /**
     * 通过项目id查询所有测试案例
     *
     * @param projectId
     * @return
     */
    @GetMapping("/findCasesByProjectId")
    @ApiOperation(value = "查询项目下所有测试案例", httpMethod = "GET")
    public Result findCasesByProjectId(Integer projectId) {
        List<CaseListVO> caseListVOS = casesService.showCaseUnderProject(projectId);
        return new Result("1", caseListVOS, "1");
    }

    /**
     * 通过集合id查询集合下测试案例
     *
     * @param suiteId
     * @return
     */
    @GetMapping("/findCasesBySuiteId")
    @ApiOperation(value = "查询集合下测试案例", httpMethod = "GET")
    public Result findCasesBySuiteId(Integer suiteId) {
        List<CaseListVO> caseListVOS = casesService.showCaseUnderSuiteId(suiteId);
        return new Result("1", caseListVOS, "1");
    }

    /**
     * 通过caseId查询案例信息
     *
     * @param caseId
     * @return
     */
    @GetMapping("/findCaseEditVO")
    @ApiOperation(value = "查询案例信息", httpMethod = "GET")
    public Result findCaseEditVO(String caseId) {
        CaseEditVO caseEditVO = casesService.findCaseEditVO(caseId);
        return new Result("1", caseEditVO, "1");
    }

    /**
     * 更新案例信息
     *
     * @param caseEditVO-案例封装对象
     * @return
     */
    @PostMapping("/updateCases")
    @ApiOperation(value = "更新案例信息", httpMethod = "POST")
    public Result updateCases(CaseEditVO caseEditVO) {
        casesService.updateCases(caseEditVO);
        return new Result("1", caseEditVO, "更新用例成功");
    }

    /**
     * 删除用例
     */

    @GetMapping("/delCases")
    @ApiOperation(value = "删除用例信息", httpMethod = "POST")
    public Result delCases(Integer caseId) {
        casesMapper.delCases(caseId);
        return new Result("1", "删除用例成功");
    }

    /**
     * 复制用例
     */

    @PostMapping("/copyCases")
    @ApiOperation(value = "复制用例信息", httpMethod = "POST")
    public Result copyCases(CaseEditVO caseEditVO) {
        casesService.save(caseEditVO);
        return new Result("1", caseEditVO, "复制用例成功");
    }


}
