/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.securescm.shipment.service.Impl;

import com.securescm.shipment.entities.Provider;
import com.securescm.shipment.entities.ProviderTransporter;
import com.securescm.shipment.entities.TransporterAddress;
import com.securescm.shipment.entities.Transporter;
import com.securescm.shipment.entities.TransporterStatus;
import com.securescm.shipment.entities.TransporterType;
import com.securescm.shipment.model.ItemName;
import com.securescm.shipment.model.TransporterModel;
import com.securescm.shipment.payload.TransporterRequest;
import com.securescm.shipment.model.Status;
import com.securescm.shipment.model.UserModel;
import com.securescm.shipment.payload.ProviderTransporterRequest;
import com.securescm.shipment.repos.AddressDao;
import com.securescm.shipment.repos.ProviderTransporterDao;
import com.securescm.shipment.util.ListItemResponse;
import com.securescm.shipment.util.Response;
import com.securescm.shipment.util.SingleItemResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.securescm.shipment.repos.TransporterDao;
import com.securescm.shipment.repos.TransporterTypeDao;
import com.securescm.shipment.service.TransporterService;
import com.securescm.shipment.util.PagedResponse;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author david
 */
@Service
public class TransporterServiceImpl implements TransporterService {

    @Autowired
    private TransporterDao transporterDao;

    @Autowired
    private AddressDao addressDao;
    
    @Autowired
    private TransporterTypeDao transporterTypeDao;
    
    @Autowired
    private ProviderTransporterDao providerTransporterDao;

