/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service;

import com.securescm.shipment.model.ShipmentItemModel;
import com.securescm.shipment.payload.ShipmentItemRequest;
import com.securescm.shipment.util.PagedResponse;
import com.securescm.shipment.util.SingleItemResponse;

/**
 *
 * @author david
 */

public interface ShipmentItemService {
    public SingleItemResponse createUpdateShipmentItem(ShipmentItemRequest request);
    public SingleItemResponse deleteShipmentItem(Integer id);
    public PagedResponse<ShipmentItemModel> getAllShipmentItems(String direction, String orderBy, int page, int size);
    public SingleItemResponse findOneShipmentItem(Integer id);
}
