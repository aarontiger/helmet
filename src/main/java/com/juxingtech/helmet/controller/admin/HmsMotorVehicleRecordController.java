package com.juxingtech.helmet.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juxingtech.helmet.common.result.PageResult;
import com.juxingtech.helmet.common.result.Result;
import com.juxingtech.helmet.entity.HmsMotorVehicleRecord;
import com.juxingtech.helmet.service.IHmsMotorVehicleRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "deviceId", value = "头盔序列号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(Integer page, Integer limit, String deviceId, String startDate, String endDate) {
        LambdaQueryWrapper<HmsMotorVehicleRecord> queryWrapper = new LambdaQueryWrapper<HmsMotorVehicleRecord>()
                .like(StrUtil.isNotBlank(deviceId), HmsMotorVehicleRecord::getDeviceId, deviceId)
                .apply(StrUtil.isNotBlank(startDate),
                        "date_format (alarm_time,'%Y-%m-%d') >= date_format('" + startDate + "','%Y-%m-%d')")
                .apply(StrUtil.isNotBlank(endDate),
                        "date_format (alarm_time,'%Y-%m-%d') <= date_format('" + endDate + "','%Y-%m-%d')")
                .orderByDesc(HmsMotorVehicleRecord::getCreateTime);

        if (page != null && limit != null) {
            Page<HmsMotorVehicleRecord> result = iHmsMotorVehicleRecordService.page(new Page<>(page, limit), queryWrapper);
            return PageResult.success(result.getRecords(), result.getTotal());
        } else if (limit != null) {
            queryWrapper.last("LIMIT " + limit);
        }
        List<HmsMotorVehicleRecord> list = iHmsMotorVehicleRecordService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "获取车牌识别记录数", httpMethod = "GET")
    @GetMapping("/count")
    public Result count() {
        int count = iHmsMotorVehicleRecordService.count();
        return Result.success(count);
    }
}
