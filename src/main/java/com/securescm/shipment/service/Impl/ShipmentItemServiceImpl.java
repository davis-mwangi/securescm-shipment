/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.OrderItem;
import com.securescm.shipment.entities.Shipment;
import com.securescm.shipment.entities.ShipmentItem;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.ShipmentItemModel;
import com.securescm.shipment.model.Status;
import com.securescm.shipment.payload.ShipmentItemRequest;
import com.securescm.shipment.repos.OrderItemDao;
import com.securescm.shipment.repos.ShipmentItemDao;
import com.securescm.shipment.service.ShipmentItemService;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
@Service
public class ShipmentItemServiceImpl implements ShipmentItemService{

    @Autowired
    private ShipmentItemDao shipmentItemDao;
    
    @Autowired
    private OrderItemDao orderItemDao;
    
    @Override
    public SingleItemResponse createUpdateShipmentItem(ShipmentItemRequest request) {
         ShipmentItem item=  new ShipmentItem();
         if(request.getId() !=  null){
            item =  shipmentItemDao.getOneShipmentItem(request.getId());
         }
         item.setOrderItem(new OrderItem(request.getOrderItem()));
         
         OrderItem orderItem =  orderItemDao.getOne(request.getOrderItem());
         
         item.setProduct(orderItem.getProduct());
         item.setShipment(new Shipment(request.getShipment()));
         item.setQuantity(orderItem.getQuantity());
         
         shipmentItemDao.save(item);
         
         return new SingleItemResponse(Response.SUCCESS.status(), new ItemName(item.getId()));
        
    }

    @Override
    public SingleItemResponse deleteShipmentItem(Integer id) {
        ShipmentItem item =  shipmentItemDao.getOneShipmentItem(id);
        SingleItemResponse singleItemResponse;
        if(item != null){
          shipmentItemDao.deleteById(id);
          singleItemResponse =  new SingleItemResponse(Response.SUCCESS.status(), new ItemName(item.getId()));
        }else{
           singleItemResponse = new SingleItemResponse(Response.SHIPMENT_ITEM_NOT_FOUND.status(), null);
        }
       
        return singleItemResponse;
    }

    @Override
    public PagedResponse<ShipmentItemModel> getAllShipmentItems(String direction, String orderBy, int page, int size) {
        Status status = null;
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, orderBy));
        }
        if (direction.equals("DESC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

       
        Page<ShipmentItem> shipmentItems = shipmentItemDao.findAll(pageable);

        if (shipmentItems.getNumberOfElements() == 0) {
            return new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), shipmentItems.getNumber(),
                    shipmentItems.getSize(), shipmentItems.getTotalElements(), shipmentItems.getTotalPages(), shipmentItems.isLast());
        }

     
        List<ShipmentItemModel> shipmentResponses = shipmentItems.map(shipment -> {
            return ShipmentItemModel.map(shipment);

        }).getContent();

        return new PagedResponse<>(Response.SUCCESS.status(), shipmentResponses, shipmentItems.getNumber(),
                shipmentItems.getSize(), shipmentItems.getTotalElements(), shipmentItems.getTotalPages(), shipmentItems.isLast());
    }

    @Override
    public SingleItemResponse findOneShipmentItem(Integer id) {
        ShipmentItem item =  shipmentItemDao.getOneShipmentItem(id);
        
      return new SingleItemResponse(Response.SUCCESS.status(), ShipmentItemModel.map(item) );
    }

 
    
}
