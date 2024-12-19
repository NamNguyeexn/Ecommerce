package com.prod.chains.createProduct;

import com.prod.chains.Chain;
import com.prod.chains.ChainHandler;
import com.prod.chains.DTO.ChainDataDTO;
import com.prod.facades.DTO.ColorSizeQuantityDTO;
import com.prod.facades.DTO.ProductDTO;
import com.prod.models.carts.Color;
import com.prod.models.carts.Color_Size_Product;
import com.prod.models.carts.Size;
import com.prod.models.carts.Small_Quantity;
import com.prod.models.details.Detail;
import com.prod.models.details.Quantity;
import com.prod.models.products.Product;
import com.prod.services.carts.IColorService;
import com.prod.services.carts.IColorSizeProductService;
import com.prod.services.carts.ISizeService;
import com.prod.services.carts.ISmallQuantityService;
import com.prod.services.details.IDetailService;
import com.prod.services.details.IQuantityService;
import com.prod.services.products.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@AllArgsConstructor
@Builder
public class CreateCSPs implements ChainHandler<ProductDTO> {
    private final IColorService colorService;
    private final ISizeService sizeService;
    private final ISmallQuantityService smallQuantityService;
    private final IColorSizeProductService colorSizeProductService;
    private final IDetailService detailService;
    private final IQuantityService quantityService;
    private final IProductService productService;

    @Override
    public Chain<ProductDTO> handle(ChainDataDTO<ProductDTO> chainDataDTO) {
        if (chainDataDTO.isSuccess()) {
            ProductDTO productDTO3 = chainDataDTO.getValue();
            List<ColorSizeQuantityDTO> colorSizeQuantityDTOS = new ArrayList<>();
            List<String> list = new ArrayList<>();
            initSig(productDTO3, list);
            for (ColorSizeQuantityDTO csquanDTO : chainDataDTO.getValue().getCsq()) {
                Color color = createColorIfDoesntExist(csquanDTO.getCode(), csquanDTO.getColor());
                Size size = createSizeIfDoesntExist(csquanDTO.getSize());
                list.add(color.getValue());
                list.add(size.getValue());
                Color_Size_Product csp = colorSizeProductService.createColorSizeProduct(
                        Color_Size_Product.builder()
                                .size_id(size.getId())
                                .color_id(color.getId())
                                .product_id(chainDataDTO.getValue().getProductId())
                                .build()
                );
                Small_Quantity s_quantity = createSmallQuantityIfDoesntExist(
                        csquanDTO.getQuantity(),
                        csp.getId(),
                        csp.getProduct_id());

                colorSizeQuantityDTOS.add(
                        ColorSizeQuantityDTO.builder()
                                .color(color.getValue())
                                .color_id(color.getId())
                                .size(size.getValue())
                                .size_id(size.getId())
                                .sold(s_quantity.getSold())
                                .build()
                );
            }
            updateSig(productDTO3, list);
            productDTO3.setCsq(colorSizeQuantityDTOS);
            chainDataDTO.setValue(productDTO3).setSuccess(true);
        }
        return new Chain<>(this);
    }

    private void updateSig(ProductDTO dto, List<String> list) {
        Set<String> set = new HashSet<>();
        for (String s : list) {
            set.add(s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase());
        }
        String signature = String.join(" ", set);
        Optional<Product> product = productService.getProductById(dto.getProductId());
        if (product.isPresent()) {
            product.get().setSignature(signature);
            product.get().setUpdate_at(LocalDateTime.now());
            productService.createProduct(product.get());
        }
    }

    private void initSig(ProductDTO dto, List<String> list) {
        list.addAll(List.of(dto.getSeason().split(" ")));
        list.addAll(List.of(dto.getCategoryName().split(" ")));
        list.addAll(dto.getLabel());
    }

    private Small_Quantity createSmallQuantityIfDoesntExist(int value,
                                                            int id,
                                                            int productId) {
        Optional<Small_Quantity> smallQuantity = smallQuantityService.getByCSProductId(id);
        if (smallQuantity.isEmpty()) {
            //khong the null, vi da tao truoc do
            Detail detail = detailService.getDetailByProductId(productId).get();
            Quantity quantity = quantityService.getQuantityById(detail.getQuantity_id()).get();
            return smallQuantityService.create(
                    Small_Quantity.builder()
                            .quantity_id(quantity.getId())
                            .quantity(value)
                            .color_size_product_id(id)
                            .build()
            );
        } else {
            int exist = smallQuantity.get().getQuantity();
            Small_Quantity smallQuantity1 = smallQuantity.get();
            smallQuantity1.setUpdate_at(LocalDateTime.now());
            smallQuantity1.setQuantity(value);
            smallQuantity1.setSold(Math.abs(exist - value));
            return smallQuantityService.create(smallQuantity1);
        }
    }


    private Color createColorIfDoesntExist(String code, String value) {
        Optional<Color> _color = colorService.getColorByCode(
                code
        );
        return _color.orElseGet(() -> colorService.createColor(
                Color.builder()
                        .code(code)
                        .value(value)
                        .build()
        ));
    }

    private Size createSizeIfDoesntExist(String value) {
        Optional<Size> _size = sizeService.getSizeByValue(value);
        return _size.orElseGet(() -> sizeService.createSize(
                Size.builder()
                        .value(value)
                        .build()
        ));
    }
}