    @Override
    public SingleItemResponse createUpdateTransporter(TransporterRequest request) {
        Status status;
        Transporter provider = new Transporter();
        SingleItemResponse singleItemResponse;
        TransporterAddress address = new TransporterAddress();
         if (request.getId() != null) {
             provider = transporterDao.getOneServiceProvider(request.getId());
         }
         
         if(request.getAddress().getId() != null){
           
            address = addressDao.getOneAddress(request.getAddress().getId());
         }
        //Create provider address
       
        address.setAddress(request.getAddress().getAddress());
        address.setCity(request.getAddress().getCity());
        address.setEmail(request.getAddress().getEmail());
        address.setCountry(request.getAddress().getCountry());
        address.setRegion(request.getAddress().getRegion());
        address.setFax(request.getAddress().getFax());
        address.setPhone(request.getAddress().getPhone());
        address.setPostalcode(request.getAddress().getPostalcode());
        address.setUrl(request.getAddress().getUrl());

        addressDao.save(address);

        //Create Provider
        
        provider.setLogo(request.getLogo());
        provider.setName(request.getName());
        provider.setStatus(new TransporterStatus(1));
        provider.setType(new TransporterType(request.getType()));
        provider.setAddress(address);
        transporterDao.save(provider);

        ItemName item = new ItemName(provider.getId(), provider.getName());
        singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(), item);
        return singleItemResponse;
    }

    @Override
    public SingleItemResponse deleteTransporter(Integer id) {
        Status status;
        SingleItemResponse singleItemResponse;
        Transporter provider = transporterDao.getOneServiceProvider(id);
        if (provider != null) {
            transporterDao.deleteById(id);
            singleItemResponse = new SingleItemResponse(Response.SUCCESS.status(),
                    null);
        } else {
            singleItemResponse = new SingleItemResponse(Response.TRANSPORTER_NOT_FOUND.status(),
                    null);
        }
        return singleItemResponse;
    }
    
    
    @Override
    public PagedResponse<TransporterModel> getAllTransporters(String direction, String orderBy, int page, int size) {
        
        Status status = null;
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, orderBy));
        }
        if (direction.equals("DESC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

  
        Page<Transporter> transporters = transporterDao.findAll(pageable);

        if (transporters.getNumberOfElements() == 0) {
            return new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), transporters.getNumber(),
                    transporters.getSize(), transporters.getTotalElements(), transporters.getTotalPages(), transporters.isLast());
        }

    
        List<TransporterModel> transporterResponses = transporters.map(transporter -> {
            return TransporterModel.map(transporter);

        }).getContent();

        return new PagedResponse<>(Response.SUCCESS.status(), transporterResponses, transporters.getNumber(),
                transporters.getSize(), transporters.getTotalElements(), transporters.getTotalPages(), transporters.isLast());
    }

    @Override
    public SingleItemResponse findOneTransporter(Integer id) {
        Transporter sp = transporterDao.getOneServiceProvider(id);
        return new SingleItemResponse(Response.SUCCESS.status(), TransporterModel.map(sp));
    }

   

    //////////////////////////TRANSPORTER TYPES///////////////////////////////////////
    @Override
    public ListItemResponse getAllTransporterTypes() {
       List<TransporterType>  type =  transporterTypeDao.findAll();
       return new ListItemResponse(Response.SUCCESS.status(), type);
    }
    
    /////////////////////////////PROVIDER TRANSPORTERS //////////////////////////

    @Override
    public SingleItemResponse createProviderTransporter(UserModel userModel, ProviderTransporterRequest request) {
        ProviderTransporter pt = new ProviderTransporter();
        if(request.getId() != null){
          pt =  providerTransporterDao.findProviderTransporter(request.getId());
        }
        pt.setProvider(new Provider(userModel.getStakeholder().getId()));
        pt.setTransporter(new Transporter(request.getTransporter()));      
        providerTransporterDao.save(pt);
      return  new  SingleItemResponse(Response.SUCCESS.status(), new ItemName(pt.getId(),null));
    }

    @Override
    public SingleItemResponse deleteProviderTransporter(Integer id) {
      ProviderTransporter pt = providerTransporterDao.findProviderTransporter(id);
        SingleItemResponse singleItemResponse;
        if(pt != null){
          providerTransporterDao.deleteById(id);
          singleItemResponse =  new SingleItemResponse(Response.SUCCESS.status(), new ItemName(pt.getId()));
        }else{
           singleItemResponse = new SingleItemResponse(Response.SHIPMENT_ITEM_NOT_FOUND.status(), null);
        }
       
        return singleItemResponse;
    }

    @Override
    public PagedResponse<ProviderTransporter> getAllProviderTransporter(String direction, String orderBy, int page, int size) {
        Status status = null;
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, orderBy));
        }
        if (direction.equals("DESC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

  
        Page<ProviderTransporter> pts = providerTransporterDao.findAll(pageable);

        if (pts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), pts.getNumber(),
                    pts.getSize(), pts.getTotalElements(), pts.getTotalPages(), pts.isLast());
        }

    
        List<ProviderTransporter> ptsResponses = pts.map(transporter -> {
            return transporter;

        }).getContent();

        return new PagedResponse<>(Response.SUCCESS.status(), ptsResponses, pts.getNumber(),
                pts.getSize(), pts.getTotalElements(), pts.getTotalPages(), pts.isLast());
    }

    @Override
    public SingleItemResponse findOneProviderTransporter(Integer id) {
         ProviderTransporter pt = providerTransporterDao.findProviderTransporter(id);
        return new SingleItemResponse(Response.SUCCESS.status(), pt);
    }
    //////////////////////GET ALL TRANSPORTERS FOR A GIVEN PROVIDER ///////////

    @Override
    public PagedResponse<ProviderTransporter> getAllTransportersForProvider(UserModel userModel, 
            String direction, String orderBy, int page, int size) {
         Status status = null;
        Sort sort = null;
        if (direction.equals("ASC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, orderBy));
        }
        if (direction.equals("DESC")) {
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, orderBy));
        }

        Pageable pageable = PageRequest.of(page, size, sort);

  
        Page<ProviderTransporter> pts = providerTransporterDao.findAllByProvider(new Provider(userModel.getStakeholder().getId()), pageable);

        if (pts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Response.SUCCESS.status(), Collections.emptyList(), pts.getNumber(),
                    pts.getSize(), pts.getTotalElements(), pts.getTotalPages(), pts.isLast());
        }

    
        List<ProviderTransporter> ptsResponses = pts.map(transporter -> {
            return transporter;

        }).getContent();

        return new PagedResponse<>(Response.SUCCESS.status(), ptsResponses, pts.getNumber(),
                pts.getSize(), pts.getTotalElements(), pts.getTotalPages(), pts.isLast());
    }

   
    
    
}
