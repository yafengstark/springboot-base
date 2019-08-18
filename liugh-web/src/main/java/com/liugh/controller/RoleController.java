package com.liugh.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liugh.base.Constant;
import com.liugh.base.PublicResultConstant;
import com.liugh.config.ResponseHelper;
import com.liugh.config.ResponseModel;
import com.liugh.entity.Role;
import com.liugh.model.RoleModel;
import com.liugh.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Api(description = "角色模块")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     *  角色列表
     */
    @ApiOperation(value = "获取所有角色", notes = "拥有超级管理员或管理员角色的用户可以访问这个接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "分页开始"
                    , required = false, dataType = "Integer",paramType="query"),
            @ApiImplicitParam(name = "pageSize", value = "页数"
                    , required = false, dataType = "Integer",paramType="query")
    })
    @GetMapping("/pageList")
    //拥有超级管理员或管理员角色的用户可以访问这个接口,换成角色控制权限,改变请看MyRealm.class
    //@RequiresRoles(value = {Constant.RoleType.SYS_ASMIN_ROLE,Constant.RoleType.ADMIN},logical =  Logical.OR)
    public ResponseModel<Page<Role>> getPageList(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                     @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        //根据姓名查分页
//        Page<Role> rolePage = roleService.selectPage(new Page<>(pageIndex, pageSize),
//                new EntityWrapper<Role>().where("role_name like {0}","%"+name+"%"));
        return ResponseHelper.buildResponseModel(roleService.selectPage(new Page<>(pageIndex, pageSize)));
    }

    /**
     *  获取所有角色
     */
    @ApiOperation(value = "获取所有角色", produces = "application/json")
    @GetMapping("/all")
    public  ResponseModel<List<Role>> getAllRole(){
        return ResponseHelper.buildResponseModel(roleService.selectList(new EntityWrapper<Role>()));
    }

    /**
     * 获取角色详细信息
     */
    @ApiOperation(value = "获取角色详细信息", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",
                    name = "roleCode",
                    dataType = "String",
                    value = "role-f7943542d87a4f028f446b71d9ede25d",
            required = true )
    })
    @GetMapping(value = "/{roleCode}")
    public ResponseModel getById(@PathVariable("roleCode") String roleCode)throws Exception{
        return ResponseHelper.buildResponseModel(roleService.selectByRoleCode(roleCode));
    }

    /**
     * 删除角色
     */
    @ApiOperation(value = "删除角色", notes = "拥有超级管理员或管理员角色的用户可以访问这个接口",
            produces = "application/json")
    @DeleteMapping(value = "/{roleCode}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",
                    name = "roleCode",
                    dataType = "String",
                    value = "role-f7943542d87a4f028f446b71d9ede25d",
                    required = true )
    })
    //拥有超级管理员或管理员角色的用户可以访问这个接口,换成角色控制权限,改变请看MyRealm.class
    //@RequiresRoles(value = {Constant.RoleType.SYS_ASMIN_ROLE,Constant.RoleType.ADMIN},logical =  Logical.OR)
    public ResponseModel deleteRole(@PathVariable("roleCode") String roleCode)throws Exception{
        roleService.deleteByRoleCode(roleCode);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }

    /**
     * 添加角色
     * @param roleModel
     * @return
     */
    @ApiOperation(value = "添加角色",notes = "", produces = "application/json")
    @PostMapping
    public ResponseModel addRole(RoleModel roleModel) throws Exception{
        return ResponseHelper.buildResponseModel(roleService.addRoleAndPermission(roleModel));
    }

    /**
     * 修改角色信息
     */
    @ApiOperation(value = "修改角色信息",notes = "", produces = "application/json")
    @PutMapping
    public ResponseModel updateRole(RoleModel roleModel) throws Exception{
        roleService.updateRoleInfo(roleModel);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }



}

