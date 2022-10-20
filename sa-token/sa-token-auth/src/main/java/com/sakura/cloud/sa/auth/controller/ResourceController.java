package com.sakura.cloud.sa.auth.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.sa.auth.entity.Resource;
import com.sakura.cloud.sa.auth.service.IResourceService;
import com.sakura.common.db.mp.CommonPage;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@RestController
@RequestMapping("/auth/resource")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @ApiOperation("添加后台资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody Resource resource) {
        resourceService.create(resource);
        return CommonResult.success();
    }

    @ApiOperation("修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id,
                               @RequestBody Resource resource) {
        resourceService.update(id, resource);
        return CommonResult.success();
    }

    @ApiOperation("根据ID获取资源详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Resource> getItem(@PathVariable Long id) {
        Resource umsResource = resourceService.getById(id);
        return CommonResult.success(umsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        resourceService.delete(id);
        return CommonResult.success();
    }

    @ApiOperation("分页模糊查询后台资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Resource>> list(@RequestParam(required = false) Long categoryId,
                                                   @RequestParam(required = false) String nameKeyword,
                                                   @RequestParam(required = false) String urlKeyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<Resource> resourceList = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Resource>> listAll() {
        List<Resource> resourceList = resourceService.list();
        return CommonResult.success(resourceList);
    }
}

