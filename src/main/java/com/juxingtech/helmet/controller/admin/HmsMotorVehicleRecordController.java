package com.juxingtech.helmet.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juxingtech.helmet.common.result.PageResult;
import com.juxingtech.helmet.common.result.Result;
import com.juxingtech.helmet.entity.HmsMotorVehicleRecord;
import com.juxingtech.helmet.entity.HmsRecognitionRecordStats;
import com.juxingtech.helmet.service.IHmsMotorVehicleRecordService;
import com.juxingtech.helmet.service.IHmsRecognitionRecordStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author haoxr
 * @date 2020-07-06
 **/
@Api
@RestController
@Slf4j
@RequestMapping("/motor-vehicle-records")
public class HmsMotorVehicleRecordController {

    @Resource
    private IHmsMotorVehicleRecordService iHmsMotorVehicleRecordService;

    @Autowired
    private IHmsRecognitionRecordStatsService iHmsRecognitionRecordStatsService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "helmetName", value = "头盔名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "serialNo", value = "头盔序列号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String helmetName,String serialNo,
                       String startDate, String endDate) {
        HmsMotorVehicleRecord hmsMotorVehicleRecord = new HmsMotorVehicleRecord();
        hmsMotorVehicleRecord.setHelmetName(helmetName);
        hmsMotorVehicleRecord.setSerialNo(serialNo);
        hmsMotorVehicleRecord.setStartDate(startDate);
        hmsMotorVehicleRecord.setEndDate(endDate);
        IPage<HmsMotorVehicleRecord> result = iHmsMotorVehicleRecordService.list(hmsMotorVehicleRecord, new Page<>(page, limit));
        return PageResult.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "获取车牌识别记录数", httpMethod = "GET")
    @GetMapping("/count")
    public Result count() {

        Map<String, Object> map = iHmsRecognitionRecordStatsService.getMap(
                new QueryWrapper<HmsRecognitionRecordStats>()
                .select("COALESCE(sum(motor_vehicle_record_count),0) as motorVehicleRecordCount")
        );
        Long motorVehicleRecordCount = Long.valueOf(map.get("motorVehicleRecordCount").toString());
        return Result.success(motorVehicleRecordCount);
    }
}
