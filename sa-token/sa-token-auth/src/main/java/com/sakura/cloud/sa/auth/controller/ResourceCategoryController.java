package com.sakura.cloud.sa.auth.controller;


import com.sakura.cloud.sa.auth.entity.ResourceCategory;
import com.sakura.cloud.sa.auth.service.IResourceCategoryService;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@RestController
@RequestMapping("/resource/category")
public class ResourceCategoryController {

    @Autowired
    private IResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有后台资源分类")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public CommonResult<List<ResourceCategory>> listAll() {
        List<ResourceCategory> resourceList = resourceCategoryService.listAll();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("添加后台资源分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@RequestBody ResourceCategory resourceCategory) {
        resourceCategoryService.create(resourceCategory);
        return CommonResult.success();
    }

    @ApiOperation("修改后台资源分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @RequestBody ResourceCategory resourceCategory) {
        resourceCategory.setLqbId(id);
        resourceCategoryService.updateById(resourceCategory);
        return CommonResult.success();
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {
        resourceCategoryService.removeById(id);
        return CommonResult.success();
    }
}

