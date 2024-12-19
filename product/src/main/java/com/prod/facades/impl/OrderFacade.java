package com.prod.facades.impl;

import com.prod.chains.Chain;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.chains.createOrder.*;
import com.prod.chains.getOrders.GetProductByOrderProduct;
import com.prod.chains.getOrders.GetUserDetail;
import com.prod.facades.DTO.BillDTO;
import com.prod.facades.DTO.CSPCartDTO;
import com.prod.facades.DTO.OrderDTO;
import com.prod.facades.DTO.OrderProductDTO;
import com.prod.facades.IOrderFacade;
import com.prod.models.ENUM.Order_Status;
import com.prod.models.orders.Bill;
import com.prod.models.orders.Order;
import com.prod.models.orders.Order_Product;
import com.prod.services.carts.*;
import com.prod.services.orders.IBillService;
import com.prod.services.orders.IOrderProductService;
import com.prod.services.orders.IOrderService;
import com.prod.services.products.IImageService;
import com.prod.services.products.IProductService;
import com.prod.utils.ConvertListPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class OrderFacade implements IOrderFacade {
    private static final String ECOMMERCE_EMAIL = "montoan01102002@gmail.com";
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderProductService orderProductService;
    @Autowired
    private ISmallQuantityService smallQuantityService;
    @Autowired
    private ICartProductService cartProductService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IColorSizeProductService cspService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IBillService billService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ICartService cartService;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        Order order = orderService.createOrder(
                Order.builder()
                        .user_id(orderDTO.getUser_id())
                        .create_at(LocalDateTime.now())
                        .update_at(LocalDateTime.now())
                        .date(LocalDateTime.now())
                        .build()
        );
        //create order product
        for (OrderProductDTO dto : orderDTO.getProducts()) {
            ChainDataDTO<CSPCartDTO> data = ChainDataDTO.<CSPCartDTO>builder()
                    .value(CSPCartDTO.builder()
                            .quantity(dto.getQuantity())
                            .csp_id(dto.getCsp_id())
                            .order_id(order.getId())
                            .build())
                    .phone(orderDTO.getPhone())
                    .address(orderDTO.getAddress_name())
                    .userName(orderDTO.getUser_name())
                    .userEmail(orderDTO.getUser_email())
                    .userId(orderDTO.getUser_id())
                    .build();
            Chain<CSPCartDTO> chain = new Chain<CSPCartDTO>()
                    .add(new GetCartByUserId(cartService))
                    .add(new GetCSPById(cspService))
                    .add(GetColorSizeByCSP.builder()
                            .colorService(colorService)
                            .sizeService(sizeService)
                            .cspService(cspService)
                            .build())
                    .add(GetProductById.builder()
                            .imageService(imageService)
                            .productService(productService)
                            .build())
                    .add(new CheckIfCSPEnough(smallQuantityService))
                    .add(CreateOrderProductIfTrue.builder()
                            .orderProductService(orderProductService)
                            .cartProductService(cartProductService)
                            .build());
            chain.execute(data);
            if (!data.isSuccess()) {
                log.error(data.getMessage());
            } else {
                log.info("Dat hang thanh cong");
                long total = order.getTotal();
                order.setTotal(total + data.getValue().getQuantity() * data.getValue().getPrice());
            }
        }
        if (order.getTotal() > 0) {
            order.setUpdate_at(LocalDateTime.now());
            orderService.createOrder(order);
            billService.createBill(
                    Bill.builder()
                            .address(orderDTO.getAddress_name())
                            .phone(orderDTO.getPhone())
                            .user(orderDTO.getUser_name())
                            .order_id(order.getId())
                            .build()
            );
        } else {
            orderService.deleteOrderById(order.getId());
        }
    }

    @Override
    public BillDTO updateBill(BillDTO billDTO) {
        Bill nBill = billService.createBill(
                Bill.builder()
                        .id(billDTO.getId())
                        .update_at(LocalDateTime.now())
                        .address(billDTO.getAddress())
                        .phone(billDTO.getPhone())
                        .user(billDTO.getUser())
                        .build()
        );
        return BillDTO.builder()
                .id(nBill.getId())
                .order_id(nBill.getOrder_id())
                .bill_status(billDTO.getBill_status())
                .address(billDTO.getAddress())
                .phone(billDTO.getPhone())
                .user(billDTO.getUser())
                .build();
    }

    @Override
    public Page<OrderDTO> getOrdersByUserId(int userId, int page, int limit) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        List<OrderDTO> res = new ArrayList<>();
        for (Order order : orders) {
            Optional<Bill> bill = billService.getBillByOrderId(order.getId());
            if (bill.isPresent()) {
                ChainDataDTO<OrderDTO> chainDataDTO = getOrderDTO(
                        orderService.getOrderById(bill.get().getOrder_id()).get()
                );
                if (chainDataDTO.isSuccess()) {
                    res.add(chainDataDTO.getValue());
                } else {
                    log.error(chainDataDTO.getMessage());
                }
            }
        }
        ConvertListPage<OrderDTO> convertListPage = new ConvertListPage<>();
        return convertListPage.listToPage(res, page, limit);
    }

    @Override
    public Page<BillDTO> getBillsByUserId(int userId, int page, int limit) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        List<BillDTO> res = new ArrayList<>();
        for (Order order : orders) {
            Optional<Bill> bill = billService.getBillByOrderId(order.getId());
            bill.ifPresent(value -> res.add(
                    BillDTO.builder()
                            .order_id(order.getId())
                            .user(value.getUser())
                            .phone(value.getPhone())
                            .address(value.getAddress())
                            .id(value.getId())
                            .bill_status(value.getStatus().toString())
                            .build()
            ));
        }
        ConvertListPage<BillDTO> convertListPage = new ConvertListPage<>();
        return convertListPage.listToPage(res, page, limit);
    }

    @Override
    public OrderDTO changeStatus(int id) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            ChainDataDTO<OrderDTO> dto = getOrderDTO(orderService.updateStatus(order.get()));
            if (!dto.isSuccess()){
                log.error(dto.getMessage());
                return null;
            } else {
                return dto.getValue();
            }
        } else {
            log.error("Khong tim thay don hang");
            return null;
        }
    }

    @Override
    public Page<OrderDTO> getAllOrders(int page, int limit) {
        List<Order> orders = orderService.getPageOrders(page, limit).getContent();
        return getAllOrderDTO(orders, page, limit);
    }

    private Page<OrderDTO> getAllOrderDTO(List<Order> orders, int page, int limit) {
        ConvertListPage<OrderDTO> convertListPage = new ConvertListPage<>();
        List<OrderDTO> res = new ArrayList<>();
        for (Order order : orders) {
            //setup user
            //setup order
            ChainDataDTO<OrderDTO> dto = getOrderDTO(order);
            if (!dto.isSuccess()) {
                log.error(dto.getMessage());
            } else {
                List<OrderProductDTO> products = dto.getValue().getProducts();
                dto.getValue().setTotal(getTotal(products));
                res.add(dto.getValue());
            }
        }
        return convertListPage.listToPage(res, page, limit);
    }

    private ChainDataDTO<OrderDTO> getOrderDTO(Order order) {
        List<OrderProductDTO> orderProductDTOS = setupListOrderProductDTO(order.getId());
        ChainDataDTO<OrderDTO> dto = ChainDataDTO.<OrderDTO>builder()
                .value(OrderDTO.builder()
                        //order
                        .id(order.getId())
                        .status(order.getStatus())
                        .date(order.getDate())
                        //user
                        .user_id(order.getUser_id())
                        //products
                        .products(orderProductDTOS)
                        .build())
                //them email de thong bao
                .userEmail(ECOMMERCE_EMAIL)
                .build();
        Chain<OrderDTO> chain = new Chain<OrderDTO>()
                .add(GetProductByOrderProduct.builder()
                        //get order product
                        //get product
                        .productService(productService)
                        .colorService(colorService)
                        .sizeService(sizeService)
                        .cspService(cspService)
                        .imageService(imageService)
                        .build())
                .add(GetUserDetail.builder()
                        .billService(billService)
                        .build());
        chain.execute(dto);
        long total = 0;
        if (dto.isSuccess()){
            for (OrderProductDTO orderProductDTO : orderProductDTOS) {
                total += orderProductDTO.getQuantity() * orderProductDTO.getPrice();
            }
            dto.getValue().setTotal(total);
        }
        return dto;
    }

    @Override
    public Page<OrderDTO> getAllOrdersByStatus(String status, int page, int limit) {
        List<String> strings = Arrays.asList(
                Order_Status.CHO_VAN_CHUYEN.toString(),
                Order_Status.CHO_XAC_NHAN.toString(),
                Order_Status.DANG_GIAO.toString(),
                Order_Status.HANG_HOAN.toString(),
                Order_Status.GIAO_HANG_THANH_CONG.toString(),
                Order_Status.HUY_HANG.toString()
        );
        status = status.toUpperCase();
        if (strings.contains(status)) {
            List<Order> orders = orderService.getOrdersByStatus(status);
            return getAllOrderDTO(orders, page, limit);
        }
        return null;
    }

    private List<OrderProductDTO> setupListOrderProductDTO(int id) {
        List<Order_Product> orderProducts = orderProductService.getOrderProductsByOrderId(id);
        List<OrderProductDTO> orderProductDTOS = new ArrayList<>();
        orderProducts.forEach(orderProduct -> {
            orderProductDTOS.add(
                    OrderProductDTO.builder()
                            .csp_id(orderProduct.getCsp_id())
                            .quantity(orderProduct.getQuantity())
                            .build()
            );
        });
        return orderProductDTOS;
    }

    private long getTotal(List<OrderProductDTO> orderProducts) {
        long res = 0;
        for (OrderProductDTO op : orderProducts) {
            res += op.getPrice() * op.getQuantity();
        }
        return res;
    }
}
