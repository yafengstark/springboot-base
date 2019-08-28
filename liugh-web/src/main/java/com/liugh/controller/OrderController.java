package com.liugh.controller;

import com.liugh.annotation.Pass;
import com.liugh.config.ResponseHelper;
import com.liugh.config.ResponseModel;
import com.liugh.entity.Order;
import com.liugh.enums.OrderAction;
import com.liugh.enums.OrderType;
import com.liugh.model.OrderModel;
import com.liugh.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liugh
 * @since 2018-10-17
 */
@Api(description = "订单模块")
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 测试工作流
     * 订单发货(动作),待发货-->已发货(状态)
     * @param orderType
     * @param orderModel
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "发货")
    @PutMapping(value = "/deliver/{orderType}")
    public ResponseModel<Order> updateDeliver(@PathVariable String orderType, @RequestBody OrderModel orderModel)
            throws Exception {
        Order orderDef = orderService.handleOrder(OrderAction.deliver, OrderType.getInstance(orderType), orderModel);
        return ResponseHelper.buildResponseModel(orderDef);
    }


}
